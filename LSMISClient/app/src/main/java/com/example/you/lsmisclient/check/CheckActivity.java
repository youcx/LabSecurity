package com.example.you.lsmisclient.check;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.check_toolbar)
    Toolbar checkToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    @BindView(R.id.check_radio_group)
    RadioGroup checkRadioGroup;
    //btn
    @BindView(R.id.previous_item_btn)
    Button previoutItemBtn;
    @BindView(R.id.next_item_btn)
    Button nextItemBtn;

    @BindView(R.id.check_item_txtv)
    TextView checkItemTxtView;
    @BindView(R.id.check_point_txtv)
    TextView checkPointTxtView;
    @BindView(R.id.check_record_edt)
    EditText checkRecordEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        //bind
        ButterKnife.bind(this);
        toolbarTextView.setText("检查项目");
        setSupportActionBar(checkToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        checkToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //btn
        previoutItemBtn.setOnClickListener(this);
        nextItemBtn.setOnClickListener(this);
        //单选框
        checkRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.check_radioBtn1:
                        showToast("符合");
                        break;
                    case R.id.check_radioBtn2:
                        showToast("不符合");
                        break;
                    case R.id.check_radioBtn3:
                        showToast("不适用");
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.next_item_btn:
                final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(this);
                dialogBuilder
                        .withTitle("提示")
                        .withMessage("确定提交结果并开始下一项检查任务吗？")
                        .withDialogColor("#99cccc")
                        .withDuration(200)
                        .withButton1Text("确定")
                        .withButton2Text("取消")
                        .isCancelableOnTouchOutside(true)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogBuilder.dismiss();
                                Intent nextintent=new Intent(getBaseContext(),CheckActivity.class);
                                startActivity(nextintent);
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogBuilder.dismiss();
                            }
                        }).show();

                break;
            case R.id.previous_item_btn:
                finish();
                break;
        }
    }

    private void showToast(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
