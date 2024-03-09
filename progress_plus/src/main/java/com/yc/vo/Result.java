package com.yc.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private Integer code;
    private String msg;
    private boolean flag;
    private T data;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(Integer code,T data) {
        this.data = data;
        this.code = code;
    }
    // 仅用于文件上传的success
    public static Result success(String msg,String url){
        Result result = new Result<>();
        result.setCode(1);
        result.setMsg(msg);
        result.setData(url);
        result.setFlag(true);
        return result;
    }

    public static Result success() {
        Result result = new Result<>();
        result.setCode(1);
        result.setMsg("操作成功!");
        result.setFlag(true);
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result<>();
        result.setCode(1);
        result.setMsg(msg);
        result.setFlag(true);
        return result;
    }

    public static Result success(String msg,boolean flag) {
        Result result = new Result();
        result.setCode(1);
        result.setMsg(msg);
        result.setFlag(flag);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode(1);
        result.setMsg("操作成功!");
        result.setFlag(true);
        return result;
    }

    public static Result error(Integer code, String msg,boolean flag) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setFlag(flag);
        return result;
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setFlag(false);
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg(msg);
        result.setFlag(false);
        return result;
    }
}
