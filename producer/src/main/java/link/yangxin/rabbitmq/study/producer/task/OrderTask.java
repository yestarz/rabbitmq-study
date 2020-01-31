package link.yangxin.rabbitmq.study.producer.task;

import link.yangxin.rabbitmq.study.model.BrokerMessageLog;
import link.yangxin.rabbitmq.study.model.cons.Constants;
import link.yangxin.rabbitmq.study.producer.dao.BrokerMessageLogDao;
import link.yangxin.rabbitmq.study.producer.service.BrokerMessageLogService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author yangxin
 * @date 2020/1/31
 */
@EnableScheduling
@Component
public class OrderTask {

    @Resource
    private BrokerMessageLogService brokerMessageLogService;

    @Resource
    private BrokerMessageLogDao brokerMessageLogDao;

    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void deal() {
        List<BrokerMessageLog> list = brokerMessageLogDao.findNoDealMessage(Constants.ORDER_SENDING, new Date());
        if (list == null || list.isEmpty()) {
            return;
        }
        list.forEach((brokerMessageLog -> brokerMessageLogService.resendMessage(brokerMessageLog)));
    }

}