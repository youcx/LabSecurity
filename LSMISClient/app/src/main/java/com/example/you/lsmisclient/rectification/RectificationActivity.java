package com.example.you.lsmisclient.rectification;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.http.HttpTask;
import com.example.you.lsmisclient.rectification.fragment.InReformFragment;
import com.example.you.lsmisclient.rectification.fragment.MyRectifiFragment;
import com.example.you.lsmisclient.rectification.fragment.ReviewFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RectificationActivity extends AppCompatActivity{
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;




    //数据

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

        initToolbar();

        //init
        mTask=new HttpTask();

        initViewPager();

        initTabLayout();

        //




    }

    private void initToolbar(){
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
    }

    private void initViewPager(){
        //Fragment
        final List<Fragment> fgList = new ArrayList<>();
        fgList.add(new MyRectifiFragment());
        fgList.add(new InReformFragment());
        fgList.add(new ReviewFragment());

        //FragmentPager
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fgList.get(position);
            }

            @Override
            public int getCount() {
                return fgList.size();
            }
        };

        //ViewPager
        viewPager.setAdapter(fragmentPagerAdapter);

    }

    private void initTabLayout(){
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("整改通知");
        tabLayout.getTabAt(1).setText("正在整改");
        tabLayout.getTabAt(2).setText("复查通知");
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
