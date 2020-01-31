package link.yangxin.rabbitmq.study.producer.dao;

import link.yangxin.rabbitmq.study.model.BrokerMessageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @author yangxin
 * @date 2020/1/31
 */
public interface BrokerMessageLogDao extends JpaRepository<BrokerMessageLog, Integer> {

    /**
     * 查询未处理的消息记录
     *
     * @param status
     * @param time
     * @return
     */
    @Query("from BrokerMessageLog where status = :status and nextRetry <= :time")
    List<BrokerMessageLog> findNoDealMessage(@Param("status") String status, @Param("time") Date time);

    /**
     * 修改状态
     *
     * @param messageId
     * @param status
     * @param updateTime
     */
    @Modifying
    @Query("update BrokerMessageLog set status = :status,updateTime = :updateTime where messageId = :messageId")
    void updateSendStatus(@Param("messageId") String messageId, @Param("status") String status, @Param("updateTime") Date updateTime);

}
