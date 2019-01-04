package com.sap.school;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button loginButton,closeButton;
    String valueType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListner();

    }

    private void setListner() {
        loginButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);
    }

    private void initView() {
        loginButton=(Button)findViewById(R.id.loginButton);
        closeButton=(Button)findViewById(R.id.closeButton);
        valueType=getIntent().getStringExtra("valueType");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.loginButton:
            if(valueType.equals("teacher"))
            {
                startActivity(new Intent(getApplicationContext(),TeacherDashBoardActivity.class));
            }else  if(valueType.equals("student"))
            {
                startActivity(new Intent(getApplicationContext(),StudentDashboardActivity.class));
            }else if(valueType.equals("parent"))
            {
                startActivity(new Intent(getApplicationContext(),ParentDashBoardActivity.class));
            }

            break;
            case R.id.closeButton:
                finish();
                break;
        }

    }
}
