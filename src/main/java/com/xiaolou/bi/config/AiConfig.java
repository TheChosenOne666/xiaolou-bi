package com.xiaolou.bi.config;

import ai.z.openapi.ZhipuAiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ai")
@Data
public class AiConfig {

    private String apiKey;

    @Bean
    public ZhipuAiClient getZhipuAiClient() {
        return new ZhipuAiClient.Builder(apiKey).build();
    }
}
