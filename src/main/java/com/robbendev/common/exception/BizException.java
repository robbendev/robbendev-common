package com.robbendev.common.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用业务异常
 *
 * @author robbendev
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -4636716497382947499L;

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 错误详情
     */
    private Object data;

    /**
     * 错误信息存储
     */
    private Map<String, Object> errorMap = new HashMap<>();

    private BizException() {

    }


    public BizException(String message) {
        this.code = 500;
        this.message = message;
    }

    public BizException(String format, Object... args) {
        this.code = 500;
        this.message = String.format(format, args);
    }


    public BizException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.putErrorMap(code, message, null);
    }

    public BizException(int code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
        this.putErrorMap(code, message, data);
    }

    private void putErrorMap(int code, String message, Object data) {
        this.errorMap.put("code", code);
        this.errorMap.put("message", message);
        this.errorMap.put("data", data);
    }

}
