package link.yangxin.rabbitmq.study.producer.service;

import link.yangxin.rabbitmq.study.model.BrokerMessageLog;

import java.util.Date;

/**
 * @author yangxin
 * @date 2020/1/31
 */
public interface BrokerMessageLogService {

    /**
     * 重新发送消息
     *
     * @param brokerMessageLog
     */
    void resendMessage(BrokerMessageLog brokerMessageLog);

    /**
     * 修改发送状态
     *
     * @param messageId
     * @param status
     * @param updateTime
     */
    void updateSendStatus(String messageId, String status, Date updateTime);


}