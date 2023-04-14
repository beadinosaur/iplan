package com.example.iplan_data.bean.request;

import lombok.Data;
/**
 * user config request
 */
@Data
public class UserConfigRequest {
    /**
     * user Name
     */
    private String userName;

    /**
     * password
     */
    private String password;
}
