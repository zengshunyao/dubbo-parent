package com.funi.distributedcomputer.dubbo.user;

import com.funi.distributedcomputer.dubbo.user.dto.UserLoginRequest;
import com.funi.distributedcomputer.dubbo.user.dto.UserLoginResponse;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface IUserLoginService {

    /**
     * 用户登录操作
     *
     * @param userLoginRequest
     * @return
     */
    UserLoginResponse login(UserLoginRequest userLoginRequest);
}
