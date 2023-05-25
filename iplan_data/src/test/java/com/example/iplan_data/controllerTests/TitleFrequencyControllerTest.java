package com.example.iplan_data.controllerTests;

import com.example.iplan_data.BootstrapperTest;
import com.example.iplan_data.bean.response.SelectHotWordsResponse;
import com.example.iplan_data.constant.ErrorCode;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TitleFrequencyControllerTest extends BootstrapperTest {


    private static final String listHotWord = "/dailyPlanTitle/getHotWord";

    @Test
    public void listHotWord() throws Exception{
        SelectHotWordsResponse response = restPost(listHotWord,"",SelectHotWordsResponse.class);
        assertThat(response.getCode(),equalTo(ErrorCode.SUCCESS.getCode()));
    }
}
