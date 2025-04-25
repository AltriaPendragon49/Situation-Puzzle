package com.situationpuzzle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 游戏生成请求参数
 */
@Data
@Schema(description = "游戏生成请求参数")
public class GameGenerateRequest {

    /**
     * 故事类型：1-推理，2-悬疑，3-恐怖，4-科幻
     */
    @NotNull(message = "故事类型不能为空")
    @Min(value = 1, message = "故事类型不正确")
    @Max(value = 4, message = "故事类型不正确")
    @Schema(description = "故事类型：1-推理，2-悬疑，3-恐怖，4-科幻", required = true, example = "1")
    private Integer type;

    /**
     * 故事主题
     */
    @Schema(description = "故事主题", example = "密室")
    private String theme;

    /**
     * 难度级别：1-简单，2-中等，3-困难
     */
    @NotNull(message = "难度级别不能为空")
    @Min(value = 1, message = "难度级别不正确")
    @Max(value = 3, message = "难度级别不正确")
    @Schema(description = "难度级别：1-简单，2-中等，3-困难", required = true, example = "2")
    private Integer difficulty;
}