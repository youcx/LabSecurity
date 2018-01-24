package com.example.you.lsmisclient.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
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
public class LabBaseInfoChangeFragment extends Fragment {

    @BindView(R.id.lab_levelid_edt)
    EditText labLevelIdEdi;
    @BindView(R.id.build_id_edt)
    EditText buildIdEdt;
    @BindView(R.id.departmentId_edt)
    EditText departmentIdEdt;
    @BindView(R.id.lab_name_edt)
    EditText labNameEdt;
    @BindView(R.id.denoter_infor_edt)
    EditText    denoterInforEdt;
    @BindView(R.id.lab_area_edt)
    EditText labAreaEdt;
    @BindView(R.id.location_edt)
    EditText locationEdt;
    @BindView(R.id.response_name_edt)
    EditText responseNameEdt;
    @BindView(R.id.response_phone_edt)
    EditText responsePhoneEdt;
    @BindView(R.id.manager_name_edt)
    EditText managerNameEdt;
    @BindView(R.id.manager_phone_edt)
    EditText managerPhoneEdt;
    @BindView(R.id.lab_status_edt)
    EditText labStatusEdt;
    @BindView(R.id.submit_person_name_edt)
    EditText   submitPersonNameEdt;
    @BindView(R.id.change_labinfo_btn)
    Button changeLabInfoBtn;

    //http任务
    private HttpTask mTask;
    //labinfo
    LabInfo labInfo;

    public LabBaseInfoChangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_lab_base_info_change, container, false);
        //绑定控件
        ButterKnife.bind(this,view);
        mTask=new HttpTask();
        changeLabInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labInfo=new LabInfo();
                labInfo.setLabLevelId(labLevelIdEdi.getText().toString());
                labInfo.setBuildId(buildIdEdt.getText().toString());
                labInfo.setDepartmentId(departmentIdEdt.getText().toString());
                labInfo.setLabName(labNameEdt.getText().toString());
                labInfo.setDenoterInfor(denoterInforEdt.getText().toString());
                labInfo.setArea(labAreaEdt.getText().toString());
                labInfo.setResponseName(responseNameEdt.getText().toString());
                labInfo.setResponsePhone(responsePhoneEdt.getText().toString());
                labInfo.setManagerName(managerNameEdt.getText().toString());
                labInfo.setManagerPhone(managerPhoneEdt.getText().toString());
                labInfo.setLabStatus(labStatusEdt.getText().toString());
                labInfo.setSubmitPersonName(submitPersonNameEdt.getText().toString());
                labInfo.setLabAddr(locationEdt.getText().toString());
                Log.i("labinfo",labInfo.toString());
                startChangeLabInfo(labInfo);
                //testhttp(true);
            }
        });
        return view;
    }
    /**
     * http测试
     * @param b
     */
    private void testhttp(boolean b)
    {

        mTask.testHttp(b)
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

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
                    public void onNext(Result result) {
                        if(result!=null)
                        {
//                            if(result.getStatus()==1)
//                            {
//                                showToast(result.getMsg());
//                            }
//                            showToast(result.getMsg());
                            Log.i("tag",result.getResult().toString());
                        }
                    }
                });
    }

    /**
     * 开始改变实验室信息
     * @param labInfo
     */
    private void startChangeLabInfo(LabInfo labInfo)
    {
        mTask.changeLabInfo(labInfo)
                .subscribe(new Subscriber<Result>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

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
                    public void onNext(Result result) {
                        if(result!=null)
                        {
                            if(result.getStatus()==200)
                            {
                                showToast(result.getMessage());
                            }else{
                                showToast(result.getMessage());
                            }
                        }
                    }

                });
    }

    /**
     * 显示Toast消息
     */
    private void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

}
