package com.example.iplan_data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.iplan_data.bean.request.AddEmailConfigRequest;
import com.example.iplan_data.entity.EmailConfig;

/**
 * <p>
 * 邮箱配置表 服务类
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-21
 */
public interface IEmailConfigService extends IService<EmailConfig> {

    boolean saveOrUpdate(AddEmailConfigRequest request);

}
