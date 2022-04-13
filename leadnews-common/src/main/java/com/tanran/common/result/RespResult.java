package com.tanran.common.result;

import java.io.Serializable;

import com.tanran.model.common.enums.ErrorCodeEnum;

import lombok.Data;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @since 3.0.x 2022/3/18 10:28
 * @description 通用结果返回类
 */
@Data
public class RespResult<T> implements Serializable {
    //结果代码
    private Integer code;
    //错误信息
    private String Msg;
    //数据信息
    private T data;

    public RespResult(){
        this.code = 200;
    };

    public RespResult(Integer code,String Msg){
        this.code = code;
        this.Msg = Msg;
    }

    public RespResult(Integer code,T data){
        this.code = code;
        this.data = data;
    }

    public RespResult(Integer code, String msg, T data) {
        this.code = code;
        this.Msg = msg;
        this.data = data;
    }

    public static RespResult OkResult(int code,String msg){
        return new RespResult(code,msg);
    }

    public  RespResult<?> ok(T data){
        this.data = data;
        return this;
    }

    public static RespResult okResult(int code,String msg){
        return new RespResult(code,null,msg);
    }

    public static RespResult errorResult(ErrorCodeEnum enums){
        return setAppHttpCodeEnum(enums,enums.getErrorMessage());
    }

    public static RespResult errorResult(ErrorCodeEnum enums, String errorMessage){
        return setAppHttpCodeEnum(enums,errorMessage);
    }

    public static RespResult setAppHttpCodeEnum(ErrorCodeEnum enums){
        return okResult(enums.getCode(),enums.getErrorMessage());
    }

    private static RespResult setAppHttpCodeEnum(ErrorCodeEnum enums, String errorMessage){
        return okResult(enums.getCode(),errorMessage);
    }


    public static RespResult okResult(Object data) {
        RespResult result = setAppHttpCodeEnum(ErrorCodeEnum.SUCCESS, ErrorCodeEnum.SUCCESS.getErrorMessage());
        if(data!=null) {
            result.setData(data);
        }
        return result;
    }

    public static  RespResult okResult(ErrorCodeEnum errorCodeEnum){
        return new RespResult(errorCodeEnum.getCode(), errorCodeEnum.getErrorMessage());
    }


}
