package com.trae.sitpuzzle.ai.service;

import com.trae.sitpuzzle.ai.config.DeepSeekConfig;
import com.trae.sitpuzzle.ai.model.ReasoningRequest;
import com.trae.sitpuzzle.ai.model.ReasoningResponse;
import com.trae.sitpuzzle.common.exception.AIServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReasoningGuidanceService {

    @Autowired
    private DeepSeekConfig deepSeekConfig;

    @Autowired
    private PromptTemplateService promptTemplateService;

    @Autowired
    private MCPRulesService mcpRulesService;

    public ReasoningResponse processReasoning(ReasoningRequest request) {
        try {
            // 获取推理引导提示模板
            String guidanceTemplate = promptTemplateService.getReasoningGuidanceTemplate();
            
            // 获取MCP分层规则
            String mcpRules = mcpRulesService.getMCPRules(request.getCurrentLayer());
            
            // 组合对话历史
            String dialogHistory = String.join("\n", request.getDialogHistory());
            
            // 构建最终提示
            String finalPrompt = String.format("%s\n%s\n对话历史：%s\n用户问题：%s",
                    guidanceTemplate,
                    mcpRules,
                    dialogHistory,
                    request.getUserQuestion());
            
            // 调用DeepSeek API生成回答
            String aiResponse = deepSeekConfig.getClient().generateText(finalPrompt);
            
            // 解析并返回推理结果
            return ReasoningResponse.builder()
                    .response(aiResponse)
                    .currentLayer(request.getCurrentLayer())
                    .isComplete(checkReasoningComplete(aiResponse))
                    .build();
            
        } catch (Exception e) {
            throw new AIServiceException("推理处理失败", e);
        }
    }
    
    private boolean checkReasoningComplete(String response) {
        // 检查推理是否完成的逻辑
        return response.contains("推理完成") || response.contains("谜题解开");
    }
}