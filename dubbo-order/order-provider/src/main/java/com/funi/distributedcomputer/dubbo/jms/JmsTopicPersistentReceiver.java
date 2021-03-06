package com.funi.distributedcomputer.dubbo.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 持久订阅
 */
public class JmsTopicPersistentReceiver {

    private final static String CLIENT_ID = "DUBBO-ORDER";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://47.52.33.73:61616");
        Connection connection = null;
        try {
            //创建连接
            connection = connectionFactory.createConnection("admin", "admin");
            connection.setClientID(CLIENT_ID);
            connection.start();

            //创建事物会话
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            //创建队列(如果队列已经存在则不会创建)
            //destination标识目的地
            Topic topic = session.createTopic("first-topic");
            //创建消息消费者
            MessageConsumer consumer = session.createDurableSubscriber(topic, CLIENT_ID);

            //取消持久订阅
            //session.unsubscribe(CLIENT_ID);

            TextMessage message = (TextMessage) consumer.receive();
            System.out.println(message.getText());
            //我消费完了 确认消息  对应Session.CLIENT_ACKNOWLEDGE
            message.acknowledge();

            //确认
            session.commit();
            session.close();

        } catch (
                JMSException e)

        {
            e.printStackTrace();
        } finally

        {
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
