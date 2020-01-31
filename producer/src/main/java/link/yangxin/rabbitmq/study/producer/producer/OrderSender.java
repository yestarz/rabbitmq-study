package link.yangxin.rabbitmq.study.producer.producer;

import com.alibaba.fastjson.JSON;
import link.yangxin.rabbitmq.study.model.Order;
import link.yangxin.rabbitmq.study.model.cons.Constants;
import link.yangxin.rabbitmq.study.producer.dao.BrokerMessageLogDao;
import link.yangxin.rabbitmq.study.producer.service.BrokerMessageLogService;
import link.yangxin.rabbitmq.study.producer.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author yangxin
 * @date 2020/1/31
 */
@Component
@Slf4j
public class OrderSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private BrokerMessageLogService brokerMessageLogService;

    // 回调确认方法
    private final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        /**
         * @param correlationData 消息id
         * @param ack 是否成功,没成功可能队列满了等原因
         * @param cause
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            log.info("broker 收到了消息！correlationData：{}", correlationData);
            String messageId = correlationData.getId();
            if (ack) {
                // 如果confirm返回成功，则执行更新
                brokerMessageLogService.updateSendStatus(messageId, Constants.ORDER_SEND_SUCCESS, new Date());
            } else {
                // 失败则进行具体的后续操作，重试或者补偿手段
                log.error("消息异常处理....");
            }
        }
    };

    //发送消息方法调用: 构建自定义对象消息
    public void sendOrder(Order order) {
        // 设置回调函数
        rabbitTemplate.setConfirmCallback(confirmCallback);
        //消息唯一ID
        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange", "order.ABC", order, correlationData);
        log.info("==== 开始发送消息：{} ===", JSON.toJSONString(order));
    }

}