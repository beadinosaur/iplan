package com.example.iplan_data.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * Mailbox Configuration Table
 * Docking with the front end
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EmailConfigVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * email config ID
     * “value”：Set database field values
     * “type”：Set the primary key type、"AUTO" is recommended if the database primary key is set to increment.
     */
    @TableId(value = "email_id", type = IdType.AUTO)
    private Long emailId;

    /**
     * Belonging user
     */
    private String userName;

    /**
     * email address
     */
    private String email;

    /**
     * Email Login Password
     */
    private String password;

    /**
     * department
     */
    //Field validation: allowing updates when null values are passed in, ignoring null judgments
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
    private String startTime;

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
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Timestamp createTime;

    /**
     * Update Time
     */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Timestamp updateTime;


}
