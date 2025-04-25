package com.trae.sitpuzzle.ai.service;

import com.trae.sitpuzzle.ai.config.DeepSeekConfig;
import com.trae.sitpuzzle.ai.model.AnswerEvaluationRequest;
import com.trae.sitpuzzle.ai.model.AnswerEvaluationResponse;
import com.trae.sitpuzzle.common.exception.AIServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerEvaluationService {

    @Autowired
    private DeepSeekConfig deepSeekConfig;

    @Autowired
    private PromptTemplateService promptTemplateService;

    public AnswerEvaluationResponse evaluateAnswer(AnswerEvaluationRequest request) {
        try {
            // 获取评估提示模板
            String evaluationTemplate = promptTemplateService.getAnswerEvaluationTemplate();
            
            // 构建评估提示
            String finalPrompt = String.format("%s\n故事内容：%s\n用户回答：%s\n正确答案：%s",
                    evaluationTemplate,
                    request.getStoryContent(),
                    request.getUserAnswer(),
                    request.getCorrectAnswer());
            
            // 调用DeepSeek API进行评估
            String evaluationResult = deepSeekConfig.getClient().generateText(finalPrompt);
            
            // 解析评估结果
            double score = parseScore(evaluationResult);
            String feedback = extractFeedback(evaluationResult);
            
            // 返回评估结果
            return AnswerEvaluationResponse.builder()
                    .score(score)
                    .feedback(feedback)
                    .isCorrect(score >= 0.8)
                    .build();
            
        } catch (Exception e) {
            throw new AIServiceException("回答评估失败", e);
        }
    }
    
    private double parseScore(String evaluationResult) {
        // 从评估结果中提取分数
        try {
            String scoreStr = evaluationResult.split("分数：")[1].split("\\n")[0].trim();
            return Double.parseDouble(scoreStr) / 100.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    private String extractFeedback(String evaluationResult) {
        // 从评估结果中提取反馈内容
        try {
            return evaluationResult.split("反馈：")[1].trim();
        } catch (Exception e) {
            return "无法解析反馈内容";
        }
    }
}