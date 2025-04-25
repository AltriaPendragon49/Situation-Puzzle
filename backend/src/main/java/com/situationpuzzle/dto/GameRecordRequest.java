package com.situationpuzzle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 游戏记录请求参数
 */
@Data
@Schema(description = "游戏记录请求参数")
public class GameRecordRequest {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", required = true, example = "1")
    private Long userId;

    /**
     * 故事ID
     */
    @NotNull(message = "故事ID不能为空")
    @Schema(description = "故事ID", required = true, example = "1")
    private Long storyId;

    /**
     * 游戏状态：0-进行中，1-已完成，2-已放弃
     */
    @NotNull(message = "游戏状态不能为空")
    @Min(value = 0, message = "游戏状态不正确")
    @Max(value = 2, message = "游戏状态不正确")
    @Schema(description = "游戏状态：0-进行中，1-已完成，2-已放弃", required = true, example = "0")
    private Integer status;

    /**
     * 游戏进度（百分比）
     */
    @Min(value = 0, message = "游戏进度不能小于0")
    @Max(value = 100, message = "游戏进度不能大于100")
    @Schema(description = "游戏进度（百分比）", example = "50")
    private Integer progress;
}