package com.sap.school;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MarkAttendenceActivity extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout attendenceButton,backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendence);
        initView();
    }

    private void initView() {
        attendenceButton=(RelativeLayout)findViewById(R.id.attendenceButton);
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        attendenceButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.attendenceButton:
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MarkAttendenceActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MarkAttendenceActivity.this);
                final View convertView = (View) inflater.inflate(R.layout.custom_popup_pie_chart_item, null);
                Button crossButton;;
                alertDialog.setView(convertView);
                final AlertDialog ad = alertDialog.show();
                crossButton=(Button)convertView.findViewById(R.id.crossButton);
                crossButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ad.dismiss();
                    }
                });
                break;
            case R.id.backButton:
                finish();
                break;
        }

    }
}
