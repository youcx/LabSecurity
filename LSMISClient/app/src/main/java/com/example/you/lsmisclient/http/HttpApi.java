package com.example.you.lsmisclient.http;

import com.example.you.lsmisclient.bean.LabInfo;
import com.example.you.lsmisclient.bean.Result;


import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by You on 2018/1/18.
 */

public interface HttpApi {

    /**
     * 请求实验室信息
     * @param labInfo
     * @return
     */
    @POST("AppFiftyToneGraph/videoLink")
    Observable<Result> getLabInfo(@Body LabInfo labInfo);

    /**
     * 修改实验室信息
     * @param labInfo
     * @return
     */
    @POST("labinfor/submitinfor")
    Observable<Result> changeLabInfo(@Body LabInfo labInfo);

    /**
     * http请求测试
     * @param b
     * @return
     */
    @POST("AppFiftyToneGraph/videoLink")
    Observable<Result> testHttp(@Body boolean b);
}
