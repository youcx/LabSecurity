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
import com.example.you.lsmisclient.lab.bean.LabInforResult;
import com.example.you.lsmisclient.rectification.bean.MyRectification;
import com.example.you.lsmisclient.rectification.bean.MyReformResult;
import com.example.you.lsmisclient.rectification.bean.ReformDetail;


import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @POST("back/detailinfor")
    Observable<LabInforResult> getLabInfo(@Query("ID") int labid);

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
     * 获取当前实验室的任务列表
     * @param flag    1表示当前可用的，0表示查询所有的
     * @param labId
     * @param pageSize
     * @param pageNum
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkResult/front/getLabCheckTaskList")
    Observable<Result<List<CheckMission>>>getLabCheckTaskList(@Field("availableFlag")int flag,
                                                              @Field("labId") int labId,
                                                              @Field("pageSize") int pageSize,
                                                              @Field("pageNum") int pageNum);

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
    Observable<Result> startCheck(@Field("taskId") int taskId,@Field("typeId") int typeId,@Field("labId") int labId);

    /**
     * 开始日常检查，无需传入taskId
     * @param typeId
     * @param labId
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkResult/back/startNewCheck")
    Observable<Result> startDailyCheck(@Field("typeId") int typeId,@Field("labId") int labId);

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
     * 上传不符合项
     * @param partList
     * @return
     */
    @Multipart
    @POST("labsafe/checkResult/back/uploadNewCheckResult")
    Observable<Result> uploadNewCheckResult(@Part List<MultipartBody.Part> partList);

    /**
     * 提交检查记录
     * @param recordId
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkResult/back/submitCheckRecord")
    Observable<Result> submitCheckRecord(@Field("recordId") int recordId);

    /**
     * 查看我的整改
     * @param pageSize 要查询的行数
     * @param pageNum   页码
     * @return
     */
    @FormUrlEncoded
    @POST("labsafety/labCheckResult/front/getLabChangeList")
    Observable<MyReformResult<List<MyRectification>>> getMyReformList(@Field("pageSize")int pageSize,
                                                                      @Field("pageNum") int pageNum);

    /**
     * 获取正在整改
     * @param pageSize
     * @param pageNum
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkResult/front/getChangingCheckResultList")
    Observable<MyReformResult<List<MyRectification>>> getInReformList(@Field("pageSize")int pageSize,
                                                                       @Field("pageNum") int pageNum);

    /**
     * 获取正在整改项详情
     * @param changeId
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkResult/front/getChangingDetail")
    Observable<Result<ReformDetail>> getChangingDetail(@Field("changeId") int changeId);

    /**
     * 获取等待复查列表
     * @param pageSize
     * @param pageNum
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkResult/front/getWaitCheckResultList")
    Observable<MyReformResult<List<MyRectification>>> getReviewList(@Field("pageSize")int pageSize,
                                                                    @Field("pageNum") int pageNum);

    /**
     * 复查通过
     * @param recordId
     * @return
     */
    @FormUrlEncoded
    @POST("labsafe/checkResult/back/reCheckPass")
    Observable<Result> reCheckPass(@Field("recordId") int recordId);

    /**
     * 复检不通过，上传驳回原因
     * @param partList
     * @return
     */
    @Multipart
    @POST("labsafe/checkResult/back/reCheckRefuse")
    Observable<Result> reCheckRefuse(@Part List<MultipartBody.Part> partList);



}
