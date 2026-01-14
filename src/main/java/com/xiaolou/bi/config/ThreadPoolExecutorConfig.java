package com.xiaolou.bi.config;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolExecutorConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        // 创建线程工厂
        ThreadFactory threadFactory = new ThreadFactory() {
            // 初始化线程数为1
            private int count =1;

            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("线程" + count);
                count++;
                return thread;
            }
        };
        return new ThreadPoolExecutor(3, 6, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4), threadFactory);
    }
}
