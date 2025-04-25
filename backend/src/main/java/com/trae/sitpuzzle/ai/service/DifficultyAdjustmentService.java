package com.trae.sitpuzzle.ai.service;

import com.trae.sitpuzzle.ai.model.DifficultyAdjustmentRequest;
import com.trae.sitpuzzle.ai.model.DifficultyAdjustmentResponse;
import com.trae.sitpuzzle.common.enums.DifficultyLevel;
import com.trae.sitpuzzle.common.exception.AIServiceException;
import org.springframework.stereotype.Service;

@Service
public class DifficultyAdjustmentService {

    private static final double SCORE_THRESHOLD_INCREASE = 0.8;
    private static final double SCORE_THRESHOLD_DECREASE = 0.4;
    private static final int HISTORY_SIZE = 3;

    public DifficultyAdjustmentResponse adjustDifficulty(DifficultyAdjustmentRequest request) {
        try {
            // 计算最近几次游戏的平均得分
            double averageScore = calculateAverageScore(request.getRecentScores());
            
            // 获取当前难度级别
            DifficultyLevel currentLevel = request.getCurrentDifficulty();
            
            // 根据得分调整难度
            DifficultyLevel newLevel = determineNewDifficulty(currentLevel, averageScore);
            
            // 生成调整建议
            String adjustment = generateAdjustmentSuggestion(currentLevel, newLevel, averageScore);
            
            return DifficultyAdjustmentResponse.builder()
                    .newDifficulty(newLevel)
                    .suggestion(adjustment)
                    .build();
            
        } catch (Exception e) {
            throw new AIServiceException("难度调整失败", e);
        }
    }
    
    private double calculateAverageScore(List<Double> scores) {
        if (scores == null || scores.isEmpty()) {
            return 0.0;
        }
        return scores.stream()
                .limit(HISTORY_SIZE)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }
    
    private DifficultyLevel determineNewDifficulty(DifficultyLevel current, double averageScore) {
        if (averageScore >= SCORE_THRESHOLD_INCREASE) {
            return current.increase();
        } else if (averageScore <= SCORE_THRESHOLD_DECREASE) {
            return current.decrease();
        }
        return current;
    }
    
    private String generateAdjustmentSuggestion(DifficultyLevel current, DifficultyLevel newLevel, double score) {
        if (newLevel.compareTo(current) > 0) {
            return String.format("您的表现很出色（平均得分：%.2f），建议尝试更具挑战性的难度。", score);
        } else if (newLevel.compareTo(current) < 0) {
            return String.format("看起来当前难度可能有点高（平均得分：%.2f），建议尝试更适合的难度。", score);
        }
        return String.format("当前难度很适合您（平均得分：%.2f），继续保持！", score);
    }
}