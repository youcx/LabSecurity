package com.example.you.lsmisclient;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.you.lsmisclient.qr.QrScannerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTestActivity extends AppCompatActivity {
    @BindView(R.id.btn)
    Button btn;
    private String TAG = "MAIN@";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_test);
        ButterKnife.bind(this);
        Log.i(TAG,"create");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),MyTestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"st");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"re");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"pau");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"stop");
    }
}
