package com.xiaolou.bi.manager;

import ai.z.openapi.ZhipuAiClient;
import ai.z.openapi.core.Constants;
import ai.z.openapi.service.model.*;
import com.xiaolou.bi.common.ErrorCode;
import com.xiaolou.bi.exception.BusinessException;
import io.reactivex.rxjava3.core.Flowable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class AiManager {
    @Resource
    private ZhipuAiClient zhipuAiClient;

    /**
     * 同步请求
     *
     * @param systemPrompt
     * @param userPrompt
     * @param temperature
     * @return
     */
    public String doSyncRequest(String systemPrompt, String userPrompt, Float temperature) {
        return doRequest(systemPrompt, userPrompt, Boolean.FALSE, temperature);
    }

    /**
     * 通用请求（简化消息传递）
     *
     * @param systemPrompt
     * @param userPrompt
     * @param stream
     * @param temperature
     * @return
     */
    public String doRequest(String systemPrompt, String userPrompt, Boolean stream, Float temperature) {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        ChatMessage systemChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemPrompt);
        chatMessageList.add(systemChatMessage);
        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userPrompt);
        chatMessageList.add(userChatMessage);
        //调用通用请求
        return doRequest(chatMessageList, stream, temperature);
    }

    /**
     * 通用请求
     *
     * @param messages
     * @param stream
     * @param temperature
     * @return
     */
    public String doRequest(List<ChatMessage> messages, Boolean stream, Float temperature) {
        // 创建聊天完成请求
        ChatCompletionCreateParams request = ChatCompletionCreateParams.builder()
                .model(Constants.ModelChatGLM4_5)
                .messages(messages)
                .stream(stream)
                .temperature(temperature)
                .build();
        try {
            // 发送请求
            ChatCompletionResponse response = zhipuAiClient.chat().createChatCompletion(request);
            // 获取回复
            return response.getData().getChoices().get(0).getMessage().toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * 通用流式请求（消息简化）
     *
     * @param systemPrompt
     * @param userPrompt
     * @param temperature
     * @return
     */
    public Flowable<ModelData> doStreamRequest(String systemPrompt, String userPrompt, Float temperature) {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemPrompt);
        chatMessageList.add(systemMessage);
        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), userPrompt);
        chatMessageList.add(userMessage);
        return doStreamRequest(chatMessageList, temperature);
    }

    /**
     * 通用流式请求
     *
     * @param messages
     * @param temperature
     * @return
     */
    public Flowable<ModelData> doStreamRequest(List<ChatMessage> messages, Float temperature) {
        // 创建聊天完成请求
        ChatCompletionCreateParams request = ChatCompletionCreateParams.builder()
                .model(Constants.ModelChatGLM4Flash250414)
                .messages(messages)
                .stream(true)
                .temperature(temperature)
                .build();

        // 发送请求
        ChatCompletionResponse response = zhipuAiClient.chat().createChatCompletion(request);
        // 获取回复
        return response.getFlowable();

    }

}
