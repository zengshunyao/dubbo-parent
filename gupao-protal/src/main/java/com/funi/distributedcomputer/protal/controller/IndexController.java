package com.funi.distributedcomputer.protal.controller;

import com.funi.distributedcomputer.dubbo.user.IUserCoreService;
import com.funi.distributedcomputer.dubbo.user.constants.ResponseCodeEnum;
import com.funi.distributedcomputer.dubbo.user.dto.UserRegisterRequest;
import com.funi.distributedcomputer.dubbo.user.dto.UserRegisterResponse;
import com.funi.distributedcomputer.protal.controller.support.ResponseData;
import com.funi.distributedcomputer.protal.controller.support.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Controller
public class IndexController {

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
        return "login";
    }

    /**
     * 登录页面
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 注册页面
     *
     * @return
     */
    @GetMapping("/register")
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
