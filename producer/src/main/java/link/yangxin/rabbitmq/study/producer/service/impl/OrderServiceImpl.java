package link.yangxin.rabbitmq.study.producer.service.impl;

import com.alibaba.fastjson.JSON;
import link.yangxin.rabbitmq.study.model.BrokerMessageLog;
import link.yangxin.rabbitmq.study.model.Order;
import link.yangxin.rabbitmq.study.model.cons.Constants;
import link.yangxin.rabbitmq.study.producer.dao.BrokerMessageLogDao;
import link.yangxin.rabbitmq.study.producer.dao.OrderDao;
import link.yangxin.rabbitmq.study.producer.producer.OrderSender;
import link.yangxin.rabbitmq.study.producer.service.OrderService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author yangxin
 * @date 2020/1/31
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private BrokerMessageLogDao brokerMessageLogDao;

    @Resource
    private OrderSender orderSender;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(Order order) {
        orderDao.save(order);
        BrokerMessageLog log = new BrokerMessageLog();
        log.setMessageId(order.getMessageId());
        log.setMessage(JSON.toJSONString(order));
        log.setTryCount(0);
        log.setStatus(Constants.ORDER_SENDING);
        log.setNextRetry(DateUtils.addMinutes(new Date(), Constants.ORDER_TIMEOUT));
        log.setCreateTime(new Date());
        log.setUpdateTime(new Date());
        brokerMessageLogDao.save(log);
        orderSender.sendOrder(order);
    }


}