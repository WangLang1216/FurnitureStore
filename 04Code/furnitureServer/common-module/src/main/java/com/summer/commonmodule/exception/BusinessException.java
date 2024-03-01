package com.summer.commonmodule.exception;

import com.summer.commonmodule.response.ResponseEnum;
import org.springframework.http.HttpStatus;

/**
 * 异常处理
 * @author WangLang
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer errCode;

    /**
     * 错误信息
     */
    private String errMsg;

    public BusinessException() {super();}

    public BusinessException(BaseErrorInterface errorInterface) {
        super(errorInterface.getErrorMsg());
        this.errCode = errorInterface.getErrorCode();
        this.errMsg = errorInterface.getErrorMsg();
    }

    public BusinessException(BaseErrorInterface errorInterface, Throwable cause) {
        super(errorInterface.getErrorMsg());
        this.errCode = errorInterface.getErrorCode();
        this.errMsg = errorInterface.getErrorMsg();
    }

    public BusinessException(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
        this.errCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public BusinessException(Integer errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BusinessException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg());
        this.errCode = responseEnum.getCode();
        this.errMsg = responseEnum.getMsg();
    }

    public BusinessException(Integer errCode, String errMsg, Throwable cause) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
