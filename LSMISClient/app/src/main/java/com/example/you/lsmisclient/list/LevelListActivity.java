package com.example.you.lsmisclient.list;

import android.content.Intent;
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
import com.example.you.lsmisclient.adapter.LevelAdapter;
import com.example.you.lsmisclient.bean.LabLevel;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.http.HttpTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class LevelListActivity extends AppCompatActivity {
    @BindView(R.id.level_list_recyclerview)
    RecyclerView levelListRecyclerView;
    @BindView(R.id.level_list_toolbar)
    Toolbar levelListToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;
    //适配器
    LevelAdapter levelAdapter;
    //数据
    List<LabLevel> datas;
    //http
    HttpTask mTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_list);
        //绑定控件
        ButterKnife.bind(this);
        mTask=new HttpTask();
        toolbarTextView.setText("级别分览");
        setSupportActionBar(levelListToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        levelListToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //init
        //initLevel();
        mProgressBar.setVisibility(View.VISIBLE);
        startGetLevelList();
        //

    }

    /**
     * 获取等级列表
     */
    private void startGetLevelList()
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
                                initMainLevelList(listResult.getData());
                            }
                        }
                    }
                });
    }

    /**
     * 初始化等级列表
     * @param data
     */
    private void initMainLevelList(List<LabLevel> data)
    {
        datas=data;
        //适配器初始化
        levelAdapter=new LevelAdapter(datas);
        levelAdapter.setLevelItemCLickListener(new LevelAdapter.LevelItemClickListener() {
            @Override
            public void onLevelItemClickListener(View view, LabLevel labLevel) {
                Intent intent=new Intent(getBaseContext(),LabListActivity.class);
                Bundle bd=new Bundle();
                bd.putInt("labLevel",labLevel.getLabLevel());
                bd.putString("task","fromLevel");
                intent.putExtras(bd);
                startActivity(intent);
            }
        });
        //RecyclerView
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                levelListRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                levelListRecyclerView.setAdapter(levelAdapter);
                mProgressBar.setVisibility(View.GONE);
            }
        });

    }

//    private void initLevel()
//    {
//        datas=new ArrayList<LabLevel>();
//        datas.add(new LabLevel("国家级","3"));
//        datas.add(new LabLevel("省级","5"));
//        datas.add(new LabLevel("校级","22"));
//        datas.add(new LabLevel("院级","25"));
//        datas.add(new LabLevel("其他","3"));
//    }

    /**
     * 显示Toast消息
     */
    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
