package com.example.iplan_data.bean.response;

import com.example.iplan_data.entity.vo.TimeAxisPlanDataVo;
import lombok.Data;

import java.util.List;

/**
 * PlanData Response
 */
@Data
public class SelectTimeAxisPlanDataResponse extends BaseResponse {
    private List<TimeAxisPlanDataVo> data;
}
