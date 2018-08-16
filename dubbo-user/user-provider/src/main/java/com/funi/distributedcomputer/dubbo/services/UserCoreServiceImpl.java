package com.funi.distributedcomputer.dubbo.services;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.funi.distributedcomputer.dubbo.dal.entity.User;
import com.funi.distributedcomputer.dubbo.dal.persistence.UserMapper;
import com.funi.distributedcomputer.dubbo.exception.ExceptionUtil;
import com.funi.distributedcomputer.dubbo.exception.ServiceException;
import com.funi.distributedcomputer.dubbo.exception.ValidateException;
import com.funi.distributedcomputer.dubbo.user.IUserCoreService;
import com.funi.distributedcomputer.dubbo.user.constants.ResponseCodeEnum;
import com.funi.distributedcomputer.dubbo.user.dto.UserRegisterRequest;
import com.funi.distributedcomputer.dubbo.user.dto.UserRegisterResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service("userCoreService")
public class UserCoreServiceImpl implements IUserCoreService {
    Logger logger = LoggerFactory.getLogger(UserCoreServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {
        logger.info("begin UserLoginService.login,request:【" + request + "】");
        UserRegisterResponse response = new UserRegisterResponse();
        try {
            this.beforeValidate(request);

            User user = new User();
            user.setMobile(request.getMobile());
            user.setPassword(request.getPassword());
            user.setUsername(request.getUsername());

            int effectRow = userMapper.insertSelective(user);

            if (effectRow > 0) {
                response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
                response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
                return response;
            }

            response.setCode(ResponseCodeEnum.DATA_SAVE_ERROR.getCode());
            response.setMsg(ResponseCodeEnum.DATA_SAVE_ERROR.getMsg());
        } catch (DuplicateKeyException e) {
//TODO 用户名重复
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
    private void beforeValidate(UserRegisterRequest request) {
        if (null == request) {
            throw new ValidateException("请求对象为空");
        }
        if (StringUtils.isEmpty(request.getUsername())) {
            throw new ValidateException("用户名为空");
        }
        if (StringUtils.isEmpty(request.getPassword())) {
            throw new ValidateException("密码为空");
        }
        if (StringUtils.isEmpty(request.getMobile())) {
            throw new ValidateException("手机号码为空");
        }
    }
}
