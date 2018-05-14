package com.example.you.lsmisclient.rectification.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.http.HttpTask;
import com.example.you.lsmisclient.rectification.ReformDetailActivity;
import com.example.you.lsmisclient.rectification.ReviewDetailActivity;
import com.example.you.lsmisclient.rectification.adapter.MyRectifiAdapter;
import com.example.you.lsmisclient.rectification.bean.MyRectification;
import com.example.you.lsmisclient.rectification.bean.MyReformResult;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    //datas
    MyRectifiAdapter myRectifiAdapter;

    private final int WAITING_FOR_REVIEW=3;

    private Toast toast=null;

    //http
    HttpTask httpTask;


    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        //bind
        ButterKnife.bind(this,view);

        httpTask = new HttpTask();

        initRecyclerView();

        initSwipeRefresh();

        return view;
    }

    private void initRecyclerView(){
        myRectifiAdapter = new MyRectifiAdapter();
        myRectifiAdapter.setMyRectifiItemListener(new MyRectifiAdapter.MyRectifiItemClickListener() {
            @Override
            public void onMyRectifiItemClickListener(View view, MyRectification myRectification) {
                Intent intent=new Intent(getContext(), ReviewDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("changeId",myRectification.getChangeId());
                bundle.putString("labName",myRectification.getLabName());
                bundle.putInt("reformFlag",WAITING_FOR_REVIEW);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myRectifiAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){

                    LinearLayoutManager linearLayoutManager=(LinearLayoutManager) recyclerView.getLayoutManager();
                    if(linearLayoutManager.findLastVisibleItemPosition()==myRectifiAdapter.getItemCount()-1)
                        showToast("到底了"+linearLayoutManager.findLastVisibleItemPosition());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initSwipeRefresh(){
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                startGetReview(50,1);
            }
        });
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startGetReview(50,1);
            }
        });
    }

    /**
     * 获取等待复查
     * @param pageSize
     * @param pageNum
     */
    private void startGetReview(int pageSize,int pageNum){
        httpTask.getReviewList(pageSize,pageNum)
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

                                if(listMyReformResult.getData()!=null)
                                {
                                    showToast(listMyReformResult.getMessage());
                                    myRectifiAdapter.setmDatas(listMyReformResult.getData());
                                }else {
                                    showToast("没有数据");
                                    myRectifiAdapter.reSetDatas();
                                }
                            }else {
                                showToast(listMyReformResult.getMessage());
                            }
                        }
                    }
                });
    }



    /**
     * Toast
     * @param str
     */
    private void showToast(String str)
    {
        if(toast==null)
            toast= Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
        else{
            toast.setText(str);
        }
        toast.show();
    }

}
