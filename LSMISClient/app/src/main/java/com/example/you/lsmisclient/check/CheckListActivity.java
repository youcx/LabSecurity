package com.example.you.lsmisclient.check;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.check.bean.CheckItem;
import com.example.you.lsmisclient.check.bean.CheckItemElement;
import com.example.you.lsmisclient.check.bean.CheckListItemClickListener;
import com.example.you.lsmisclient.check.bean.FirstCheckList;
import com.example.you.lsmisclient.check.bean.SecondCheckList;
import com.example.you.lsmisclient.http.HttpTask;
import com.example.you.lsmisclient.treeview.Element;
import com.example.you.lsmisclient.treeview.TreeViewAdapter;
import com.example.you.lsmisclient.treeview.TreeViewItemClickListener;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class  CheckListActivity extends AppCompatActivity {
    /** 树中的元素集合 */
    private ArrayList<Element> elements;
    /** 数据源元素集合 */
    private ArrayList<Element> elementsData;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    @BindView(R.id.treeview)
    ListView treeview;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    /** http */
    HttpTask mTask;

    //data
    private static int id;
    TreeViewAdapter treeViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        ButterKnife.bind(this);

      //  LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        init();
        //toolbar
        setToolbar();
        //swipeRefreshLayout
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                startGetInfo();
            }
        });
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startGetInfo();
            }
        });

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        treeViewAdapter = new TreeViewAdapter(inflater);
        treeview.setAdapter(treeViewAdapter);
        treeview.setOnItemClickListener(new TreeViewItemClickListener(treeViewAdapter)
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                super.onItemClick(parent, view, position, id);
                //点击的item代表的元素
                Element element = (Element) treeViewAdapter.getItem(position);
                //点击没有子项的item
                if (!element.isHasChildren()) {
                    Log.i("ITEM_CLICK","点击了"+element.getCheckItem().getCheckTitle());
                    showCheckDialog(element.getCheckItem());
                }
            }
        });


    }

    private void showCheckDialog(final CheckItem checkItem)
    {
        final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle(checkItem.getTitleSerialNumber())
                .withMessage("检查项目："+checkItem.getCheckTitle()+"\n\n"+
                                "检查要点："+checkItem.getCheckImportant())
                .withDialogColor("#99cccc")
                .withDuration(200)
                .withButton1Text("取消")
                .withButton2Text("不符合")
                .isCancelableOnTouchOutside(true)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        Intent fillInCheckResult = new Intent(getBaseContext(),CheckActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("checkTitle",checkItem.getCheckTitle());
                        bundle.putString("checkPoint",checkItem.getCheckImportant());
                        bundle.putString("serialNum",checkItem.getTitleSerialNumber());
                        bundle.putInt("titleId",checkItem.getTitleId());
                        fillInCheckResult.putExtras(bundle);
                        startActivity(fillInCheckResult);

                    }
                }).show();
    }


    private void setToolbar()
    {
        toolbarTextView.setText("安全检查表");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBackDialog();
            }
        });
    }

    private void showBackDialog()
    {
        final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("提示")
                .withMessage("结束并提交当前检查任务结果吗？")
                .withDialogColor("#99cccc")
                .withDuration(200)
                .withButton1Text("再检查下")
                .withButton2Text("确认提交")
                .isCancelableOnTouchOutside(true)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        startSubmitCheckRecord();


                    }
                }).show();
    }

    private void init() {
        mTask=new HttpTask();


        //添加节点 -- 节点名称，节点level，节点id，父节点id，是否有子节点，是否展开

//        //添加最外层节点
//        Element e1 = new Element("山东省", Element.TOP_LEVEL, 0, Element.NO_PARENT, true, false);
//
//        //添加第一层节点
//        Element e2 = new Element("青岛市", Element.TOP_LEVEL + 1, 1, e1.getId(), true, false);
//        //添加第二层节点
//        Element e3 = new Element("市南区", Element.TOP_LEVEL + 2, 2, e2.getId(), true, false);
//        //添加第三层节点
//        Element e4 = new Element("香港中路", Element.TOP_LEVEL + 3, 3, e3.getId(), false, false);
//
//        //添加第一层节点
//        Element e5 = new Element("烟台市", Element.TOP_LEVEL + 1, 4, e1.getId(), true, false);
//        //添加第二层节点
//        Element e6 = new Element("芝罘区", Element.TOP_LEVEL + 2, 5, e5.getId(), true, false);
//        //添加第三层节点
//        Element e7 = new Element("凤凰台街道", Element.TOP_LEVEL + 3, 6, e6.getId(), false, false);
//
//        //添加第一层节点
//        Element e8 = new Element("威海市", Element.TOP_LEVEL + 1, 7, e1.getId(), false, false);
//
//        //添加最外层节点
//        Element e9 = new Element("广东省", Element.TOP_LEVEL, 8, Element.NO_PARENT, true, false);
//        //添加第一层节点
//        Element e10 = new Element("深圳市", Element.TOP_LEVEL + 1, 9, e9.getId(), true, false);
//        //添加第二层节点
//        Element e11 = new Element("南山区", Element.TOP_LEVEL + 2, 10, e10.getId(), true, false);
//        //添加第三层节点
//        Element e12 = new Element("深南大道", Element.TOP_LEVEL + 3, 11, e11.getId(), true, false);
//        //添加第四层节点
//        Element e13 = new Element("10000号", Element.TOP_LEVEL + 4, 12, e12.getId(), false, false);
//
//        //添加初始树元素
//        elements.add(e1);
//        elements.add(e9);
//        //创建数据源
//        elementsData.add(e1);
//        elementsData.add(e2);
//        elementsData.add(e3);
//        elementsData.add(e4);
//        elementsData.add(e5);
//        elementsData.add(e6);
//        elementsData.add(e7);
//        elementsData.add(e8);
//        elementsData.add(e9);
//        elementsData.add(e10);
//        elementsData.add(e11);
//        elementsData.add(e12);
//        elementsData.add(e13);
    }

    private void startGetInfo()
    {
        mTask.getFirstCheckList()
                .subscribe(new Subscriber<Result<List<FirstCheckList>>>() {
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
                            showToast(msg);
                        }else {

                        }
                    }

                    @Override
                    public void onNext(Result<List<FirstCheckList>> listResult) {
                        swipeRefreshLayout.setRefreshing(false);
                        if(listResult!=null)
                        {
                            if(listResult.getStatus()==200)
                            {
                                initFirstCheckList(listResult.getData());
                            }
                        }
                    }
                });
    }

    private void initFirstCheckList(List<FirstCheckList> data)
    {
        id=0;
        elements = new ArrayList<Element>();
        elementsData = new ArrayList<Element>();
        for(int i=0;i<data.size();i++)
        {
            Element e1 = new Element(data.get(i).getGroupSerialNumber()+" "+data.get(i).getGroupLevelName(), Element.TOP_LEVEL, id, Element.NO_PARENT, true, false);
            elements.add(e1);
            elementsData.add(e1);
            id++;
            startGetSecondList(e1.getId(),data.get(i).getGroupSerialNumber());

        }
        treeViewAdapter.setData(elements,elementsData);

    }

    private void startGetSecondList(final int pid,String serialNum)
    {
        mTask.getSecondCheckList(serialNum)
                .subscribe(new Subscriber<Result<List<SecondCheckList>>>() {
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
                            showToast(msg);
                        }else {

                        }
                    }

                    @Override
                    public void onNext(Result<List<SecondCheckList>> listResult) {
                        if(listResult!=null)
                        {
                            if(listResult.getStatus()==200)
                            {
                                initSecondCheckList(pid,listResult.getData());
                            }
                        }
                    }
                });
    }

    private void initSecondCheckList(int pid,List<SecondCheckList> data)
    {
        for(int j=0;j<data.size();j++)
        {
            Element e1 = new Element(data.get(j).getGroupSerialNumber()+" "+data.get(j).getGroupLevelName(), Element.TOP_LEVEL+1, id, pid, true, false);
            elementsData.add(e1);
            id++;
            startGetThirdCheckList(e1.getId(),data.get(j).getGroupSerialNumber(),4);
        }
    }

    private void startGetThirdCheckList(final int pid,String serialNum,int labId)
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
                            showToast(msg);
                        }else {

                        }
                    }

                    @Override
                    public void onNext(Result<List<CheckItem>> listResult) {
                        initCheckItem(pid,listResult.getData());
                    }
                });
    }

    private void initCheckItem(int pid,List<CheckItem> data)
    {
        for(int j=0;j<data.size();j++)
        {
            Element e1 = new Element(data.get(j).getTitleSerialNumber()+" "+data.get(j).getCheckTitle(), Element.TOP_LEVEL+2, id, pid, false, false,data.get(j));
            elementsData.add(e1);
            id++;
        }
    }

    private void startSubmitCheckRecord()
    {
        int recordId=getSharedPreferences("checkdata",MODE_PRIVATE).getInt("recordId",0);
        if(recordId==0)
        {
            Log.i("CheckListActivity","return");
            return;
        }
        mTask.submitCheckRecord(recordId)
                .subscribe(new Subscriber<Result>() {
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
                            showToast(msg);
                        }else {

                        }
                    }

                    @Override
                    public void onNext(Result result) {
                        if(result!=null)
                        {
                            if(result.getStatus()==200)
                            {
                                showToast("提交成功");
                                finish();
                            }
                        }
                    }
                });

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode==KeyEvent.KEYCODE_BACK)
//        {
//            showBackDialog();
//        }
//        return false;
//    }

    @Override
    public void onBackPressed() {
        showBackDialog();
        //super.onBackPressed();
    }

    /**
     * 显示Toast消息
     */
    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
