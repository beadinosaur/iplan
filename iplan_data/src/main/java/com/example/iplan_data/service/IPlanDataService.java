package com.example.iplan_data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.iplan_data.bean.request.AddDailyPlanRequest;
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
     * 查询时间轴
     *
     * @param username
     * @param pageIndex
     * @return
     */
    List<TimeAxisPlanDataVo> selectByTimeRange(String username, int pageIndex);

    /**
     * 查询日历程
     *
     * @param username
     * @return
     */
    List<PlanData> selectByMonthRange(String username);


    /**
     * 根据热词查询
     *
     * @param words
     * @return
     */
    List<HotWordsPlanDataVo> selectPlanDataByHotWords(String words);

}
