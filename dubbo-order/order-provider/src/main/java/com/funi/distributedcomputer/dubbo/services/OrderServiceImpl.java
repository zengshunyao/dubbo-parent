package com.funi.distributedcomputer.dubbo.services;

import com.funi.distributedcomputer.dubbo.dal.persistence.OrderMapper;
import com.funi.distributedcomputer.dubbo.order.IOrderService;
import com.funi.distributedcomputer.dubbo.order.dto.DoOrderRequest;
import com.funi.distributedcomputer.dubbo.order.dto.DoOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
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
