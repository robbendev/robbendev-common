package com.robbendev.common.base;

/**
 * <p>
 * 通用响应结果
 * </p>
 *
 * @param <T>
 */
public class BaseResult<T> {

	// 返回信息-失败
	public static final String APP_DEFINE_ERR = "参数错误或者操作未成功";
	// 返回信息-成功
	public static final String APP_DEFINE_SUC = "操作成功";


	public static final String CODE_SUCCESS = "0";
	public static final String CODE_ERROR = "500";


	//返回状态码
	private String code;
	//返回信息
	private String message;
	// 返回数据
	private T data;

	public BaseResult() {
		this.code = "200";
		this.message = APP_DEFINE_SUC;
	}

	public BaseResult(boolean success) {
		if (success) {
			this.message = APP_DEFINE_SUC;
		} else {
			this.message = APP_DEFINE_ERR;
		}
	}

	public BaseResult(String message) {
		this.message = message;
	}

	public BaseResult(String message, T data) {
		this.message = message;
		this.data = data;
	}

	public BaseResult(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static <T> BaseResult<T> success() {
		return new BaseResult<>(CODE_SUCCESS, APP_DEFINE_SUC, null);
	}

	public static <T> BaseResult<T> success(T data) {
		return new BaseResult<>(CODE_SUCCESS, APP_DEFINE_SUC, data);
	}

	public String getMessage() {
		return message;
	}

	public BaseResult<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public T getData() {
		return data;
	}

	public BaseResult<T> setData(T data) {
		this.data = data;
		return this;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
