package com.example.iplan_data.bean.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Select Plan request
 */
@Data
public class SelectDailyPlanByMonthRangeRequest {
    /**
     * Belonging user
     */
    @ApiModelProperty("userName")
    @NotBlank(message = "not null")
    String userName;
}
