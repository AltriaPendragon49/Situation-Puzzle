package com.situationpuzzle.controller;

import com.situationpuzzle.dto.LoginRequest;
import com.situationpuzzle.dto.RegisterRequest;
import com.situationpuzzle.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * 用户认证控制器
 */
@Tag(name = "用户认证", description = "用户登录注册相关接口")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * 用户登录
     *
     * @param request 登录请求参数
     * @return 登录结果
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<String> login(@RequestBody @Valid LoginRequest request) {
        // TODO: 实现登录逻辑
        return Result.success("登录成功，返回token");
    }

    /**
     * 用户注册
     *
     * @param request 注册请求参数
     * @return 注册结果
     */
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<Void> register(@RequestBody @Valid RegisterRequest request) {
        // TODO: 实现注册逻辑
        return Result.success();
    }

    /**
     * 退出登录
     *
     * @return 退出结果
     */
    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public Result<Void> logout() {
        // TODO: 实现退出登录逻辑
        return Result.success();
    }
}