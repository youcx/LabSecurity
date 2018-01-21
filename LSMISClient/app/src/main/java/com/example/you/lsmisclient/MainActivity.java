package com.example.you.lsmisclient;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.you.lsmisclient.fragment.AboutFragment;
import com.example.you.lsmisclient.fragment.HomeFragment;
import com.example.you.lsmisclient.fragment.ManageFragment;
import com.example.you.lsmisclient.fragment.MineFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    //绑定控件
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.mBottomView)
    BottomNavigationView mBottomView;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    //标题栏
    private String[] titles=new String[]{"LSMIS","管理","我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //控件绑定
        ButterKnife.bind(this);

        //toolbar
        mToolbar.setTitle("LSMIS");
        setSupportActionBar(mToolbar);

        //ViewPager监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomView.getMenu().getItem(position).setChecked(true);
                if(position==2)
                {
                    mToolbar.setVisibility(View.GONE);
                }else{
                    mToolbar.setVisibility(View.VISIBLE);
                    mToolbar.setTitle(titles[position]);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //Fragment
        final ArrayList<Fragment> fgList=new ArrayList<>(3);
        fgList.add(new HomeFragment());
        fgList.add(new ManageFragment());
        fgList.add(new MineFragment());
        //fgList.add(new MineFragment());
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
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);//预加载剩下三页

        //BottomNavigationView监听
        mBottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.bottom_home:
                        mViewPager.setCurrentItem(0);
                        mToolbar.setVisibility(View.VISIBLE);
                        mToolbar.setTitle(titles[0]);
                        break;
                    case R.id.bottom_manage:
                        mViewPager.setCurrentItem(1);
                        mToolbar.setVisibility(View.VISIBLE);
                        mToolbar.setTitle(titles[1]);
                        break;
//                    case R.id.bottom_about:
//                        mViewPager.setCurrentItem(2);
//                        mToolbar.setTitle(titles[2]);
//                        break;
                    case R.id.bottom_mine:
                        mViewPager.setCurrentItem(2);
                        mToolbar.setTitle(titles[2]);
                        mToolbar.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    /**
     * 菜单项点击监听
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.toolbar_scan:
                showToast("开始扫描");
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showToast(String str)
    {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }
}
