package com.situationpuzzle.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ai.openai")
public class OpenAIConfig {

    /**
     * OpenAI API Key
     */
    private String apiKey = "sk-ODU2LTIxOTE4OTk3MzgyLTE3NDU1NjY3NDAxMTY=";

    /**
     * OpenAI API Endpoint URL
     */
    private String apiUrl = "https://api.scnet.cn/api/llm/v1"; // Default URL
    
    private String modelName = "DeepSeek-R1-Distill-Qwen-7B";
}