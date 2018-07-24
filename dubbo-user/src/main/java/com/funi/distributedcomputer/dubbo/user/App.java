package com.funi.distributedcomputer.dubbo.user;

import com.funi.distributedcomputer.dubbo.order.DoOrderRequest;
import com.funi.distributedcomputer.dubbo.order.DoOrderResponse;
import com.funi.distributedcomputer.dubbo.order.IOrderService;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        IOrderService orderService = null;

        DoOrderRequest request = new DoOrderRequest();
        request.setName("bug");
        DoOrderResponse response = orderService.doOrder(request);

        System.out.println(response);
    }
}
