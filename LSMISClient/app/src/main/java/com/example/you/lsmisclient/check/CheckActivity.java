package com.example.you.lsmisclient.check;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.check.bean.CheckItem;
import com.example.you.lsmisclient.fragment.photopicker.PhotoPickerFragment;
import com.example.you.lsmisclient.http.HttpTask;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class CheckActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.check_toolbar)
    Toolbar checkToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    @BindView(R.id.check_radio_group)
    RadioGroup checkRadioGroup;
    //btn
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.DateBtn)
    Button dateBtn;

    @BindView(R.id.check_item_txtv)
    TextView checkItemTxtView;
    @BindView(R.id.check_point_txtv)
    TextView checkPointTxtView;

    @BindView(R.id.check_record_edt)
    EditText checkRecordEdt;
    @BindView(R.id.check_suggestion_edt)
    EditText suggestionEdt;

    //整改主体下拉框
    @BindView(R.id.reform_target)
    Spinner reformTargetSpinner;

    //检查项序号
    String serialNum;
    List<CheckItem> checkItem;
    //检查项id
    private int titleId;
    private String checkTitle;
    private String checkPoint;
//    int startTitleId;
//    int endTitleId;
    //任务记录id
    int recordId;
    int labId;
    //该类总检查项
    int checkCount;
    //提交日期
    int year,month,day;
    //符合结果
    int checkResult;                        //符合结果
    final int CONFORM=200;                  //符合
    final int INCONFORMITY=300;             //不符合
    final int INAPPLICABLE=400;             //不适用

    //整改主体Adapter
    private int ReformBody;
    private ArrayAdapter<String> reformTargetAdapter=null;


    //http
    HttpTask mTask;
    PhotoPickerFragment photoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        /**获取检查项数据*/
        Bundle bundle=getIntent().getExtras();
        titleId=bundle.getInt("titleId",0);
        checkTitle=bundle.getString("checkTitle");
        checkPoint = bundle.getString("checkPoint");
        serialNum = bundle.getString("serialNum");
//        checkItem= DataSupport.where("titleId = ?",""+titleId).find(CheckItem.class);
        //bind
        ButterKnife.bind(this);
        mTask=new HttpTask();
       photoFragment= (PhotoPickerFragment) getSupportFragmentManager().findFragmentById(R.id.photo_fragment);
        //init
        SharedPreferences sp=getSharedPreferences("checkdata",MODE_PRIVATE);
//        startTitleId=sp.getInt("startTitleId",0);
//        endTitleId=sp.getInt("endTitleId",0);
        recordId=sp.getInt("recordId",0);
        labId=sp.getInt("labId",0);
//        checkCount=sp.getInt("checkCount",0);
//        Log.i("endTitleID!!!!!",""+endTitleId);
        //ToolBar
        toolbarTextView.setText(serialNum);
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
        submit.setOnClickListener(this);
        dateBtn.setOnClickListener(this);
        //设置UI
        checkItemTxtView.setText(checkTitle);
        checkPointTxtView.setText(checkPoint);
        //初始化整改主体
        initTarget();
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

        //整改时间
        final Calendar ca = Calendar.getInstance();
        year = ca.get(Calendar.YEAR);
        month = ca.get(Calendar.MONTH);
        day = ca.get(Calendar.DAY_OF_MONTH);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.submit:

                final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(this);
                dialogBuilder
                        .withTitle("提示")
                        .withMessage("确定提交结果吗？")
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
                                //填写记录
                                if(checkRecordEdt.getText().toString().equals(""))
                                {
                                    checkRecordEdt.setError("请填写检查记录");
                                }else if(suggestionEdt.getText().toString().equals(""))
                                {
                                    suggestionEdt.setError("请填写整改意见");
                                }else if(dateBtn.getText().toString().equals(""))
                                {
                                    dateBtn.performClick();
                                    //dateBtn.setError("请选择整改时间");
                                }else
                                {
                                    //上传不符合结果
                                    uploadCheckResult();
                                }
//                                checkNextItem();

                            }
                        }).show();
                break;
            case R.id.DateBtn:
                ArrayList<String> picPath=new ArrayList<>();
                picPath=photoFragment.getPickedPicsPath();
                for(String path:picPath)
                {
                    Log.i("PICPATH",path);
                }
                new DatePickerDialog(this,mdateListener,year,month,day).show();
                break;
        }
    }




    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        dateBtn.setText(new StringBuffer().append(year).append("-").append(month + 1).append("-").append(day).append(" ")
        .append("12:00:00"));
    }

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener mdateListener=new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int yyear, int mmonth, int dayOfMonth) {
            year=yyear;
            month=mmonth;
            day=dayOfMonth;
            display();
        }
    };

    /**
     * 初始化整改主体
     */
    private void initTarget()
    {
        String[] reformTargetList={"学校","学院","实验室","负责人"};
        reformTargetAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,reformTargetList);
        reformTargetSpinner.setAdapter(reformTargetAdapter);
    }

//    /**
//     * 进入上一项
//     */
//    private void gotoLastItem()
//    {
//        if(titleId==startTitleId)
//        {
//            //是开始项
//            showToast("已经是最初项");
//        }else
//        {
//            //进入上一项
//            titleId--;
//            updataItem();
//        }
//    }
//
//    /**
//     * 进入下一项
//     */
//    private void gotoNextItem()
//    {
//        if(titleId==endTitleId)
//        {
//            //是最后一项
//            showToast("已经是最后一项");
//        }else
//        {
//            //不是最后一项，进入下一项
//            titleId++;
//            updataItem();
//        }
//    }
    /**
     * 检查下一项
     */
//    private void checkNextItem()
//    {
//        switch (checkResult)
//        {
//            case CONFORM:
//                //符合
//                //提交结果,先提交结果，再根据id是否进入下一项
//                showToast("提交检查记录成功");
//                if(titleId==endTitleId)
//                {
//                    //是最后一项
//                    showToast("该类已经检查完毕");
//                }else
//                {
//                    //不是最后一项，进入下一项
//                    titleId++;
//                    updataItem();
//                }
//                break;
//            case INCONFORMITY:
//                //填写记录
//               // CheckRecordDialog();
//                if(checkRecordEdt.getText().toString().equals(""))
//                {
//                    checkRecordEdt.setError("请填写检查记录");
//                }else if(suggestionEdt.getText().toString().equals(""))
//                {
//                    suggestionEdt.setError("请填写整改意见");
//                }else if(dateBtn.getText().toString().equals(""))
//                {
//                    dateBtn.performClick();
//                    //dateBtn.setError("请选择整改时间");
//                }else
//                {
//                    //上传不符合结果
//                    uploadCheckResult();
//                }
//
//                break;
//            case INAPPLICABLE:
//                //上传不适用结果
//                uploadUnUseTitle(recordId,labId,titleId);
//                if(titleId==endTitleId)
//                {
//                    //是最后一项
//                    showToast("该类已经检查完毕");
//                }else
//                {
//                    //不是最后一项，进入下一项
//                    titleId++;
//                    updataItem();
//                }
//                break;
//            default:
//                showToast("请勾选检查情况");
//                break;
//        }
//    }

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
     * 上传不符合项
     */
    private void uploadCheckResult()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(dateBtn.getText().toString(), pos);
        String mtime=formatter.format(strtodate);
        Log.i("RecordId",""+recordId);
        MultipartBody.Builder builder=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)  //表单类型
                .addFormDataPart("titleId",""+titleId)
                .addFormDataPart("recordId",""+recordId)
                .addFormDataPart("questionDesc",checkRecordEdt.getText().toString())
                .addFormDataPart("changeAdvice",suggestionEdt.getText().toString())
                .addFormDataPart("adviceTargetOrgLevel",""+1)
                .addFormDataPart("adviceChangeTimeStr",mtime);
        ArrayList<String> picPath=photoFragment.getPickedPicsPath();
        for(String path:picPath)
        {
            File file=new File(path);
            RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
            builder.addFormDataPart("pic",file.getName(),requestBody);
        }
        List<MultipartBody.Part> parts=builder.build().parts();
//        mTask.uploadNewCheckResult(titleId,recordId,checkRecordEdt.getText().toString(),suggestionEdt.getText().toString(),
//                1,mtime,null,null)
        mTask.uploadNewCheckResult(parts)
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
                                showToast("提交检查记录成功");
//                                if(titleId==endTitleId)
//                                {
//                                    //是最后一项
//                                    showToast("该类已经检查完毕");
//                                }else
//                                {
//                                    //不是最后一项，进入下一项
//                                    titleId++;
//                                    updataItem();
//                                }
                            }else{
                                showToast(result.getMessage());
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
        checkRecordEdt.setText("");
        suggestionEdt.setText("");
    }

    protected void CheckRecordDialog() {

        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.change_passwd, null);
        final EditText oldpwdet = (EditText) textEntryView.findViewById(R.id.oldpwd_edit);
        final EditText newpwdet = (EditText)textEntryView.findViewById(R.id.newpwd_edit);
        final EditText newpwdconfmet=(EditText) textEntryView.findViewById(R.id.newpwd_confirm);
        AlertDialog.Builder ad1 = new AlertDialog.Builder(this);
        ad1.setTitle("提交整改意见");
        ad1.setIcon(android.R.drawable.ic_dialog_info);
        ad1.setView(textEntryView);
        ad1.setPositiveButton("是", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        ad1.setNegativeButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        //ad1.show();// 显示对话框
        final AlertDialog dialog=ad1.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        //拦截按键
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean canChange=true;
                String oldpwd=oldpwdet.getText().toString();
                String newpwd=newpwdet.getText().toString();
                String newpwd_confirm=newpwdconfmet.getText().toString();
                if(TextUtils.isEmpty(oldpwd))
                {
                    oldpwdet.setError("原始密码不能为空");
                    canChange=false;
                    oldpwdet.requestFocus();
                }else if(TextUtils.isEmpty(newpwd))
                {
                    newpwdet.setError("新密码不能为空");
                    canChange=false;
                    newpwdet.requestFocus();
                }else if(newpwd.length()<6)
                {
                    newpwdet.setError("密码太短");
                    canChange=false;
                    newpwdet.requestFocus();
                }else if(!newpwd.equals(newpwd_confirm))
                {
                    newpwdconfmet.setError("密码不一致");
                    canChange=false;
                    newpwdconfmet.requestFocus();
                }
                if(canChange)
                {
//                    String phonenum=(String)SharePreferenceUtil.getParam(getActivity(),Constants.PHONE,"");//获取电话号码
//                    String sessionid=(String)SharePreferenceUtil.getParam(getActivity(),Constants.SESSIONID,"");
//                    updatePwd(phonenum,sessionid,oldpwd,newpwd);
                    dialog.dismiss();
                }
            }
        });

    }
    private void showToast(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
