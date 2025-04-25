package com.situationpuzzle.service.impl;

import com.situationpuzzle.config.DeepSeekConfig;
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

@Service
public class DeepSeekServiceImpl implements AiService {

    private final RestTemplate restTemplate;
    private final DeepSeekConfig deepSeekConfig;

    @Autowired
    public DeepSeekServiceImpl(RestTemplate restTemplate, DeepSeekConfig deepSeekConfig) {
        this.restTemplate = restTemplate;
        this.deepSeekConfig = deepSeekConfig;
    }

    @Override
    public String chatCompletion(List<Map<String, String>> messages, String model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(deepSeekConfig.getApiKey());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", messages);
        // Add other parameters like temperature, max_tokens if needed

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    deepSeekConfig.getApiUrl(),
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // Extract the content from the response
                // The exact structure depends on the DeepSeek API response format
                // Assuming a structure like: {"choices": [{"message": {"content": "..."}}]}
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    if (message != null && message.containsKey("content")) {
                        return (String) message.get("content");
                    }
                }
            } else {
                // Handle error response
                System.err.println("Error calling DeepSeek API: " + response.getStatusCode() + " " + response.getBody());
                return "Error: Could not get response from AI.";
            }
        } catch (Exception e) {
            System.err.println("Exception calling DeepSeek API: " + e.getMessage());
            e.printStackTrace();
            return "Error: Exception during AI call.";
        }
        return "Error: Unexpected issue during AI call.";
    }

    // Configuration for RestTemplate bean should be added in a @Configuration class
    // Example:
    // @Bean
    // public RestTemplate restTemplate() {
    //     return new RestTemplate();
    // }
}