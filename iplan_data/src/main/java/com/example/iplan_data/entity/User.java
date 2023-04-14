package com.example.iplan_data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * <p>
 * user information Table
 * </p>
 *
 * @author huangdeyu
 * @since 2023-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

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

    /**
     * authority to login 1: Yes  0: No
     */
    private int enabled;


}
