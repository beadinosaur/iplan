package com.example.iplan_data.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.iplan_data.BootstrapperTest;
import com.example.iplan_data.bean.request.AddEmailConfigRequest;
import com.example.iplan_data.bean.request.SelectConferenceDataByToEmailRequest;
import com.example.iplan_data.bean.request.SelectEmailConfigRequest;
import com.example.iplan_data.bean.response.AddEmailConfigResponse;
import com.example.iplan_data.bean.response.SelectEmailConfigResponse;
import com.example.iplan_data.bean.response.SelectFromEmailResponse;
import com.example.iplan_data.constant.ErrorCode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;

public class EmailConfigControllerTest extends BootstrapperTest {

    @Autowired
    protected ObjectMapper objectMapper;

    private static final String insertOrUpdateMail = "/emailConfig/saveOrUpdate";
    private static final String mailSelect = "/emailConfig/getEmailConfig";
    private static final String getFromEmail = "/emailConfig/getFromEmail";

    @Value("classpath:data/querySaveOrUpdate.json")
    private Resource querySaveOrUpdate;
    @Value("classpath:data/queryGetEmailConfig.json")
    private Resource queryGetEmailConfig;
    @Value("classpath:data/queryGetFromEmail.json")
    private Resource queryGetFromEmail;

    @Test
    public void insertOrUpdateMail() throws Exception{
        AddEmailConfigRequest request = objectMapper.readValue(querySaveOrUpdate.getInputStream(),AddEmailConfigRequest.class);
        AddEmailConfigResponse response = restPost(insertOrUpdateMail,request,AddEmailConfigResponse.class);
        assertThat(response.getCode(),equalTo(ErrorCode.SUCCESS.getCode()));
    }

    @Test
    public void mailSelect() throws Exception{
        SelectEmailConfigRequest request = objectMapper.readValue(queryGetEmailConfig.getInputStream(),SelectEmailConfigRequest.class);
        SelectEmailConfigResponse response = restPost(mailSelect,request,SelectEmailConfigResponse.class);
        assertThat(response.getCode(),equalTo(ErrorCode.SUCCESS.getCode()));
    }

    @Test
    public void getFromEmail() throws Exception{
        SelectConferenceDataByToEmailRequest request = objectMapper.readValue(queryGetFromEmail.getInputStream(),SelectConferenceDataByToEmailRequest.class);
        SelectFromEmailResponse response = restPost(getFromEmail,request,SelectFromEmailResponse.class);
        assertThat(response.getCode(),equalTo(ErrorCode.SUCCESS.getCode()));
    }



}
