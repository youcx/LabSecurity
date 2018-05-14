package com.example.you.lsmisclient.rectification.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.http.HttpTask;
import com.example.you.lsmisclient.rectification.adapter.ReviewDetailAdapter;
import com.example.you.lsmisclient.rectification.bean.ReviewResult;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewDetailFragment extends Fragment {
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    //http
    HttpTask httpTask;

    private String TAG = "ReviewDetailFragment";

    //Adapter
    ReviewDetailAdapter reviewDetailAdapter;

    //recordId用于复查合格
    private int recordId;
    //changeid
    private int changeId;


    public ReviewDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review_detail,container,false);

        //bind
        ButterKnife.bind(this,view);

        httpTask = new HttpTask();

        Bundle bundle = getArguments();
        changeId = bundle.getInt("changeId");

        initRecyclerView();

        initSwipeRefresh();


        return view;
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        reviewDetailAdapter = new ReviewDetailAdapter();
        reviewDetailAdapter.setOnbtnClick(new ReviewDetailAdapter.BtnClickInterface() {
            @Override
            public void onclick(View view, int postion) {
                switch (view.getId()){
                    case R.id.btn_review_success:

                        showReviewPassDialog();
                        break;
                    default:
                        break;
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(reviewDetailAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){

                    LinearLayoutManager linearLayoutManager=(LinearLayoutManager) recyclerView.getLayoutManager();
                    if(linearLayoutManager.findLastVisibleItemPosition()==reviewDetailAdapter.getItemCount()-1){}
//                        showToast("到底了"+linearLayoutManager.findLastVisibleItemPosition());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    /**
     * 初始化下拉
     *
     */
    private void initSwipeRefresh(){
        //首先执行一次下拉
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                startGetReviewDetail();
            }
        });
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startGetReviewDetail();
            }
        });
    }

    /**
     * 获取复检项详情
     */
    private void startGetReviewDetail(){
        httpTask.getWaitCheckDetail(changeId)
                .subscribe(new Subscriber<ReviewResult>() {
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
                    public void onNext(ReviewResult reviewResult) {
                        swipeRefreshLayout.setRefreshing(false);
                        if(reviewResult!=null){
                            if(reviewResult.getStatus() == 200){
                                recordId = reviewResult.getChangeRecord().getRecordId();
                                if(reviewResult.getAdvice()!=null){

                                    reviewDetailAdapter.setOneData(reviewResult);
                                    reviewDetailAdapter.setImgBaseUrl(reviewResult.getParentPath());
                                    reviewDetailAdapter.setImgAdaviceBaseUrl(reviewResult.getAdviceParentPath());
                                }
                            }else{
                                showToast(reviewResult.getMsg());
                            }
                        }
                    }
                });
    }

    /**
     * 复查通过
     */
    private void ReviewPass(){
        httpTask.reCheckPass(recordId)
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
                        if(result!=null){
                            showToast(result.getMessage());
                        }
                    }
                });
    }

    private void showReviewPassDialog()
    {
        final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(getContext());
        dialogBuilder
                .withTitle("提示")
                .withMessage("确认复查合格吗？")
                .withDialogColor("#99cccc")
                .withDuration(200)
                .withButton1Text("再看看")
                .withButton2Text("确认合格")
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
                        ReviewPass();


                    }
                }).show();
    }

    /**
     *
     * @param str
     */
    private void showToast(String str)
    {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

}
