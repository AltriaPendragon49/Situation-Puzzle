package com.situationpuzzle.controller;

import com.situationpuzzle.dto.GameGenerateRequest;
import com.situationpuzzle.dto.GameQueryRequest;
import com.situationpuzzle.dto.GameRecordRequest;
import com.situationpuzzle.entity.GameRecord;
import com.situationpuzzle.entity.GameStory;
import com.situationpuzzle.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 游戏控制器
 */
@Tag(name = "游戏管理", description = "游戏生成、记录管理相关接口")
@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    /**
     * 参数化生成游戏
     *
     * @param request 游戏生成请求参数
     * @return 游戏故事信息
     */
    @Operation(summary = "参数化生成游戏")
    @PostMapping("/generate")
    public Result<GameStory> generateGame(@RequestBody @Valid GameGenerateRequest request) {
        // TODO: 实现游戏生成逻辑
        GameStory gameStory = new GameStory();
        gameStory.setId(1L);
        gameStory.setTitle("神秘的房间");
        gameStory.setContent("一个人被锁在一个密闭的房间里，房间里只有一张床、一把椅子和一面镜子。他最终是如何逃出去的？");
        gameStory.setType(request.getType());
        gameStory.setTheme(request.getTheme());
        gameStory.setDifficulty(request.getDifficulty());
        return Result.success(gameStory);
    }

    /**
     * 保存游戏记录
     *
     * @param request 游戏记录请求参数
     * @return 保存结果
     */
    @Operation(summary = "保存游戏记录")
    @PostMapping("/record")
    public Result<GameRecord> saveGameRecord(@RequestBody @Valid GameRecordRequest request) {
        // TODO: 实现游戏记录保存逻辑
        GameRecord gameRecord = new GameRecord();
        gameRecord.setId(1L);
        gameRecord.setUserId(request.getUserId());
        gameRecord.setStoryId(request.getStoryId());
        gameRecord.setStatus(request.getStatus());
        gameRecord.setProgress(request.getProgress());
        return Result.success(gameRecord);
    }

    /**
     * 查询游戏记录列表
     *
     * @param request 查询请求参数
     * @return 游戏记录列表
     */
    @Operation(summary = "查询游戏记录列表")
    @GetMapping("/records")
    public Result<List<GameRecord>> listGameRecords(GameQueryRequest request) {
        // TODO: 实现游戏记录查询逻辑
        return Result.success(List.of());
    }

    /**
     * 获取游戏记录详情
     *
     * @param id 记录ID
     * @return 游戏记录详情
     */
    @Operation(summary = "获取游戏记录详情")
    @GetMapping("/record/{id}")
    public Result<GameRecord> getGameRecord(@PathVariable Long id) {
        // TODO: 实现游戏记录详情查询逻辑
        GameRecord gameRecord = new GameRecord();
        gameRecord.setId(id);
        return Result.success(gameRecord);
    }

    /**
     * 分享游戏记录
     *
     * @param id 记录ID
     * @return 分享链接
     */
    @Operation(summary = "分享游戏记录")
    @PostMapping("/share/{id}")
    public Result<String> shareGameRecord(@PathVariable Long id) {
        // TODO: 实现游戏分享逻辑
        String shareUrl = "https://puzzle.example.com/share/" + id;
        return Result.success(shareUrl);
    }
}