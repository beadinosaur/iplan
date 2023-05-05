package com.example.iplan_data.bean.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * select by email
 */
@Data
public class SelectConferenceDataByToEmailRequest {
    /**
     * email
     */
    @ApiModelProperty("email")
    @NotBlank(message = "email")
    private String email;
}
