package com.example.iplan_data.bean.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * add planData request
 */
@Data
public class AddDailyPlanRequest {

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
     * startTime
     */
    @ApiModelProperty("startTime")
    private String startTime;

    /**
     * endTime
     */
    @ApiModelProperty("endTime")
    private String endTime;

    /**
     * 会议结束时间
     */
    @ApiModelProperty("id,Added time transmission space")
    Long planId;
}
