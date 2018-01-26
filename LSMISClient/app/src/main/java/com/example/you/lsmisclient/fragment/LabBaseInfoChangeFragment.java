package com.example.you.lsmisclient.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.adapter.MyAdapter;
import com.example.you.lsmisclient.bean.LabId;
import com.example.you.lsmisclient.bean.LabInfo;
import com.example.you.lsmisclient.bean.LabLevel;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.http.HttpTask;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabBaseInfoChangeFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    @BindView(R.id.main_level_spin)
    Spinner mainLevelSpin;

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
    @BindView(R.id.departmentName_edt)
    EditText departmentNameEdt;
    @BindView(R.id.detaillevelName_edt)
    EditText detailLevelNameEdt;
//    @BindView(R.id.mainLevelName_edt)
//    EditText mainLevelNameEdt;
    @BindView(R.id.change_labinfo_btn)
    Button changeLabInfoBtn;
    @BindView(R.id.lab_baseinfor_change_progressbar)
    ProgressBar labBaseInfoProgressBar;

    //http任务
    private HttpTask mTask;
    //labinfo
    LabInfo labInfo;
    //实验室id
    int labId=4;

    //数据
    private ArrayList<LabLevel> mainLevelDatas=null;

    //Adapter
    private BaseAdapter mainLevelAdapter=null;

    //
    private boolean mainLevelSelected=false;

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
        labBaseInfoProgressBar.setVisibility(View.VISIBLE);
        labInfo=new LabInfo();
        //首先获取实验室信息
        startGetLabInfo();
        //点击

        initMainLevel();
        //
        changeLabInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labBaseInfoProgressBar.setVisibility(View.VISIBLE);
                labInfo.setLabLevelId(labLevelIdEdi.getText().toString());
                labInfo.setBuildId(buildIdEdt.getText().toString());
                labInfo.setDepartmentId(departmentIdEdt.getText().toString());
                labInfo.setLabName(labNameEdt.getText().toString());
                labInfo.setDenoterInfor(denoterInforEdt.getText().toString());
                labInfo.setArea(labAreaEdt.getText().toString());
                labInfo.setResponserName(responseNameEdt.getText().toString());
                labInfo.setResponsePhone(responsePhoneEdt.getText().toString());
                labInfo.setManagerName(managerNameEdt.getText().toString());
                labInfo.setManagerPhone(managerPhoneEdt.getText().toString());
                labInfo.setLabStatus(labStatusEdt.getText().toString());
                labInfo.setSubmitPersonName(submitPersonNameEdt.getText().toString());
                labInfo.setLabAddr(locationEdt.getText().toString());
                labInfo.setDepartmentName(departmentNameEdt.getText().toString());
                labInfo.setDetailLevelName(detailLevelNameEdt.getText().toString());
               // labInfo.setMainLevelName(mainLevelNameEdt.getText().toString());
                //加入实验室id
                Log.i("labidddd",""+labId);
                labInfo.setID(labId);
                Log.i("labinfo",labInfo.toString());
                startChangeLabInfo(labInfo);
                //testhttp(true);
            }
        });
        return view;
    }

    /**
     * spin点击
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId())
        {
            case R.id.main_level_spin:
                TextView txt=(TextView) view.findViewById(R.id.txt_name);
                    labInfo.setMainLevelName(txt.getText().toString());
                Log.i("asdfsdf",labInfo.getMainLevelName());
                //Toast.makeText(getActivity(),labInfo.getMainLevelName(),Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * spin
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                            Log.i("tag",result.getData().toString());
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
        mTask.changeLabInfo(4,labInfo)
                .subscribe(new Subscriber<Result>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onCompleted() {
                        labBaseInfoProgressBar.setVisibility(View.GONE);
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
                                showToast("信息修改成功");
                            }else{
                                showToast(result.getMessage());
                            }
                        }
                    }

                });
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
                            showToast("返回实验室信息成功");
                            if(result.getStatus()==200)
                            {
                                startSetInfo(result.getData());
                            }else{

                            }
                        }

                    }
                });
    }

    /**
     * 设置实验室信息到文本框
     * @param labInfo
     */
    private void startSetInfo(LabInfo labInfo)
    {
        labLevelIdEdi.setText(labInfo.getLabLevelId());
        buildIdEdt.setText(labInfo.getBuildId());
        departmentIdEdt.setText(labInfo.getDepartmentId());
        locationEdt.setText(labInfo.getLabAddr());
        labNameEdt.setText(labInfo.getLabName());
        denoterInforEdt.setText(labInfo.getDenoterInfor());
        labAreaEdt.setText(labInfo.getArea());
        responseNameEdt.setText(labInfo.getResponserName());
        responsePhoneEdt.setText(labInfo.getResponsePhone());
        managerNameEdt.setText(labInfo.getManagerName());
        managerPhoneEdt.setText(labInfo.getManagerPhone());
        labStatusEdt.setText(labInfo.getLabStatus());
        submitPersonNameEdt.setText(labInfo.getSubmitPersonName());
        departmentNameEdt.setText(labInfo.getDepartmentName());
        detailLevelNameEdt.setText(labInfo.getDetailLevelName());
       // mainLevelNameEdt.setText(labInfo.getMainLevelName());
        labBaseInfoProgressBar.setVisibility(View.GONE);
    }

    /**
     * 显示Toast消息
     */
    private void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    private void initMainLevel()
    {
        mainLevelDatas=new ArrayList<LabLevel>();
        mainLevelDatas.add(new LabLevel("国家级"));
        mainLevelDatas.add(new LabLevel("省级"));

        mainLevelAdapter=new MyAdapter<LabLevel>(mainLevelDatas,R.layout.spin_string) {
            @Override
            public void bindView(ViewHolder holder, LabLevel obj) {
                holder.setText(R.id.txt_name,obj.getLevelName());
            }
        };
        mainLevelSpin.setAdapter(mainLevelAdapter);
        mainLevelSpin.setOnItemSelectedListener(this);
    }

}
