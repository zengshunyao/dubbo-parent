package com.funi.distributedcomputer.dubbo.services;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.funi.distributedcomputer.dubbo.dal.entity.User;
import com.funi.distributedcomputer.dubbo.dal.persistence.UserMapper;
import com.funi.distributedcomputer.dubbo.exception.ExceptionUtil;
import com.funi.distributedcomputer.dubbo.exception.ServiceException;
import com.funi.distributedcomputer.dubbo.exception.ValidateException;
import com.funi.distributedcomputer.dubbo.user.IUserLoginService;
import com.funi.distributedcomputer.dubbo.user.constants.ResponseCodeEnum;
import com.funi.distributedcomputer.dubbo.user.dto.UserLoginRequest;
import com.funi.distributedcomputer.dubbo.user.dto.UserLoginResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service("userLoginService")
public class UserLoginServiceImpl implements IUserLoginService {

    Logger logger = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    /**
     * 用户登录操作
     *
     * @param userLoginRequest
     * @return
     */
    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        logger.info("begin UserLoginService.login,request:【" + userLoginRequest + "】");
        UserLoginResponse response = new UserLoginResponse();
        try {
            beforeValidate(userLoginRequest);

            User user = userMapper.getUserByUserName(userLoginRequest.getUsername());
            if (user == null || !user.getPassword().equals(userLoginRequest.getPassword())) {
                response.setCode(ResponseCodeEnum.USER_OR_PASSWORD_ERROR.getCode());
                response.setMsg(ResponseCodeEnum.USER_OR_PASSWORD_ERROR.getMsg());
                return response;
            }
            //todo 判断用户状态
            response.setAvatar(user.getAvatar());
            response.setMobile(user.getMobile());
            response.setRealName(user.getRealName());
            response.setSex(user.getSex());

            response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
            response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
            return response;
        } catch (Exception e) {
            ServiceException serviceException = (ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        } finally {
            logger.info("login response:【" + response + "】");
        }
        return response;
    }

    /**
     * 验证
     *
     * @param request
     */
    private void beforeValidate(UserLoginRequest request) {
        if (null == request) {
            throw new ValidateException("请求对象为空");
        }
        if (StringUtils.isEmpty(request.getUsername())) {
            throw new ValidateException("用户名为空");
        }
        if (StringUtils.isEmpty(request.getPassword())) {
            throw new ValidateException("密码为空");
        }
    }
}
