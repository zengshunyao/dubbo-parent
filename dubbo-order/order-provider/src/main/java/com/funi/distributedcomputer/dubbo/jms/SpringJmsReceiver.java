package com.funi.distributedcomputer.dubbo.jms;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**********************************************************************
 * &lt;p&gt;文件名：${FILE_NAME} &lt;/p&gt;
 * &lt;p&gt;文件描述：${DESCRIPTION}(描述该文件做什么)
 * @project_name：dubbo-order
 * @author zengshunyao
 * @date 2018/8/14 16:30
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2014
 *                    All Rights Reserved.
 */
public class SpringJmsReceiver {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:META-INF/spring/service-jms.xml");
        JmsTemplate jmsTemplate = context.getBean("jmsTemplate", JmsTemplate.class);

        String message = (String) jmsTemplate.receiveAndConvert();
        System.out.println(message);
    }
}
