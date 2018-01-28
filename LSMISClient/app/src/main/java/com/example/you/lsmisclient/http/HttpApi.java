package com.example.you.lsmisclient.http;

import com.example.you.lsmisclient.bean.LabDetailLevel;
import com.example.you.lsmisclient.bean.LabId;
import com.example.you.lsmisclient.bean.LabInfo;
import com.example.you.lsmisclient.bean.LabLevel;
import com.example.you.lsmisclient.bean.Result;


import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by You on 2018/1/18.
 */

public interface HttpApi {

    /**
     * 请求实验室信息
     * @param labid
     * @return
     */
    @POST("labinfor/labinfor")
    Observable<Result<LabInfo>> getLabInfo(@Query("ID") int labid);

    /**
     * 修改实验室信息
     * @param labInfo
     * @return
     */
    @POST("labinfor/submitinfor")
    Observable<Result> changeLabInfo(@Query("ID") int labid,@Body LabInfo labInfo);

    /**
     * http请求测试
     * @param b
     * @return
     */
    @POST("AppFiftyToneGraph/videoLink")
    Observable<Result> testHttp(@Body boolean b);

    /**
     * 获取实验室等级
     * @return
     */
    @GET("labinfor/leveloption")
    Observable<Result<List<LabLevel>>> getLabLevelList();

    /**
     * 获取实验室具体等级
     * @param levelId
     * @return
     */
    @POST("labinfor/detaillevel")
    Observable<Result<List<LabDetailLevel>>> getLabDetailLevel(@Query("levelId") int levelId);

    @GET("labinfor/department")
    Observable<Result<String>> getDepartmentList();
}
