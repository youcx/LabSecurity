package com.example.you.lsmisclient.contacts;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.UserInfoBean;
import org.w3c.dom.Text;


public class UserInfoView extends LinearLayout {

    AvatarView avatarView;
    TextView userNameTextView;
    TextView phoneTextView;
    TextView collegeTextView;
    TextView workPlaceView;
    TextView emailTextView;
    ImageView imgView ;
    private UserInfoBean mUserInfo = null;
    private final static String TAG = "UserInfoView";
    public UserInfoView (Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.user_constraint_view,this);
        initChildren();
    }



    public void initChildren(){
        avatarView = (AvatarView)findViewById(R.id.avatarView);
        userNameTextView = (TextView) findViewById(R.id.nameText);
        phoneTextView = (TextView)findViewById(R.id.phoneText);
        collegeTextView = (TextView) findViewById(R.id.collegeText);
        workPlaceView = (TextView) findViewById(R.id.workText);
        emailTextView = (TextView)findViewById(R.id.emailText);
        emailTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_email, 0, 0, 0);
        imgView = (ImageView) findViewById(R.id.sexView);

    }

    //刷新界面信息
    public void updateInfo(){
        if(mUserInfo != null){
            userNameTextView.setText(mUserInfo.getAccountName());
            phoneTextView.setText(mUserInfo.getPhoneNum());
            collegeTextView.setText(mUserInfo.getCollege());
            workPlaceView.setText(mUserInfo.getWorkPlace());
            emailTextView.setText(mUserInfo.getEmail());
            switch (mUserInfo.getSex()) {
                case 1:
                    imgView.setImageResource(R.drawable.ico_man);
                    break;
                case 2:
                    imgView.setImageResource(R.drawable.ico_women);
                    break;
            }
            avatarView.updateAvatarView(mUserInfo);

        }else {
            Log.w(TAG, "updateInfo: mUserInfo is null !" );
        }
    }

    public UserInfoBean getmUserInfo() {
        return mUserInfo;
    }

    public void setmUserInfo(UserInfoBean mUserInfo) {
        this.mUserInfo = mUserInfo;
        updateInfo();
    }
}
