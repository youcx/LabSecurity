package com.example.you.lsmisclient.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.adapter.LabAdapter;
import com.example.you.lsmisclient.bean.College;
import com.example.you.lsmisclient.bean.Lab;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.check.CheckActivity;
import com.example.you.lsmisclient.http.HttpTask;
import com.example.you.lsmisclient.lab.LabInfoActivity;
import com.example.you.lsmisclient.lab.LabInfoChangeActivity;
import com.example.you.lsmisclient.lab.LabOverViewActivity;
import com.example.you.lsmisclient.qr.QrScannerActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class LabListActivity extends AppCompatActivity {
    @BindView(R.id.lab_list_recyclerview)
    RecyclerView labListRecyclerView;
    @BindView(R.id.lab_list_toolbar)
    Toolbar labListToolbar;
    //适配器
    LabAdapter labAdapter;
    //数据
   //List<Lab> datas;
    //指令
    String order=null;
    int departmentId;
    int labLevel;
    int dangerMainTypeId;
    int taskId;
    //http
    HttpTask mTask;
    LabAdapter.LabItemClickListener itemClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_list);
        Intent intent=getIntent();
        Bundle bd=intent.getExtras();
        order=bd.getString("task");
        //绑定控件
        ButterKnife.bind(this);
        //init
        init();
        if(order.equals("fromDepartment"))
        {
            //按学院获取实验室列表
            departmentId=bd.getInt("departmentId");
            Log.i("学院id",departmentId+"");
            startGetLabListFromDepart(0,departmentId);
        }else if(order.equals("fromLevel"))
        {
            //按级别获取实验室列表
            labLevel=bd.getInt("labLevel");
            startGetLabListFromLevel(labLevel,0);
        }else if(order.equals("fromHazard"))
        {
            //按危险源获取实验室列表
            dangerMainTypeId=bd.getInt("dangerMainTypeId");
            startGetLabListFromHazard(dangerMainTypeId,0);

        }else if(order.equals("check"))
        {
            //按任务获取实验室列表
            taskId=bd.getInt("taskId");
            startGetLabListFromTask(taskId,50,1);

        }
        //initLab();
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
                startActivityForResult(intent,22);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case 22:
                Toast.makeText(this,data.getStringExtra("qrValue"),Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void init()
    {
        mTask=new HttpTask();
        labAdapter=new LabAdapter();
        itemClickListener=new LabAdapter.LabItemClickListener() {
            @Override
            public void onLabItemClickListener(View view, Lab lab) {
                if(order.equals("check"))
                {

                }else if(order.equals("fromDepartment")||order.equals("fromLevel")||order.equals("fromHazard")){
                    Intent intent=new Intent(getBaseContext(), LabInfoActivity.class);
                    intent.putExtra("labId",lab.getID());
                    startActivity(intent);
                }
            }
        };
        //设置布局
        labListRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        //设置适配器
        labListRecyclerView.setAdapter(labAdapter);
        labAdapter.setLabItemClickListener(itemClickListener);
    }

    /**
     * 按学院获取实验室列表
     * @param page  页码
     * @param departmentId  学院id
     */
    private void startGetLabListFromDepart(int page,int departmentId)
    {
        mTask.getLabListByDepartment(page,departmentId)
                .subscribe(new Subscriber<Result<List<Lab>>>() {
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
                            Log.i("Error",msg);
                        }else {

                        }
                    }

                    @Override
                    public void onNext(Result<List<Lab>> listResult) {
                        if(listResult!=null)
                        {
                            if(listResult.getStatus()==200)
                            {
                                Log.i("初始化",listResult.getData().toString());
                                labAdapter.setmDatas(listResult.getData());
                                //initLabByDepart(listResult.getData());
                            }
                        }
                    }
                });
    }


    /**
     * 按级别获取实验室列表
     * @param labLevel
     * @param page
     */
    private void startGetLabListFromLevel(final int labLevel, int page)
    {
        mTask.getLabListByLevel(labLevel,0,page)
                .subscribe(new Subscriber<Result<List<Lab>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<Lab>> listResult) {
                        if(listResult!=null)
                        {
                            if(listResult.getStatus()==200)
                            {
                                labAdapter.setmDatas(listResult.getData());
                            }
                        }
                    }
                });
    }



    /**
     * 按危险源获取实验室列表
     * @param id
     * @param page
     */
    private void startGetLabListFromHazard(int id,int page)
    {
        mTask.getLabListByHazard(id,page)
                .subscribe(new Subscriber<Result<List<Lab>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<Lab>> listResult) {
                        if(listResult!=null)
                        {
                            if(listResult.getStatus()==200)
                            {
                                labAdapter.setmDatas(listResult.getData());
                            }
                        }
                    }
                });
    }

    /**
     * 按任务获取实验室列表
     * @param id
     * @param pageSize
     * @param pageNum
     */
    private void startGetLabListFromTask(int id,int pageSize,int pageNum)
    {
        mTask.getLabListByTask(id,pageSize,pageNum)
                .subscribe(new Subscriber<Result<List<Lab>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<List<Lab>> listResult) {
                        if(listResult!=null)
                        {
                            if(listResult.getStatus()==200)
                            {
                               labAdapter.setmDatas(listResult.getData());
                            }
                        }
                    }
                });
    }



    /**
     * 初始化数据
     */
    private void initLab()
    {
//        datas=new ArrayList<Lab>();
//        datas.add(new Lab("ACM","信息工程学院","国家级","辐射","老师1","老师2"));
//        datas.add(new Lab("306","信息工程学院","院级","放射物","老师1","老师3"));
//        datas.add(new Lab("402","生命学院","院级","易燃易爆","老师1","老师4"));
    }
}
