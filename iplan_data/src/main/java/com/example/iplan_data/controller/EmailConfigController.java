package com.example.iplan_data.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.iplan_data.bean.request.AddEmailConfigRequest;
import com.example.iplan_data.bean.request.SelectConferenceDataByToEmailRequest;
import com.example.iplan_data.bean.response.AddEmailConfigResponse;
import com.example.iplan_data.bean.request.SelectEmailConfigRequest;
import com.example.iplan_data.bean.response.SelectEmailConfigResponse;
import com.example.iplan_data.bean.response.SelectFromEmailResponse;
import com.example.iplan_data.constant.ErrorCode;
import com.example.iplan_data.entity.ConferenceData;
import com.example.iplan_data.entity.EmailConfig;
import com.example.iplan_data.entity.vo.EmailConfigVo;
import com.example.iplan_data.service.IConferenceDataService;
import com.example.iplan_data.service.IEmailConfigService;
import com.example.iplan_data.util.CheckUtil;
import com.example.iplan_data.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Front-end controller of mailbox configuration table
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-21
 */
@Slf4j(topic = "emailConfigLogger")
@RestController
public class EmailConfigController {

    @Resource
    private IEmailConfigService emailConfigService;

    @Resource
    private IConferenceDataService conferenceDataService;

    /**
     * bind email address
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Email Config", notes = "Email Config")
    @PostMapping(value = "/emailConfig/saveOrUpdate", produces = "application/json;charset=utf-8")
    public AddEmailConfigResponse insertOrUpdateMail(@Validated @RequestBody AddEmailConfigRequest request) {
        AddEmailConfigResponse response = new AddEmailConfigResponse();
        try {
            //Insert or update
            boolean flag = emailConfigService.saveOrUpdate(request);
            //If the insert or update is successful, perform a pull mail and pull meeting method
            if (flag) {
                List<EmailConfig> list = emailConfigService.list(new QueryWrapper<EmailConfig>().lambda()
                        .eq(!StringUtils.isEmpty(request.getUserName()), EmailConfig::getUserName, request.getUserName()));
                //Pull mail
                conferenceDataService.transferEmail(list);
                //Pull-out meeting
                conferenceDataService.transferConference(list);
                response.setErrorCode(ErrorCode.SUCCESS);
            } else {
                response.setErrorCode(ErrorCode.DB_ERROR);
            }
        } catch (Exception e) {
            log.error("", e);
            response.setErrorCode(ErrorCode.DB_ERROR);
        }
        return response;
    }

    /**
     * Query EmailConfig based on userName
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Query EmailConfig", notes = "Query EmailConfig")
    @PostMapping(value = "/emailConfig/getEmailConfig", produces = "application/json;charset=utf-8")
    public SelectEmailConfigResponse mailSelect(@Validated @RequestBody SelectEmailConfigRequest request) {
        SelectEmailConfigResponse response = new SelectEmailConfigResponse();
        try {
            //Create an object that returns to the front end to query email information based on the username
            EmailConfig emailConfig = emailConfigService.getOne(new QueryWrapper<EmailConfig>().lambda()
                    .eq(!StringUtils.isEmpty(request.getUserName()), EmailConfig::getUserName, request.getUserName())
            );
            //Creates an object that returns the front end
            EmailConfigVo emailConfigVo = new EmailConfigVo();
            if (!CheckUtil.isEmpty(emailConfig)) {
                BeanUtil.copyProperties(emailConfig, emailConfigVo);
                //Convert the time format into emailConfigVo
                emailConfigVo.setStartTime(DateUtil.format(emailConfig.getStartTime()));
            }
            response.setData(emailConfigVo);
            response.setErrorCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            log.error("", e);
            response.setErrorCode(ErrorCode.DB_ERROR);
        }
        return response;
    }

    @ApiOperation(value = "select FromEmail", notes = "select FromEmail")
    @PostMapping(value = "/emailConfig/getFromEmail", produces = "application/json;charset=utf-8")
    public SelectFromEmailResponse getFromEmail(@Validated @RequestBody SelectConferenceDataByToEmailRequest request) {
        SelectFromEmailResponse response = new SelectFromEmailResponse();
        try {
            List<ConferenceData> conferenceDataList = conferenceDataService.list(new QueryWrapper<ConferenceData>().lambda()
                    .like(!StringUtils.isEmpty(request.getEmail()), ConferenceData::getReceiver, request.getEmail())
            );
            List<String> list = new ArrayList<>();
            for (ConferenceData c : conferenceDataList) {
                if (!list.contains(c.getSender())) {
                    list.add(c.getSender());
                }
            }
            response.setData(list);
            response.setErrorCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            log.error("", e);
            response.setErrorCode(ErrorCode.DB_ERROR);
        }
        return response;
    }

}

