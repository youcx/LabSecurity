package com.example.you.lsmisclient.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.adapter.MyAdapter;
import com.example.you.lsmisclient.bean.LabDetailLevel;
import com.example.you.lsmisclient.bean.LabId;
import com.example.you.lsmisclient.bean.LabInfo;
import com.example.you.lsmisclient.bean.LabLevel;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.http.HttpTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabBaseInfoChangeFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    //下拉框控件
    @BindView(R.id.main_level_spin)
    Spinner mainLevelSpin;
    @BindView(R.id.detaillevelName_spin)
    Spinner detailLevelNameSpin;
    @BindView(R.id.departmentName_spin)
    Spinner departmentNameSpin;

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
//    @BindView(R.id.departmentName_edt)
//    EditText departmentNameEdt;
//    @BindView(R.id.detaillevelName_edt)
//    EditText detailLevelNameEdt;
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
    private List<LabLevel> mainLevelDatas=null;
    private List<LabDetailLevel> detailLevels=null;
    //Adapter
    private ArrayAdapter<String> mainLevelAdapter=null;
    private ArrayAdapter<String> detailLevelAdapter=null;
    private ArrayAdapter<String> departmentAdapter=null;
    //
//    private boolean mainLevelSelected=false;
//    private boolean detailLevelSelected=false;


    //Handler,用于动态更新下拉列表值

    private final int UPDATADETAILLEVEL=11;
    private final int UPDATADEPARTMENTNAME=22;

    final Handler myHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==UPDATADETAILLEVEL)
            {
                detailLevelNameSpin.setAdapter(detailLevelAdapter);
            }else if(msg.what==UPDATADEPARTMENTNAME)
            {
                departmentNameSpin.setAdapter(departmentAdapter);
            }
        }
    };

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
        //获取各大列表
        startGetLabLevelList();
        startGetDepartmentList();
        //点击

        //
        changeLabInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labBaseInfoProgressBar.setVisibility(View.VISIBLE);
                //labInfo.setLabLevelId(labLevelIdEdi.getText());
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
                //labInfo.setDepartmentName(departmentNameEdt.getText().toString());
               // labInfo.setDetailLevelName(detailLevelNameEdt.getText().toString());
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

                    labInfo.setMainLevelName(mainLevelDatas.get(position).getLevelName());
                    Log.i("实验室等级",labInfo.getMainLevelName());
                    //startGetLabDetailLevel(mainLevelDatas.get(0).getLabLevel());
                    for(int i=0;i<mainLevelDatas.size();i++)
                    {
                        if(labInfo.getMainLevelName().equals(mainLevelDatas.get(i).getLevelName()))
                        {
                            startGetLabDetailLevel(mainLevelDatas.get(i).getLabLevel());
                            break;
                        }
                    }



                //Toast.makeText(getActivity(),labInfo.getMainLevelName(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.detaillevelName_spin:
                    Log.i("具体等级啊",position+"："+detailLevels.get(position).getLevelId());
                    labInfo.setLabLevelId(detailLevels.get(position).getLevelId());
                    Log.i("具体等级",""+labInfo.getLabLevelId());


                break;
        }
    }

    /**
     * spin
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
            switch (parent.getId())
            {
                case R.id.main_level_spin:
                    startGetLabLevelList();
                    break;
                case R.id.detaillevelName_spin:
                    showToast("请先选择实验室等级");
                    break;
            }
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

                            if(result.getStatus()==200)
                            {
                                showToast("返回实验室信息成功");
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
        //labLevelIdEdi.setText(labInfo.getLabLevelId());
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
        //departmentNameEdt.setText(labInfo.getDepartmentName());
       // detailLevelNameEdt.setText(labInfo.getDetailLevelName());
       // mainLevelNameEdt.setText(labInfo.getMainLevelName());
        labBaseInfoProgressBar.setVisibility(View.GONE);
    }

    /**
     * 显示Toast消息
     */
    private void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取实验室等级列表
     */
    private void startGetLabLevelList()
    {
        mTask.getLabLevelList()
                .subscribe(new Subscriber<Result<List<LabLevel>>>() {
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
                    public void onNext(Result<List<LabLevel>> listResult) {
                        if(listResult!=null)
                        {
                            if(listResult.getStatus()==200)
                            {
                                //mainLevelDatas=listResult.getData();

                                //初始化学院等级列表
                                initMainLevel(listResult.getData());
//                                mainLevelDatas=new ArrayList<String>();
//                                for(int i=0;i<listResult.getData().size();i++)
//                                {
//                                    mainLevelDatas.add(listResult.getData().get(i).getLevelName());
//                                }

                            }
                        }
                    }
                });
    }

    /**
     * 获取实验室具体等级
     * @param levelId
     */
    private void startGetLabDetailLevel(int levelId)
    {
        mTask.getLabDetailLevel(levelId)
                .subscribe(new Subscriber<Result<List<LabDetailLevel>>>() {
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
                    public void onNext(Result<List<LabDetailLevel>> listResult) {
                        if(listResult!=null)
                        {
                            if(listResult.getStatus()==200)
                            {
                                initDetailLevel(listResult.getData());
                                Log.i("返回结果",listResult.getData().get(0).toString());
                            }
                        }
                    }
                });
    }

    /**
     * 获取学院列表
     */
    private void startGetDepartmentList()
    {
        mTask.getDepartmentList()
                .subscribe(new Subscriber<Result<String>>() {
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
                    public void onNext(Result<String> stringResult) {
                        if(stringResult!=null)
                        {
                            Log.i("学院列表",stringResult.getData());
                            if(stringResult.getStatus()==200)
                            {
                                Log.i("学院列表",stringResult.getData());
                               // initDepartmentList(stringResult.getData());
                            }
                        }
                    }
                });
    }


    /**
     * 初始化实验室等级
     * @param datas
     */
    private void initMainLevel(List<LabLevel> datas)
    {
        mainLevelDatas=datas;
        String[] mainLevelString=new String[datas.size()];
        for(int i=0;i<datas.size();i++)
        {
            mainLevelString[i]=datas.get(i).getLevelName();
        }
        mainLevelAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mainLevelString);
        mainLevelSpin.setAdapter(mainLevelAdapter);
        mainLevelSpin.setOnItemSelectedListener(this);
    }

    /**
     * 初始化实验室具体等级
     * @param datas
     */
    private void initDetailLevel(List<LabDetailLevel> datas)
    {
        try {
            detailLevels = datas;
            String[] detailLevelString = new String[datas.size()];
            for (int i = 0; i < datas.size(); i++) {
                detailLevelString[i] = datas.get(i).getLevelName();
            }
            detailLevelAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detailLevelString);
            myHandler.sendEmptyMessage(UPDATADETAILLEVEL);
            detailLevelNameSpin.setOnItemSelectedListener(this);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 初始化学院列表
     * @param s
     */
    private void initDepartmentList(String s){
        try{
            //JSONArray jsonArray=new JSONArray(s);
            Log.i("字符串",s);
            JSONObject jsonObject=new JSONObject(s);
            JSONArray dataArray=jsonObject.getJSONArray("data");
            String[] datas=new String[dataArray.length()];
            for(int i=0;i<dataArray.length();i++)
            {
                JSONObject dataObject=(JSONObject) dataArray.get(i);
                datas[i]=dataObject.getString("departmentName");
                Log.i("数据",datas[i]);
            }
            departmentAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,datas);
            myHandler.sendEmptyMessage(UPDATADEPARTMENTNAME);
            departmentNameSpin.setOnItemSelectedListener(this);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //
    }



}
