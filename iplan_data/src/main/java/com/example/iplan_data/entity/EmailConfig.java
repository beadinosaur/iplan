package com.example.iplan_data.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * Mailbox Configuration Table
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EmailConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * email config ID
     */
    @TableId(value = "email_id", type = IdType.AUTO)
    private Long emailId;

    /**
     * Belonging user
     */
    private String userName;

    /**
     * Email address
     */
    private String email;

    /**
     * Email Login Password
     */
    private String password;

    /**
     * department
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String department;

    /**
     * station
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String station;

    /**
     * Set the start time of mailbox synchronization
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private Timestamp startTime;

    /**
     * Filter keywords/role tags
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String keyWordS;

    /**
     * Filter keywords/generic tags
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String keyWordT;

    /**
     * Filtering emails
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String keyEmail;

    /**
     * Password encryption, 1: plaintext, 2: *
     */
    private String encrypt;

    /**
     * Create Time
     */
    private Timestamp createTime;

    /**
     * Update Time
     */
    private Timestamp updateTime;

    /**
     * New Start Time of mailbox synchronization
     */
    private Timestamp newStartTime;

}
