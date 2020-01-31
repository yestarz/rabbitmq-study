package link.yangxin.rabbitmq.study.producer.service.impl;

import com.alibaba.fastjson.JSON;
import link.yangxin.rabbitmq.study.model.BrokerMessageLog;
import link.yangxin.rabbitmq.study.model.Order;
import link.yangxin.rabbitmq.study.model.cons.Constants;
import link.yangxin.rabbitmq.study.producer.dao.BrokerMessageLogDao;
import link.yangxin.rabbitmq.study.producer.producer.OrderSender;
import link.yangxin.rabbitmq.study.producer.service.BrokerMessageLogService;
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
public class BrokerMessageLogServiceImpl implements BrokerMessageLogService {

    @Resource
    private BrokerMessageLogDao brokerMessageLogDao;

    @Resource
    private OrderSender orderSender;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resendMessage(BrokerMessageLog brokerMessageLog) {
        int retryCount = brokerMessageLog.getTryCount() == null ? 0 : brokerMessageLog.getTryCount();

        // 已经投递3次以上
        if (retryCount >= 3) {
            this.updateSendStatus(brokerMessageLog.getMessageId(), Constants.ORDER_SEND_FAILURE, new Date());
            return;
        }

        brokerMessageLog.setUpdateTime(new Date());
        brokerMessageLog.setNextRetry(DateUtils.addMinutes(new Date(), Constants.ORDER_TIMEOUT));
        brokerMessageLog.setTryCount(retryCount + 1);
        brokerMessageLogDao.save(brokerMessageLog);

        String message = brokerMessageLog.getMessage();
        Order order = JSON.parseObject(message, Order.class);

        // mq再次发送消息
        orderSender.sendOrder(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSendStatus(String messageId, String status, Date updateTime) {
        brokerMessageLogDao.updateSendStatus(messageId, status, updateTime);
    }
}