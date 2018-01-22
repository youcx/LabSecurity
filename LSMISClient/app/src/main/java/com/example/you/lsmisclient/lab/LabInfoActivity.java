package com.example.you.lsmisclient.lab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.you.lsmisclient.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LabInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_info);
        OkHttpClient client=new OkHttpClient();
        String json1="{\n" +
                "\t\"password\":123456,\n" +
                "\t\"phoneNum\":18683667326\n" +
                "}";
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json1);
        Request request=new Request.Builder()
                .post(body)
                .url("http://222.196.33.254:8080/rm_httpserver/login")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG","err");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                {
                    Log.i("TAG",response.body().string());
                }else{
                    Log.i("TAG","response err");
                }
            }
        });
    }
}
