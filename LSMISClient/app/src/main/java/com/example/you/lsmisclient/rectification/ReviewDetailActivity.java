package com.example.you.lsmisclient.rectification;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.rectification.fragment.ReviewDetailFragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;

    private int changeId;
    private String labName;
    private int reformFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        ButterKnife.bind(this);

        getDatasFromLastAc();

        initToolbar();

        //Activity向Fragment传值
        Bundle bundle=new Bundle();
        bundle.putInt("changeId",changeId);
        ReviewDetailFragment reviewDetailFragment = new ReviewDetailFragment();
        reviewDetailFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,reviewDetailFragment);
        fragmentTransaction.commit();



    }

    private void initToolbar(){
        toolbarTextView.setText("整改:"+labName);
        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDatasFromLastAc(){
        Bundle bundle = getIntent().getExtras();
        try{
            changeId = bundle.getInt("changeId");
            labName = bundle.getString("labName");
            reformFlag = bundle.getInt("reformFlag");
            Log.i("changeId",""+changeId+",labName:"+labName+",reformFlag:"+reformFlag);
        }catch (Exception e){
            e.printStackTrace();
            finish();
        }


    }
}
