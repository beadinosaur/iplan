package com.example.iplan_data.bean.response;

import com.example.iplan_data.entity.PlanData;
import lombok.Data;

import java.util.List;

/**
 * PlanData
 */
@Data
public class SelectPlanDataResponse extends BaseResponse {
    private List<PlanData> data;
}
