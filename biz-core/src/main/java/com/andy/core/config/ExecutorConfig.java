package com.andy.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 * @author min.lai
 * @date 2020-07-09
 */
@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig implements AsyncConfigurer {
    /**
     * 配置异常处理机制
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex,method,params)->{
            log.error("异步线程执行失败。方法：[{}],异常信息[{}] : ", method, ex.getMessage(),ex);
        };
    }
    /**
     * 异步线程
     * @return
     */
    @Bean
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new VisiableThreadPoolTaskExecutor();
        //核心线程数
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        //最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(5);
        //配置队列大小
        threadPoolTaskExecutor.setQueueCapacity(30);
        //配置线程池前缀
        threadPoolTaskExecutor.setThreadNamePrefix("async-service-");
        //拒绝策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    /**
     * 业务线程
     * @return
     */
    @Bean
    public Executor customServiceExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor=new ThreadPoolTaskExecutor();
        //线程核心数目
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        //最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(10);
        //配置队列大小
        threadPoolTaskExecutor.setQueueCapacity(20);
        //配置线程池前缀
        threadPoolTaskExecutor.setThreadNamePrefix("custom-service-");
        //配置拒绝策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //数据初始化
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
        //打印队列的详细信息
        private void showThreadPoolInfo(String prefix){
            ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

            if(null==threadPoolExecutor){
                return;
            }

            log.info("{}, {},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                    this.getThreadNamePrefix(),
                    prefix,
                    threadPoolExecutor.getTaskCount(),
                    threadPoolExecutor.getCompletedTaskCount(),
                    threadPoolExecutor.getActiveCount(),
                    threadPoolExecutor.getQueue().size());
        }


        @Override
        public void execute(Runnable task) {
            showThreadPoolInfo("1. do execute");
            super.execute(task);
        }

        @Override
        public void execute(Runnable task, long startTimeout) {
            showThreadPoolInfo("2. do execute");
            super.execute(task, startTimeout);
        }

        @Override
        public Future<?> submit(Runnable task) {
            showThreadPoolInfo("1. do submit");
            return super.submit(task);
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            showThreadPoolInfo("2. do submit");
            return super.submit(task);
        }

        @Override
        public ListenableFuture<?> submitListenable(Runnable task) {
            showThreadPoolInfo("1. do submitListenable");
            return super.submitListenable(task);
        }

        @Override
        public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
            showThreadPoolInfo("2. do submitListenable");
            return super.submitListenable(task);
        }
    }
}
