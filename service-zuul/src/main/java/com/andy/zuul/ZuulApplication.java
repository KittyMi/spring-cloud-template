package com.andy.zuul;

import com.didispace.swagger.butler.EnableSwaggerButler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author min.lai
 * Zuul网关
 */
@EnableFeignClients(
        basePackages = {
                "com.andy.core.feign",
        }
)
@ComponentScans(value = @ComponentScan(
        basePackages = {
                "com.andy.core.config"
        }
))
@EnableZuulProxy
@EnableEurekaClient
@SpringCloudApplication
@EnableSwaggerButler
@Slf4j
public class ZuulApplication {

    public static void main(String[] args) throws UnknownHostException {
        Environment env = new SpringApplicationBuilder(ZuulApplication.class).run(args).getEnvironment();
        log.info(
                "\n----------------------------------------------------------\n\t"
                        + "Application '{}' is running! Access URLs:\n\t" + "Local: \t\thttp://127.0.0.1:{}\n\t"
                        + "External: \thttp://{}:{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"), env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"));

    }
}
