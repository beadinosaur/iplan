package com.example.iplan_data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.iplan_data.bean.request.AddDailyPlanRequest;
import com.example.iplan_data.entity.PlanData;


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

}
