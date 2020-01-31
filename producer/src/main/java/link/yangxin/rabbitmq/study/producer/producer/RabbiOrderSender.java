package link.yangxin.rabbitmq.study.producer.producer;

import link.yangxin.rabbitmq.study.model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yangxin
 * @date 2020/1/31
 */
@Component
public class RabbiOrderSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    //发送消息方法调用: 构建自定义对象消息
    public void sendOrder(Order order) {
        //消息唯一ID
        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange", "order.ABC", order, correlationData);
    }


}