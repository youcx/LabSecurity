package com.example.you.lsmisclient.lab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.check.SecurityCheckActivity;
import com.example.you.lsmisclient.list.CollegeListActivity;
import com.example.you.lsmisclient.list.HazardListActivity;
import com.example.you.lsmisclient.list.LevelListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LabActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.browse_as_college)
    View browseAsCollege;
    @BindView(R.id.browse_as_level)
    View browseAsLevel;
    @BindView(R.id.browse_as_hazard)
    View browseAsHazard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);
        //绑定
        ButterKnife.bind(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.browse_as_college:
                Intent intent1=new Intent(this,CollegeListActivity.class);
                startActivity(intent1);
               // Toast.makeText(this,"学院分览",Toast.LENGTH_SHORT).show();
                break;
            case R.id.browse_as_level:
                Intent intent2=new Intent(this,LevelListActivity.class);
                startActivity(intent2);
               // Toast.makeText(this,"级别分览",Toast.LENGTH_SHORT).show();
                break;
            case R.id.browse_as_hazard:
                Intent intent3=new Intent(this,HazardListActivity.class);
                startActivity(intent3);
                //Toast.makeText(this,"危险源分览",Toast.LENGTH_SHORT).show();
                break;

        }
    }


}
