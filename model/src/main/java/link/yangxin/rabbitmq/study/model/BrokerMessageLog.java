package link.yangxin.rabbitmq.study.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "t_broker_message_log")
public class BrokerMessageLog implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String messageId;

    // 消息内容
    private String message;

    // 重试次数
    private Integer tryCount;

    // 投递状态 0投送中 1投送成功 2投送失败
    private String status;

    // 下次重试时间
    private Date nextRetry;

    private Date createTime;

    private Date updateTime;

}

