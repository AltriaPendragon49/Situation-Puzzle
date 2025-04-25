package com.trae.sitpuzzle.ai.service;

import com.trae.sitpuzzle.ai.config.DeepSeekConfig;
import com.trae.sitpuzzle.ai.model.StoryGenerationRequest;
import com.trae.sitpuzzle.ai.model.StoryGenerationResponse;
import com.trae.sitpuzzle.common.exception.AIServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoryGenerationService {

    @Autowired
    private DeepSeekConfig deepSeekConfig;

    @Autowired
    private PromptTemplateService promptTemplateService;

    public StoryGenerationResponse generateStory(StoryGenerationRequest request) {
        try {
            // 获取故事生成提示模板
            String promptTemplate = promptTemplateService.getStoryGenerationTemplate(request.getTheme());
            
            // 根据难度级别获取提示策略
            String difficultyStrategy = promptTemplateService.getDifficultyStrategy(request.getDifficulty());
            
            // 组合最终提示
            String finalPrompt = String.format("%s\n%s", promptTemplate, difficultyStrategy);
            
            // 调用DeepSeek API生成故事
            String generatedStory = deepSeekConfig.getClient().generateText(finalPrompt);
            
            // 解析并返回生成的故事
            return StoryGenerationResponse.builder()
                    .story(generatedStory)
                    .theme(request.getTheme())
                    .difficulty(request.getDifficulty())
                    .build();
            
        } catch (Exception e) {
            throw new AIServiceException("故事生成失败", e);
        }
    }
}