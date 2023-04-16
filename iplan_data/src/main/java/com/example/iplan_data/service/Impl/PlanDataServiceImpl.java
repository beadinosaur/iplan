package com.example.iplan_data.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.iplan_data.bean.request.AddDailyPlanRequest;
import com.example.iplan_data.entity.PlanData;
import com.example.iplan_data.mapper.PlanDataMapper;
import com.example.iplan_data.service.IPlanDataService;
import com.example.iplan_data.util.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;


/**
 * <p>
 * Schedule data table service implementation class
 * </p>
 *
 * @author xieguangwei/huangdeyu
 * @since 2023-04-16
 */
@Slf4j(topic = "planDataLogger")
@Service
public class PlanDataServiceImpl extends ServiceImpl<PlanDataMapper, PlanData> implements IPlanDataService {

    @Resource
    private PlanDataMapper planDataMapper;

    /**
     * Add, modify (calendar, hot word list)
     *
     * @param request
     * @return
     */
    @Override
    public boolean saveOrUpdate(AddDailyPlanRequest request) {
        log.info("planData saveOrUpdate start! {}", request);
        int flag = 0;
        PlanData planData = new PlanData();
        log.info("planData saveOrUpdate planId {}", request.getPlanId());
        if (CheckUtil.isEmpty(request.getPlanId())) {
            BeanUtils.copyProperties(request, planData);
            //The passed time is in the non-complete format yyyy-MM-dd HH:mm,
            // which is converted to the complete format yyyy-MM-dd HH:mm:ss
            planData.setEndTime(com.example.iplan_data.util.DateUtil.fullParse(request.getEndTime()));
            planData.setStartTime(com.example.iplan_data.util.DateUtil.fullParse(request.getStartTime()));
            planData.setSource("0");
            planData.setCreateTime(new Timestamp(System.currentTimeMillis()));
            flag = planDataMapper.insert(planData);
            log.info("planDataMapper.insert flag {}", flag);
        } else {
            PlanData p = planDataMapper.selectById(request.getPlanId());
            log.info("planDataMapper.selectById PlanData {}", p);
            if (!CheckUtil.isEmpty(p)) {
                BeanUtils.copyProperties(request, p);
                p.setEndTime(com.example.iplan_data.util.DateUtil.fullParse(request.getEndTime()));
                p.setStartTime(com.example.iplan_data.util.DateUtil.fullParse(request.getStartTime()));
                p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                flag = planDataMapper.updateById(p);
                log.info("planDataMapper.updateById flag {}", flag);
            }
        }
        //Word segmentation is performed on the incoming topic todo
        log.info("planData saveOrUpdate end! flag {}", flag > 0);
        return flag > 0;
    }
}
