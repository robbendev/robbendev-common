package com.bailuntec.common.base;

/**
 * <p>
 *
 * </p>
 *
 * @param <T>
 */
public class BaseResult<T> {


    public static final boolean CODE_SUCCESS = true;
    public static final boolean CODE_ERROR = false;


    public static final String APP_DEFINE_ERR = "参数错误或者操作未成功";// 返回信息-失败
    public static final String APP_DEFINE_SUC = "操作成功";// 返回信息-成功


    private boolean success;    // 返回状态
    private String code;       //返回状态码
    private String message;   //返回信息
    private T data;          // 返回数据

    public BaseResult() {
        this.success = CODE_SUCCESS;
        this.code = "200";
        this.message = APP_DEFINE_SUC;
    }

    public BaseResult(boolean success) {
        this.success = success;
        if (success) {
            this.message = APP_DEFINE_SUC;
        } else {
            this.message = APP_DEFINE_ERR;
        }
    }

    public BaseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public BaseResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public BaseResult(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static <T> BaseResult<T> success() {
        return new BaseResult<>(CODE_SUCCESS, "200", APP_DEFINE_SUC, null);
    }

    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<>(CODE_SUCCESS, "200", APP_DEFINE_SUC, data);
    }

    public boolean getSuccess() {
        return success;
    }

    public BaseResult<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BaseResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
//        if (code != 200) {
//            throw new RRException(code, message);
//        }
        return data;
    }

    public BaseResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "JsonResult [success=" + success + ", message=" + message
                + ", data=" + data + "]";
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
