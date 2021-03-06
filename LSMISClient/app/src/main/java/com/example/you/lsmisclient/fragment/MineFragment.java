package com.example.you.lsmisclient.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.http.HttpTask;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener{

    //控件绑定
    @BindView(R.id.quit)
    CardView quit;
    @BindView(R.id.about)
    TextView about;
    @BindView(R.id.settings)
    TextView setttings;


    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_mine, container, false);
        //绑定控件
        ButterKnife.bind(this,view);
        //TextView点击监听
        quit.setOnClickListener(this);
        about.setOnClickListener(this);
        setttings.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.quit:
                changePasswdDialog();
                break;
            case R.id.about:
                showToast("关于");
                File file=new File("/storage/emulated/0/1.jpg");
                RequestBody imageBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Builder builder=new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);  //表单类型
                builder
                        .addFormDataPart("titleId",""+15)
                        .addFormDataPart("recordId",""+53)
                        .addFormDataPart("questionDesc","youwenti")
                        .addFormDataPart("changeAdvice","wenti")
                        .addFormDataPart("adviceTargetOrgLevel",""+1)
                        .addFormDataPart("adviceChangeTimeStr","2018-03-10 12:00:00")
                        .addFormDataPart("pic",file.getName(),imageBody);
                List<MultipartBody.Part> parts=builder.build().parts();
////        mTask.uploadNewCheckResult(titleId,recordId,checkRecordEdt.getText().toString(),suggestionEdt.getText().toString(),
////                1,mtime,null,null)
//                HttpTask mTask=new HttpTask();
//                mTask.uploadNewCheckResult(parts)
//                        .subscribe(new Subscriber<Result>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//
//                            @Override
//                            public void onNext(Result result) {
//                                if(result!=null)
//                                {
//
//                                    if(result.getStatus()==200)
//                                    {
//                                        showToast("提交检查记录成功");
//                                    }else{
//                                        showToast(result.getMessage());
//                                    }
//                                }
//                            }
//                        });
                break;
            case R.id.settings:
                showToast("设置");
                break;
        }
    }

    public void showToast(String str)
    {
        Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT).show();
    }

    /**
     * 密码更新对话框
     */
    protected void changePasswdDialog() {

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View textEntryView = factory.inflate(R.layout.change_passwd, null);
        final EditText oldpwdet = (EditText) textEntryView.findViewById(R.id.oldpwd_edit);
        final EditText newpwdet = (EditText)textEntryView.findViewById(R.id.newpwd_edit);
        final EditText newpwdconfmet=(EditText) textEntryView.findViewById(R.id.newpwd_confirm);
        AlertDialog.Builder ad1 = new AlertDialog.Builder(getActivity());
        ad1.setTitle("密码修改:");
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
}
