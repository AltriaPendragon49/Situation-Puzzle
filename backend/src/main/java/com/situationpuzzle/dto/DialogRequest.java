package com.situationpuzzle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 对话请求参数
 */
@Data
@Schema(description = "对话请求参数")
public class DialogRequest {

    /**
     * 游戏记录ID
     */
    @NotNull(message = "游戏记录ID不能为空")
    @Schema(description = "游戏记录ID", required = true, example = "1")
    private Long gameRecordId;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", required = true, example = "1")
    private Long userId;

    /**
     * 对话内容
     */
    @NotBlank(message = "对话内容不能为空")
    @Schema(description = "对话内容", required = true, example = "这个房间里的镜子有什么特别之处吗？")
    private String content;

    /**
     * 对话序号
     */
    @NotNull(message = "对话序号不能为空")
    @Schema(description = "对话序号", required = true, example = "1")
    private Integer sequence;
}