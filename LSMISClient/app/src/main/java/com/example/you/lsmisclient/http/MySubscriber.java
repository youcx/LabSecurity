package com.example.you.lsmisclient.http;

import android.widget.Toast;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by You on 2018/3/10.
 */

public abstract class MySubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();if ( e instanceof HttpException){
            HttpException httpException= (HttpException) e;
            int code=httpException.code();
            String msg=httpException.getMessage();
            if (code==504){
                msg="网络不给力";
            }else if(code==404){
                msg="请求内容不存在！";
            }
        }else {

        }
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
