package com.situationpuzzle.service;

import java.util.List;
import java.util.Map;

/**
 * AI Service Interface
 */
public interface AiService {

    /**
     * Call AI model for chat completion.
     *
     * @param messages List of message maps (e.g., [{"role": "user", "content": "Hello"}])
     * @param model    The model to use (e.g., "deepseek-chat")
     * @return AI response content
     */
    String chatCompletion(List<Map<String, String>> messages, String model);

    // Add other AI related methods here if needed

}