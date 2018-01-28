package com.example.you.lsmisclient.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.adapter.LabAdapter;
import com.example.you.lsmisclient.bean.Lab;
import com.example.you.lsmisclient.lab.LabInfoActivity;
import com.example.you.lsmisclient.lab.LabInfoChangeActivity;
import com.example.you.lsmisclient.qr.QrScannerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LabListActivity extends AppCompatActivity {
    @BindView(R.id.lab_list_recyclerview)
    RecyclerView labListRecyclerView;
    @BindView(R.id.lab_list_toolbar)
    Toolbar labListToolbar;
    //适配器
    LabAdapter labAdapter;
    //数据
    List<Lab> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_list);
        //绑定控件
        ButterKnife.bind(this);
        //init
        initLab();
        //toolbar
        labListToolbar.setTitle("实验室列表");
        setSupportActionBar(labListToolbar);
        //toolbarTextView.setText("实验室分览");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        labListToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //适配器初始化
        labAdapter=new LabAdapter(datas);
        labAdapter.setLabItemClickListener(new LabAdapter.LabItemClickListener() {
            @Override
            public void onLabItemClickListener(View view, Lab lab) {
                Intent intent=new Intent(getBaseContext(), LabInfoActivity.class);
                startActivity(intent);
            }
        });
        //设置布局
            labListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
            labListRecyclerView.setAdapter(labAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.toolbar_scan:
                Intent intent=new Intent(this, QrScannerActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化数据
     */
    private void initLab()
    {
        datas=new ArrayList<Lab>();
        datas.add(new Lab("ACM","信息工程学院","国家级","辐射","老师1","老师2"));
        datas.add(new Lab("306","信息工程学院","院级","放射物","老师1","老师3"));
        datas.add(new Lab("402","生命学院","院级","易燃易爆","老师1","老师4"));
    }
}
