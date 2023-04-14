package com.example.iplan_data.entity.vo;

import lombok.Data;
/**
 * <p>
 * user information vo
 * Docking with the front end
 * </p>
 *
 * @author huangdeyu
 * @since 2023-04-14
 */
@Data
public class UserVo {

    /**
     * user ID
     */
    private String userId;
    /**
     * user Name
     */
    private String userName;

    /**
     * password
     */
    private String password;
}
