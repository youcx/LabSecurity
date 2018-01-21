package com.example.you.lsmisclient.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by You on 2018/1/18.
 */

public class HttpManager {
    //服务端基地址
    public static final String BASE_URL="http://222.196.33.254:8080/rm_httpserver/";
    //请求超时时间
    private static final int DEFAULT_TIME_OUT=5;
    //retrofit
    private Retrofit retrofit;
    //自定义的接口
    private HttpApi mHttpApi;
    private HttpManager()
    {
        //创建OkHttpClient
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//链接超时时间
        //添加公共参数拦截器
        builder.addInterceptor(new MyIntercepteor());
        //retrofit实例化
        retrofit=new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        //接口实例
        mHttpApi=retrofit.create(HttpApi.class);
    }

    /**
     * 单例
     */
    private static class SingletonHolder{
        private static final HttpManager INSTANCE=new HttpManager();
    }

    /**
     * 获取单例
     * @return
     */
    public static HttpManager getInstance()
    {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 得到接口实例
     * @return
     */
    public static HttpApi getApi(){
        return SingletonHolder.INSTANCE.mHttpApi;
    }

    /**
     * okhttp拦截器
     */
    private class MyIntercepteor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            return null;
        }
    }
}
