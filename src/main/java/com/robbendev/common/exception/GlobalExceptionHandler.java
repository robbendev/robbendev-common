package com.bailuntec.common.exception;


import com.robbendev.common.base.BaseResult;
import com.robbendev.common.exception.BizException;
import com.robbendev.common.util.JsonUtilByJackson;
import com.robbendev.common.wrapper.RequestBakRequestWrapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 统一异常处理器
 *
 * @author robbendev
 */
@ControllerAdvice
@ResponseBody
@Slf4j
@ConditionalOnWebApplication
public class GlobalExceptionHandler {

    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * 自定义异常
     */
    @ExceptionHandler(BizException.class)
    public BaseResult<String> handleRRException(HttpServletRequest request,
                                                BizException e) {
        BaseResult<String> result = new BaseResult<>();
        result.setCode(e.getCode());
        result.setMessage(e.getMessage());

        log.error(e.getMessage());
        return result;
    }

    /**
     * 未捕获异常
     */
    @ExceptionHandler(Exception.class)
    public BaseResult<String> handleException(HttpServletRequest request,
                                              Exception ex) {
        BaseResult<String> result = new BaseResult<>();
        result.setCode("500");
        result.setMessage(ex.getMessage());

        RequestBakRequestWrapper bakRequestWrapper = (RequestBakRequestWrapper) request;

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);

        ErrorLog errorLog = ErrorLog.builder()
                .uri(bakRequestWrapper.getRequestURI())
                .param(JsonUtilByJackson.writeValueAsString(bakRequestWrapper.getParameterMap()))
                .payload(bakRequestWrapper.getCachedContent().toString())
                .errorMsg(ex.getMessage())
                .StackTrace(sw.toString())
                .build();


        //输出日志
        log.error("******异常开始*******");
        log.info("uri:{}", errorLog.getUri());
        log.info("param:{}", errorLog.getParam());
        log.info("payload:{}", errorLog.getPayload());
        log.error(ex.getMessage(), ex);
        log.error("******异常结束********");

        return result;
    }


    @Data
    @Builder
    static class ErrorLog {

        private String uri;
        private String traceId;
        private String param;
        private String payload;
        private String errorMsg;
        private String StackTrace;

    }
}
