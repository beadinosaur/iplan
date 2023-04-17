package com.example.iplan_data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.iplan_data.bean.request.AddDailyPlanRequest;
import com.example.iplan_data.bean.request.DeleteDailyPlanRequest;
import com.example.iplan_data.bean.request.SelectPlanDataByTimeRequest;
import com.example.iplan_data.bean.response.AddDailyPlanResponse;
import com.example.iplan_data.bean.response.DeleteDailyPlanReponse;
import com.example.iplan_data.bean.response.SelectPlanDataResponse;
import com.example.iplan_data.constant.ErrorCode;
import com.example.iplan_data.entity.PlanData;
import com.example.iplan_data.service.IPlanDataService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * plan data
 * </p>
 *
 * @author xieguangwei/huangdeyu
 * @since 2023-04-14
 */
@Slf4j(topic = "planDataLogger")
@RestController
public class PlanDataController {

    @Resource
    private IPlanDataService planDataService;

    /**
     * Query the current day's schedule
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Query the current day's schedule", notes = "Query the current day's schedule")
    @PostMapping(value = "/dailyPlan/getPlanDataByTime", produces = "application/json;charset=utf-8")
    public SelectPlanDataResponse selectPlanDataByTime(@Validated @RequestBody SelectPlanDataByTimeRequest request) {
        SelectPlanDataResponse response = new SelectPlanDataResponse();
        try {
            List<PlanData> list = planDataService.list(new QueryWrapper<PlanData>().lambda()
                    .eq(!StringUtils.isEmpty(request.getUserName()), PlanData::getUserName, request.getUserName())
                    .apply("date_format (start_Time,'%Y-%m-%d') = date_format('" + request.getTime() + "','%Y-%m-%d')")
                    .orderByDesc(PlanData::getStartTime));
            response.setData(list);
            response.setErrorCode(ErrorCode.SUCCESS);
            return response;
        } catch (Exception e) {
            log.error("", e);
            response.setErrorCode(ErrorCode.DB_ERROR);
        }
        return response;
    }

    /**
     * Manually add and modify the schedule
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Manually add and modify the schedule", notes = "Manually add and modify the schedule")
    @PostMapping(value = "/dailyPlan/saveOrUpdate", produces = "application/json;charset=utf-8")
    public AddDailyPlanResponse saveOrUpdate(@Validated @RequestBody AddDailyPlanRequest request) {
        AddDailyPlanResponse response = new AddDailyPlanResponse();
        boolean b = planDataService.saveOrUpdate(request);
        if (b) {
            response.setErrorCode(ErrorCode.SUCCESS);
        } else {
            response.setErrorCode(ErrorCode.DB_ERROR);
        }
        return response;
    }

    /**
     * delete plan
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "delete plan", notes = "delete plan")
    @PostMapping(value = "/dailyPlan/delete", produces = "application/json;charset=utf-8")
    public DeleteDailyPlanReponse dailyPlanDeleteById(@Validated @RequestBody DeleteDailyPlanRequest request) {
        DeleteDailyPlanReponse response = new DeleteDailyPlanReponse();
        try {
            planDataService.remove(new QueryWrapper<PlanData>().lambda()
                    .eq(!StringUtils.isEmpty(request.getUserName()), PlanData::getUserName, request.getUserName())
                    .eq(!StringUtils.isEmpty(request.getPlanId()), PlanData::getPlanId, request.getPlanId())
                    .orderByDesc(PlanData::getStartTime));
            response.setErrorCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            log.error("", e);
            response.setErrorCode(ErrorCode.DB_ERROR);
        }
        return response;
    }
}