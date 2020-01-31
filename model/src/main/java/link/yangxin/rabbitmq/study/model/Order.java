package link.yangxin.rabbitmq.study.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    // 存储消息发送的唯一标识
    private String messageId;


}
