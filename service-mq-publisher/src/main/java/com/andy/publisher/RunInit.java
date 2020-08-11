package com.andy.publisher;

import com.andy.core.bean.dto.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableBinding(Source.class)
@Slf4j
public class RunInit implements CommandLineRunner {
    @Resource
    private Source source;
    @Override
    public void run(String... args) throws Exception {
        for(int i=0;i<10;i++) {
            UserInfoDTO user = new UserInfoDTO();
            user.setNickname("min.lai"+i)
                    .setUsername("13826458956")
                    .setPassword("641230056@qq.com")
                    //.setGmtCreate(new Timestamp(System.nanoTime()))
                   // .setGmtUpdated(new Timestamp(System.nanoTime()))
                    .setId(Long.parseLong(i+""));
            Boolean result = source.output().send(MessageBuilder.withPayload(user).build());

            log.error(result + "");
        }
    }
}
