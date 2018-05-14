package com.example.you.lsmisclient.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.you.lsmisclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PicShowActivity extends AppCompatActivity {
    @BindView(R.id.show_pic)
    ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_show);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        Glide.with(this).load(url).placeholder(R.mipmap.background4).into(pic);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
