package com.example.you.lsmisclient.http;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.LabInfo;
import com.example.you.lsmisclient.bean.Result;

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
    public Observable<Result> changeLabInfo(LabInfo labInfo)
    {
        return HttpManager
                .getApi()
                .changeLabInfo(labInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取实验室信息
     * @param labInfo
     * @return
     */
    public Observable<Result> getLabInfo(LabInfo labInfo)
    {
        return HttpManager
                .getApi()
                .getLabInfo(labInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
