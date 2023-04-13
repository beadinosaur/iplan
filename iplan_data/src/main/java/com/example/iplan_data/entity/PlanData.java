package com.example.iplan_data.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.example.iplan_data.util.CheckUtil;
import com.example.iplan_data.util.DateUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>
 * plan data table
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PlanData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * plan ID
     */
    @TableId(value = "plan_id", type = IdType.AUTO)
    private Long planId;

    /**
     * Belonging user
     */
    private String userName;

    /**
     * sender
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String sender;

    /**
     * receiver
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String receiver;

    /**
     * title
     */
    private String title;

    /**
     * content
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String content;

    /**
     * Conference position
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String position;

    /**
     * Conference start time
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    /**
     * Conference end time
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    /**
     * Receive Time
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date receiveTime;

    /**
     * Create Time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp createTime;

    /**
     * Update Time
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp updateTime;

    /**
     * data source 0：add by user 1: from email
     */
    private String source;


    @TableField(exist = false)
    private String time;

    @TableField(exist = false)
    private String week;

    public String getTime() {
        if (!CheckUtil.isEmpty(startTime)) {
            return DateUtil.formatHHMMSS(startTime);
        }
        return "";
    }

    public String getWeek() {
        if (!CheckUtil.isEmpty(startTime)) {
            return DateUtil.dateToWeek(startTime);
        }
        return "";
    }


}
