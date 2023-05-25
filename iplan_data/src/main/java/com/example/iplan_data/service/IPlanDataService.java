package com.example.iplan_data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.iplan_data.bean.request.AddDailyPlanRequest;
import com.example.iplan_data.bean.request.SelectPlanDataByEmailRequest;
import com.example.iplan_data.entity.PlanData;
import com.example.iplan_data.entity.vo.HotWordsPlanDataVo;
import com.example.iplan_data.entity.vo.TimeAxisPlanDataVo;

import java.util.List;


/**
 * <p>
 * plan data
 * </p>
 *
 * @author xieguangwei/huangdeyu
 * @since 2023-04-14
 */
public interface IPlanDataService extends IService<PlanData> {

    /**
     * add or update
     *
     * @param request
     * @return
     */
    boolean saveOrUpdate(AddDailyPlanRequest request);

    /**
     * selectByTimeRange
     *
     * @param username
     * @param pageIndex
     * @return
     */
    List<TimeAxisPlanDataVo> selectByTimeRange(String username, int pageIndex);

    /**
     * selectByMonthRange
     *
     * @param username
     * @return
     */
    List<PlanData> selectByMonthRange(String username);


    /**
     * selectPlanDataByHotWords
     *
     * @param words
     * @return
     */
    List<HotWordsPlanDataVo> selectPlanDataByHotWords(String words);

    /**
     * selectPlanDataByEmail
     *
     * @param
     * @return
     */
    List<PlanData> selectPlanDataByEmail(SelectPlanDataByEmailRequest request);

}
