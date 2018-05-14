package com.example.you.lsmisclient.http;

import com.example.you.lsmisclient.bean.CheckMission;
import com.example.you.lsmisclient.bean.Lab;
import com.example.you.lsmisclient.bean.LabDetailLevel;
import com.example.you.lsmisclient.bean.LabInfo;
import com.example.you.lsmisclient.bean.LabLevel;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.check.bean.CheckItem;
import com.example.you.lsmisclient.check.bean.FirstCheckList;
import com.example.you.lsmisclient.check.bean.SecondCheckList;
import com.example.you.lsmisclient.lab.bean.LabInforResult;
import com.example.you.lsmisclient.rectification.bean.InReformResult;
import com.example.you.lsmisclient.rectification.bean.MyRectification;
import com.example.you.lsmisclient.rectification.bean.MyReformResult;
import com.example.you.lsmisclient.rectification.bean.CheckRecordDetail;
import com.example.you.lsmisclient.rectification.bean.ReviewResult;

import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 各种http任务
 * Created by You on 2018/1/18.
 */

public class HttpTask {
    private static final String TAG = "HttpTask>>>>>>>";

    /**
     * 改变实验室信息
     * @param labInfo
     * @return
     */
    public Observable<Result> changeLabInfo(int labid,LabInfo labInfo)
    {
        return HttpManager
                .getApi()
                .changeLabInfo(labid,labInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取实验室信息
     * @param labId
     * @return
     */
    public Observable<LabInforResult> getLabInfo(int labId)
    {
        return HttpManager
                .getApi()
                .getLabInfo(labId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取实验室等级
     * @return
     */
    public Observable<Result<List<LabLevel>>> getLabLevelList()
    {
        return HttpManager
                .getApi()
                .getLabLevelList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取实验室具体等级
     * @param levelId
     * @return
     */
    public Observable<Result<List<LabDetailLevel>>> getLabDetailLevel(int levelId)
    {
        return HttpManager
                .getApi()
                .getLabDetailLevel(levelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取学院列表
     * @return
     */
    public Observable<Result> getDepartmentList()
    {
        return HttpManager
                .getApi()
                .getDepartmentList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取危险源大类
     * @return
     */
    public Observable<Result> getMainDanger()
    {
        return HttpManager
                .getApi()
                .getMainDanger()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取危险源子类
     * @param id
     * @return
     */
    public Observable<Result> getDetailDanger(int id,int labid)
    {
        return HttpManager
                .getApi()
                .getDetailDanger(id,labid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取检查任务列表
     * @param flag
     * @return
     */
    public Observable<Result<List<CheckMission>>> getCheckTaskList(int flag)
    {
        return HttpManager
                .getApi()
                .getCheckTaskList(flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取当前实验室检查任务列表
     * @param flag
     * @param labId
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Observable<Result<List<CheckMission>>> getLabCheckTaskList(int flag,int labId,int pageSize,int pageNum)
    {
        return HttpManager
                .getApi()
                .getLabCheckTaskList(flag,labId,pageSize,pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 按学院获取实验室列表
     * @param page
     * @param id
     * @return
     */
    public Observable<Result<List<Lab>>> getLabListByDepartment(int page, int id)
    {
        return HttpManager
                .getApi()
                .getLabByDepartment(page,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 按等级获取实验室列表
     * @param level
     * @param levelId
     * @param page
     * @return
     */
    public Observable<Result<List<Lab>>> getLabListByLevel(int level,int levelId,int page)
    {
        return HttpManager
                .getApi()
                .getLabByLevel(level,levelId,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 按危险源获取实验室列表
     * @param id
     * @param page
     * @return
     */
    public Observable<Result<List<Lab>>> getLabListByHazard(int id,int page)
    {
        return HttpManager
                .getApi()
                .getLabByHazard(id,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 按任务选择实验室列表
     * @param id
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Observable<Result<List<Lab>>> getLabListByTask(int id,int pageSize,int pageNum)
    {
        return HttpManager
                .getApi()
                .getLabByTask(id,pageSize,pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取检查表第一大类
     * @return
     */
    public Observable<Result<List<FirstCheckList>>> getFirstCheckList()
    {
        return HttpManager
                .getApi()
                .getFirstCheckGroupList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据第一大类获取检查表子类
     * @param serialNum
     * @return
     */
    public Observable<Result<List<SecondCheckList>>> getSecondCheckList(String serialNum)
    {
        return HttpManager
                .getApi()
                .getSecondCheckGroupList(serialNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取检查项
     * @param serialNum
     * @param labId
     * @return
     */
    public Observable<Result<List<CheckItem>>> getCheckItemList(String serialNum,int labId)
    {
        return HttpManager
                .getApi()
                .getCheckItemList(serialNum,labId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 开始检查
     * @param taskId
     * @param typeId
     * @return
     */
    public Observable<Result> startCheck(int taskId,int typeId,int labId)
    {
        return HttpManager
                .getApi()
                .startCheck(taskId,typeId,labId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 开始日常检查
     * @param typeId
     * @param labId
     * @return
     */
   public Observable<Result> startDailyCheck(int typeId,int labId)
   {
       return HttpManager
               .getApi()
               .startDailyCheck(typeId,labId)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread());
   }

    /**
     * 上传不适用项
     * @param recordId
     * @param labId
     * @param titleId
     * @return
     */
    public Observable<Result> uploadUnUseTitle(int recordId,int labId,int titleId)
    {
        return HttpManager
                .getApi()
                .uploadUnUseTitle(recordId,labId,titleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    /**
     * 上传不符合项
     * @param partList
     * @return
     */
    public Observable<Result> uploadNewCheckResult(List<MultipartBody.Part> partList){
        return HttpManager
                .getApi()
                .uploadNewCheckResult(partList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 提交检查记录
     * @param recordId
     * @return
     */
    public Observable<Result> submitCheckRecord(int recordId)
    {
        return HttpManager
                .getApi()
                .submitCheckRecord(recordId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 查看我的整改
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Observable<MyReformResult<List<MyRectification>>> getMyReformList(int pageSize,int pageNum)
    {
        return HttpManager
                .getApi()
                .getMyReformList(pageSize,pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取正在整改的项
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Observable<MyReformResult<List<MyRectification>>> getInReformList(int pageSize,int pageNum)
    {
        return HttpManager
                .getApi()
                .getInReformList(pageSize,pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取正在整改项详情
     * @param changeId
     * @return
     */
    public Observable<InReformResult<CheckRecordDetail>> getChangingDetail(int changeId)
    {
        return HttpManager
                .getApi()
                .getChangingDetail(changeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取等待复查列表
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Observable<MyReformResult<List<MyRectification>>> getReviewList(int pageSize,int pageNum)
    {
        return HttpManager
                .getApi()
                .getReviewList(pageSize,pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取等待复检详情
     * @param changeId
     * @return
     */
    public Observable<ReviewResult> getWaitCheckDetail(int changeId)
    {
        return HttpManager
                .getApi()
                .getWaitCheckDetail(changeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 申请复检
     * @param partList
     * @return
     */
    public Observable<Result> uploadChangeRecord(List<MultipartBody.Part> partList){
        return HttpManager
                .getApi()
                .uploadChangeRecord(partList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 复检通过
     * @param recordId
     * @return
     */
    public Observable<Result> reCheckPass(int recordId)
    {
        return HttpManager
                .getApi()
                .reCheckPass(recordId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 复检不通过，上传驳回原因
     * @param partList
     * @return
     */
    public Observable<Result> reCheckRefuse(List<MultipartBody.Part> partList){
        return HttpManager
                .getApi()
                .reCheckRefuse(partList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
