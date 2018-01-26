package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/22.
 */

public class Result<T> {
    private T data;
    private int status;
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{\"Result\":{"
                + "\"data\":" + data
                + ", \"status\":\"" + status + "\""
                + ", \"message\":\"" + message + "\""
                + "}}";
    }
}
