package com.example.you.lsmisclient.lab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.LabInfo;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.check.CheckActivity;
import com.example.you.lsmisclient.check.SafetyCheckListActivity;
import com.example.you.lsmisclient.http.HttpTask;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.exceptions.Exceptions;

public class LabOverViewActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.lab_overview_toolbar)
    Toolbar labOverViewToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    //btn
    @BindView(R.id.start_check_btn)
    Button startCheckBtn;
    @BindView(R.id.reform_btn)
    Button reformBtn;
    @BindView(R.id.lab_info_btn)
    Button labInfoBtn;
    //信息显示控件
    @BindView(R.id.lab_name_tv)
    TextView labNameTv;
    @BindView(R.id.lab_manager_name)
    TextView labManagerName;
    @BindView(R.id.lab_manager_phone)
    TextView labManagerPhone;
    //进度条
    @BindView(R.id.lab_overview_progressbar)
    ProgressBar labOverViewProgressbar;

    //获取数据
    private HttpTask mTask;
    //
    int labid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_over_view);

        //绑定控件
        ButterKnife.bind(this);
        mTask=new HttpTask();
        Intent intent=getIntent();
        labid=intent.getIntExtra("labId",4);
        //toolbar
        toolbarTextView.setText("实验室概览");
        setSupportActionBar(labOverViewToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        labOverViewToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //btn
        startCheckBtn.setOnClickListener(this);
        reformBtn.setOnClickListener(this);
        labInfoBtn.setOnClickListener(this);
        //获取信息
        labOverViewProgressbar.setVisibility(View.VISIBLE);
        startGetLabInfo();
    }

    /**
     * 获取实验室信息
     */
    private void startGetLabInfo()
    {
        mTask.getLabInfo(labid)
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
                    public void onNext(Result<LabInfo> labInfoResult) {
                        if(labInfoResult!=null)
                        {
                            if(labInfoResult.getStatus()==200)
                            {
                                showToast("获取实验室信息成功");
                                startSetInfo(labInfoResult.getData());

                            }else{
                                showToast(labInfoResult.getMessage());
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
        labNameTv.setText(labInfo.getLabName());
        labManagerName.setText(labInfo.getManagerName());
        labManagerPhone.setText(labInfo.getManagerPhone());
        labOverViewProgressbar.setVisibility(View.GONE);
        SharedPreferences.Editor editor=getSharedPreferences("checkdata",MODE_PRIVATE).edit();
        Log.i("labID",""+labInfo.getID());
        editor.putInt("labId",labInfo.getID());
        editor.apply();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.start_check_btn:
                SharedPreferences sp=getSharedPreferences("checkdata",MODE_PRIVATE);
                int taskId=sp.getInt("taskId",0);
                int typeId=sp.getInt("typeId",0);
                int labId=sp.getInt("labId",0);
                Log.i("labId",taskId+":"+typeId+":"+labId);
                startCheck(taskId,typeId,labId);
                break;
            case R.id.reform_btn:
                break;
            case R.id.lab_info_btn:
                Intent intent=new Intent(this,LabInfoActivity.class);
                intent.putExtra("labId",labid);
                startActivity(intent);
                break;
        }
    }

    private void startCheck(int taskId,int typeId,int labId)
    {
        mTask.startCheck(taskId,typeId,labId)
                .subscribe(new Subscriber<Result>() {
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
                                SharedPreferences.Editor editor=getSharedPreferences("checkdata",MODE_PRIVATE).edit();
                                Log.i("recordId",""+result.getRecordId());
                                editor.putInt("recordId",result.getRecordId());
                                editor.apply();
                                Intent intoCheckAc=new Intent(getBaseContext(),SafetyCheckListActivity.class);
                                startActivity(intoCheckAc);
                            }else if(result.getStatus()==203)
                            {
                                show203Dialog();
                            }


                        }
                    }
                });
    }

    /**
     *本实验室已存在检查记录
     */
    private void show203Dialog()
    {
        final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("提示")
                .withMessage("当前任务下本实验室已经存在检查记录了,是否再次检查？")
                .withDialogColor("#99cccc")
                .withDuration(200)
                .withButton1Text("取消")
                .withButton2Text("确定")
                .isCancelableOnTouchOutside(true)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        Intent intoCheckAc=new Intent(getBaseContext(),SafetyCheckListActivity.class);
                        startActivity(intoCheckAc);
                    }
                }).show();
    }
    /**
     * 显示Toast消息
     */
    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
