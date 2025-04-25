package com.situationpuzzle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 游戏故事实体类
 */
@Data
@TableName("game_story")
public class GameStory {

    /**
     * 故事ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 故事标题
     */
    private String title;

    /**
     * 故事内容
     */
    private String content;

    /**
     * 故事类型：1-推理，2-悬疑，3-恐怖，4-科幻
     */
    private Integer type;

    /**
     * 故事主题
     */
    private String theme;

    /**
     * 难度级别：1-简单，2-中等，3-困难
     */
    private Integer difficulty;

    /**
     * 故事答案
     */
    private String answer;

    /**
     * 提示信息（JSON数组）
     */
    private String hints;

    /**
     * 创建者ID
     */
    private Long creatorId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer deleted;
}