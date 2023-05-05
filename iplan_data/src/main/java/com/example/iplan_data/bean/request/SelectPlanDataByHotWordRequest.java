package com.example.iplan_data.bean.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * select by hotword
 */
@Data
public class SelectPlanDataByHotWordRequest {

    /**
     * hotWords
     */
    @ApiModelProperty("hotWords")
    private String hotWords;
}
