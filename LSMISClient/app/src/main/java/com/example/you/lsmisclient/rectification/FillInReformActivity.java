package com.example.you.lsmisclient.rectification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.fragment.photopicker.PhotoPickerFragment;
import com.example.you.lsmisclient.http.HttpTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

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
    //data
    int changeId;

    PhotoPickerFragment photoPickerFragment;

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

        Bundle bundle = getIntent().getExtras();
        changeId = bundle.getInt("changeId",0);

        photoPickerFragment = (PhotoPickerFragment) getSupportFragmentManager().findFragmentById(R.id.photoFragment);

        //btn
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交整改结果
                startUploadChangeRecord();
            }
        });
    }

    private void startUploadChangeRecord(){
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("changeId",""+changeId)
                .addFormDataPart("changeContent"," "+reformMeasureEdt.getText().toString());
        ArrayList<String> picPath = photoPickerFragment.getPickedPicsPath();
        for(String path:picPath){
            File file = new File(path);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
            builder.addFormDataPart("pic",file.getName(),requestBody);
        }
        List<MultipartBody.Part> parts = builder.build().parts();

        mTask.uploadChangeRecord(parts)
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
                            if(result.getStatus()==200){
                                showToast(result.getMessage());
                            }else{
                                showToast(result.getMessage());
                            }
                        }
                    }
                });
    }

    private void showToast(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
