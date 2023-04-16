package com.example.iplan_data.bean.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * delete planData request
 */
@Data
public class DeleteDailyPlanRequest {
    /**
     * Belonging user
     */
    @ApiModelProperty("userName")
    @NotBlank(message = "not null")
    String userName;

    /**
     * planId
     */
    @ApiModelProperty("planId")
    @NotBlank(message = "not null")
    String planId;
    ;
}
