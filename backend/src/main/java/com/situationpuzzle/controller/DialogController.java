package com.situationpuzzle.controller;

import com.situationpuzzle.dto.DialogRequest;
import com.situationpuzzle.entity.DialogRecord;
import com.situationpuzzle.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 对话控制器
 */
@Tag(name = "对话管理", description = "多轮AI对话相关接口")
@RestController
@RequestMapping("/dialog")
@RequiredArgsConstructor
public class DialogController {

    /**
     * 发送对话消息
     *
     * @param request 对话请求参数
     * @return 对话回复
     */
    @Operation(summary = "发送对话消息")
    @PostMapping("/send")
    public Result<DialogRecord> sendMessage(@RequestBody @Valid DialogRequest request) {
        // TODO: 实现对话消息处理逻辑
        DialogRecord response = new DialogRecord();
        response.setId(System.currentTimeMillis());
        response.setGameRecordId(request.getGameRecordId());
        response.setUserId(request.getUserId());
        response.setType(1); // AI回复
        response.setContent("这是一个AI回复的示例内容，实际应调用AI服务生成回复。");
        response.setSequence(request.getSequence() + 1);
        return Result.success(response);
    }

    /**
     * 获取对话历史记录
     *
     * @param gameRecordId 游戏记录ID
     * @return 对话历史记录列表
     */
    @Operation(summary = "获取对话历史记录")
    @GetMapping("/history/{gameRecordId}")
    public Result<List<DialogRecord>> getDialogHistory(@PathVariable Long gameRecordId) {
        // TODO: 实现对话历史记录查询逻辑
        return Result.success(List.of());
    }

    /**
     * 评估用户回答
     *
     * @param dialogId 对话记录ID
     * @return 评估结果
     */
    @Operation(summary = "评估用户回答")
    @PostMapping("/evaluate/{dialogId}")
    public Result<String> evaluateAnswer(@PathVariable Long dialogId) {
        // TODO: 实现用户回答评估逻辑
        return Result.success("你的回答接近了真相，但还需要考虑更多细节。");
    }
}