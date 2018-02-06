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
import com.example.you.lsmisclient.adapter.CollegeAdapter;
import com.example.you.lsmisclient.bean.College;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.http.HttpTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class CollegeListActivity extends AppCompatActivity {
    @BindView(R.id.college_list_recyclerview)
    RecyclerView collegeListRecyclerView;
    @BindView(R.id.college_list_toolbar)
    Toolbar collegeListToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;
    //适配器
    CollegeAdapter collegeAdapter;
    //数据
    private static List<College> mDatas;
    //http
    HttpTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);
        //绑定控件
        ButterKnife.bind(this);
        mTask=new HttpTask();
        toolbarTextView.setText("学院分览");
        setSupportActionBar(collegeListToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        collegeListToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //getList
        mProgressBar.setVisibility(View.VISIBLE);
        startGetDepartmentList();
        //init

    }

    /**
     * 获取学院列表
     */
    private void startGetDepartmentList()
    {
        mTask.getDepartmentList()
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
                                initCollege(result.getData().toString());
                            }
                        }
                    }
                });
    }

    /**
     * 初始化
     * @param s
     */
    public void initCollege(String s) {
        try{
            JSONArray jsonArray=new JSONArray(s);
            mDatas=new ArrayList<College>();
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=(JSONObject) jsonArray.get(i);
                Log.i("学院名",jsonObject.getString("departmentName"));
                if("".equals(jsonObject.optString("memberList")) || jsonObject.optString("memberList")==null)
                {
                    mDatas.add(new College(
                            jsonObject.getInt("departmentId"),
                            jsonObject.getString("departmentName"),
                            "",
                            jsonObject.getString("labNumb")));
                }else{
                    JSONArray jsonArray1=new JSONArray(jsonObject.getString("memberList"));
                    JSONObject jsonObject1=(JSONObject) jsonArray1.get(0);
                    mDatas.add(new College(
                            jsonObject.getInt("departmentId"),
                            jsonObject.getString("departmentName"),
                            jsonObject1.getString("memberName"),
                            jsonObject.getString("labNumb")));
                }

            }
            collegeAdapter = new CollegeAdapter(mDatas);
            collegeAdapter.setCollegeItemClickListener(new CollegeAdapter.CollegeItemClickListener() {
                        @Override
                        public void onCollegeItemClick(View view, College college) {
                            Intent intent=new Intent(getBaseContext(),LabListActivity.class);
                            Bundle bd=new Bundle();
                            Log.i("点击项",""+college.getDepartmentName());
                            bd.putInt("departmentId",college.getDepartmentId());
                            bd.putString("task","fromDepartment");
                            intent.putExtras(bd);
                            startActivity(intent);
                        }
            });
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //设置布局
                    collegeListRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    //设置适配器
                    collegeListRecyclerView.setAdapter(collegeAdapter);
                    mProgressBar.setVisibility(View.GONE);
                }
            });

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }






    /**
     * 显示Toast消息
     */
    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}
