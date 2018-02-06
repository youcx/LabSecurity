package com.example.you.lsmisclient.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.http.HttpTask;
import com.example.you.lsmisclient.lab.adapter.HazardListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabSecurityInfoFragment extends Fragment {
    @BindView(R.id.security_info_expandLV)
    ExpandableListView securityInfoExpandLV;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;

    //数据
    String[] item1=null;
    String[][][] item2=null;

    int labId;
    //adapter
    HazardListAdapter hazardListAdapter;
    //http
    HttpTask mTask;

    public LabSecurityInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lab_security_info, container, false);
        //bind
        ButterKnife.bind(this,view);
        mTask=new HttpTask();
        mProgressBar.setVisibility(View.VISIBLE);
        Bundle bundle=LabSecurityInfoFragment.this.getArguments();
        labId=bundle.getInt("labId",4);
        //进行数据请求
       startGetInfo();
        item1=new String[6];
        item2=new String[6][20][2];
        for(int i=0;i<5;i++)
        {
            item1[i]="父节点"+i;
            for(int j=0;j<5;j++)
            {
               item2[i][j][0]="子节点"+j;
                item2[i][j][1]="0";
            }
        }

        //二级列表
//        hazardListAdapter=new HazardListAdapter(item1,item2,getActivity());
//        securityInfoExpandLV.setAdapter(hazardListAdapter);
        return view;
    }

    /**
     * 获取危险源大类
     */
    private void startGetInfo()
    {
        mTask.getMainDanger()
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
                            if(result.getStatus()==200)
                            {
                                Log.i("危险源大类",result.getData().toString());
                                initMainDangerDatas(result.getData().toString());
                            }else{
                                showToast(result.getMessage());
                            }
                        }
                    }
                });
    }

    /**
     * 初始化危险源大类
     * @param s
     */
    private void initMainDangerDatas(String s)
    {
        try{
            JSONArray dataArray=new JSONArray(s);
            item1=new String[dataArray.length()];
            item2=new String[dataArray.length()][20][2];
            for(int i=0;i<dataArray.length();i++)
            {
                JSONObject jsonObject=(JSONObject) dataArray.get(i);
                item1[i]=jsonObject.getString("dangerMainTypeName");
                //Log.i("数据",item1[i]);
                Log.i("一项",""+jsonObject.getInt("dangerMainTypeId"));
                startGetDetailDanger(i,jsonObject.getInt("dangerMainTypeId"),labId);
            }
//            for(int i=0;i<5;i++)
//            {
//                Log.i("父节点",item1[i]);
//                for(int j=0;j<5;j++)
//                {
//                    Log.i("子节点",item2[i][j][0]);
//                    Log.i("子节点数量",item2[i][j][1]);
//                }
//            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hazardListAdapter=new HazardListAdapter(item1,item2,getActivity());
                    securityInfoExpandLV.setAdapter(hazardListAdapter);
                    mProgressBar.setVisibility(View.GONE);
                }
            });


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取危险源子类
     * @param i
     * @param id
     * @param labid
     */
    private void startGetDetailDanger(final int i, int id, final int labid)
    {
        mTask.getDetailDanger(id,labid)
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
                            if(result.getStatus()==200){
                                Log.i("item1！！！",""+labid);
                                inidDetailDangerDatas(i,result.getData().toString());
                            }
                        }
                    }
                });
    }

    /**
     * 初始化危险源子类
     */
    private void inidDetailDangerDatas(int i,String s){
        try{
            JSONArray jsonArray=new JSONArray(s);
            Log.i("具体危险源",s);
            for (int j=0;j<jsonArray.length();j++)
            {
                JSONObject jsonObject=(JSONObject) jsonArray.get(j);
                item2[i][j][0]=jsonObject.getString("dangerTypeName");
//                Log.i("危险数量",jsonObject.getString("dangerNumb"));
//                if(jsonObject.getString("dangerNumb")!=null)
                item2[i][j][1]=""+jsonObject.getInt("dangerNumb");
//                else
                    //item2[i][j][1]="0";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 显示Toast消息
     */
    private void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
