package com.example.iplan_data.bean.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


/**
 * EmailConfig request
 */
@Data
public class AddEmailConfigRequest {

    /**
     * Belonging user
     */
    @ApiModelProperty("username")
    @NotBlank(message = "not null")
    private String userName;

    /**
     * email
     */
    @ApiModelProperty("email")
    @NotBlank(message = "not null")
    private String email;

    /**
     * email password
     */
    @ApiModelProperty("email password")
    @NotBlank(message = "not null")
    private String password;

    /**
     * department
     */
    @ApiModelProperty("department")
    private String department;

    /**
     * station
     */
    @ApiModelProperty("station")
    private String station;

    /**
     * startTime
     */
    @ApiModelProperty("startTime")
    private String startTime;

    /**
     * keyWordS
     */
    @ApiModelProperty("keyWordS")
    private String keyWordS;

    /**
     * keyWordT
     */
    @ApiModelProperty("keyWordT")
    private String keyWordT;

    /**
     * keyEmail
     */
    @ApiModelProperty("keyEmail")
    private String keyEmail;
}
