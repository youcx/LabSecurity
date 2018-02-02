package com.example.you.lsmisclient.http;

import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by You on 2018/1/18.
 */

public class HttpManager {
    //服务端基地址
    public static final String BASE_URL="http://119.29.201.35:8080/";
    //public static final String BASE_U0RL="http://www.izaodao.com/Api/";
    //请求超时时间
    private static final int DEFAULT_TIME_OUT=5;
    //retrofit
    private Retrofit retrofit;
    //自定义的接口
    private HttpApi mHttpApi;
    private HttpManager()
    {
//        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor(new HttpLogger());
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //创建OkHttpClient
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//链接超时时间
        //添加拦截器
        builder.addInterceptor(new MyIntercepteor());
        //builder.addNetworkInterceptor(httpLoggingInterceptor);
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
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Request request = chain.request();
           // request=request.newBuilder().addHeader("content-type","application/json").build();
            Logger.d(request.toString() + request.headers().toString());
            Log.i("拦截器",request.toString());
            okhttp3.Response response = chain.proceed(chain.request());
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            if (!TextUtils.isEmpty(content)) {
               Logger.json(content);
                Log.i("!!!!!!!!!!",content);
            }
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }


}
