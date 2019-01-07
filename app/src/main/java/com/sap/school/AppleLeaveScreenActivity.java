package com.sap.school;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sap.school.PojoClass.NoticePojoClass;

import java.util.ArrayList;

public class AppleLeaveScreenActivity extends AppCompatActivity implements View.OnClickListener{
        RelativeLayout backButton;
        Button btnApplyLeave;
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
        btnApplyLeave.setOnClickListener(this);
    }

    private void initView() {
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        btnApplyLeave = (Button) findViewById(R.id.btnApplyLeave);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backButton:
                finish();
                break;

            case R.id.btnApplyLeave:
                showDialog();
                break;
        }

    }


    private void showDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setMessage("Your leave application has \nbeen submitted.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTextSize(20);


    }
}
