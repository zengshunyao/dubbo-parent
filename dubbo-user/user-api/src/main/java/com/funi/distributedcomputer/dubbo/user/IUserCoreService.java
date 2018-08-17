package com.funi.distributedcomputer.dubbo.user;

import com.funi.distributedcomputer.dubbo.user.dto.UserLoginRequest;
import com.funi.distributedcomputer.dubbo.user.dto.UserLoginResponse;
import com.funi.distributedcomputer.dubbo.user.dto.UserRegisterRequest;
import com.funi.distributedcomputer.dubbo.user.dto.UserRegisterResponse;

public interface IUserCoreService {


    /**
     * 用户注册
     *
     * @param request
     * @return
     */
    public UserRegisterResponse register(UserRegisterRequest request);

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @return
     */
    UserLoginResponse login(UserLoginRequest userLoginRequest);
}
