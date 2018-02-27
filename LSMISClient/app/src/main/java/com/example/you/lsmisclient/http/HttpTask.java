package com.example.you.lsmisclient.http;

import com.example.you.lsmisclient.R;
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

import rx.Observable;
import rx.Scheduler;
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
    public Observable<Result<LabInfo>> getLabInfo(int labId)
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
     * @param titleId
     * @param recordId
     * @param descrip
     * @param changeAdvice
     * @param tagLevel
     * @param changeTime
     * @param pic
     * @param video
     * @return
     */
    public Observable<Result> uploadNewCheckResult(int titleId, int recordId, String descrip, String changeAdvice,
                                                   int tagLevel, String changeTime, File pic,File video)
    {
        return HttpManager
                .getApi()
                .uploadNewCheckResult(titleId,recordId,descrip,changeAdvice,tagLevel,changeTime,pic,video)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
