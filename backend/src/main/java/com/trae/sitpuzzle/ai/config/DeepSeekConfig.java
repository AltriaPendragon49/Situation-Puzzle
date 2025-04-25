package com.trae.sitpuzzle.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ai.deepseek")
public class DeepSeekConfig {
    /**
     * DeepSeek API Key
     */
    private String apiKey = "sk-ODU2LTIxOTE4OTk3MzgyLTE3NDU1NjY3NDAxMTY=";

    /**
     * DeepSeek API Endpoint URL
     */
    private String apiUrl = "https://api.scnet.cn/api/llm/v1";

    /**
     * DeepSeek Model Name
     */
    private String modelName = "DeepSeek-R1-Distill-Qwen-7B";

    /**
     * Maximum tokens to generate
     */
    private Integer maxTokens = 2048;

    /**
     * Temperature for response generation
     */
    private Double temperature = 0.7;

    /**
     * Top P sampling
     */
    private Double topP = 0.9;
}