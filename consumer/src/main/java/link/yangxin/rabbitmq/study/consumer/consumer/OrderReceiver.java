package link.yangxin.rabbitmq.study.consumer.consumer;

import com.rabbitmq.client.Channel;
import link.yangxin.rabbitmq.study.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yangxin
 * @date 2020/1/31
 */
@Component
@Slf4j
public class OrderReceiver {

    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "order-queue", durable = "true"),
                    exchange = @Exchange(name = "order-exchange", durable = "true", type = "topic"),
                    key = "order.*"
            )
    )
    public void onMessage(@Payload Order order, @Headers Map<String, Object> headers, Channel channel) throws Exception {
        log.info("收到消息，开始消费,订单id:{}", order.getId());
        // 手动签收消息
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 第二个参数是是否批量签收
        channel.basicAck(tag, false);
    }

}