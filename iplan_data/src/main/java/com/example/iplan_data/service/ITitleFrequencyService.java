package com.example.iplan_data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.iplan_data.entity.TitleFrequency;

/**
 * <p>
 * 日程主题名词词频统计表 服务类
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-21
 */
public interface ITitleFrequencyService extends IService<TitleFrequency> {
    void saveOrUpdate();
}

