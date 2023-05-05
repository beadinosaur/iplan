package com.example.iplan_data.controller;

import com.example.iplan_data.bean.response.TransferConferenceDataResponse;
import com.example.iplan_data.constant.ErrorCode;
import com.example.iplan_data.service.IConferenceDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * Conference data sheet front controller
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-21
 */
@RestController
@Slf4j(topic = "getDataFromEmail")
public class ConferenceDataController {
    @Resource
    private IConferenceDataService iConferenceDataService;

    //Pull the mailbox data
    @Scheduled(cron = "0 30 15 * * * ")
    @PostMapping(value = "/conferenceData/transferEmail", produces = "application/json;charset=utf-8")
    public TransferConferenceDataResponse transferEmail() {
        TransferConferenceDataResponse response = new TransferConferenceDataResponse();
        try {
            iConferenceDataService.transferEmail(null);
            response.setErrorCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            log.error("", e);
            response.setErrorCode(ErrorCode.DB_ERROR);
        }
        return response;
    }

    //Pull meeting data
    @Scheduled(cron = "0 30 15 * * * ")
    @PostMapping(value = "/conferenceData/transferConference", produces = "application/json;charset=utf-8")
    public TransferConferenceDataResponse transferConference() {
        TransferConferenceDataResponse response = new TransferConferenceDataResponse();
        try {
            iConferenceDataService.transferConference(null);
            response.setErrorCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            log.error("", e);
            response.setErrorCode(ErrorCode.DB_ERROR);
        }
        return response;
    }
}
