package com.situationpuzzle.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回结果
 *
 * @param <T> 数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "统一返回结果")
public class Result<T> {

    /**
     * 状态码
     */
    @Schema(description = "状态码", example = "200")
    private Integer code;

    /**
     * 返回消息
     */
    @Schema(description = "返回消息", example = "操作成功")
    private String message;

    /**
     * 返回数据
     */
    @Schema(description = "返回数据")
    private T data;

    /**
     * 成功返回结果
     *
     * @param <T> 数据类型
     * @return 成功结果
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 成功返回结果
     *
     * @param data 返回数据
     * @param <T>  数据类型
     * @return 成功结果
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 成功返回结果
     *
     * @param message 返回消息
     * @param data    返回数据
     * @param <T>     数据类型
     * @return 成功结果
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    /**
     * 失败返回结果
     *
     * @param code    状态码
     * @param message 返回消息
     * @param <T>     数据类型
     * @return 失败结果
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 返回消息
     * @param <T>     数据类型
     * @return 失败结果
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }
}