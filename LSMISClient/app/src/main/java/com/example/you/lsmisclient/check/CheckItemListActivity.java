package com.example.you.lsmisclient.check;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.check.bean.CheckItem;
import com.example.you.lsmisclient.database.MyDatabaseHelper;
import com.example.you.lsmisclient.http.HttpTask;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class CheckItemListActivity extends AppCompatActivity {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    @BindView(R.id.item_list_view)
    ListView itemListView;

    //数据库
    private MyDatabaseHelper dbHelper;

    //数据
    List<CheckItem> mDatas;
    String[] checkTitle;
    String groupSerialNumber; //第二组的序号
    int labId;  //实验室id
    //http
    HttpTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_item_list);
        //bind
        ButterKnife.bind(this);
        mTask=new HttpTask();
        Intent intent=getIntent();
        groupSerialNumber=intent.getStringExtra("serialNum");
        toolbarTextView.setText("检查项选择");
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
        SharedPreferences sp=getSharedPreferences("checkdata",MODE_PRIVATE);
        labId=sp.getInt("labId",0);
        Log.i("labID!!!!!!!",""+labId);
        startGetCheckItemList(groupSerialNumber,labId);

    }

    /**
     * 获取检查项列表
     * @param serialNum
     * @param labId
     */
    private void  startGetCheckItemList(String serialNum,int labId)
    {
        mTask.getCheckItemList(serialNum,labId)
                .subscribe(new Subscriber<Result<List<CheckItem>>>() {
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
                            Toast.makeText(CheckItemListActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }else {

                        }
                    }

                    @Override
                    public void onNext(Result<List<CheckItem>> listResult) {
                        if(listResult!=null)
                        {
                            if(listResult.getStatus()==200)
                            {
                                initCheckItem(listResult.getData());
                            }
                        }
                    }
                });
    }

    private void initCheckItem(List<CheckItem> data)
    {

        mDatas=data;
        checkTitle=new String[data.size()];
        int startTitleId=data.get(0).getTitleId();
        int endTitleId=data.get(data.size()-1).getTitleId();
        //记录该类检查项总数
        SharedPreferences.Editor editor=getSharedPreferences("checkdata",MODE_PRIVATE).edit();
        editor.putInt("checkCount",data.size());
        for(int i=0;i<data.size();i++)
        {
            CheckItem checkItem=data.get(i);
            List<CheckItem> checks=DataSupport.where("titleSerialNumber = ?",data.get(i).getTitleSerialNumber()).find(CheckItem.class);
            if(checks==null||checks.size()==0)
                checkItem.save();
            checkTitle[i]=data.get(i).getTitleSerialNumber()+" "+data.get(i).getCheckTitle().substring(0,10)+"......";
            //判断检查项起止
            if(startTitleId>checkItem.getTitleId())
            {
                startTitleId=checkItem.getTitleId();
            }
            if(endTitleId<checkItem.getTitleId())
            {
                endTitleId=checkItem.getTitleId();
            }
        }
        editor.putInt("startTitleId",startTitleId);
        editor.putInt("endTitleId",endTitleId);
        editor.apply();
       final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_checked,checkTitle);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                itemListView.setAdapter(arrayAdapter);
                itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(getBaseContext(),CheckActivity.class);
                        intent.putExtra("titleId",mDatas.get(position).getTitleId());
                        startActivity(intent);
                    }
                });
            }
        });

    }
}
