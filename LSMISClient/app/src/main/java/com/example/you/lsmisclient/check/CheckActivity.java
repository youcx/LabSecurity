package com.example.you.lsmisclient.check;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.check.bean.CheckItem;
import com.example.you.lsmisclient.http.HttpTask;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class CheckActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.check_toolbar)
    Toolbar checkToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    @BindView(R.id.check_radio_group)
    RadioGroup checkRadioGroup;
    //btn
    @BindView(R.id.previous_item_btn)
    Button previoutItemBtn;
    @BindView(R.id.next_item_btn)
    Button nextItemBtn;
    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.check_item_txtv)
    TextView checkItemTxtView;
    @BindView(R.id.check_point_txtv)
    TextView checkPointTxtView;
    @BindView(R.id.check_record_edt)
    EditText checkRecordEdt;

    //检查项序号
    String serialNum;
    List<CheckItem> checkItem;
    //检查项id
    int titleId;
    int startTitleId;
    int endTitleId;
    //任务记录id
    int recordId;
    int labId;
    //该类总检查项
    int checkCount;
    //符合结果
    int checkResult;                        //符合结果
    final int CONFORM=200;                  //符合
    final int INCONFORMITY=300;             //不符合
    final int INAPPLICABLE=400;             //不适用

    //http
    HttpTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Intent intent=getIntent();
        titleId=intent.getIntExtra("titleId",0);
        checkItem= DataSupport.where("titleId = ?",""+titleId).find(CheckItem.class);
        //bind
        ButterKnife.bind(this);
        mTask=new HttpTask();
        //init
        SharedPreferences sp=getSharedPreferences("checkdata",MODE_PRIVATE);
        startTitleId=sp.getInt("startTitleId",0);
        endTitleId=sp.getInt("endTitleId",0);
        recordId=sp.getInt("recordId",0);
        labId=sp.getInt("labId",0);
        checkCount=sp.getInt("checkCount",0);
        Log.i("endTitleID!!!!!",""+endTitleId);
        //ToolBar
        toolbarTextView.setText(checkItem.get(0).getTitleSerialNumber());
        setSupportActionBar(checkToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        checkToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //btn
        previoutItemBtn.setOnClickListener(this);
        nextItemBtn.setOnClickListener(this);
        submit.setOnClickListener(this);
        //设置UI
        checkItemTxtView.setText(checkItem.get(0).getCheckTitle());
        checkPointTxtView.setText(checkItem.get(0).getCheckImportant());
        //单选框
        checkRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.check_radioBtn1:
                        checkResult=CONFORM;       //符合
                        break;
                    case R.id.check_radioBtn2:
                        checkResult=INCONFORMITY;       //不符合
                        break;
                    case R.id.check_radioBtn3:
                        checkResult=INAPPLICABLE;       //不适用
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.next_item_btn:
                gotoNextItem();
                break;
            case R.id.previous_item_btn:
                gotoLastItem();
                break;
            case R.id.submit:
                final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(this);
                dialogBuilder
                        .withTitle("提示")
                        .withMessage("确定提交结果并开始下一项检查任务吗？")
                        .withDialogColor("#99cccc")
                        .withDuration(200)
                        .withButton1Text("取消")
                        .withButton2Text("确定")
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
                                checkNextItem();

                            }
                        }).show();
                break;
        }
    }

    /**
     * 进入上一项
     */
    private void gotoLastItem()
    {
        if(titleId==startTitleId)
        {
            //是开始项
            showToast("已经是最初项");
        }else
        {
            //进入上一项
            titleId--;
            updataItem();
        }
    }

    /**
     * 进入下一项
     */
    private void gotoNextItem()
    {
        if(titleId==endTitleId)
        {
            //是最后一项
            showToast("已经是最后一项");
        }else
        {
            //不是最后一项，进入下一项
            titleId++;
            updataItem();
        }
    }
    /**
     * 检查下一项
     */
    private void checkNextItem()
    {
        switch (checkResult)
        {
            case CONFORM:
                //符合
                //提交结果,先提交结果，再根据id是否进入下一项
                showToast("提交检查记录成功");
                if(titleId==endTitleId)
                {
                    //是最后一项
                    showToast("该类已经检查完毕");
                }else
                {
                    //不是最后一项，进入下一项
                    titleId++;
                    updataItem();
                }
                break;
            case INCONFORMITY:
                //上传不符合结果
                if(titleId==endTitleId)
                {
                    //是最后一项
                    showToast("该类已经检查完毕");
                }else
                {
                    //不是最后一项，进入下一项
                    titleId++;
                    updataItem();
                }
                break;
            case INAPPLICABLE:
                //上传不适用结果
                uploadUnUseTitle(recordId,labId,titleId);
                if(titleId==endTitleId)
                {
                    //是最后一项
                    showToast("该类已经检查完毕");
                }else
                {
                    //不是最后一项，进入下一项
                    titleId++;
                    updataItem();
                }
                break;
            default:
                showToast("请勾选检查情况");
                break;
        }
    }

    /**
     * 上传不适用项
     * @param recordId
     * @param labId
     * @param titleId
     */
    private void uploadUnUseTitle(final int recordId, int labId, int titleId)
    {
        mTask.uploadUnUseTitle(recordId,labId,titleId)
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if(result!=null)
                        {
                            if(result.getStatus()==200)
                            {
                                showToast("提交检查记录成功");
                            }
                        }
                    }
                });
    }

    /**
     * 更新检查项
     */
    private void updataItem()
    {
        checkItem= DataSupport.where("titleId = ?",""+titleId).find(CheckItem.class);
        toolbarTextView.setText(checkItem.get(0).getTitleSerialNumber());
        //设置UI
        checkItemTxtView.setText(checkItem.get(0).getCheckTitle());
        checkPointTxtView.setText(checkItem.get(0).getCheckImportant());
    }

    private void showToast(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
