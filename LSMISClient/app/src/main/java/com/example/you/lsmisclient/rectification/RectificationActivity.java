package com.example.you.lsmisclient.rectification;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.rectification.adapter.MyRectifiAdapter;
import com.example.you.lsmisclient.rectification.bean.MyRectification;
import com.example.you.lsmisclient.rectification.fragment.MyRectifiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RectificationActivity extends AppCompatActivity {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    //适配器
    MyRectifiAdapter myRectifiAdapter;
    //数据
    List<MyRectification> datas;

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
        //init
        initDatas();
        //
        myRectifiAdapter=new MyRectifiAdapter(datas);
        myRectifiAdapter.setMyRectifiItemListener(new MyRectifiAdapter.MyRectifiItemClickListener() {
            @Override
            public void onMyRectifiItemClickListener(View view, MyRectification myRectification) {

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(myRectifiAdapter);


    }
    private void initDatas()
    {
        datas=new ArrayList<>();
        datas.add(new MyRectification("实验室过道发现堆放大量杂物，请尽快将杂物清除，不要影响正常的实验室工作，谢谢配合",
                "整改期限五天","2018-2-5 12:30:00"));
        datas.add(new MyRectification("实验室过道发现堆放大量杂物，请尽快将杂物清除，不要影响正常的实验室工作，谢谢配合",
                "整改期限五天","2018-2-5 12:30:00"));
        datas.add(new MyRectification("实验室过道发现堆放大量杂物，请尽快将杂物清除，不要影响正常的实验室工作，谢谢配合",
                "整改期限五天","2018-2-5 12:30:00"));

    }
}
