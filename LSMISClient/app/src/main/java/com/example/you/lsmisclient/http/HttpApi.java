package com.example.you.lsmisclient.http;

import com.example.you.lsmisclient.bean.CheckMission;
import com.example.you.lsmisclient.bean.Lab;
import com.example.you.lsmisclient.bean.LabDetailLevel;
import com.example.you.lsmisclient.bean.LabId;
import com.example.you.lsmisclient.bean.LabInfo;
import com.example.you.lsmisclient.bean.LabLevel;
import com.example.you.lsmisclient.bean.Result;


import org.json.JSONObject;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

    /**
     * 获取学院列表
     * @return
     */
    @GET("labinfor/department")
    Observable<Result> getDepartmentList();

    /**
     * 获取检查任务列表
     * @param flag
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkResult/front/getCheckTaskList")
    Observable<Result<List<CheckMission>>> getCheckTaskList(@Field("availableFlag") int flag);

    /**
     * 获取危险源大类
     * @return
     */
    @GET("dangersubmit/maindanger")
    Observable<Result>  getMainDanger();

    /**
     * 危险源显示
     * @param id
     * @return
     */
    @POST("dangersubmit/dangerinfor")
    Observable<Result> getDetailDanger(@Query("dangerMainTypeId") int id,@Query("labId") int labid);

    /**
     * 按学院查看实验室列表
     * @param page
     * @param id
     * @return
     */
    @POST("back/labsinfor")
    Observable<Result<List<Lab>>> getLabByDepartment(@Query("pageNumb") int page, @Query("departmentId") int id );
}
