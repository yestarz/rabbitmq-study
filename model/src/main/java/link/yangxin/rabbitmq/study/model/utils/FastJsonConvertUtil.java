package link.yangxin.rabbitmq.study.model.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import link.yangxin.rabbitmq.study.model.Order;

public class FastJsonConvertUtil {
    public static Order convertJSONToObject(String message) {
        return JSON.parseObject(message, new TypeReference<Order>() {
        });
    }

    public static String convertObjectToJSON(Object obj) {
        return JSON.toJSONString(obj);
    }
}
