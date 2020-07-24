package com.andy.subscribe;

import com.alibaba.fastjson.JSON;
import com.andy.mybatis.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @author min.lai
 */
@EnableBinding(Sink.class)
@Slf4j
public class SinkReceiver {
    @StreamListener(Sink.INPUT)
    public void receive(UserInfo userInfo) {
        log.error("Received:"+ JSON.toJSONString(userInfo));
    }
}
