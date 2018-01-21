package com.example.you.lsmisclient.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener{

    //控件绑定
    @BindView(R.id.passwd_change)
    TextView passwd_change;
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
        passwd_change.setOnClickListener(this);
        about.setOnClickListener(this);
        setttings.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.passwd_change:
                changePasswdDialog();
                break;
            case R.id.about:
                showToast("关于");
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
