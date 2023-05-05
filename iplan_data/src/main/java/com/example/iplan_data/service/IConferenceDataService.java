package com.example.iplan_data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.iplan_data.entity.ConferenceData;
import com.example.iplan_data.entity.EmailConfig;

import java.util.List;

/**
 * <p>
 * conference data
 * </p>
 *
 * @author xieguangwei
 * @since 2023-05-03
 */
public interface IConferenceDataService extends IService<ConferenceData> {

    void transferEmail(List<EmailConfig> list) throws Exception;

    void transferConference(List<EmailConfig> list) throws Exception;
}
