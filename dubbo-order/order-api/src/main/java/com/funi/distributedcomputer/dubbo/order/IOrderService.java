package com.funi.distributedcomputer.dubbo.order;


import com.funi.distributedcomputer.dubbo.order.dto.DoOrderRequest;
import com.funi.distributedcomputer.dubbo.order.dto.DoOrderResponse;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface IOrderService {

    /**
     * 下单操作
     */
    DoOrderResponse doOrder(DoOrderRequest request);

}
