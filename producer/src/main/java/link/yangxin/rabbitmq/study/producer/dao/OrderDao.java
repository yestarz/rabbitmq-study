package link.yangxin.rabbitmq.study.producer.dao;

import link.yangxin.rabbitmq.study.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yangxin
 * @date 2020/1/31
 */
public interface OrderDao extends JpaRepository<Order,Integer> {
}
