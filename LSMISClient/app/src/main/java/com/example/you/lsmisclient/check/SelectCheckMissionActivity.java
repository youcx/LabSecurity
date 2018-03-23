package com.example.you.lsmisclient.check;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.adapter.CheckMissionAdapter;
import com.example.you.lsmisclient.bean.CheckMission;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.http.HttpTask;
import com.example.you.lsmisclient.list.LabListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class SelectCheckMissionActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.check_mission_recyclerview)
    RecyclerView checkMissionRecyclerView;
    @BindView(R.id.select_mission_toolbar)
    Toolbar selectMissionToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    //http
    HttpTask mTask;
    //适配器
    CheckMissionAdapter missionAdapter;
    //数据
    List<CheckMission> datas;
    private int order;
    private int labId;
    private final int GET_LIST_BY_LAB = 111;
    private final int GET_ALL_LIST = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_check_mission);
        //bind
        ButterKnife.bind(this);
        mTask=new HttpTask();
        Bundle bundle = getIntent().getExtras();
        order=bundle.getInt("fromWhere");
        if(order==GET_LIST_BY_LAB)
            labId=bundle.getInt("labId");
        toolbarTextView.setText("检查任务选择");
        setSupportActionBar(selectMissionToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        selectMissionToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //init
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                switch (order)
                {
                    case GET_ALL_LIST:
                        startGetCheckTaskList(0);
                        break;
                    case GET_LIST_BY_LAB:
                        startGetLabCheckList(0,labId,100,1);
                        break;
                }

            }
        });
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#5CACEE"));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (order)
                {
                    case GET_ALL_LIST:
                        startGetCheckTaskList(0);
                        break;
                    case GET_LIST_BY_LAB:
                        startGetLabCheckList(0,labId,100,1);
                        break;
                    default:
                        break;
                }
            }
        });
        //inidatas();
        //适配器初始化
        missionAdapter=new CheckMissionAdapter();
        //设置布局
        checkMissionRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        checkMissionRecyclerView.setAdapter(missionAdapter);
        missionAdapter.setCheckMissionItemListener(new CheckMissionAdapter.CheckMissionItemClickListener() {
            @Override
            public void onCheckMissionItemClickListener(View view, CheckMission checkMission) {
                switch (order)
                {
                    case GET_ALL_LIST:
                        Intent intent=new Intent(getBaseContext(), LabListActivity.class);
                        Bundle bd=new Bundle();
                        bd.putString("task","check");
                        bd.putInt("taskId",checkMission.getTaskId());
                        intent.putExtras(bd);
                        startActivity(intent);
                        break;
                    case GET_LIST_BY_LAB:
                        startCheck(checkMission.getTaskId(),checkMission.getTypeId(),labId);

                        break;
                }
            }
        });


    }

    /**
     * 获取所有检查任务列表
     * @param flag
     */
   private void startGetCheckTaskList(int flag)
   {
       mTask.getCheckTaskList(flag)
               .subscribe(new Subscriber<Result<List<CheckMission>>>() {
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
                   public void onNext(Result<List<CheckMission>> result) {
                       swipeRefreshLayout.setRefreshing(false);
                        if(result!=null){
                            if(result.getStatus()==200)
                            {
                                missionAdapter.setmDatas(result.getData());
                            }
                        }
                   }
               });
   }



    /**
     * 获取当前实验室任务列表
     * @param flag
     * @param labId
     * @param pageSize
     * @param pageNum
     */
    private void startGetLabCheckList(int flag,int labId,int pageSize,int pageNum)
    {
        mTask.getLabCheckTaskList(flag,labId,pageSize,pageNum)
                .subscribe(new Subscriber<Result<List<CheckMission>>>() {
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
                    public void onNext(Result<List<CheckMission>> listResult) {
                        swipeRefreshLayout.setRefreshing(false);
                        if(listResult!=null)
                        {
                            if(listResult.getStatus()==200)
                            {
                                missionAdapter.setmDatas(listResult.getData());
                            }
                        }
                    }
                });

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
                                Intent intent1=new Intent(getBaseContext(),CheckListActivity.class);
                                startActivity(intent1);
                            }else if(result.getStatus()==203)
                            {
                                showToast(result.getMessage());
                            }

                            Intent intent1=new Intent(getBaseContext(),CheckListActivity.class);
                            startActivity(intent1);
                        }
                    }
                });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
//            case R.id.daily_mission:
//                Intent intent=new Intent(getBaseContext(), LabListActivity.class);
//                startActivity(intent);
//                break;
        }
    }

    private void showToast(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
