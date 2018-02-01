package com.example.you.lsmisclient.check;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.check.fragment.CheckListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SafetyCheckListActivity extends AppCompatActivity {
    @BindView(R.id.check_list_toolbar)
    Toolbar checkListToolbar;
    @BindView(R.id.check_list_tablayout)
    TabLayout checkListTablayout;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    @BindView(R.id.check_list_ViewPager)
    ViewPager checkListViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_check_list);
        //bind
        ButterKnife.bind(this);
        toolbarTextView.setText("安全检查表 ");
        setSupportActionBar(checkListToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        checkListToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //fragment
        final ArrayList<Fragment> fgList=new ArrayList<>(4);
        fgList.add(new CheckListFragment());
        fgList.add(new CheckListFragment());
        fgList.add(new CheckListFragment());
        fgList.add(new CheckListFragment());
        //fragment适配器
        FragmentPagerAdapter fragmentPagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fgList.get(position);
            }

            @Override
            public int getCount() {
                return fgList.size();
            }
        };
        //VeiwPager设置
        checkListViewPager.setAdapter(fragmentPagerAdapter);
        checkListTablayout.setupWithViewPager(checkListViewPager);
        checkListTablayout.getTabAt(0).setText("层级");
        checkListTablayout.getTabAt(1).setText("流式");
        checkListTablayout.getTabAt(2).setText("未检");
        checkListTablayout.getTabAt(3).setText("不符");




    }
}
