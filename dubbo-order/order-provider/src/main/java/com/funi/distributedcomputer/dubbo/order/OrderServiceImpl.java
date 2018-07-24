package com.funi.distributedcomputer.dubbo.order;

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
public class OrderServiceImpl implements IOrderService {

    @Override
    public DoOrderResponse doOrder(DoOrderRequest doOrderRequest) {
        System.out.println("曾经来过：" + doOrderRequest);
        DoOrderResponse response = new DoOrderResponse();
        response.setCode("200");
        response.setMemo("处理成功");
        response.setData(null);
        return response;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
