package link.yangxin.rabbitmq.study.producer.test;

import link.yangxin.rabbitmq.study.model.Order;
import link.yangxin.rabbitmq.study.producer.dao.OrderDao;
import link.yangxin.rabbitmq.study.producer.producer.RabbiOrderSender;
import link.yangxin.rabbitmq.study.producer.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author yangxin
 * @date 2020/1/31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderService orderService;

    @Resource
    private RabbiOrderSender rabbiOrderSender;

    @Test
    public void test(){
        List<Order> all = orderDao.findAll();
        System.out.println(all);
    }

    @Test
    public void testSendOrder(){
        Order order = new Order();
        order.setId(2018092102);
        order.setName("测试订单1");
        order.setMessageId(System.currentTimeMillis()+"$"+ UUID.randomUUID().toString());
        rabbiOrderSender.sendOrder(order);
    }

    @Test
    public void testSendOrder2(){
        Order order = new Order();
        order.setName("测试订单2");
        order.setMessageId(System.currentTimeMillis()+"$"+ UUID.randomUUID().toString());
        orderService.createOrder(order);
    }


}