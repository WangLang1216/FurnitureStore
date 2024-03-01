package com.summer.commonmodule.exception;

import com.summer.commonmodule.response.ResponseEntity;
import com.summer.commonmodule.response.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常捕获
 * @author WangLang
 */
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     * @param request 请求
     * @param e 异常信息
     * @return 异常信息
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<String> businessExceptionHandler(HttpServletRequest request, BusinessException e) {
        logger.error("A business exception occurred, error reason:{}", e.getErrMsg());
        return ResponseEntity.showFailMsg(e.getErrMsg());
    }

    /**
     * 处理参数异常
     * @param request 请求
     * @param e 异常信息
     * @return 异常信息
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        logger.error("Parameter exception, error reason:{}", defaultMessage);
        return ResponseEntity.showFailMsg(defaultMessage);
    }

    /**
     * 处理空指针异常
     * @param request 请求
     * @param e 异常信息
     * @return 异常信息
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<String> nullPointerExceptionHandler(HttpServletRequest request, NullPointerException e) {
        logger.error("A null pointer exception occurred, error reason:", e);
        return ResponseEntity.fail(ResponseEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理其他异常
     * @param request 请求
     * @param e 异常信息
     * @return 异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> exceptionHandler(HttpServletRequest request, Exception e) {
        logger.error("Unknown exception, error reason:{}", e.getMessage(), e);
        return ResponseEntity.fail(ResponseEnum.INTERNAL_SERVER_ERROR);
    }

}
