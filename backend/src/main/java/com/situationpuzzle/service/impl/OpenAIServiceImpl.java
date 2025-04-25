package com.situationpuzzle.service.impl;

import com.situationpuzzle.config.OpenAIConfig;
import com.situationpuzzle.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Service // Uncomment or use @Primary or qualifiers if enabling this implementation
public class OpenAIServiceImpl implements AiService {

    private final RestTemplate restTemplate;
    private final OpenAIConfig openAIConfig;

    @Autowired
    public OpenAIServiceImpl(RestTemplate restTemplate, OpenAIConfig openAIConfig) {
        this.restTemplate = restTemplate;
        this.openAIConfig = openAIConfig;
    }

    @Override
    public String chatCompletion(List<Map<String, String>> messages, String model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAIConfig.getApiKey());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model); // e.g., "gpt-3.5-turbo"
        requestBody.put("messages", messages);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    openAIConfig.getApiUrl(),
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // Extract content similarly to DeepSeekServiceImpl, adjust based on OpenAI response structure
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    if (message != null && message.containsKey("content")) {
                        return (String) message.get("content");
                    }
                }
            } else {
                System.err.println("Error calling OpenAI API: " + response.getStatusCode() + " " + response.getBody());
                return "Error: Could not get response from OpenAI.";
            }
        } catch (Exception e) {
            System.err.println("Exception calling OpenAI API: " + e.getMessage());
            e.printStackTrace();
            return "Error: Exception during OpenAI call.";
        }
        return "Error: Unexpected issue during OpenAI call.";
    }
}