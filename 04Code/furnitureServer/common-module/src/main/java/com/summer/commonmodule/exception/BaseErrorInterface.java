package com.summer.commonmodule.exception;

/**
 * 异常处理信息
 * @author WangLang
 */
public interface BaseErrorInterface {

    /**
     * 错误码
     * @return
     */
    Integer getErrorCode();

    /**
     * 错误信息
     * @return
     */
    String getErrorMsg();
}
