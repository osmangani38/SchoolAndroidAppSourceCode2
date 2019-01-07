package com.sap.school;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sap.school.PojoClass.NoticePojoClass;

import java.util.ArrayList;

public class AppleLeaveScreenActivity extends AppCompatActivity implements View.OnClickListener{
        RelativeLayout backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appleleave_screen);
        initView();
        setListner();
        noticeInfo();
    }

    private void noticeInfo() {
    }

    private void setListner() {
        backButton.setOnClickListener(this);
    }

    private void initView() {
        backButton=(RelativeLayout)findViewById(R.id.backButton);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backButton:
                finish();
                break;
        }

    }
}
