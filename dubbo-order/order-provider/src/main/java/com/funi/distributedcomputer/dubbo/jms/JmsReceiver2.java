package com.funi.distributedcomputer.dubbo.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class JmsReceiver2 {

    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("" +
                "failover:(tcp://192.168.11.140:61616,tcp://192.168.11.137:61616)?randomize=false");
        Connection connection = null;
        try {
            //创建连接
            connection = connectionFactory.createConnection();
            connection.start();
            connectionFactory.setDispatchAsync(false);
            Session session = connection.createSession(Boolean.FALSE, Session.DUPS_OK_ACKNOWLEDGE);

            //创建队列（如果队列已经存在则不会创建， first-queue是队列名称）
            //destination表示目的地
            Destination destination = session.createQueue("first-queue");
            //创建消息接收者
            MessageConsumer consumer = session.createConsumer(destination);

            for (int i = 0; i < 10; i++) {
                TextMessage textMessage = (TextMessage) consumer.receive();
                System.out.println(textMessage.getText());
                if (i == 4) {
                    textMessage.acknowledge();
                }
            }

            TextMessage textMessage = (TextMessage) consumer.receive();
            System.out.println(textMessage.getText() + "--->" + textMessage.getStringProperty("key"));

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