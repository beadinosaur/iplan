package com.example.iplan_data.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class PlanDataVo {

    /**
     * Belonging user
     */
    @ApiModelProperty("username")
    @NotBlank(message = "not null")
    private String userName;

    /**
     * sender
     */
    @ApiModelProperty("sender")
    private String sender;

    /**
     * receiver
     */
    @ApiModelProperty("receiver")
    private String receiver;

    /**
     * topic
     */
    @ApiModelProperty("topic")
    @NotBlank(message = "not null")
    private String title;

    /**
     * content
     */
    @ApiModelProperty("content")
    private String content;

    /**
     * position
     */
    @ApiModelProperty("position")
    private String position;

    /**
     * startDate
     */
    @ApiModelProperty("startDate")
    private String startDate;

    /**
     * startTime
     */
    @ApiModelProperty("startTime")
    private String startTime;

    /**
     * endDate
     */
    @ApiModelProperty("endDate")
    private String endDate;

    /**
     * endTime
     */
    @ApiModelProperty("endTime")
    private String endTime;

    /**
     * planId
     */
    @ApiModelProperty("id,Added time transmission space")
    Long planId;
}
