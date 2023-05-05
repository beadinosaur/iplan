package com.example.iplan_data.bean.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * request
 */
@Data
public class SelectEmailConfigRequest {

    /**
     * Belonging user
     */
    @ApiModelProperty("username")
    @NotBlank(message = "not null")
    String userName;
}
