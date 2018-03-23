package com.example.you.lsmisclient.rectification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.http.HttpTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FillInReformActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
            Toolbar mToolbar;
    @BindView(R.id.toolbar_textview)
            TextView toolbarTextView;
    @BindView(R.id.check_item_txtv)
            TextView checkItemTxtv;
    @BindView(R.id.check_point_txtv)
            TextView checkPointTxtv;
    @BindView(R.id.reform_measure_edt)
            EditText reformMeasureEdt;
    @BindView(R.id.reform_result_edt)
            EditText reformResultEdt;
    //btn
    @BindView(R.id.submit)
            Button submit;

    //http
    HttpTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_reform);
        //bind
        ButterKnife.bind(this);
        mTask=new HttpTask();
        //ToolBar
        toolbarTextView.setText("整改");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //btn
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交整改结果
            }
        });
    }
}
