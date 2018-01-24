package com.example.you.lsmisclient.contacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.you.lsmisclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.browse_as_college_contact)
    View browseAsCollege;
    @BindView(R.id.browse_as_level_contact)
    View browseAsLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        //bind
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.browse_as_college_contact:
                break;
            case R.id.browse_as_level_contact:
                break;
        }
    }
}
