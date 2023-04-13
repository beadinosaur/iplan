package com.example.iplan_data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>
 * Conference Data Table
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ConferenceData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Conference ID
     */
    @TableId(value = "conference_id", type = IdType.AUTO)
    private Long conferenceId;

    /**
     * Reference id
     */
    private String calendarId;

    /**
     * Sender
     */
    private String sender;

    /**
     * Receiver
     */
    private String receiver;

    /**
     * Receive Time
     */
    private Date receiveTime;

    /**
     * Title
     */
    private String title;

    /**
     * Content
     */
    private String content;

    /**
     * Conference position
     */
    private String position;

    /**
     * Conference start time
     */
    private Date startTime;

    /**
     * Conference end time
     */
    private Date endTime;

    /**
     * Create Time
     */
    private Timestamp createTime;

    /**
     * datatype：regular-mail：Message/ Conference ：MeetingRequest
     */
    private String type;

    /**
     * User Name
     */
    private String userName;


}
