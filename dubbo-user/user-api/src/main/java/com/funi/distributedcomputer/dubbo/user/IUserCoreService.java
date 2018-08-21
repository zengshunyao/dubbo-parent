package com.funi.distributedcomputer.dubbo.user;

import com.funi.distributedcomputer.dubbo.user.dto.*;

public interface IUserCoreService {


    /**
     * 用户注册
     *
     * @param request
     * @return
     */
    UserRegisterResponse register(UserRegisterRequest request);

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @return
     */
    UserLoginResponse login(UserLoginRequest userLoginRequest);

    /**
     * 检查授权
     *
     * @param checkAuthRequest
     * @return
     */
    CheckAuthResponse checkAuth(CheckAuthRequest checkAuthRequest);
}
