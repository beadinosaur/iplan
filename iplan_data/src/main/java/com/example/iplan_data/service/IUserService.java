package com.example.iplan_data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.iplan_data.bean.request.UserConfigRequest;
import com.example.iplan_data.entity.User;
import com.example.iplan_data.entity.vo.UserVo;

import javax.validation.Valid;

public interface IUserService extends IService<User> {
    /**
     * user signUp
     * @param userVo
     * @return user ID
     */
    UserVo createUser(@Valid UserConfigRequest userVo);

    /**
     * user signIn
     * @param userVo
     * @return
     */
    UserVo login(@Valid UserConfigRequest userVo);
}
