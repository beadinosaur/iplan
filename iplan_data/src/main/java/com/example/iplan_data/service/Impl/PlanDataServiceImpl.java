package com.example.iplan_data.service.Impl;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.iplan_data.bean.request.AddDailyPlanRequest;
import com.example.iplan_data.bean.request.SelectPlanDataByEmailRequest;
import com.example.iplan_data.contentSimilarity.tokenizer.Tokenizer;
import com.example.iplan_data.contentSimilarity.tokenizer.Word;
import com.example.iplan_data.entity.PlanData;
import com.example.iplan_data.entity.TitleFrequency;
import com.example.iplan_data.entity.vo.HotWordsVo;
import com.example.iplan_data.entity.vo.HotWordsPlanDataVo;
import com.example.iplan_data.entity.vo.TimeAxisPlanDataVo;
import com.example.iplan_data.mapper.PlanDataMapper;
import com.example.iplan_data.mapper.TitleFrequencyMapper;
import com.example.iplan_data.service.IPlanDataService;
import com.example.iplan_data.util.CheckUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * Schedule data table service implementation class
 * </p>
 *
 * @author xieguangwei/huangdeyu
 * @since 2023-04-16
 */
@Slf4j(topic = "planDataLogger")
@Service
public class PlanDataServiceImpl extends ServiceImpl<PlanDataMapper, PlanData> implements IPlanDataService {

    @Resource
    private PlanDataMapper planDataMapper;

    @Resource
    private TitleFrequencyMapper titleFrequencyMapper;

    /**
     * Weekly inquiry
     *
     * @param username
     * @param pageIndex
     * @return
     */
    @Override
    public List<TimeAxisPlanDataVo> selectByTimeRange(String username, int pageIndex) {
        log.info("planData selectByTimeRange start! userName {},pageIndex {}", username, pageIndex);
        // The collection of dates returned
        List<String> days = new ArrayList<String>();
        //Get the current time
        Date now = DateUtil.date();
        //Current week corresponding to the current time (pageIndex=0) and offset (pageIndex=-1: last week/pageIndex=1: next week)
        Date d = DateUtil.offsetWeek(now, pageIndex);
        Date startTime = DateUtil.beginOfWeek(d);
        //The end of the week, with Sunday as the last day of the week
        Date endTime = DateUtil.endOfWeek(d, true);
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startTime);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(endTime);
        //Get the 7 days of the current week and put it in the Days list
        while (tempStart.before(tempEnd)) {
            //Starting from the first day of the current week, the time is less than the (end) time of the last day of the current week (after converting the format) and put into the days collection
            days.add(com.example.iplan_data.util.DateUtil.format(tempStart.getTime()));
            //date+1
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        List<TimeAxisPlanDataVo> timeAxisPlanDataVos = new ArrayList<>();
        for (String s : days) {
            TimeAxisPlanDataVo timeAxisPlanData = new TimeAxisPlanDataVo();
            List<PlanData> list = planDataMapper.selectList(new QueryWrapper<PlanData>().lambda()
                    //User name condition
                    .eq(!StringUtils.isEmpty(username), PlanData::getUserName, username)
                    //Start time condition
                    .apply("date_format (start_Time,'%Y-%m-%d') = date_format('" + s + "','%Y-%m-%d')")
                    //Sorted in ascending order by start time
                    .orderByAsc(PlanData::getStartTime)
            );
            //Return day
            timeAxisPlanData.setTime(s);
            //Returns data of a specific day
            timeAxisPlanData.setData(list);
            //Encapsulate the date with the corresponding data (data within 7 days at the end of the loop)
            timeAxisPlanDataVos.add(timeAxisPlanData);
        }
        log.info("planData selectByTimeRange startTime {},endTime {}", startTime, endTime);
        log.info("planData selectByTimeRange end! list.size {}", timeAxisPlanDataVos.size());
        return timeAxisPlanDataVos;
    }

    /**
     * Monthly enquiry
     *
     * @param username
     * @return
     */
    @Override
    public List<PlanData> selectByMonthRange(String username) {
        log.info("planData selectByMonthRange start! userName {}", username);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        String first = format.format(c.getTime());
        System.out.println("===============本月first day:" + first);

        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        System.out.println("===============本月last day:" + last);
        log.info("planData selectByMonthRange first day {} last day {}", first, last);
        List<PlanData> list = planDataMapper.selectList(new QueryWrapper<PlanData>().lambda()
                .eq(!StringUtils.isEmpty(username), PlanData::getUserName, username)
                .ne(PlanData::getSource, "Message")
                .apply("date_format (start_Time,'%Y-%m-%d') >= date_format('" + first + "','%Y-%m-%d')")
                .apply("date_format (end_Time,'%Y-%m-%d') <= date_format('" + last + "','%Y-%m-%d')")
                .orderByAsc(PlanData::getStartTime)
        );
        log.info("planData selectByMonthRange end! list.size {}", list.size());
        return list;
    }

    /**
     * Add, modify (calendar, hot word list)
     *
     * @param request
     * @return
     */
    @Override
    public boolean saveOrUpdate(AddDailyPlanRequest request) {
        log.info("planData saveOrUpdate start! {}", request);
        int flag = 0;
        PlanData planData = new PlanData();
        log.info("planData saveOrUpdate planId {}", request.getPlanId());
        if (CheckUtil.isEmpty(request.getPlanId())) {
            BeanUtils.copyProperties(request, planData);
            //The passed time is in the non-complete format yyyy-MM-dd HH:mm,
            // which is converted to the complete format yyyy-MM-dd HH:mm:ss
            planData.setEndTime(com.example.iplan_data.util.DateUtil.fullParse(request.getEndTime()));
            planData.setStartTime(com.example.iplan_data.util.DateUtil.fullParse(request.getStartTime()));
            planData.setSource("0");
            planData.setCreateTime(new Timestamp(System.currentTimeMillis()));
            flag = planDataMapper.insert(planData);
            log.info("planDataMapper.insert flag {}", flag);
        } else {
            PlanData p = planDataMapper.selectById(request.getPlanId());
            log.info("planDataMapper.selectById PlanData {}", p);
            if (!CheckUtil.isEmpty(p)) {
                BeanUtils.copyProperties(request, p);
                p.setEndTime(com.example.iplan_data.util.DateUtil.fullParse(request.getEndTime()));
                p.setStartTime(com.example.iplan_data.util.DateUtil.fullParse(request.getStartTime()));
                p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                flag = planDataMapper.updateById(p);
                log.info("planDataMapper.updateById flag {}", flag);
            }
        }
        //Word segmentation of the incoming topic
        List<Word> seg = Tokenizer.segment(request.getTitle());
        log.info("Tokenizer.segment {} ", seg);
        for (Word w : seg) {
            //Select n, vn, nx several parts of speech words ready to be stored
            if ("n".equals(w.getPos()) || "vn".equals(w.getPos()) || "nx".equals(w.getPos())) {
                //Check the database according to the divided words
                TitleFrequency titleFrequency = titleFrequencyMapper.selectOne(new QueryWrapper<TitleFrequency>().lambda()
                        .eq(!StringUtils.isEmpty(w.getName()), TitleFrequency::getWords, w.getName())
                );
                log.info("titleFrequencyMapper.selectOne {} ", titleFrequency);
                //If the current participle is empty in the table, it is inserted directly. The word frequency is 1
                if (CheckUtil.isEmpty(titleFrequency)) {
                    TitleFrequency t = new TitleFrequency();
                    t.setWords(w.getName());
                    t.setFrequency(1);
                    int flag2 = titleFrequencyMapper.insert(t);
                    log.info("titleFrequencyMapper.insert {} ", flag2);
                    //Otherwise (it already exists), it is updated by id, word frequency +1
                } else {
                    titleFrequency.setFrequency(titleFrequency.getFrequency() + 1);
                    int flag3 = titleFrequencyMapper.updateById(titleFrequency);
                    log.info("titleFrequencyMapper.updateById {} ", flag3);
                }
            }
        }
        log.info("planData saveOrUpdate end! flag {}", flag > 0);
        return flag > 0;
    }

    /**
     * Query by hot word
     *
     * @param words
     * @return
     */
    @Override
    public List<HotWordsPlanDataVo> selectPlanDataByHotWords(String words) {
        log.info("planData selectPlanDataByHotWords start! words {}", words);
        List<HotWordsPlanDataVo> list = new ArrayList<>();
        //Fuzzy query username based on hot word comparison topics. The number of hot words (topics) is grouped by user name and sorted in descending order by occurrence times
        List<HotWordsVo> wordsList = planDataMapper.selectHotWordsByGroupByUserName(words);
        log.info("planData planDataMapper.selectHotWordsByGroupByUserName wordsList {}", wordsList);
        //If no data is found according to the hot word, return directly
        if (CheckUtil.isEmpty(wordsList)) {
            return list;
        }
        //Query schedule by hot word and userName
        for (HotWordsVo h : wordsList) {
            HotWordsPlanDataVo hotWordsPlanData = new HotWordsPlanDataVo();
            //Gets the username stored in wordsList
            hotWordsPlanData.setUserName(h.getUserName());
            //Gets the number of users stored in wordsList
            hotWordsPlanData.setSum(h.getTitleCount());
            //According to the username in wordsList and the calendar corresponding to the hot word fuzzy query topic, and sorted by the start time in descending order
            List<PlanData> planData = planDataMapper.selectList(new QueryWrapper<PlanData>().lambda()
                    .eq(PlanData::getUserName, h.getUserName())
                    .like(PlanData::getTitle, words)
                    .orderByDesc(PlanData::getStartTime)
            );
            hotWordsPlanData.setData(planData);
            list.add(hotWordsPlanData);
        }
        log.info("planData selectPlanDataByHotWords end! list {}", list);
        return list;
    }


    /**
     * Query by email
     *
     * @param
     * @return
     */
    @Override
    public List<PlanData> selectPlanDataByEmail(SelectPlanDataByEmailRequest request){
        List<PlanData> planData = planDataMapper.selectList(new QueryWrapper<PlanData>().lambda()
                .eq(PlanData::getUserName, request.getUserName())
                .like(PlanData::getSender, request.getEmail())
                .orderByDesc(PlanData::getStartTime));
        return planData;
    }
}
