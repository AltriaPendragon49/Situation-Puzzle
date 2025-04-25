package com.situationpuzzle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 游戏记录实体类
 */
@Data
@TableName("game_record")
public class GameRecord {

    /**
     * 记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 故事ID
     */
    private Long storyId;

    /**
     * 游戏状态：0-进行中，1-已完成，2-已放弃
     */
    private Integer status;

    /**
     * 游戏进度（百分比）
     */
    private Integer progress;

    /**
     * 游戏开始时间
     */
    private LocalDateTime startTime;

    /**
     * 游戏结束时间
     */
    private LocalDateTime endTime;

    /**
     * 游戏用时（秒）
     */
    private Integer duration;

    /**
     * 是否解谜成功：0-失败，1-成功
     */
    private Integer solved;

    /**
     * 分享码
     */
    private String shareCode;

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