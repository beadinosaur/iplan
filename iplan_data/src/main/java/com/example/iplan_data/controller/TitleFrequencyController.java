package com.example.iplan_data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.iplan_data.bean.response.SelectHotWordsResponse;
import com.example.iplan_data.entity.TitleFrequency;
import com.example.iplan_data.service.ITitleFrequencyService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * Schedule subject noun word frequency statistics table front controller
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-21
 */
@Slf4j(topic = "titleFrequency")
@RestController
public class TitleFrequencyController {

    @Resource
    private ITitleFrequencyService titleFrequencyService;

    /**
     * Get the top 10 words
     *
     * @return
     */
    @ApiOperation(value = "Get the top 10 words", notes = "Get the top 10 words")
    @PostMapping(value = "/dailyPlanTitle/getHotWord", produces = "application/json;charset=utf-8")
    public SelectHotWordsResponse listHotWord() {
        SelectHotWordsResponse response = new SelectHotWordsResponse();
        List<TitleFrequency> list = titleFrequencyService.list(new QueryWrapper<TitleFrequency>().lambda()
                .orderByDesc(TitleFrequency::getFrequency)
                .last("limit 10")
        );
        response.setData(list);
        return response;
    }


}

