package com.example.you.lsmisclient.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.check.SelectCheckMissionActivity;
import com.example.you.lsmisclient.rectification.ReformActivity;
import com.example.you.lsmisclient.rectification.ReviewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.check_noti)
            View checkNoti;
    @BindView(R.id.review_noti)
            View reviewNoti;
    @BindView(R.id.reform_noti)
            View reformNoti;
    @BindView(R.id.change_checker_noti)
            View changeCheckerNoti;
    @BindView(R.id.daily_noti)
            View dailyNoti;
    @BindView(R.id.others_noti)
            View othersNoti;

    Toast toast;

    //data
    private final int GET_ALL_LIST = 222;


    public ManageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage,container,false);

        //绑定控件
        ButterKnife.bind(this,view);

        checkNoti.setOnClickListener(this);
        reviewNoti.setOnClickListener(this);
        reformNoti.setOnClickListener(this);
        changeCheckerNoti.setOnClickListener(this);
        dailyNoti.setOnClickListener(this);
        othersNoti.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_noti:
                Intent missionSelect=new Intent(getContext(),SelectCheckMissionActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("fromWhere",GET_ALL_LIST);
                missionSelect.putExtras(bundle);
                startActivity(missionSelect);
                break;
            case R.id.review_noti:
                Intent gotoReviewAc = new Intent(getContext(), ReviewActivity.class);
                startActivity(gotoReviewAc);
                break;
            case R.id.reform_noti:
                Intent gotoReformAc = new Intent(getContext(), ReformActivity.class);
                startActivity(gotoReformAc);
                break;
            case R.id.change_checker_noti:
                showToast("暂未开发");
                break;
            case R.id.daily_noti:
                showToast("暂未开发");
                break;
            case R.id.others_noti:
                showToast("暂未开发");
                break;

        }
    }


    private void showToast(String s){
        if(toast==null){
            toast = Toast.makeText(getContext(),s,Toast.LENGTH_SHORT);
        }else{
            toast.setText(s);
        }

        toast.show();
    }
}
