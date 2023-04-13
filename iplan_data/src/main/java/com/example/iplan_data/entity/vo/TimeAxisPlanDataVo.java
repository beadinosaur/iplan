package com.example.iplan_data.entity.vo;


import com.example.iplan_data.entity.PlanData;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * Returns data along a timeline
 * Docking with the front end
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-13
 */
@Data
public class TimeAxisPlanDataVo {
    private String time;
    private List<PlanData> data;
}
