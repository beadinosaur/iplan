package com.example.iplan_data.bean.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * request
 */
@Data
public class SelectDailyPlanByTimeRangeRequest {

    /**
     * Belonging user
     */
    @ApiModelProperty("username")
    @NotBlank(message = "not null")
    private String userName;

    /**
     * current week（pageIndex=0）（pageIndex=-1：prev / pageIndex=1：next）
     */
    @ApiModelProperty("current week（pageIndex=0）（pageIndex=-1：prev / pageIndex=1：next）")
    @NotNull(message = "not null")
    private int pageIndex;
}

