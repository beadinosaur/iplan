package com.example.iplan_data.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.iplan_data.bean.request.AddEmailConfigRequest;
import com.example.iplan_data.entity.EmailConfig;
import com.example.iplan_data.mapper.EmailConfigMapper;
import com.example.iplan_data.service.IEmailConfigService;
import com.example.iplan_data.util.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * <p>
 * Mailbox configuration table service implementation class
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-21
 */
@Service
@Slf4j(topic = "emailConfigLogger")
public class EmailConfigServiceImpl extends ServiceImpl<EmailConfigMapper, EmailConfig> implements IEmailConfigService {

    @Resource
    private EmailConfigMapper emailConfigMapper;

    @Override
    public boolean saveOrUpdate(AddEmailConfigRequest request) {
        log.info("emailConfig saveOrUpdate start! {}", request);
        int flag = 0;
        //Determine whether the bond is bound based on userName
        String username = request.getUserName();
        //Get the current time
        Timestamp time = new Timestamp(System.currentTimeMillis());
        //Check database by username (null first)
        EmailConfig emailConfig1 = emailConfigMapper.selectOne(new QueryWrapper<EmailConfig>()
                .lambda()
                .eq(!StringUtils.isEmpty(username), EmailConfig::getUserName, username)
        );
        log.info("emailConfigMapper.selectOne {} , request.getUserName {} ", emailConfig1, username);
        //If the lookup library is empty according to the username passed in, insert it directly
        if (CheckUtil.isEmpty(emailConfig1)) {
            EmailConfig emailConfig = new EmailConfig();
            BeanUtils.copyProperties(request, emailConfig);
            //The start time of email synchronization is added
            String startTime = request.getStartTime();
            Timestamp startTimeTimestamp = null;
            if (!CheckUtil.isEmpty(startTime)) {
                startTimeTimestamp = Timestamp.valueOf(request.getStartTime());
            }
            emailConfig.setStartTime(startTimeTimestamp);
            //creation time
            emailConfig.setCreateTime(time);
            //turnover time
            emailConfig.setUpdateTime(time);
            //Password encryption mode
            emailConfig.setEncrypt("1");
            flag = emailConfigMapper.insert(emailConfig);
            log.info("emailConfigMapper.insert  {} ", flag);
        } else {
            //The start time of email synchronization is added
            String startTime = request.getStartTime();
            Timestamp startTimeTimestamp = null;
            if (!CheckUtil.isEmpty(startTime)) {
                startTimeTimestamp = Timestamp.valueOf(request.getStartTime());
            }
            emailConfig1.setStartTime(startTimeTimestamp);
            //Add filter keyword
            BeanUtils.copyProperties(request, emailConfig1);
            //turnover time
            emailConfig1.setUpdateTime(time);
            //Password encryption mode
            emailConfig1.setEncrypt("1");
            //If the start time passed in is different than before, the new time is inserted into the NewStartTime field
            if (emailConfig1.getStartTime().getTime() != startTimeTimestamp.getTime()) {
                emailConfig1.setNewStartTime(startTimeTimestamp);
            }
            flag = emailConfigMapper.updateById(emailConfig1);
            log.info("emailConfigMapper.updateById  {} ", flag);
        }
        log.info("emailConfig saveOrUpdate end! {}", flag > 0);
        return flag > 0;
    }
}

