package com.xiaolou.bi.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 配置（初始化RedissonClient对象单例）
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    private Integer database;

    private String host;

    private Integer port;

    // 如果你的redis默认没有设置密码则不用写
    private String password;

    @Bean
    public RedissonClient getRedissonClient() {
        // 1.创建配置对象
        Config config = new Config();
        config.useSingleServer()
                .setDatabase(database)
                .setAddress("redis://" + host + ":" + port);
        // 2.创建Redisson实例
        return Redisson.create(config);
    }
}
