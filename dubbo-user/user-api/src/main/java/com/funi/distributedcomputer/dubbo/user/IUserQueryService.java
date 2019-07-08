package com.funi.distributedcomputer.dubbo.user;

import com.funi.distributedcomputer.dubbo.user.dto.UserQueryRequest;
import com.funi.distributedcomputer.dubbo.user.dto.UserQueryResponse;


public interface IUserQueryService {

    /**
     * 根据用户id来查询用户信息
     *
     * @param request
     * @return
     */
    UserQueryResponse getUserById(UserQueryRequest request);

    /**
     * 根据用户id来查询用户信息
     *
     * @param request
     * @return
     */
    UserQueryResponse getUserByIdWithLimiter(UserQueryRequest request);
}
