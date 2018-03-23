package com.example.you.lsmisclient.rectification;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.http.HttpTask;
import com.example.you.lsmisclient.rectification.adapter.InReformDetailAdapter;
import com.example.you.lsmisclient.rectification.adapter.ReformDetailAdapter;
import com.example.you.lsmisclient.rectification.bean.ReformDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class ReformDetailActivity extends AppCompatActivity {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    @BindView(R.id.lab_name)
    TextView labNameTv;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    //适配器
    ReformDetailAdapter reformDetailAdapter;
    InReformDetailAdapter inReformDetailAdapter;
    //数据
    List<ReformDetail> datas;
    private int changeId;
    private String labName;
    private int reformFlag;
    private final int MY_REFORM=1;
    private final int IN_REFORM=2;
    private final int WAITING_FOR_REVIEW=3;
    //http
    HttpTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reform_detail);
        //bind
        ButterKnife.bind(this);
        toolbarTextView.setText("整改 ");
        Bundle bundle = getIntent().getExtras();
        changeId = bundle.getInt("changeId");
        labName = bundle.getString("labName");
        reformFlag = bundle.getInt("reformFlag");
        Log.i("changeId",""+changeId+",labName:"+labName+",reformFlag:"+reformFlag);
        labNameTv.setText(labName);
        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //init
        mTask=new HttpTask();
        datas=new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(reformFlag==MY_REFORM)
        {
            reformDetailAdapter=new ReformDetailAdapter();
            reformDetailAdapter.setOnBtnClick(new ReformDetailAdapter.BtnClickInterface() {
                @Override
                public void onclick(View view, int postion) {
                    switchClick(view);
                }
            });
            mRecyclerView.setAdapter(reformDetailAdapter);
        }else if(reformFlag==IN_REFORM)
        {
            inReformDetailAdapter=new InReformDetailAdapter();
            inReformDetailAdapter.setOnBtnClickListener(new InReformDetailAdapter.BtnClickListener() {
                @Override
                public void onclick(View view, int postion) {
                   switchClick(view);
                }
            });
            mRecyclerView.setAdapter(inReformDetailAdapter);
        }else{
            inReformDetailAdapter=new InReformDetailAdapter();
            inReformDetailAdapter.setOnBtnClickListener(new InReformDetailAdapter.BtnClickListener() {
                @Override
                public void onclick(View view, int postion) {
                    switchClick(view);
                }
            });
            mRecyclerView.setAdapter(inReformDetailAdapter);
        }

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                startGetReformDetail();
            }
        });
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startGetReformDetail();
            }
        });
        //init();


        //



    }

    /**
     * 获取正在整改详情
     */
    private void startGetReformDetail()
    {
        mTask.getChangingDetail(changeId)
                .subscribe(new Subscriber<Result<ReformDetail>>() {
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
                    public void onNext(Result<ReformDetail> reformDetailResult) {
                        swipeRefreshLayout.setRefreshing(false);
                        if(reformDetailResult!=null)
                        {
                            if(reformDetailResult.getStatus()==200)
                            {
                                if(reformDetailResult.getData()!=null)
                                {
                                    initReformDetail(reformDetailResult.getData());
                                }
                            }else{
                                showToast(reformDetailResult.getMessage());
                            }
                        }
                    }
                });
    }

    /**
     * 设置布局
     * @param detailDatas
     */
    private void initReformDetail(ReformDetail detailDatas)
    {
        if(reformFlag==MY_REFORM)
        {
            if(reformDetailAdapter.getItemCount()==0)
                datas.add(detailDatas);
            reformDetailAdapter.setmDatas(datas);
        }else if(reformFlag==IN_REFORM)
        {
            if(inReformDetailAdapter.getItemCount()==0)
                datas.add(detailDatas);
            inReformDetailAdapter.setmDatas(datas);
        }else if(reformFlag==WAITING_FOR_REVIEW)
        {
            if(inReformDetailAdapter.getItemCount()==0)
                datas.add(detailDatas);
            inReformDetailAdapter.setmDatas(datas);
        }

    }

    private void switchClick(View view)
    {
        switch (view.getId())
        {

            case R.id.reform_item_detail:
                Log.i("Adatper按钮","点击了细节展开");
                break;
            case R.id.check_result:
                Log.i("Adatper按钮","result");
                break;
            case R.id.pic:
                Log.i("Adatper按钮","照片展示");
                break;
            case R.id.document:
                Log.i("Adatper按钮","相关文档");
                break;
            //我的整改
            case R.id.reform_result:
                break;
            case R.id.reform_pic:
                break;
            case R.id.reform_document:
                break;
            case R.id.reform_btn:
                Intent intent=new Intent(getBaseContext(),FillInReformActivity.class);
                startActivity(intent);
                break;
            //复查
            case R.id.review_result:
                Log.i("Adatper按钮","复查结果");
                break;
            case R.id.review_pic:
                Log.i("Adatper按钮","复查照片");
                break;
            case R.id.review_document:
                Log.i("Adatper按钮","复查文档");
                break;
            case R.id.btn_change_checker:
                Log.i("Adatper按钮","更换检查人");
                break;
            case R.id.btn_review_fail:
                Log.i("Adatper按钮","复查失败");
                break;
            case R.id.btn_review_success:
                Log.i("Adatper按钮","复查成功");
                break;
            default:
                break;
        }
    }


    /**
     *
     * @param str
     */
    private void showToast(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
