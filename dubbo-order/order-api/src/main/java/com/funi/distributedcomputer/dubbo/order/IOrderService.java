package com.funi.distributedcomputer.dubbo.order;

/**********************************************************************
 * &lt;p&gt;文件名：${FILE_NAME} &lt;/p&gt;
 * &lt;p&gt;文件描述：${DESCRIPTION}(描述该文件做什么)
 * @project_name：dubbo-order
 * @author zengshunyao
 * @date 2018/7/24 16:30
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2014
 *                    All Rights Reserved.
 */
public interface IOrderService {
    /**
     * 下单操作
     *
     * @param doOrderRequest
     * @return
     */
    DoOrderResponse doOrder(DoOrderRequest doOrderRequest);
}
