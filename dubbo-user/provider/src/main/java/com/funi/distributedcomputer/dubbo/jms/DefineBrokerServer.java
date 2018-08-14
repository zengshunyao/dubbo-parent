package com.funi.distributedcomputer.dubbo.jms;

import org.apache.activemq.broker.BrokerService;

/**
 * 自定义broker
 */
public class DefineBrokerServer {

    public static void main(String[] args) {
        BrokerService brokerService = new BrokerService();
        try {
            brokerService.setUseJmx(true);
            brokerService.addConnector("tcp://localhost:61616");
            brokerService.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
