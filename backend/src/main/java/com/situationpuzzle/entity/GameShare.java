package com.situationpuzzle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 游戏分享实体类
 */
@Data
@TableName("game_share")
public class GameShare {

    /**
     * 分享ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 游戏记录ID
     */
    private Long gameRecordId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 分享码
     */
    private String shareCode;

    /**
     * 分享标题
     */
    private String title;

    /**
     * 分享描述
     */
    private String description;

    /**
     * 分享链接
     */
    private String shareUrl;

    /**
     * 分享状态：0-无效，1-有效
     */
    private Integer status;

    /**
     * 访问次数
     */
    private Integer visitCount;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

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