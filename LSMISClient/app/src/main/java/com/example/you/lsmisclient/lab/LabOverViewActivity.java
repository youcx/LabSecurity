package com.example.you.lsmisclient.lab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class LabOverViewActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.lab_overview_toolbar)
    Toolbar labOverViewToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    @BindView(R.id.start_check_btn)
    Button startCheckBtn;
    @BindView(R.id.reform_btn)
    Button reformBtn;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_over_view);

        //绑定控件
        ButterKnife.bind(this);
        mTask=new HttpTask();
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
        //获取信息
        labOverViewProgressbar.setVisibility(View.VISIBLE);
        startGetLabInfo();
    }

    /**
     * 过去实验室信息
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.start_check_btn:
                Intent intoCheckAc=new Intent(this,SafetyCheckListActivity.class);
                startActivity(intoCheckAc);
                break;
            case R.id.reform_btn:
                break;
        }
    }

    /**
     * 显示Toast消息
     */
    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
