package com.example.you.lsmisclient.lab;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.LabInfo;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.fragment.LabBaseInfoChangeFragment;
import com.example.you.lsmisclient.fragment.LabBaseInfoFragment;
import com.example.you.lsmisclient.fragment.LabSecurityInfoChangeFragment;
import com.example.you.lsmisclient.fragment.LabSecurityInfoFragment;
import com.example.you.lsmisclient.http.HttpTask;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class LabInfoChangeActivity extends AppCompatActivity {

    @BindView(R.id.lab_info_change_toolbar)
    Toolbar labInfoChangeToolbar;
    @BindView(R.id.lab_info_change_tablayout)
    TabLayout labInfoChangeTabLayout;
    @BindView(R.id.lab_info_change_viewpager)
    ViewPager labInfoChangeViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_info_change);
        //绑定控件
        ButterKnife.bind(this);
        //toolbar
        labInfoChangeToolbar.setTitle("实验室信息");
        setSupportActionBar(labInfoChangeToolbar);
        //Fragment
        final ArrayList<Fragment> fgList=new ArrayList<>(2);
        fgList.add(new LabBaseInfoChangeFragment());
        fgList.add(new LabSecurityInfoChangeFragment());
        //Fragment适配器
        FragmentPagerAdapter mFragmentPagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fgList.get(position);
            }

            @Override
            public int getCount() {
                return fgList.size();
            }
        };
        //ViewPager设置
        labInfoChangeViewPager.setAdapter(mFragmentPagerAdapter);
        labInfoChangeTabLayout.setupWithViewPager(labInfoChangeViewPager);
        labInfoChangeTabLayout.getTabAt(0).setText("基本信息修改");
        labInfoChangeTabLayout.getTabAt(1).setText("危险源信息修改");


    }



    /**
     * 显示Toast消息
     */
    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}
