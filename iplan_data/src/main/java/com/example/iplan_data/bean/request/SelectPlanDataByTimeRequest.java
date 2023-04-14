package com.example.iplan_data.bean.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Query the current day's schedule request
 */
@Data
public class SelectPlanDataByTimeRequest {
    /**
     * Belonging user
     */
    @ApiModelProperty("user name")
    @NotBlank(message = "not null")
    private String userName;

    /**
     * date
     */
    @ApiModelProperty("date")
    @NotBlank(message = "not null")
    private String time;
}
