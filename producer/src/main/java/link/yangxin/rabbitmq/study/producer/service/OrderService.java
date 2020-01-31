package link.yangxin.rabbitmq.study.producer.service;

import link.yangxin.rabbitmq.study.model.Order;

/**
 * @author yangxin
 * @date 2020/1/31
 */
public interface OrderService {

    /**
     * 新增订单
     *
     * @param order
     */
    void createOrder(Order order);

}