package com.trae.sitpuzzle.ai.service;

import org.springframework.stereotype.Service;

@Service
public class PromptTemplateService {

    /**
     * 获取故事生成的提示模板
     * @param theme 故事主题
     * @return 故事生成提示模板
     */
    public String getStoryGenerationTemplate(String theme) {
        return String.format(
            "请根据以下主题创建一个海龟汤故事：\n" +
            "主题：%s\n" +
            "要求：\n" +
            "1. 故事要有明确的谜题和答案\n" +
            "2. 故事要有合理的逻辑性\n" +
            "3. 故事要有适当的长度（200-500字）\n" +
            "4. 答案要简洁明了",
            theme
        );
    }

    /**
     * 获取难度策略提示
     * @param difficulty 难度等级（1-5）
     * @return 难度策略提示
     */
    public String getDifficultyStrategy(Integer difficulty) {
        switch (difficulty) {
            case 1:
                return "难度要求：故事逻辑简单直接，线索明显，适合初学者。";
            case 2:
                return "难度要求：故事有一定转折，需要简单推理，线索较为明显。";
            case 3:
                return "难度要求：故事包含多个线索，需要综合分析，有一定难度。";
            case 4:
                return "难度要求：故事线索隐晦，需要深入思考和推理，具有挑战性。";
            case 5:
                return "难度要求：故事极具迷惑性，线索极其隐晦，需要缜密的逻辑推理。";
            default:
                return "难度要求：故事有一定转折，需要简单推理，线索较为明显。";
        }
    }

    /**
     * 获取推理引导的提示模板
     * @return 推理引导提示模板
     */
    public String getReasoningGuidanceTemplate() {
        return "请根据玩家的问题和当前故事信息，提供适当的引导：\n" +
               "1. 不要直接给出答案\n" +
               "2. 引导玩家思考关键线索\n" +
               "3. 适当提供启发性的问题\n" +
               "4. 保持故事的神秘感";
    }

    /**
     * 获取答案评估的提示模板
     * @return 答案评估提示模板
     */
    public String getAnswerEvaluationTemplate() {
        return "请评估玩家的答案是否正确：\n" +
               "1. 判断答案的准确性\n" +
               "2. 分析推理过程的合理性\n" +
               "3. 指出答案中的优点和不足\n" +
               "4. 给出改进建议（如果需要）";
    }
}