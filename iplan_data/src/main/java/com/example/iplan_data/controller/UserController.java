package com.example.iplan_data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.iplan_data.bean.request.UserConfigRequest;
import com.example.iplan_data.bean.response.UserConfigResponse;
import com.example.iplan_data.constant.ErrorCode;
import com.example.iplan_data.entity.vo.UserVo;
import com.example.iplan_data.service.IUserService;
import com.example.iplan_data.util.CheckUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * user management
 * </p>
 *
 * @author huangdeyu
 * @since 2023-04-14
 */
@Slf4j(topic = "userLogger")
@RestController
public class UserController {
    @Resource
    private IUserService iUserService;

    /**
     * user signUp
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "user signUp interface", notes = "user signUp interface")
    @PostMapping(value = "/user/signUp", produces = "application/json;charset=utf-8")
    public UserConfigResponse userSignUp(@Validated @RequestBody UserConfigRequest request) {
        UserConfigResponse response = new UserConfigResponse();
        try {
            UserVo userVo = iUserService.createUser(request);
            if (!CheckUtil.isEmpty(userVo)) {
               response.setUserVo(userVo);
                response.setErrorCode(ErrorCode.SUCCESS);
            } else {
                response.setErrorCode(ErrorCode.DB_ERROR);
            }
        } catch (Exception e) {
            log.error("", e);
            response.setErrorCode(ErrorCode.DB_ERROR);
        }
        return response;
    }

    /**
     * user signIn
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "user signIn interface", notes = "user signIn interface")
    @PostMapping(value = "/user/signIn", produces = "application/json;charset=utf-8")
    public UserConfigResponse userSignIn(@Validated @RequestBody UserConfigRequest request) {
        UserConfigResponse response = new UserConfigResponse();
        try {
            UserVo userVo = iUserService.login(request);
            if (!CheckUtil.isEmpty(userVo)) {
                response.setUserVo(userVo);
                response.setErrorCode(ErrorCode.SUCCESS);
            } else {
                response.setErrorCode(ErrorCode.DB_ERROR);
            }
        } catch (Exception e) {
            log.error("", e);
            response.setErrorCode(ErrorCode.DB_ERROR);
        }
        return response;
    }
}
