package com.funi.distributedcomputer.dubbo.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

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
public class JmsReceiver {

    public static void main(String[] args) {
        //47.52.33.73
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = null;
        try {
            //创建连接
            connection = connectionFactory.createConnection("admin", "admin");
            connection.start();

            //创建事物会话
            Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);

            //创建队列(如果队列已经存在则不会创建)
            //destination标识目的地
            Destination destination = session.createQueue("first-queue");
            //创建消息消费者
            MessageConsumer consumer = session.createConsumer(destination);
            for (int i = 0; i < 10; i++) {
                TextMessage message = (TextMessage) consumer.receive();
                System.out.println(message.getText());
                if (i == 4) {
                    //我消费完了 确认消息  对应Session.CLIENT_ACKNOWLEDGE
                    message.acknowledge();
                }
            }

//            session.commit();
            session.close();

        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
