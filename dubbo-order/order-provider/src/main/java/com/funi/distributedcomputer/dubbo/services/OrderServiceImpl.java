package com.funi.distributedcomputer.dubbo.services;

import com.funi.distributedcomputer.dubbo.dal.persistence.OrderMapper;
import com.funi.distributedcomputer.dubbo.order.IOrderService;
import com.funi.distributedcomputer.dubbo.order.dto.DoOrderRequest;
import com.funi.distributedcomputer.dubbo.order.dto.DoOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.JtaTransactionManager;

@Service(value = "orderService")
public class OrderServiceImpl implements IOrderService {

    @Autowired
    JtaTransactionManager springTransactionManager;

    @Autowired
    OrderMapper orderMapper;


    @Override
    public DoOrderResponse doOrder(DoOrderRequest request) {
        DoOrderResponse response = new DoOrderResponse();
        response.setCode("000000");
        return response;
    }
}
