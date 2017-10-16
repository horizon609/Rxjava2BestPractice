package com.example.echoplex_x.rxjava2bestpractice.bean;

/**
 * Created by wangting31 on 2017/10/16.
 */

public class ApiResponse<T> {
    private int code;
    private T data;
    private String error;
    private boolean show;

    public ApiResponse(int code, T data, String error, boolean show) {
        this.code = code;
        this.data = data;
        this.error = error;
        this.show = show;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return null == this.error && null != this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
