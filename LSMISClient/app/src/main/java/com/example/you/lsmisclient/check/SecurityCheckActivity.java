package com.example.you.lsmisclient.check;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.rectification.RectificationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecurityCheckActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.security_check_toolbar)
    Toolbar securityCheckToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_check);
        //bind
        ButterKnife.bind(this);
        toolbarTextView.setText("LS-MIS");
        setSupportActionBar(securityCheckToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        securityCheckToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.security_check_start:
                Intent missionSelect=new Intent(this,SelectCheckMissionActivity.class);
                startActivity(missionSelect);
                break;
            case R.id.security_check_review:
                //Toast.makeText(this,"还没有写",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,RectificationActivity
                        .class);
                startActivity(intent);
                break;
        }
    }
}
