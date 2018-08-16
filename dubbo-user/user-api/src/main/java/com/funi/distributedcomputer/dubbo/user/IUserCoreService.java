package com.funi.distributedcomputer.dubbo.user;

import com.funi.distributedcomputer.dubbo.user.dto.UserRegisterRequest;
import com.funi.distributedcomputer.dubbo.user.dto.UserRegisterResponse;

public interface IUserCoreService {
    /**
     * 注册
     *
     * @param request
     * @return
     */
    public UserRegisterResponse register(UserRegisterRequest request);
}
