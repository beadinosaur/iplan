package com.example.iplan_data.entity.vo;

import com.example.iplan_data.entity.PlanData;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * Hot word statistics
 * Docking with the front end
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-13
 */
@Data
public class HotWordsPlanDataVo {
    /**
     * User Name
     */
    private String userName;

    /**
     * The number of hot words
     */
    private int sum;

    /**
     * The list of hot words
     */
    private List<PlanData> data;
}
