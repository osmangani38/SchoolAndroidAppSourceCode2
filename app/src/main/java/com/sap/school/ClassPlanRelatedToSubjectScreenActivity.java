package com.sap.school;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sap.school.PojoClass.ClassPlanSubjectPojoClass;

import java.util.ArrayList;

public class ClassPlanRelatedToSubjectScreenActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView classPlanRecyclerview;
    RelativeLayout backButton;
    TextView headerText;
    String type;
    ClassPlassSubjectRecyclerViewAdapter classPlassSubjectRecyclerViewAdapter;
    ArrayList<ClassPlanSubjectPojoClass>classPlanSubjectPojoClassArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_plan_related_to_subject_screen);
        initView();
        setListenr();
        SubjectInfo();
    }

    private void SubjectInfo() {
        classPlanSubjectPojoClassArrayList.add(new ClassPlanSubjectPojoClass("01","Bengali"));
        classPlanSubjectPojoClassArrayList.add(new ClassPlanSubjectPojoClass("01","English"));
        classPlanSubjectPojoClassArrayList.add(new ClassPlanSubjectPojoClass("01","Geogrphy"));
        classPlanSubjectPojoClassArrayList.add(new ClassPlanSubjectPojoClass("01","Life Science"));
        classPlanSubjectPojoClassArrayList.add(new ClassPlanSubjectPojoClass("01","Physices"));
        classPlassSubjectRecyclerViewAdapter = new ClassPlassSubjectRecyclerViewAdapter(getApplication(), classPlanSubjectPojoClassArrayList);
        classPlanRecyclerview.setLayoutManager(new GridLayoutManager(getApplication(), 1));
        classPlanRecyclerview.setItemAnimator(new DefaultItemAnimator());
        classPlanRecyclerview.setAdapter(classPlassSubjectRecyclerViewAdapter);


    }

    private void setListenr() {
        backButton.setOnClickListener(this);
    }

    private void initView() {
        headerText = (TextView) findViewById(R.id.headerText);
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        classPlanRecyclerview=(RecyclerView)findViewById(R.id.classPlanRecyclerview);
        classPlanSubjectPojoClassArrayList=new ArrayList<ClassPlanSubjectPojoClass>();
        Intent intent = getIntent();
        String str = intent.getStringExtra("isHomeTask");
        type = intent.getStringExtra("type");

        if(isNullOrEmpty(str)){

        }
        else {
            if (str.equals("Yes")) {
                headerText.setText("Home Task");
            }
        }

    }
    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
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

    private class ClassPlassSubjectRecyclerViewAdapter extends RecyclerView.Adapter<ClassPlassSubjectRecyclerViewAdapter.MyViewHolderClass>{
        Context context;ArrayList<ClassPlanSubjectPojoClass> classPlanSubjectPojoClassArrayList;
        public ClassPlassSubjectRecyclerViewAdapter(Context context, ArrayList<ClassPlanSubjectPojoClass> classPlanSubjectPojoClassArrayList) {
            this.context=context;this.classPlanSubjectPojoClassArrayList=classPlanSubjectPojoClassArrayList;
        }

        @NonNull
        @Override
        public MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_plan_related_to_subject_class_page_item, parent, false);
            return new MyViewHolderClass(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderClass myViewHolderClass, final int i) {
            myViewHolderClass.titleTextView.setText(classPlanSubjectPojoClassArrayList.get(i).getTitle());
            myViewHolderClass.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if(i==3)
                    {
                        Intent intent = getIntent();
                        String str = intent.getStringExtra("isHomeTask");
                        Boolean goToClassPlan = true;
                        if(isNullOrEmpty(str)){

                        }
                        else {
                            if (str.equals("Yes")) {
                                goToClassPlan = false;
                                headerText.setText("Home Task");
                            }
                        }
                        if (goToClassPlan) {
                            Intent goNext = new Intent(getApplication(), SubjectPlanDetailsActivity.class);
                            goNext.putExtra("type",type);

                            startActivity(goNext);
                        }
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return classPlanSubjectPojoClassArrayList.size();
        }

        public class MyViewHolderClass extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView titleTextView;
            LinearLayout linearLayout;
            public MyViewHolderClass(@NonNull View itemView) {
                super(itemView);
                titleTextView=(TextView)itemView.findViewById(R.id.titleTextView);
                linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);
            }
        }
    }
}
