package com.example.iplan_data.bean.response;

import com.example.iplan_data.entity.vo.HotWordsPlanDataVo;
import lombok.Data;

import java.util.List;

/**
 * PlanData Response
 */
@Data
public class SelectPlanDataByHotWordsResponse extends BaseResponse {
    private List<HotWordsPlanDataVo> data;
}
