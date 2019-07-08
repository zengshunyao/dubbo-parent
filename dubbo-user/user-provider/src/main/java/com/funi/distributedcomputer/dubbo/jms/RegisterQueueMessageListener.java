package com.funi.distributedcomputer.dubbo.jms;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


@Component
public class RegisterQueueMessageListener implements MessageListener {

    Logger logger = LoggerFactory.getLogger(RegisterQueueMessageListener.class);

    /**
     * 注册成功发送邮件
     *
     * @param message
     */
    public void onMessage(Message message) {
        TextMessage message1 = (TextMessage) message;
        try {
            logger.info(message1.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
