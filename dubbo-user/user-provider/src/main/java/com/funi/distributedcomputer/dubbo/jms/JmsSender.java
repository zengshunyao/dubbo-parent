package com.funi.distributedcomputer.dubbo.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class JmsSender {
//    private static final String BROKER_URL = "tcp://47.52.33.73:61616?jms.optimizeAcknowledge=true";
    private static final String BROKER_URL = "tcp://192.168.137.102:61616?jms.optimizeAcknowledge=true";


    public static void main(String[] args) {
        mytest();
    }

    /**
     * 咕泡学院的
     */
    public static void test() {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://47.52.33.73:61616");
        Connection connection = null;
        try {
            //创建连接
            connection = connectionFactory.createConnection();
            connection.start();

            //创建事物会话
            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

            //创建队列（如果队列已经存在则不会创建， first-queue是队列名称）
            //destination表示目的地
            Destination destination = session.createQueue("first-queue");
            //创建消息发送者
            MessageProducer producer = session.createProducer(destination);

            TextMessage textMessage = session.createTextMessage("hello, 菲菲,我是帅帅的mic");
            textMessage.setStringProperty("key", "value");
            producer.send(textMessage);
            session.commit();
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

    /**
     * 测试
     */
    public static void mytest() {


        //使用连接URI配置异步发送
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL
        );
        Connection connection = null;
        try {
            //创建连接
            connection = connectionFactory.createConnection("admin", "admin");
            //设置消息发送端发送持久化消息的异步方式
            //在ConnectionFactory级别配置异步发送
//            connectionFactory.setUseAsyncSend();

//            在连接级别配置异步发送
//            ((ActiveMQConnection) connection).setUseAsyncSend();

            //回执窗口大小设置
//            connectionFactory.setProducerWindowSize();

            connection.start();

            //创建事物会话 (True,ack类型 参考javax.jms.Session)
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            //创建队列(如果队列已经存在则不会创建)
            //destination标识目的地
            Destination destination = session.createQueue("first-queue?customer.prefetchSize=100");
            //创建消息生产者
            MessageProducer producer = session.createProducer(destination);

            TextMessage message = session.createTextMessage();
            for (int i = 0; i < 10; i++) {
                message.setText("hello world!!!" + i+"--->102");
                producer.send(message);
            }

            session.commit();
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
