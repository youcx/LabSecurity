package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/22.
 */

public class Result<T> {
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Result{"+
                "result="+result+
                '}';
    }
}
