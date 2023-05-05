package com.example.iplan_data.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import cn.hutool.core.util.StrUtil;
import com.example.iplan_data.contentSimilarity.similarity.text.CosineSimilarity;
import com.example.iplan_data.contentSimilarity.similarity.text.TextSimilarity;
import com.example.iplan_data.contentSimilarity.tokenizer.Tokenizer;
import com.example.iplan_data.contentSimilarity.tokenizer.Word;
import com.example.iplan_data.entity.*;
import com.example.iplan_data.mapper.ConferenceDataMapper;
import com.example.iplan_data.mapper.EmailConfigMapper;
import com.example.iplan_data.mapper.PlanDataMapper;
import com.example.iplan_data.mapper.TitleFrequencyMapper;
import com.example.iplan_data.service.IConferenceDataService;
import com.example.iplan_data.util.*;
import lombok.extern.slf4j.Slf4j;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.SortDirection;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.Attendee;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.Mailbox;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * General mail/conference data table service implementation class
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-18
 */
@Service
@Slf4j(topic = "ConferenceDataLogger")
public class ConferenceDataServiceImpl extends ServiceImpl<ConferenceDataMapper, ConferenceData> implements IConferenceDataService {

    private final String HTTPS_PROTOCOLS = "https.protocols";
    private final String TLSV = "TLSv1,TLSv1.1,TLSv1.2,SSLv3";
    private final String EXCHANGE = "https://s.outlook.com/EWS/Exchange.asmx";
    private final String DOMAIN = "outlook.com";


    @Resource
    private ConferenceDataMapper conferenceDataMapper;
    @Resource
    private EmailConfigMapper emailConfigMapper;
    @Resource
    private PlanDataMapper planDataMapper;
    @Resource
    private TitleFrequencyMapper titleFrequencyMapper;

    /**
     * Synchronizing mail data
     *
     * @throws Exception
     */
    @Override
    public void transferEmail(List<EmailConfig> list) throws Exception {
        //Setting the TLS version
        System.setProperty(HTTPS_PROTOCOLS, TLSV);
        //Example Set the version of the exchange email management system
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
        //Example Query email configuration information
        List<EmailConfig> configList = new ArrayList<>();
        if (CheckUtil.isEmpty(list)) {
            configList = emailConfigMapper.selectList(new QueryWrapper<EmailConfig>());
        } else {
            configList = list;
        }
        log.info("emailConfigMapper.selectList {} ", configList);
        //Get the current time
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < configList.size(); i++) {
            EmailConfig emailConfig = configList.get(i);
            log.info("configList.get(i) {} ", emailConfig);
            //Bind personal account information
            ExchangeCredentials credentials = new WebCredentials(emailConfig.getEmail(), emailConfig.getPassword(), DOMAIN);
            log.info("WebCredentials {} ", credentials);
            service.setCredentials(credentials);
            service.setUrl(new URI(EXCHANGE));
            Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);

            //Set the start time for mailbox pulling
            long day = DateUtil.betweenDate(DateUtil.getNewDate(emailConfig.getStartTime(), emailConfig.getNewStartTime()));
            log.info("betweenDate day {} ", day);
            //Number of mail traversals
            ItemView itemView = new ItemView(inbox.getTotalCount());
            //To set filtering conditions for mail traversal, pull the mail with the inverse day from the current time
            SearchFilter searchFilter = new SearchFilter.IsGreaterThan(ItemSchema.DateTimeReceived, DateUtils.addDays(now, -new Long(day).intValue()));
            //Iterate through the message from late to early by receipt time
            itemView.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Descending);
            FindItemsResults<Item> findResults = service.findItems(inbox.getId(), searchFilter, itemView);
            ArrayList<Item> items = findResults.getItems();
            log.info("findResults.getItems {} ", items);

            //Query filter keywords (role tags/generic tags)
            String keyWords = SplitUtil.splitString(emailConfig.getKeyWordS(), emailConfig.getKeyWordT());
            //Merge two types of keywords
            String[] keyWordAll = TokenUtil.tokenString(keyWords);
            //Query filter mailbox
            String[] keyEmails = TokenUtil.tokenString(emailConfig.getKeyEmail());
            log.info("SplitUtil.splitString {},TokenUtil.tokenString {} {}", keyWords, keyWordAll, keyEmails);
            //Query the schedule topic corresponding to the current username
            List<PlanData> planDataList = planDataMapper.selectList(new QueryWrapper<PlanData>().eq("user_name", emailConfig.getUserName()));
            log.info("planDataMapper.selectList {} ", planDataList);
            for (int j = 0; j < items.size(); j++) {
                Item item = items.get(j);
                EmailMessage message = EmailMessage.bind(service, item.getId());
                log.info("EmailMessage.bind {} ", message);
                message.load();
                //Get email subject
                String subject = item.getSubject();
                //Get sender email
                String sender = message.getSender().toString();
                log.info("item.getSubject {} message.getSender {}", subject, sender);
                //Determine whether the email subject contains filter keywords
                boolean statusTitle = StrUtil.containsAny(subject, keyWordAll);
                log.info("StrUtil.containsAny {} ", statusTitle);
                //Check whether the mailbox is blacklisted
                boolean statusSender = StrUtil.equalsAny(sender, keyEmails);
                log.info("StrUtil.equalsAny {} ", statusSender);
                //If the subject contains filtering keywords or the sender is a blacklist email address, the system skips this step
                if (statusTitle || statusSender) {
                    continue;
                }
                //If the email is a meeting, skip it
                if (item.getXmlElementName().equals("MeetingRequest")) {
                    continue;
                }
                //Get the mail receiving time
                Date compareTime = item.getDateTimeReceived();
                log.info("item.getDateTimeReceived {} ", compareTime);
                //If the set time is later than (or later than) the mail receiving time, the system skips
                if (new Date(emailConfig.getStartTime().getTime()).compareTo(compareTime) >= 0) {
                    continue;
                }
                //Check the library according to the current mail id, and skip if the library is not empty (that is, it already exists)

                ConferenceData cd = conferenceDataMapper.selectOne(new QueryWrapper<ConferenceData>().lambda()
                        .eq(!StringUtils.isEmpty(item.getId().toString()), ConferenceData::getCalendarId, item.getId().toString())
                );
                log.info("conferenceDataMapper.selectOne {} ", cd);
                if (!CheckUtil.isEmpty(cd)) {
                    continue;
                }
                ConferenceData conferenceData = new ConferenceData();
                //1.Insert reference ID
                conferenceData.setCalendarId(item.getId().toString());
                //2.Insert sender
                conferenceData.setSender(sender);
                //3.Insert recipient
                conferenceData.setReceiver(emailConfig.getEmail());
                //4.Insert pick-up time
                conferenceData.setReceiveTime(item.getDateTimeReceived());
                //5.Insert topic
                conferenceData.setTitle(subject);
                //Get the text content of the body in an html document (mail content)
                String html_body = message.getBody().toString();
                String body = HtmlUtil.getContentFromHtml(html_body);
                log.info("HtmlUtil.getContentFromHtml {} ", body);
                //6.Insert message content
                conferenceData.setContent(body);
                //Insert start time (temporarily replaced by receipt time)
                conferenceData.setStartTime(item.getDateTimeReceived());
                //7.Insert creation time
                conferenceData.setCreateTime(time);
                //8.Insert message type
                conferenceData.setType(item.getXmlElementName());
                //9.Insert user name
                conferenceData.setUserName(emailConfig.getUserName());
                int flag = conferenceDataMapper.insert(conferenceData);
                log.info("conferenceDataMapper.insert {} ", flag > 0);

                //Topic similarity check
                if (similarity(subject, planDataList)) {
                    continue;
                }
                PlanData planData = new PlanData();
                BeanUtils.copyProperties(conferenceData, planData);
                planData.setSource("1");
                int flag2 = planDataMapper.insert(planData);
                log.info("planDataMapper.insert {} ", flag2 > 0);
                planDataList.add(planData);
                log.info("planDataList.add {}", planDataList);
                //1.The word segmentation list is obtained according to the title
                participle(conferenceData.getTitle());
                //The receiving time of the first mail (that is, the latest receiving time) is stored in the mailbox configuration table as the start time for retrieving the mail after the next timer is started
                if (j == 0) {
                    emailConfig.setNewStartTime(new Timestamp(item.getDateTimeReceived().getTime()));
                    emailConfigMapper.updateById(emailConfig);
                }
            }
        }
    }

    @Override

    //Synchronize meeting (calendar) data
    public void transferConference(List<EmailConfig> list) throws Exception {
        System.setProperty(HTTPS_PROTOCOLS, TLSV);
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
        //Example Query email configuration information
        List<EmailConfig> configList = new ArrayList<>();
        if (CheckUtil.isEmpty(list)) {
            configList = emailConfigMapper.selectList(new QueryWrapper<EmailConfig>());
        } else {
            configList = list;
        }
        log.info("emailConfigMapper.selectList {} ", configList);
        //Get the current time
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < configList.size(); i++) {
            EmailConfig emailConfig = configList.get(i);
            log.info("emailConfigMapper.selectList {} ", configList);
            //Query the schedule topic corresponding to the current username
            List<PlanData> planDataList = planDataMapper.selectList(new QueryWrapper<PlanData>()
                    .eq("user_name", emailConfig.getUserName())
            );
            log.info("planDataMapper.selectList {} ", planDataList);
            double score1pkx = 0;
            //Create a similarity analysis method object
            TextSimilarity similarity = new CosineSimilarity();
            //bind email address
            ExchangeCredentials credentials = new WebCredentials(emailConfig.getEmail(), emailConfig.getPassword(), DOMAIN);
            log.info("WebCredentials {} ", credentials);
            service.setCredentials(credentials);
            service.setUrl(new URI(EXCHANGE));
            service.setCredentials(credentials);
            service.setTraceEnabled(true);
            //Specifies that the fetch type is Calendar
            Folder inbox = Folder.bind(service, WellKnownFolderName.Calendar);
            //Folder (Client)
            String EmailBox = inbox.getDisplayName();
            log.info("inbox.getDisplayName {} ", EmailBox);
            //Set cut-off time
            Date end = DateUtils.addDays(now, +30);
            //If the specified period is empty, set the start time to January 1 of the current year
            if (CheckUtil.isEmpty(emailConfig.getStartTime())) {
                emailConfig.setStartTime(new Timestamp(DateUtil.getFirstDay().getTime()));
                log.info("emailConfig.getStartTime() {} ", DateUtil.getFirstDay().getTime());
            }
            CalendarView cView = new CalendarView(new Date(emailConfig.getStartTime().getTime()), end);
            //Specify the mailbox to view
            FolderId folderId = new FolderId(WellKnownFolderName.Calendar, new Mailbox(emailConfig.getEmail()));
            CalendarFolder calendar = CalendarFolder.bind(service, folderId);
            FindItemsResults<Appointment> findResults = calendar.findAppointments(cView);
            log.info("calendar.findAppointments {} ", findResults);
            try {
                findResults = service.findAppointments(folderId, cView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Query filter keywords (role tags/generic tags)
            String keyWords = SplitUtil.splitString(emailConfig.getKeyWordS(), emailConfig.getKeyWordT());
            //Merge two types of keywords
            String[] keyWordAll = TokenUtil.tokenString(keyWords);
            //Query filter mailbox
            String[] keyEmails = TokenUtil.tokenString(emailConfig.getKeyEmail());
            log.info("SplitUtil.splitString {},TokenUtil.tokenString {} {}", keyWords, keyWordAll, keyEmails);
            ArrayList<Appointment> appointmentItems = findResults == null ? null : findResults.getItems();
            for (Appointment ap : appointmentItems) {
                ap.load();
                //If the reference ID of the meeting already exists in the library, skip it
                ConferenceData cd = conferenceDataMapper.selectOne(new QueryWrapper<ConferenceData>().lambda()
                        .eq(!StringUtils.isEmpty(ap.getId().toString()), ConferenceData::getCalendarId, ap.getId().toString()));
                log.info("conferenceDataMapper.selectOne {} ", cd);
                if (!CheckUtil.isEmpty(cd)) {
                    continue;
                }
                //Get the topic of the meeting
                String subject = ap.getSubject();
                //Acquire the meeting organizer
                String sender = ap.getOrganizer().toString();
                log.info("ap.getSubject {} ap.getOrganizer {}", subject, sender);
                //todo Filter keyword sender
                //Check whether the filter keyword is contained todo
                boolean statusTitle = StrUtil.containsAny(subject, keyWordAll);
                log.info("StrUtil.containsAny {} ", statusTitle);
                //Check whether the mailbox is blacklisted todo
                boolean statusSender = StrUtil.equalsAny(sender, keyEmails);
                log.info("StrUtil.equalsAny {} ", statusSender);
                //If the email subject contains keywords or the sender is in the blacklist, the meeting is filtered out
                if (statusTitle || statusSender) {
                    continue;
                } else {
                    ConferenceData conferenceData = new ConferenceData();
                    //Insert reference ID
                    conferenceData.setCalendarId(ap.getId().toString());
                    //Insert meeting organizer (sender)
                    conferenceData.setSender(ap.getOrganizer().toString());
                    //Employees attending the meeting
                    List<Attendee> RequiredAttendees = ap.getRequiredAttendees().getItems();
                    List<Attendee> OptionalAttendees = ap.getOptionalAttendees().getItems();
                    String receiver = SplitUtil.splitUtil(RequiredAttendees, OptionalAttendees);
                    log.info("SplitUtil.splitUtil {} ", receiver);
                    //Insert meeting employee (participant)
                    conferenceData.setReceiver(receiver);
                    //Insert receive time
                    conferenceData.setReceiveTime(ap.getDateTimeReceived());
                    //Insert meeting topic
                    conferenceData.setTitle(ap.getSubject());
                    //Remove the html frame and get the text content in the body
                    String html_body = ap.getBody().toString();
                    String body = HtmlUtil.getContentFromHtml(html_body);
                    //Insert meeting content
                    conferenceData.setContent(body);
                    //Insert meeting position
                    conferenceData.setPosition(ap.getLocation());
                    //Insert the start time of the meeting
                    conferenceData.setStartTime(ap.getStart());
                    //Insert the end time of the meeting
                    conferenceData.setEndTime(ap.getEnd());
                    //Insert creation time
                    conferenceData.setCreateTime(time);
                    //Insertion type
                    conferenceData.setType(ap.getXmlElementName());
                    conferenceData.setUserName(emailConfig.getUserName());
                    int flag = conferenceDataMapper.insert(conferenceData);
                    log.info("conferenceDataMapper.insert {} ", flag > 0);
                    //Topic similarity check
                    if (similarity(subject, planDataList)) {
                        continue;
                    }
                    PlanData planData = new PlanData();
                    BeanUtils.copyProperties(conferenceData, planData);
                    planData.setSource("2");
                    int flag2 = planDataMapper.insert(planData);
                    log.info(" planDataMapper.insert {} ", flag2 > 0);
                    planDataList.add(planData);
                    //1.The word segmentation list is obtained according to the title
                    participle(conferenceData.getTitle());
                }
            }
        }
    }


    /**
     * similarity analysis
     *
     * @param title
     * @param planDataList
     * @return
     */
    public boolean similarity(String title, List<PlanData> planDataList) {
        TextSimilarity similarity = new CosineSimilarity();
        for (PlanData p : planDataList) {
            double score1pkx = similarity.getSimilarity(title, p.getTitle());
            if (score1pkx > 0.8) {
                return true;
            }
        }
        return false;
    }

    /**
     * Word segmentation statistics
     *
     * @param title
     */
    public void participle(String title) {
        int flag = 0;
        List<Word> seg = Tokenizer.segment(title);
        log.info("Tokenizer.segment {} ", seg);
        //2.Check the database according to the divided words
        for (Word w : seg) {
            if ("n".equals(w.getPos()) || "vn".equals(w.getPos()) || "nx".equals(w.getPos())) {
                TitleFrequency titleFrequency = titleFrequencyMapper.selectOne(new QueryWrapper<TitleFrequency>().lambda()
                        .eq(!StringUtils.isEmpty(w.getName()), TitleFrequency::getWords, w.getName())
                );
                log.info("titleFrequencyMapper.selectOne {} ", titleFrequency);
                if (CheckUtil.isEmpty(titleFrequency)) {
                    TitleFrequency t = new TitleFrequency();
                    t.setWords(w.getName());
                    t.setFrequency(1);
                    flag = titleFrequencyMapper.insert(t);
                    log.info("titleFrequencyMapper.insert {} ", flag);
                } else {
                    titleFrequency.setFrequency(titleFrequency.getFrequency() + 1);
                    flag = titleFrequencyMapper.updateById(titleFrequency);
                    log.info("titleFrequencyMapper.updateById {} ", flag);
                }
            }
        }
    }


}
