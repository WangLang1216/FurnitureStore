package com.summer.commonmodule.response;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;


/**
 * 统一返回数据
 * @author WangLang
 * @param <T>
 */
@Data
@ToString
@Accessors(chain = true)
public class ResponseEntity<T> implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ResponseEntity.class);

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    public Boolean isSuccess() {
        return Objects.equals(ResponseEnum.SUCCESS.getCode(), this.code);
    }

    public static <T> ResponseEntity<T> success(T data) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setData(data);
        responseEntity.setCode(ResponseEnum.SUCCESS.getCode());
        return responseEntity;
    }

    public static <T> ResponseEntity<T> success() {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setCode(ResponseEnum.SUCCESS.getCode());
        responseEntity.setMsg(ResponseEnum.SUCCESS.getMsg());
        return responseEntity;
    }

    /**
     * 前端显示失败消息
     * @param msg 失败消息
     * @return
     */
    public static <T> ResponseEntity<T> showFailMsg(String msg) {
        logger.error(msg);
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setMsg(msg);
        responseEntity.setCode(ResponseEnum.SHOW_FAIL.getCode());
        return responseEntity;
    }

    public static <T> ResponseEntity<T> fail(ResponseEnum responseEnum) {
        logger.error(responseEnum.toString());
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setMsg(responseEnum.getMsg());
        responseEntity.setCode(responseEnum.getCode());
        return responseEntity;
    }

    public static <T> ResponseEntity<T> fail(ResponseEnum responseEnum, T data) {
        logger.error(responseEnum.toString());
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setMsg(responseEnum.getMsg());
        responseEntity.setCode(responseEnum.getCode());
        responseEntity.setData(data);
        return responseEntity;
    }

    public static <T> ResponseEntity<T> transform(ResponseEntity<?> oldServerResponseEntity) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setMsg(oldServerResponseEntity.getMsg());
        responseEntity.setCode(oldServerResponseEntity.getCode());
        logger.error(responseEntity.toString());
        return responseEntity;
    }

}
