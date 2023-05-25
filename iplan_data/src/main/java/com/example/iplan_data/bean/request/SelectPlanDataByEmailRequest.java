package com.example.iplan_data.bean.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class SelectPlanDataByEmailRequest {

    /**
     * email
     */
    @ApiModelProperty("email")
    private String email;

    @ApiModelProperty("userName")
    private String userName;

}
