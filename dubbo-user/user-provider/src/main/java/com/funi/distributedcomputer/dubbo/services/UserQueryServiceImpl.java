package com.funi.distributedcomputer.dubbo.services;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.funi.distributedcomputer.dubbo.dal.entity.User;
import com.funi.distributedcomputer.dubbo.dal.persistence.UserMapper;
import com.funi.distributedcomputer.dubbo.exception.ExceptionUtil;
import com.funi.distributedcomputer.dubbo.exception.ServiceException;
import com.funi.distributedcomputer.dubbo.exception.ValidateException;
import com.funi.distributedcomputer.dubbo.user.IUserQueryService;
import com.funi.distributedcomputer.dubbo.user.constants.ResponseCodeEnum;
import com.funi.distributedcomputer.dubbo.user.dto.UserQueryRequest;
import com.funi.distributedcomputer.dubbo.user.dto.UserQueryResponse;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service("userQueryService")
public class UserQueryServiceImpl implements IUserQueryService {

    Logger logger = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    RateLimiter rateLimiter = RateLimiter.create(2.0);

    /**
     * 根据用户id来查询用户信息
     *
     * @param request
     * @return
     */
    @Override
    public UserQueryResponse getUserById(UserQueryRequest request) {
        UserQueryResponse response = new UserQueryResponse();
        try {
            beforeValidate(request);
            response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
            response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
            User user = userMapper.getUserByUid(request.getUid());
            if (user != null) {
                response.setAvatar(user.getAvatar());
                response.setSex(user.getSex());
                response.setRealName(user.getRealName());
                response.setMobile(user.getMobile());
                return response;
            }
            response.setCode(ResponseCodeEnum.QUERY_DATA_NOT_EXIST.getCode());
            response.setMsg(ResponseCodeEnum.QUERY_DATA_NOT_EXIST.getMsg());
        } catch (Exception e) {
            ServiceException serviceException = (ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }
        return response;
    }

    /**
     * 验证
     *
     * @param request
     */
    private void beforeValidate(UserQueryRequest request) {
        if (null == request) {
            throw new ValidateException("请求对象为空");
        }
        if (request.getUid() == null || request.getUid().intValue() == 0) {
            throw new ValidateException("用户id不能为空");
        }
    }

    /**
     * 根据用户id来查询用户信息
     *
     * @param request
     * @return
     */
    @Override
    public UserQueryResponse getUserByIdWithLimiter(UserQueryRequest request) {
        UserQueryResponse response = new UserQueryResponse();
        if (!rateLimiter.tryAcquire()) {
            response.setCode(ResponseCodeEnum.ACCESS_LIMITER.getCode());
            response.setMsg(ResponseCodeEnum.ACCESS_LIMITER.getMsg());
            return response;
        }
        try {
            beforeValidate(request);
            response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
            response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
            User user = userMapper.getUserByUid(request.getUid());
            if (user != null) {
                response.setAvatar(user.getAvatar());
                response.setSex(user.getSex());
                response.setRealName(user.getRealName());
                response.setMobile(user.getMobile());
                return response;
            }
            response.setCode(ResponseCodeEnum.QUERY_DATA_NOT_EXIST.getCode());
            response.setMsg(ResponseCodeEnum.QUERY_DATA_NOT_EXIST.getMsg());
        } catch (Exception e) {
            ServiceException serviceException = (ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }
        return response;
    }
}
