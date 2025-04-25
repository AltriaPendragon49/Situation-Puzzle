package com.situationpuzzle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 游戏查询请求参数
 */
@Data
@Schema(description = "游戏查询请求参数")
public class GameQueryRequest {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", example = "1")
    private Long userId;

    /**
     * 故事类型：1-推理，2-悬疑，3-恐怖，4-科幻
     */
    @Schema(description = "故事类型：1-推理，2-悬疑，3-恐怖，4-科幻", example = "1")
    private Integer type;

    /**
     * 难度级别：1-简单，2-中等，3-困难
     */
    @Schema(description = "难度级别：1-简单，2-中等，3-困难", example = "2")
    private Integer difficulty;

    /**
     * 游戏状态：0-进行中，1-已完成，2-已放弃
     */
    @Schema(description = "游戏状态：0-进行中，1-已完成，2-已放弃", example = "1")
    private Integer status;

    /**
     * 页码
     */
    @Schema(description = "页码", example = "1")
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    @Schema(description = "每页条数", example = "10")
    private Integer pageSize = 10;
}