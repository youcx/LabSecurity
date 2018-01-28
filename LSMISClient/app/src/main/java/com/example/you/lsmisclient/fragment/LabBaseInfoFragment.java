package com.example.you.lsmisclient.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.LabId;
import com.example.you.lsmisclient.bean.LabInfo;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.http.HttpTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabBaseInfoFragment extends Fragment {
    @BindView(R.id.lab_levelid_tv)
    TextView labLevelIdTv;
    @BindView(R.id.labName_tv)
    TextView labNameTv;
    @BindView(R.id.buildId_tv)
    TextView buildIdTv;
    @BindView(R.id.departmentId_tv)
    TextView departmentIdTv;
    @BindView(R.id.denoterInfor_tv)
    TextView denoterInforTv;
    @BindView(R.id.area_tv)
    TextView areaTv;
    @BindView(R.id.responseName_tv)
    TextView responseNameTv;
    @BindView(R.id.responsePhone_tv)
    TextView responsePhoneTv;
    @BindView(R.id.managerName_tv)
    TextView managerNameTv;
    @BindView(R.id.managerPhone_tv)
    TextView managerPhoneTv;
    @BindView(R.id.labAddr_tv)
    TextView labAddrTv;
    @BindView(R.id.labStatus_tv)
    TextView labStatusTv;
    @BindView(R.id.submitPersonName_tv)
    TextView submitPersonNameTv;
    @BindView(R.id.departmentName_tv)
    TextView departmentNameTv;
    @BindView(R.id.detailLevelName_tv)
    TextView detailLevelNameTv;
    @BindView(R.id.mainLevelName_tv)
    TextView mainLevelNameTv;
    @BindView(R.id.lab_baseinfo_progressbar)
    ProgressBar labBaseInfoProgressBar;

    //http
    private HttpTask mTask;


    public LabBaseInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_lab_base_info,container,false);
        //bind
        ButterKnife.bind(this,view);
        mTask=new HttpTask();
        labBaseInfoProgressBar.setVisibility(View.VISIBLE);
        startGetLabInfo();
        return view;
    }

    /**
     * 获取实验室信息
     */
    private void startGetLabInfo()
    {
        mTask.getLabInfo(4)
                .subscribe(new Subscriber<Result<LabInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();if ( e instanceof HttpException){
                            HttpException httpException= (HttpException) e;
                            int code=httpException.code();
                            String msg=httpException.getMessage();
                            if (code==504){
                                msg="网络不给力";
                            }else if(code==404){
                                msg="请求内容不存在！";
                            }
                            showToast(msg);
                        }else {

                        }
                    }

                    @Override
                    public void onNext(Result<LabInfo> result) {
                        if(result!=null)
                        {

                            if(result.getStatus()==200)
                            {
                                showToast("获取实验室信息成功");
                                startSetInfo(result.getData());
                            }else{
                                showToast(result.getMessage());
                            }
                        }

                    }
                });
    }

    /**
     * 设置实验室信息
     * @param labInfo
     */
    private void startSetInfo(LabInfo labInfo)
    {
//          labLevelIdTv.setText(labInfo.getLabLevelId());
 //         buildIdTv.setText(labInfo.getBuildId());
          departmentIdTv.setText(labInfo.getDepartmentId());
          labAddrTv.setText(labInfo.getLabAddr());
          labNameTv.setText(labInfo.getLabName());
          denoterInforTv.setText(labInfo.getDenoterInfor());
          areaTv.setText(labInfo.getArea());
          responseNameTv.setText(labInfo.getResponserName());
            responsePhoneTv.setText(labInfo.getResponsePhone());
            managerNameTv.setText(labInfo.getManagerName());
            managerPhoneTv.setText(labInfo.getManagerPhone());
          labStatusTv.setText(labInfo.getLabStatus());
         submitPersonNameTv.setText(labInfo.getSubmitPersonName());
         departmentNameTv.setText(labInfo.getDepartmentName());
         detailLevelNameTv.setText(labInfo.getDetailLevelName());
         mainLevelNameTv.setText(labInfo.getMainLevelName());
        labBaseInfoProgressBar.setVisibility(View.GONE);
    }

    /**
     * 显示Toast消息
     */
    private void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

}
