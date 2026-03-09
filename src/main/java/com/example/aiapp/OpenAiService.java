package com.example.aiapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {

    @Value("${OPENAI_API_KEY:}")
    private String apiKey;

    @Value("${OPENAI_MODEL:gpt-4o-mini}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public ChatResponse ask(String userMessage) {
        if (apiKey == null || apiKey.isBlank()) {
            return new ChatResponse(
                    "你还没有配置 OPENAI_API_KEY。\n这是演示模式回复：请先配置 Key。\n你刚刚说的是：" + userMessage,
                    "demo-mode"
            );
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> payload = Map.of(
                "model", model,
                "temperature", 0.7,
                "messages", List.of(
                        Map.of("role", "system", "content", "你是一个耐心、友好的 AI 助手，回答简洁且容易理解。"),
                        Map.of("role", "user", "content", userMessage)
                )
        );

        try {
            OpenAiResponse response = restTemplate.postForObject(
                    "https://api.openai.com/v1/chat/completions",
                    new HttpEntity<>(payload, headers),
                    OpenAiResponse.class
            );

            String content = "模型没有返回内容。";
            if (response != null && response.choices() != null && !response.choices().isEmpty()) {
                OpenAiResponse.Message message = response.choices().get(0).message();
                if (message != null && message.content() != null && !message.content().isBlank()) {
                    content = message.content();
                }
            }
            return new ChatResponse(content, model);
        } catch (Exception ex) {
            throw new RuntimeException("调用模型失败: " + ex.getMessage(), ex);
        }
    }
}
