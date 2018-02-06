package com.example.you.lsmisclient.lab;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.fragment.HomeFragment;
import com.example.you.lsmisclient.fragment.LabBaseInfoFragment;
import com.example.you.lsmisclient.fragment.LabSecurityInfoFragment;
import com.example.you.lsmisclient.fragment.ManageFragment;
import com.example.you.lsmisclient.fragment.MineFragment;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LabInfoActivity extends AppCompatActivity {
    //绑定控件
    @BindView(R.id.lab_info_toolbar)
    Toolbar labInfoToolbar;
    @BindView(R.id.lab_info_tablayout)
    TabLayout labInfoTabLayout;
    @BindView(R.id.lab_info_viewpager)
    ViewPager labInfoViewPager;
    //实验室id
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_info);
        //bind
        ButterKnife.bind(this);
        Intent intent=getIntent();
        id=intent.getIntExtra("labId",4);
        //toolbar
        labInfoToolbar.setTitle("实验室信息");
        setSupportActionBar(labInfoToolbar);
        //toolbarTextView.setText("实验室分览");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        labInfoToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LabBaseInfoFragment labBaseInforFragment=new LabBaseInfoFragment();
        LabSecurityInfoFragment labSecurityInfoFragment=new LabSecurityInfoFragment();
        //Activity向Fragment传值
        Bundle bundle=new Bundle();
        bundle.putInt("labId",id);
        labBaseInforFragment.setArguments(bundle);
        labSecurityInfoFragment.setArguments(bundle);
        //Fragment
        final ArrayList<Fragment> fgList=new ArrayList<>(2);
        fgList.add(labBaseInforFragment);
        fgList.add(labSecurityInfoFragment);
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
        labInfoViewPager.setAdapter(mFragmentPagerAdapter);
        //labInfoViewPager.setOffscreenPageLimit(1);//预加载剩下三页
        //labInfoTabLayout.addTab(newTab());
        labInfoTabLayout.setupWithViewPager(labInfoViewPager);
        labInfoTabLayout.getTabAt(0).setText("基本信息");
        labInfoTabLayout.getTabAt(1).setText("安全信息");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_lab_info_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.change_labinfo_menu:
                Intent intent=new Intent(this,LabInfoChangeActivity.class);
                startActivity(intent );
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * test okhttp
     */
    private void okhttpTest()
    {
        OkHttpClient client=new OkHttpClient();
        //String json1="{\"area\":\"\", \"labLevelId\":\"\", \"buildId\":\"\", \"departmentId\":\"\", \"labName\":\"\", \"denoterInfor\":\"\", \"responseName\":\"\", \"responsePhone\":\"\", \"managerName\":\"\", \"managerPhone\":\"\", \"labStatus\":\"\", \"submitPersonName\":\"\"}";
        String json1="{\n" +
                "\t\"labLevelId\":3,\n" +
                "\t\"labName\":\"ACM\",\n" +
                "\t\"labAddr\":\"东六\",\n" +
                "\t\"buildId\":11,\n" +
                "\t\"departmentId\":12,\n" +
                "\t\"denoterInfor\":\"ACM工作室\",\n" +
                "\t\"area\":11,\n" +
                "\t\"responseName\":\"郑小东\",\n" +
                "\t\"responsePhone\":\"11\",\n" +
                "\t\"managerName\":\"年后\",\n" +
                "\t\"managerPhone\":\"22\",\n" +
                "\t\"labStatus\":1,\n" +
                "\t\"submitPersonName\":\"SWUST\"\n" +
                "}";
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json1);
        Request request=new Request.Builder()
                .post(body)
                .url("http://192.168.43.174/labinfor/addinfor")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG","err");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                {
                    Log.i("TAG",response.body().string());
                }else{
                    Log.i("TAG","response err");
                }
            }
        });
    }
}
