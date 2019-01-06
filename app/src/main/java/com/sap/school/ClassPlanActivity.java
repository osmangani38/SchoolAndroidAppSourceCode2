package com.sap.school;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sap.school.PojoClass.CreatePlanPojoClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ClassPlanActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView createPlanRecyclerView;
    RelativeLayout backButton,makePlanButton;
    CreatePlanRecyclerViewAdapter createPlanRecyclerViewAdapter;
    ArrayList<CreatePlanPojoClass>createPlanPojoClassArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
        initView();
        setListner();
        CreatePlan();
    }
    private void setListner() {
        backButton.setOnClickListener(this);
        makePlanButton.setOnClickListener(this);
    }
    private void CreatePlan() {
        createPlanPojoClassArrayList.add(new CreatePlanPojoClass("01","Select Plan Date",R.drawable.calender_ic));
        createPlanPojoClassArrayList.add(new CreatePlanPojoClass("01","Select Class",R.drawable.teacher_ic_make_plan));
        createPlanPojoClassArrayList.add(new CreatePlanPojoClass("01","Select Class Section",R.drawable.teacher_ic_make_plan));
        createPlanPojoClassArrayList.add(new CreatePlanPojoClass("01","Select Subject",R.drawable.subject_ic));
        createPlanRecyclerViewAdapter = new CreatePlanRecyclerViewAdapter(getApplication(), createPlanPojoClassArrayList);
        createPlanRecyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 1));
        createPlanRecyclerView.setItemAnimator(new DefaultItemAnimator());
        createPlanRecyclerView.setAdapter(createPlanRecyclerViewAdapter);
    }
    private void initView() {
        createPlanRecyclerView=(RecyclerView)findViewById(R.id.createPlanRecyclerView);
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        makePlanButton=(RelativeLayout)findViewById(R.id.makePlanButton);
        createPlanPojoClassArrayList=new ArrayList<CreatePlanPojoClass>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backButton:
                finish();
                break;
            case R.id.makePlanButton:
                Intent goNext = new Intent(getApplication(), SubjectClassPlanActivity.class);
                goNext.putExtra("type","Teacher");
                startActivity(goNext);
                //startActivity(new Intent(getApplicationContext(),SubjectClassPlanActivity.class));
                break;
        }

    }

    private class CreatePlanRecyclerViewAdapter extends RecyclerView.Adapter<CreatePlanRecyclerViewAdapter.MyViewHolderClass>{
        Context context;ArrayList<CreatePlanPojoClass> createPlanPojoClassArrayList;
        public CreatePlanRecyclerViewAdapter(Context context, ArrayList<CreatePlanPojoClass> createPlanPojoClassArrayList) {
         this.context=context;this.createPlanPojoClassArrayList=createPlanPojoClassArrayList;
        }

        @NonNull
        @Override
        public CreatePlanRecyclerViewAdapter.MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_create_class, parent, false);
            return new CreatePlanRecyclerViewAdapter.MyViewHolderClass(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CreatePlanRecyclerViewAdapter.MyViewHolderClass myViewHolderClass, int i) {
            myViewHolderClass.titleTextView.setText(createPlanPojoClassArrayList.get(i).getTitle());
            Picasso.with(getApplication()).load(createPlanPojoClassArrayList.get(i).getImage()).into(myViewHolderClass.imageView);
        }
        @Override
        public int getItemCount() {
            return createPlanPojoClassArrayList.size();
        }

        public class MyViewHolderClass extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView titleTextView;
            public MyViewHolderClass(@NonNull View itemView) {
                super(itemView);
                imageView=(ImageView)itemView.findViewById(R.id.imageView);
                titleTextView=(TextView)itemView.findViewById(R.id.titleTextView);
            }
        }
    }
}
