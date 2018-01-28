package com.example.you.lsmisclient.http;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.LabDetailLevel;
import com.example.you.lsmisclient.bean.LabId;
import com.example.you.lsmisclient.bean.LabInfo;
import com.example.you.lsmisclient.bean.LabLevel;
import com.example.you.lsmisclient.bean.Result;

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
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取学院列表
     * @return
     */
    public Observable<Result<String>> getDepartmentList()
    {
        return HttpManager
                .getApi()
                .getDepartmentList()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * http测试
     * @param b
     * @return
     */
    public Observable<Result> testHttp(boolean b)
    {
        return HttpManager
                .getApi()
                .testHttp(b)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
