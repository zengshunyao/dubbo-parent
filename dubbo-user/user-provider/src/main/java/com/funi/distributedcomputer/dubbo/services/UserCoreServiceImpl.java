package com.funi.distributedcomputer.dubbo.services;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.funi.distributedcomputer.dubbo.dal.entity.User;
import com.funi.distributedcomputer.dubbo.dal.persistence.UserMapper;
import com.funi.distributedcomputer.dubbo.exception.ExceptionUtil;
import com.funi.distributedcomputer.dubbo.exception.ServiceException;
import com.funi.distributedcomputer.dubbo.exception.ValidateException;
import com.funi.distributedcomputer.dubbo.user.IUserCoreService;
import com.funi.distributedcomputer.dubbo.user.constants.Constants;
import com.funi.distributedcomputer.dubbo.user.constants.ResponseCodeEnum;
import com.funi.distributedcomputer.dubbo.user.dto.*;
import com.funi.distributedcomputer.dubbo.utils.JwtInfo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service("userCoreService")
public class UserCoreServiceImpl implements IUserCoreService {

    Logger logger = LoggerFactory.getLogger(UserCoreServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtTokenService jwtTokenService;

    /**
     * 注册
     *
     * @param userRegisterRequest
     * @return
     */
    @Override
    public UserRegisterResponse register(final UserRegisterRequest userRegisterRequest) {
        logger.info("begin UserCoreService.register,request:【" + userRegisterRequest + "】");

        UserRegisterResponse response = new UserRegisterResponse();
        try {
            this.beforeRegisterValidate(userRegisterRequest);

            User user = new User();
            user.setUsername(userRegisterRequest.getUsername());
            user.setPassword(userRegisterRequest.getPassword());
            user.setStatus(Constants.USER_FORZEN_STATUS);
            user.setCreateTime(new Date());

            int effectRow = userMapper.insertSelective(user);
            if (effectRow > 0) {
                response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
                response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
                return response;
            }
            response.setCode(ResponseCodeEnum.DATA_SAVE_ERROR.getCode());
            response.setMsg(ResponseCodeEnum.DATA_SAVE_ERROR.getMsg());
            return response;
        } catch (DuplicateKeyException e) {
            //TODO 用户名重复
        } catch (Exception e) {
            ServiceException serviceException = ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        } finally {
            logger.info("register response:【" + response + "】");
        }
        return response;
    }

    /**
     * 登录
     *
     * @param userLoginRequest
     * @return
     */
    @Override
    public UserLoginResponse login(final UserLoginRequest userLoginRequest) {
        logger.info("begin UserCoreService.login,request:【" + userLoginRequest + "】");
        final UserLoginResponse response = new UserLoginResponse();
        try {
            this.beforeLoginValidate(userLoginRequest);

            final User user = userMapper.getUserByUserName(userLoginRequest.getUsername());
            if (user == null || !user.getPassword().equals(userLoginRequest.getPassword())) {
                response.setCode(ResponseCodeEnum.USER_OR_PASSWORD_ERROR.getCode());
                response.setMsg(ResponseCodeEnum.USER_OR_PASSWORD_ERROR.getMsg());
                return response;
            }
            //todo 判断用户状态
            response.setAvatar(user.getAvatar());
            response.setMobile(user.getMobile());
            response.setRealName(user.getRealname());
            response.setSex(user.getSex());

            //todo 生成token
            response.setToken(jwtTokenService.generateToken(
                    new JwtInfo(user.getId().toString())));

            response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
            response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
            return response;
        } catch (Exception e) {
            ServiceException serviceException = ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        } finally {
            logger.info("login response:【" + response + "】");
        }
        return response;
    }

    /**
     * 检查授权
     *
     * @param checkAuthRequest
     * @return
     */
    @Override
    public CheckAuthResponse checkAuth(final CheckAuthRequest checkAuthRequest) {
        logger.info("begin UserCoreService.checkAuth,request:【" + checkAuthRequest + "】");
        CheckAuthResponse response = new CheckAuthResponse();
        try {
            this.beforeCheckAuthValidate(checkAuthRequest);
            JwtInfo jwtInfo = jwtTokenService.getInfoFromToken(checkAuthRequest.getToken());
            response.setUid(jwtInfo.getUid());

            response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
            response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
            return response;
        } catch (ExpiredJwtException e) {
            //todo
            logger.error("error", e);
        } catch (SignatureException e) {
            //todo
            logger.error("error", e);
        } catch (Exception e) {
            ServiceException serviceException = ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        } finally {
            logger.info("checkAuth response:【" + response + "】");
        }
        return response;
    }

    /**
     * 授权验证
     *
     * @param request
     */
    private void beforeCheckAuthValidate(final CheckAuthRequest request) {
        if (null == request) {
            throw new ValidateException("请求对象为空");
        }
        if (StringUtils.isEmpty(request.getToken())) {
            throw new ValidateException("token为空");
        }
    }

    /**
     * 注册校验
     *
     * @param request
     */
    private void beforeRegisterValidate(final UserRegisterRequest request) {
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

    /**
     * 登录验证
     *
     * @param request
     */
    private void beforeLoginValidate(final UserLoginRequest request) {
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
