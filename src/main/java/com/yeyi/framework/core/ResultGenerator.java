package com.yeyi.framework.core;

/**
 * 相应结果生成
 * @author 张国伟
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_ERROR_MESSAGE = "ERROR";

    /**
     * 返回成功
     * @return
     */
    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 返回成功携带数据
     * @param data
     * @return
     */
    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    /**
     * 返回失败显示信息
     * @param message
     * @return
     */
    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
    /**
     * 返回失败
     * @return
     */
    public static Result genFailResult() {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(DEFAULT_ERROR_MESSAGE);
    }
    /**
     * 通用返回
     * @return
     */
    public static Result genResult(boolean flag) {
        if (flag) {
            return genSuccessResult();
        } else {
            return genFailResult();
        }
    }
    /**
     * 返回未授权
     * @return
     */
    public static Result genUnauthorizedResult() {
        return new Result()
                .setCode(ResultCode.UNAUTHORIZED)
                .setMessage(DEFAULT_ERROR_MESSAGE);
    }
}
