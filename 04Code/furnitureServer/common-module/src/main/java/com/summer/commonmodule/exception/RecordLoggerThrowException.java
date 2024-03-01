package com.summer.commonmodule.exception;

import com.summer.commonmodule.response.ResponseEnum;
import org.slf4j.Logger;

/**
 * 记录日志和抛出异常
 * @author WangLang
 */
public class RecordLoggerThrowException {

    /**
     * 记录日志和抛出异常
     * @param responseEnum 信息提示
     * @param logger 抛出异常类
     */
    public static void record(ResponseEnum responseEnum, Logger logger) {
        logger.error(responseEnum.getMsg());
        throw new BusinessException(responseEnum.getCode(), responseEnum.getMsg());
    }

    /**
     * 记录日志和抛出异常
     * @param responseEnum 信息提示
     * @param errMsg 异常信息
     * @param logger 抛出异常类
     */
    public static void record(ResponseEnum responseEnum, String errMsg, Logger logger) {
        logger.error(responseEnum.getMsg(), "：", errMsg);
        throw new BusinessException(responseEnum.getCode(), responseEnum.getMsg() + "：" + errMsg);
    }


    /**
     * 记录日志和抛出异常
     * @param errCode 错误码
     * @param errMsg 异常信息
     * @param logger 抛出异常类
     */
    public static void record(Integer errCode, String errMsg, Logger logger) {
        logger.error(errMsg);
        throw new BusinessException(errCode, errMsg);
    }
}
