package com.funi.distributedcomputer.dubbo.order;


import com.funi.distributedcomputer.dubbo.order.dto.DoOrderRequest;
import com.funi.distributedcomputer.dubbo.order.dto.DoOrderResponse;

public interface IOrderService {

    /**
     * 下单操作
     */
    DoOrderResponse doOrder(DoOrderRequest request);

}
