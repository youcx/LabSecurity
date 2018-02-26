package com.example.you.lsmisclient.http;

import com.example.you.lsmisclient.bean.CheckMission;
import com.example.you.lsmisclient.bean.Lab;
import com.example.you.lsmisclient.bean.LabDetailLevel;
import com.example.you.lsmisclient.bean.LabId;
import com.example.you.lsmisclient.bean.LabInfo;
import com.example.you.lsmisclient.bean.LabLevel;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.check.bean.CheckItem;
import com.example.you.lsmisclient.check.bean.FirstCheckList;
import com.example.you.lsmisclient.check.bean.SecondCheckList;


import org.json.JSONObject;

import java.io.File;
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
    @GET("lablist/levellist")
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
    @GET("lablist/departlist")
    Observable<Result> getDepartmentList();



    /**
     * 获取危险源大类列表
     * @return
     */
    @GET("lablist/dangerlist")
    Observable<Result>  getMainDanger();

    /**
     * 获取危险源子类列表
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

    /**
     * 按等级查看实验室列表
     * @param labLevel
     * @param labLevelId
     * @param page
     * @return
     */
    @POST("back/labbylevel")
    Observable<Result<List<Lab>>> getLabByLevel(@Query("labLevel") int labLevel,
                                                @Query("labLevelId") int labLevelId,
                                                @Query("pageNumb") int page);

    /**
     * 按危险源查看实验室列表
     * @param id
     * @param page
     * @return
     */
    @POST("back/labbydanger")
    Observable<Result<List<Lab>>> getLabByHazard(@Query("dangerMainTypeId") int id,@Query("pageNumb") int page);

    /**
     * 获取检查任务列表
     * @param flag
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkResult/front/getCheckTaskList")
    Observable<Result<List<CheckMission>>> getCheckTaskList(@Field("availableFlag") int flag);

    /**
     * 按任务查看实验室列表
     * @param id
     * @param pageSize
     * @param pageNum
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkTask/front/getTaskIncLabList")
    Observable<Result<List<Lab>>> getLabByTask(@Field("taskId") int id,@Field("pageSize") int pageSize,@Field("pageNum") int pageNum);

    /**
     * 获取检查表第一大类
     * @return
     */
    @POST("labsafe/checkResult/front/getFirstCheckGroupList")
    Observable<Result<List<FirstCheckList>>> getFirstCheckGroupList();

    /**
     * 获取检查表第二大类
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkResult/front/getSecondCheckGroupList")
    Observable<Result<List<SecondCheckList>>> getSecondCheckGroupList(@Field("groupSerialNumber") String id);

    /**
     * 获取检查项
     * @param id
     * @param labId
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkResult/front/getLabCheckTitleList")
    Observable<Result<List<CheckItem>>> getCheckItemList(@Field("groupSerialNumber") String id,@Field("labId") int labId);

    /**
     * 开始检查
     * @param taskId
     * @param typeId
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkResult/back/startNewCheck")
    Observable<String> startCheck(@Field("taskId") int taskId,@Field("typeId") int typeId,@Field("labId") int labId);

    /**
     * 上传不适用项
     * @param recordId
     * @param labId
     * @param titleId
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkResult/back/uploadLabUnUseTitle")
    Observable<Result> uploadUnUseTitle(@Field("recordId") int recordId,@Field("labId") int labId,@Field("titleId") int titleId);

    /**
     *上传不符合项
     * @param titleId              检查项id
     * @param recordId              检查记录id
     * @param descrip               问题描述
     * @param changeAdvice          整改意见
     * @param targetLevel           建议整改主体，1为学校,2为学院,3为实验室,4为责任人
     * @param time                  建议整改完成时间,yyyy-MM-dd HH:mm:ss格式
     * @param pic                   上传的图片，多张，name都为pic
     * @param video                 上传的视频，只能有一个
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkResult/back/uploadNewCheckResult")
    Observable<Result> uploadNewCheckResult(@Field("titleId")int titleId,
                                            @Field("recordId") int recordId,
                                            @Field("questionDesc") String descrip,
                                            @Field("changeAdvice") String changeAdvice,
                                            @Field("adviceTargetOrgLevel") int targetLevel,
                                            @Field("adviceChangeTimeStr") String time,
                                            @Field("pic")File  pic,
                                            @Field("video") File video);


}
