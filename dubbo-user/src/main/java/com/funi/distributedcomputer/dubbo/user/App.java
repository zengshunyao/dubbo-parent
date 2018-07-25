package com.funi.distributedcomputer.dubbo.user;

import com.funi.distributedcomputer.dubbo.order.DoOrderRequest;
import com.funi.distributedcomputer.dubbo.order.DoOrderResponse;
import com.funi.distributedcomputer.dubbo.order.IOrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("order-consumer.xml");

        System.out.println("Hello World!");
        IOrderService orderService = applicationContext.getBean("orderService", IOrderService.class);
        IOrderService orderService2 = applicationContext.getBean("orderService2", IOrderService.class);

        DoOrderRequest request = new DoOrderRequest();
        request.setName("bug");
        DoOrderResponse response = orderService.doOrder(request);
        System.out.println(response);

        DoOrderResponse response2 = orderService2.doOrder(request);
        System.out.println(response2);

//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
