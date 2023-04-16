package com.example.iplan_data.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.iplan_data.bean.request.UserConfigRequest;
import com.example.iplan_data.entity.User;
import com.example.iplan_data.entity.vo.UserVo;
import com.example.iplan_data.mapper.UserMapper;
import com.example.iplan_data.service.IUserService;
import com.example.iplan_data.util.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
 * <p>
 * user management
 * </p>
 *
 * @author huangdeyu
 * @since 2023-04-14
 */
@Service
@Slf4j(topic = "UserLogger")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     *  user signUp
     * @param req
     * @return
     */
    @Override
    public UserVo createUser(UserConfigRequest req) {
        int flag = 0;
        UserVo userVo =new UserVo();
        //generate random id
        String randomId = String.valueOf((long) ((Math.random() * 9 + 1) * Math.pow(10, 10 - 1)));
        User user_db = userMapper.selectOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getUserId,randomId)
        );
        //If the same id does not exist  add this user
        if(CheckUtil.isEmpty(user_db)){
            User user = new User();
            //encrypt password
            String password =passwordEncoder.encode(req.getPassword());
            user.setUserName(req.getUserName());
            user.setPassword(password);
            user.setEnabled(1);
            user.setUserId(randomId);
            flag = userMapper.insert(user);
        }
         if(flag>0){
             userVo.setUserName(req.getUserName());
             userVo.setUserId(randomId);
         }
        return userVo;
    }

    /**
     *  user signIn
     * @param req
     * @return
     */
    @Override
    public UserVo login(UserConfigRequest req) {
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getUserName,req.getUserName())
        );
        //Check whether the user exists
        if(CheckUtil.isEmpty(user)){
            throw new RuntimeException("The user does not exist!");
        }
        //Check whether the password matches
        boolean match = passwordEncoder.matches(req.getPassword(),user.getPassword());
        if(!match){
            throw new RuntimeException("Invalid password!");
        }
        UserVo userVo = new UserVo();
        if(!CheckUtil.isEmpty(user)){
            userVo.setUserName(user.getUserName());
            userVo.setUserId(user.getUserId());
         }
        return userVo;
    }
}
