package com.xiaolou.bi.ai;

import ai.z.openapi.ZhipuAiClient;
import ai.z.openapi.core.Constants;
import ai.z.openapi.service.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
public class ZhiPuAiTest {

    @Resource
    private ZhipuAiClient zhipuAiClient;

    @Test
    public void test() {
        // 初始化客户端
//        ZhipuAiClient client = ZhipuAiClient.builder().ofZHIPU()
//            .apiKey("YOUR_API_KEY")
//            .build();

        // 创建聊天完成请求
        ChatCompletionCreateParams request = ChatCompletionCreateParams.builder()
                .model(Constants.ModelChatGLM4Flash250414)
                .messages(Arrays.asList(
                        ChatMessage.builder()
                                .role(ChatMessageRole.USER.value())
                                .content("作为一个专业的大数据分析师，请帮我分析2026年Java后端的要求与市场岗位行情")
                                .build()
                ))
                .stream(false)
                .temperature(0.6f)
                .build();

        // 发送请求
        ChatCompletionResponse response = zhipuAiClient.chat().createChatCompletion(request);

        // 获取回复
        System.out.println(response.getData().getChoices().get(0).getMessage());
    }
}