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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sap.school.PojoClass.SelectPojoClass;

import java.util.ArrayList;

public class SelectClassActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView selectInfoRecyclerView;
    SelectInfoReyclerViewAdapter selectInfoReyclerViewAdapter;
    RelativeLayout backButton;
    ArrayList<SelectPojoClass>selectPojoClassArrayList;
    String page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_class);
        initView();
        setListner();
        SelectClassInfo();
    }

    private void SelectClassInfo() {
        selectPojoClassArrayList.add(new SelectPojoClass("01","V","Class"));
        selectPojoClassArrayList.add(new SelectPojoClass("02","VI","Class"));
        selectPojoClassArrayList.add(new SelectPojoClass("03","VII","Class"));
        selectPojoClassArrayList.add(new SelectPojoClass("04","VIII","Class"));
        selectPojoClassArrayList.add(new SelectPojoClass("05","IX","Class"));
        selectPojoClassArrayList.add(new SelectPojoClass("06","X","Class"));
        selectPojoClassArrayList.add(new SelectPojoClass("07","XI","Class"));
        selectPojoClassArrayList.add(new SelectPojoClass("08","XII","Class"));
        selectInfoReyclerViewAdapter = new SelectInfoReyclerViewAdapter(getApplication(), selectPojoClassArrayList);
        selectInfoRecyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 3));
        selectInfoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        selectInfoRecyclerView.setAdapter(selectInfoReyclerViewAdapter);

    }

    private void setListner() {
        backButton.setOnClickListener(this);
    }

    private void initView() {
        selectInfoRecyclerView=(RecyclerView)findViewById(R.id.selectInfoRecyclerView);
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        selectPojoClassArrayList=new ArrayList<SelectPojoClass>();

        page = getIntent().getStringExtra("page");
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

    private class SelectInfoReyclerViewAdapter extends RecyclerView.Adapter<SelectInfoReyclerViewAdapter.MyViewHolderClass>{
        Context context;ArrayList<SelectPojoClass> selectPojoClassArrayList;
        public SelectInfoReyclerViewAdapter(Context context, ArrayList<SelectPojoClass> selectPojoClassArrayList) {
            this.context=context;this.selectPojoClassArrayList=selectPojoClassArrayList;
        }

        @NonNull
        @Override
        public SelectInfoReyclerViewAdapter.MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_select_class_page_item, parent, false);
            return new SelectInfoReyclerViewAdapter.MyViewHolderClass(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull SelectInfoReyclerViewAdapter.MyViewHolderClass myViewHolderClass, int i) {
            myViewHolderClass.classInfoTextView.setText(selectPojoClassArrayList.get(i).getTitle());
            myViewHolderClass.classTextView.setText(selectPojoClassArrayList.get(i).getClassInfo());

            myViewHolderClass.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(page.equals("1")) {
                        startActivity(new Intent(getApplicationContext(), StudentScreenActivity.class));
                    }else if(page.equals("2")){
                        startActivity(new Intent(getApplicationContext(), AttendenceScreenActivity.class));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return selectPojoClassArrayList.size();
        }

        public class MyViewHolderClass extends RecyclerView.ViewHolder {
            TextView classInfoTextView,classTextView;
            LinearLayout linearLayout;
            public MyViewHolderClass(@NonNull View itemView) {
                super(itemView);
                classInfoTextView=(TextView)itemView.findViewById(R.id.classInfoTextView);
                classTextView=(TextView)itemView.findViewById(R.id.classTextView);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

            }
        }
    }
}
