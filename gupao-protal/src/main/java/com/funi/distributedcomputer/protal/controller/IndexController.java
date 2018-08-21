package com.funi.distributedcomputer.protal.controller;

import com.funi.distributedcomputer.dubbo.user.IUserCoreService;
import com.funi.distributedcomputer.dubbo.user.constants.ResponseCodeEnum;
import com.funi.distributedcomputer.dubbo.user.dto.UserLoginRequest;
import com.funi.distributedcomputer.dubbo.user.dto.UserLoginResponse;
import com.funi.distributedcomputer.dubbo.user.dto.UserRegisterRequest;
import com.funi.distributedcomputer.dubbo.user.dto.UserRegisterResponse;
import com.funi.distributedcomputer.protal.controller.support.Anonymous;
import com.funi.distributedcomputer.protal.controller.support.ResponseData;
import com.funi.distributedcomputer.protal.controller.support.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletResponse;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    IUserCoreService userCoreService;

    @Autowired
    JmsTemplate jmsTemplate;

    /**
     * 首页页面
     *
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String index(Model model) {
        System.out.println("index------------------");
        return "index";
    }

    /**
     * 登录页面
     *
     * @return
     */
    @GetMapping("/login")
    @Anonymous
    public String login() {
        return "login";
    }

    /**
     * 登录页面
     *
     * @return
     */
    @PostMapping("/login")
    @Anonymous
    public ResponseEntity login(String txtUser, String txtPwd, HttpServletResponse response) {
        ResponseData responseData = new ResponseData();
        try {
            UserLoginRequest request = new UserLoginRequest();
            request.setUsername(txtUser);
            request.setPassword(txtPwd);

            UserLoginResponse userLoginResponse = userCoreService.login(request);
            if (ResponseCodeEnum.SYS_SUCCESS.getCode().equals(userLoginResponse.getCode())) {
                response.setHeader(HttpHeaders.SET_COOKIE,
                        com.funi.distributedcomputer.protal.constants.Constants.ACCESS_TOKEN +
                                "=" + userLoginResponse.getToken() + ";Path=/;HttpOnly");
            }
            responseData.setMessage(userLoginResponse.getMsg());
            responseData.setCode(userLoginResponse.getCode());
        } catch (Exception e) {
            responseData.setMessage(ResponseEnum.FAILED.getMsg());
            responseData.setCode(ResponseEnum.FAILED.getCode());
        }
        return ResponseEntity.ok(responseData);
    }

    /**
     * 注册页面
     *
     * @return
     */
    @GetMapping("/register")
    @Anonymous
    public String register() {
        return "register";
    }

    /**
     * 注册表单提交
     *
     * @param username 用户名
     * @param password 密码
     * @param mobile   手机号码
     * @return
     */
    @PostMapping("/register")
    @Anonymous
    public @ResponseBody
    ResponseData register(String username, String password, String mobile) {
        ResponseData data = new ResponseData();

        UserRegisterRequest request = new UserRegisterRequest();
        request.setMobile(mobile);
        request.setUsername(username);
        request.setPassword(password);
        try {

            UserRegisterResponse response = userCoreService.register(request);

            if (response.getCode().equals(ResponseCodeEnum.SYS_SUCCESS.getCode())) {
                //发送邮件  发送卡券
                jmsTemplate.send(new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage("发送邮件");
                    }
                });
            }
            data.setMessage(response.getMsg());
            data.setCode(response.getCode());
        } catch (Exception e) {
            data.setMessage(ResponseEnum.FAILED.getMsg());
            data.setCode(ResponseEnum.FAILED.getCode());
        }
        return data;
    }
}
