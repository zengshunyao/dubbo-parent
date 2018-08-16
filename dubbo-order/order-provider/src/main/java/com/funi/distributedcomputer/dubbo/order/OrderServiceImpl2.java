package com.funi.distributedcomputer.dubbo.order;

import com.funi.distributedcomputer.dubbo.order.dto.DoOrderRequest;
import com.funi.distributedcomputer.dubbo.order.dto.DoOrderResponse;
import org.springframework.stereotype.Service;

/**********************************************************************
 * &lt;p&gt;文件名：${FILE_NAME} &lt;/p&gt;
 * &lt;p&gt;文件描述：${DESCRIPTION}(描述该文件做什么)
 * @project_name：dubbo-order
 * @author zengshunyao
 * @date 2018/7/24 16:38
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2014
 *                    All Rights Reserved.
 */
@Service("orderService2")
public class OrderServiceImpl2 implements IOrderService {

    @Override
    public DoOrderResponse doOrder(DoOrderRequest doOrderRequest) {
        System.out.println("曾经来过：2.0版本" + doOrderRequest);
        DoOrderResponse response = new DoOrderResponse();
        response.setCode("200");
        response.setMemo("处理成功2.0版本");
        response.setData(null);
        return response;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
