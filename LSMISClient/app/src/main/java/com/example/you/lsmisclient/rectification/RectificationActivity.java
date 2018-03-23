package com.example.you.lsmisclient.rectification;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.http.HttpTask;
import com.example.you.lsmisclient.rectification.adapter.MyRectifiAdapter;
import com.example.you.lsmisclient.rectification.bean.MyRectification;
import com.example.you.lsmisclient.rectification.bean.MyReformResult;
import com.example.you.lsmisclient.rectification.bean.ReformDetail;
import com.example.you.lsmisclient.rectification.fragment.MyRectifiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class RectificationActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.myReform)
            Button myReform;
    @BindView(R.id.inReform)
            Button inReform;
    @BindView(R.id.review)
            Button review;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    //适配器
    MyRectifiAdapter myRectifiAdapter;
    //数据
    List<MyRectification> datas;
    private Toast toast=null;
    //刷新标致
    private int refreshFlag;
    private final int MY_REFORM=1;
    private final int IN_REFORM=2;
    private final int WAITING_FOR_REVIEW=3;

    HttpTask mTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rectification);
        //bind
        ButterKnife.bind(this);
        toolbarTextView.setText("复查整改 ");
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
        //Button
        myReform.setOnClickListener(this);
        inReform.setOnClickListener(this);
        review.setOnClickListener(this);
        //init
        mTask=new HttpTask();
        datas=new ArrayList<>();
        myRectifiAdapter=new MyRectifiAdapter();
        //适配器监听
        myRectifiAdapter.setMyRectifiItemListener(new MyRectifiAdapter.MyRectifiItemClickListener() {
            @Override
            public void onMyRectifiItemClickListener(View view, MyRectification myRectification) {
                Intent intent=new Intent(getBaseContext(), ReformDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("changeId",myRectification.getChangeId());
                bundle.putString("labName",myRectification.getLabName());
                bundle.putInt("reformFlag",refreshFlag);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(myRectifiAdapter);
        refreshFlag=MY_REFORM;
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                myReform.performClick();
            }
        });
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (refreshFlag)
                {
                    case MY_REFORM:
                        //我的整改
                        myReform.performClick();
                        break;
                    case IN_REFORM:
                        //正在整改
                        Log.i("TAGH","正在整改");
                        inReform.performClick();
                        break;
                    case WAITING_FOR_REVIEW:
                        //等待复查
                        review.performClick();
                        break;
                }
            }
        });

        //




    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.myReform:
                refreshFlag=MY_REFORM;
                myReform.setTextColor(Color.parseColor("#eeeeee"));
                inReform.setTextColor(Color.parseColor("#5CACEE"));
                review.setTextColor(Color.parseColor("#5CACEE"));
                myReform.setBackgroundResource(R.drawable.backgroud_color1);
                inReform.setBackgroundColor(Color.parseColor("#eeeeee"));
                review.setBackgroundColor(Color.parseColor("#eeeeee"));
                startGetMyReform(50,1);
                break;
            case R.id.inReform:
                refreshFlag=IN_REFORM;
                myReform.setTextColor(Color.parseColor("#5CACEE"));
                inReform.setTextColor(Color.parseColor("#eeeeee"));
                review.setTextColor(Color.parseColor("#5CACEE"));
                myReform.setBackgroundColor(Color.parseColor("#eeeeee"));
                inReform.setBackgroundResource(R.drawable.backgroud_color1);
                review.setBackgroundColor(Color.parseColor("#eeeeee"));
                startGetInReform(50,1);
                break;
            case R.id.review:
                refreshFlag=WAITING_FOR_REVIEW;
                myReform.setTextColor(Color.parseColor("#5CACEE"));
                inReform.setTextColor(Color.parseColor("#5CACEE"));
                review.setTextColor(Color.parseColor("#eeeeee"));
                myReform.setBackgroundColor(Color.parseColor("#eeeeee"));
                inReform.setBackgroundColor(Color.parseColor("#eeeeee"));
                review.setBackgroundResource(R.drawable.backgroud_color1);
                startGetReview(50,1);
                break;
        }
    }

    /**
     * 获取我的整改
     */
    private void startGetMyReform(int pageSize,int pageNum)
    {
        mTask.getMyReformList(pageSize,pageNum)
                .subscribe(new Subscriber<MyReformResult<List<MyRectification>>>() {
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
                    public void onNext(MyReformResult<List<MyRectification>> listMyReformResult) {
                        swipeRefreshLayout.setRefreshing(false);
                        if(listMyReformResult!=null)
                        {
                            if(listMyReformResult.getStatus()==200)
                            {
                                showToast(listMyReformResult.getMessage());
                                if(listMyReformResult.getData()!=null)
                                {
                                    initMyReform(listMyReformResult.getData());
                                }else{
                                    //initMyReform(null);
                                    datas=new ArrayList<MyRectification>();
                                    myRectifiAdapter.setmDatas(datas);
                                }

                            }else {
                                showToast(listMyReformResult.getMessage());
                            }
                        }
                    }
                });
    }

    /**
     * 初始化我的整改
     * @param mdatas
     */
    private void initMyReform(List<MyRectification> mdatas)
    {
           datas=mdatas;
        //刷新
        myRectifiAdapter.setmDatas(datas);

    }

    /**
     * 获取正在整改
     * @param pageSize
     * @param pageNum
     */
    private void startGetInReform(int pageSize,int pageNum)
    {
        mTask.getInReformList(pageSize,pageNum)
                .subscribe(new Subscriber<MyReformResult<List<MyRectification>>>() {
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
                    public void onNext(MyReformResult<List<MyRectification>> listMyReformResult) {
                        swipeRefreshLayout.setRefreshing(false);
                        if(listMyReformResult!=null)
                        {
                            if(listMyReformResult.getStatus()==200)
                            {
                                showToast(listMyReformResult.getMessage());
                                if(listMyReformResult.getData()!=null)
                                {
                                    initMyReform(listMyReformResult.getData());
                                }else{
                                    datas=new ArrayList<MyRectification>();
                                    myRectifiAdapter.setmDatas(datas);
                                }
                            }else{
                                showToast(listMyReformResult.getMessage());
                            }
                        }
                    }
                });
    }

    /**
     * 获取等待复查
     * @param pageSize
     * @param pageNum
     */
    private void startGetReview(int pageSize,int pageNum){
        mTask.getReviewList(pageSize,pageNum)
                .subscribe(new Subscriber<MyReformResult<List<MyRectification>>>() {
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
                    public void onNext(MyReformResult<List<MyRectification>> listMyReformResult) {
                        if(listMyReformResult!=null)
                        {
                            if(listMyReformResult.getStatus()==200)
                            {

                                if(listMyReformResult.getData()!=null)
                                {
                                    showToast(listMyReformResult.getMessage());
                                    initMyReform(listMyReformResult.getData());
                                }else {
                                    showToast("没有数据");
                                    datas=new ArrayList<MyRectification>();
                                    myRectifiAdapter.setmDatas(datas);
                                }
                            }else {
                                showToast(listMyReformResult.getMessage());
                            }
                        }
                    }
                });
    }

    private void initInReform(List<MyRectification> mdatas)
    {
        datas=mdatas;

    }

    /**
     *
     * @param str
     */
    private void showToast(String str)
    {
        if(toast==null)
            toast=Toast.makeText(this, str, Toast.LENGTH_SHORT);
        else{
            toast.setText(str);
        }
        toast.show();
    }
}
