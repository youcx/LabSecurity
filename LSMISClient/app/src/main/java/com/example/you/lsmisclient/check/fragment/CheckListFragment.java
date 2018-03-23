package com.example.you.lsmisclient.check.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.check.CheckActivity;
import com.example.you.lsmisclient.check.CheckItemListActivity;
import com.example.you.lsmisclient.check.adapter.CheckListAdapter;
import com.example.you.lsmisclient.check.bean.FirstCheckList;
import com.example.you.lsmisclient.check.bean.SecondCheckList;
import com.example.you.lsmisclient.http.HttpTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckListFragment extends Fragment {
    //bind
    @BindView(R.id.check_list_expandLV)
    ExpandableListView checkListExpandTV;
    //下拉控件
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    //数据
    String[] item1=null;
    String[][] item2=null;
    String[][] groupSerialNumb;
    //adapter
    private CheckListAdapter checkListAdapter;
    //http
    HttpTask mTask;

    public CheckListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_check_list,container, false);
        //bind
        ButterKnife.bind(this,view);
        //初始化数据
        mTask=new HttpTask();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                startGetInfo();
            }
        });
        //startGetInfo();
       // initDatas();
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startGetInfo();

            }
        });

        return view;
    }


    /**
     * 获取检查表
     */
    private void startGetInfo()
    {
        mTask.getFirstCheckList()
                .subscribe(new Subscriber<Result<List<FirstCheckList>>>() {
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
                    public void onNext(Result<List<FirstCheckList>> listResult) {
                        swipeRefreshLayout.setRefreshing(false);
                        if(listResult!=null)
                        {
                            if(listResult.getStatus()==200)
                            {
                                initFirstCheckList(listResult.getData());
                            }
                        }
                    }
                });
    }

    private void initFirstCheckList(List<FirstCheckList> data)
    {
        item1=new String[data.size()];
        item2=new String[data.size()][10];
        groupSerialNumb=new String[data.size()][10];
        for(int i=0;i<data.size();i++)
        {
            item1[i]=data.get(i).getGroupSerialNumber()+"."+data.get(i).getGroupLevelName();
            startGetSecondList(i,data.get(i).getGroupSerialNumber());
        }
        checkListAdapter=new CheckListAdapter(item1,item2,getActivity());
                checkListExpandTV.setAdapter(checkListAdapter);
                checkListExpandTV.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        //  Toast.makeText(getActivity(),"点击了"+item2[groupPosition][childPosition],Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(), CheckItemListActivity.class);
                        intent.putExtra("serialNum",groupSerialNumb[groupPosition][childPosition]);
                        startActivity(intent);
                        return false;
                    }
                });

    }

    private void startGetSecondList(final int i,String serialNum)
    {
        mTask.getSecondCheckList(serialNum)
                .subscribe(new Subscriber<Result<List<SecondCheckList>>>() {
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
                    public void onNext(Result<List<SecondCheckList>> listResult) {
                        if(listResult!=null)
                        {
                            if(listResult.getStatus()==200)
                            {
                                initSecondCheckList(i,listResult.getData());
                            }
                        }
                    }
                });
    }

    /**
     * 初始化二级检查表
     * @param i
     * @param data
     */
    private void initSecondCheckList(int i,List<SecondCheckList> data )
    {
        for(int j=0;j<data.size();j++)
        {
            item2[i][j]=data.get(j).getGroupSerialNumber()+" "+data.get(j).getGroupLevelName();
            groupSerialNumb[i][j]=data.get(j).getGroupSerialNumber();
        }
    }

    private void initDatas()
    {
        item1=new String[12];
        item2=new String[12][6];
        for(int i=0;i<12;i++)
        {
            item1[i]="组织结构:"+i;
            for(int j=0;j<6;j++)
            {
                item2[i][j]="子目录"+j;
            }
        }
    }


    /**
     * 显示Toast消息
     */
    private void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
