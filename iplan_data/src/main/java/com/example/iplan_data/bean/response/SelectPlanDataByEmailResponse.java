package com.example.iplan_data.bean.response;

import com.example.iplan_data.entity.PlanData;
import lombok.Data;

import java.util.List;
@Data
public class SelectPlanDataByEmailResponse {
    private List<PlanData> data;
}
