package link.yangxin.rabbitmq.study.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author yangxin
 * @date 2020/1/31
 */
@SpringBootApplication
@EntityScan(basePackages = "link.yangxin.rabbitmq.study.model")
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

}