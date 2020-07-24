package com.andy;

import com.andy.mybatis.entity.UserInfo;
import com.andy.publisher.PublisherApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(classes = PublisherApplication.class)
@EnableBinding(Source.class)
public class TestMq {
    @Resource
    private Source source;
    @Test
    @Output(Source.OUTPUT)
    public void test(){
        UserInfo user=new UserInfo();
        user.setNickName("min.lai")
                .setPhone("13826458956")
                .setEmail("641230056@qq.com")
                .setGmtCreate(new Timestamp(System.nanoTime()))
                .setGmtUpdated(new Timestamp(System.nanoTime()))
                .setId(200L);
        Boolean result=source.output().send(MessageBuilder.withPayload(user).build());

        log.error(result+"");
    }

}
