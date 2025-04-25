package com.situationpuzzle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 对话记录实体类
 */
@Data
@TableName("dialog_record")
public class DialogRecord {

    /**
     * 记录ID
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
     * 对话类型：0-用户问题，1-AI回答
     */
    private Integer type;

    /**
     * 对话内容
     */
    private String content;

    /**
     * 对话序号
     */
    private Integer sequence;

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