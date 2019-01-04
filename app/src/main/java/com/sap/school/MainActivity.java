package com.sap.school;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout teacherButton,studentButton,parentButton,hmAccessButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListner();
    }

    private void setListner() {
        hmAccessButton.setOnClickListener(this);
        teacherButton.setOnClickListener(this);
        studentButton.setOnClickListener(this);
        parentButton.setOnClickListener(this);
    }

    private void initView() {
        hmAccessButton=(LinearLayout)findViewById(R.id.hmAccessButton);
        teacherButton=(LinearLayout)findViewById(R.id.teacherButton);
        studentButton=(LinearLayout)findViewById(R.id.studentButton);
        parentButton=(LinearLayout)findViewById(R.id.parentButton);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.hmAccessButton:
                //startActivity(new Intent(getApplication(),LoginActivity.class));
                break;
            case R.id.teacherButton:
                Intent goNext=new Intent(getApplication(),LoginActivity.class);
                goNext.putExtra("valueType","teacher");
                startActivity(goNext);
                //startActivity(new Intent(getApplication(),LoginActivity.class));
                break;
            case R.id.studentButton:
                goNext=new Intent(getApplication(),LoginActivity.class);
                goNext.putExtra("valueType","student");
                startActivity(goNext);
                //startActivity(new Intent(getApplication(),LoginActivity.class));
                break;
            case R.id.parentButton:
                goNext=new Intent(getApplication(),LoginActivity.class);
                goNext.putExtra("valueType","parent");
                startActivity(goNext);
                //startActivity(new Intent(getApplication(),LoginActivity.class));
                break;
        }

    }
}
