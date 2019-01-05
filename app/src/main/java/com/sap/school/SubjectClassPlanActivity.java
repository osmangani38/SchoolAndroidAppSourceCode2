package com.sap.school;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SubjectClassPlanActivity extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout backButton,submitClassButton;
    LinearLayout diverasityLinearLayout,diverasityChildLinearLayout;
    LinearLayout levelOfOrganisationLinearLayout,levelOfOrganisationChildLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_class_plan);
        initView();
        setLisnter();
    }

    private void setLisnter() {
        backButton.setOnClickListener(this);
        diverasityLinearLayout.setOnClickListener(this);
        levelOfOrganisationLinearLayout.setOnClickListener(this);
        submitClassButton.setOnClickListener(this);
    }

    private void initView() {
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        diverasityLinearLayout=(LinearLayout)findViewById(R.id.diverasityLinearLayout);
        diverasityChildLinearLayout=(LinearLayout)findViewById(R.id.diverasityChildLinearLayout);
        levelOfOrganisationLinearLayout=(LinearLayout)findViewById(R.id.levelOfOrganisationLinearLayout);
        levelOfOrganisationChildLinearLayout=(LinearLayout)findViewById(R.id.levelOfOrganisationChildLinearLayout);
        submitClassButton=(RelativeLayout)findViewById(R.id.submitClassButton);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backButton:
                finish();
                break;
            case R.id.diverasityLinearLayout:
                if (diverasityChildLinearLayout.getVisibility() == View.VISIBLE) {
                    diverasityChildLinearLayout.setVisibility(View.GONE);

                } else {
                    diverasityChildLinearLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.levelOfOrganisationLinearLayout:
                if (levelOfOrganisationChildLinearLayout.getVisibility() == View.VISIBLE) {
                    levelOfOrganisationChildLinearLayout.setVisibility(View.GONE);

                } else {
                    levelOfOrganisationChildLinearLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.submitClassButton:
                SubmitInfo();
                break;
        }

    }

    private void SubmitInfo() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SubjectClassPlanActivity.this);
        LayoutInflater inflater = LayoutInflater.from(SubjectClassPlanActivity.this);
        final View convertView = (View) inflater.inflate(R.layout.custom_submit_class_plan_page_popup_item, null);
        Button goToDashBoardButton,makeOtherPlanButton;
        alertDialog.setView(convertView);
        final AlertDialog ad = alertDialog.show();
        goToDashBoardButton=(Button)convertView.findViewById(R.id.goToDashBoardButton);
        makeOtherPlanButton=(Button)convertView.findViewById(R.id.makeOtherPlanButton);
        goToDashBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
              startActivity(new Intent(getApplication(),TeacherDashBoardActivity.class));
              finish();

            }
        });

        makeOtherPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),ClassPlanActivity.class));
            }
        });
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ad.dismiss();
//            }
//        });
    }
}
