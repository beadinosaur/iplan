package com.example.iplan_data.entity.vo;

import lombok.Data;

/**
 * <p>
 * Hot words grouping statistics
 * Docking with the front end
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-13
 */
@Data
public class HotWordsVo {
    /**
     * User Name
     */
    private String userName;

    /**
     * The number of hot words
     */
    private int titleCount;
}
