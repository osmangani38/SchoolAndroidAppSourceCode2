package com.sap.school;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;


import com.sap.school.PojoClass.DateItem;
import com.sap.school.common.ExamScheduleAdapter;
import com.sap.school.PojoClass.ExamSchedulePOJO;
import com.sap.school.PojoClass.GeneralItem;
import com.sap.school.PojoClass.ListItem;
import com.sap.school.R;
import com.sap.school.common.TodaysClassPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class ViewMoreClassPlan extends AppCompatActivity implements View.OnClickListener {
    
    RecyclerView routinRecyclerView;
    RelativeLayout backButton;
    Button btnAdd;
    ArrayList<ExamSchedulePOJO> classRoutinePojoClassArrayList;
    private List<ExamSchedulePOJO> myOptions = new ArrayList<>();
    List<ListItem> consolidatedList = new ArrayList<>();

    private TodaysClassPlan adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_classplan_screen);
        initView();
        croutinInfo();
        setListner();
    }

    private void setListner() {
        backButton.setOnClickListener(this);
       // btnMore.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backButton:
                finish();
                break;
            case R.id.btnMore:
                Intent i = new Intent(getApplicationContext(), SelectClassActivity.class);
//                i.putExtra("page", "1");
                i.putExtra("type","classPlan");
                startActivity(i);
                break;
            case R.id.btnAdd:
                Intent j = new Intent(getApplicationContext(), ClassPlanActivity.class);
//                i.putExtra("page", "1");
                j.putExtra("type","classPlan");
                startActivity(j);
        }
    }


    private void croutinInfo() {
        myOptions.add(new ExamSchedulePOJO("Life Science","Class - V | Section - B","11 am - 2.00 pm", "02 February, 2019"));
        myOptions.add(new ExamSchedulePOJO("Hindi","Class - V | Section - B","11 am - 2.00 pm", "02 February, 2019"));
        myOptions.add(new ExamSchedulePOJO("English","Class - V | Section - B","11 am - 2.00 pm", "04 February, 2019"));
        myOptions.add(new ExamSchedulePOJO("Life Science","Class - V | Section - B","11 am - 2.00 pm", "04 February, 2019"));
        HashMap<String, List<ExamSchedulePOJO>> groupedHashMap = groupDataIntoHashMap(myOptions);

        for (String date : groupedHashMap.keySet()) {
            DateItem dateItem = new DateItem();
            dateItem.setDate(date);
            //consolidatedList.add(dateItem);
            for (ExamSchedulePOJO examSchedulePOJO : groupedHashMap.get(date)) {
                GeneralItem generalItem = new GeneralItem();
                generalItem.setExamSchedulePOJO(examSchedulePOJO);//setBookingDataTabs(bookingDataTabs);
                consolidatedList.add(generalItem);
            }
        }
        adapter = new TodaysClassPlan(this, consolidatedList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        routinRecyclerView.setLayoutManager(layoutManager);
        routinRecyclerView.setItemAnimator(new DefaultItemAnimator());
        routinRecyclerView.setAdapter(adapter);

    }

    private void initView() {
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        routinRecyclerView = (RecyclerView)findViewById(R.id.examRecyclerView);
       // btnMore = (Button)findViewById(R.id.btnMore);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        classRoutinePojoClassArrayList=new ArrayList<>();
        routinRecyclerView.setHasFixedSize(true);
    }


    private HashMap<String, List<ExamSchedulePOJO>> groupDataIntoHashMap(List<ExamSchedulePOJO> listOfPojosOfJsonArray) {

        HashMap<String, List<ExamSchedulePOJO>> groupedHashMap = new HashMap<>();

        for (ExamSchedulePOJO examSchedulePOJO : listOfPojosOfJsonArray) {

            String hashMapKey = examSchedulePOJO.getExamdate();

            if (groupedHashMap.containsKey(hashMapKey)) {
                // The key is already in the HashMap; add the pojo object
                // against the existing key.
                groupedHashMap.get(hashMapKey).add(examSchedulePOJO);
            } else {
                // The key is not there in the HashMap; create a new key-value pair
                List<ExamSchedulePOJO> list = new ArrayList<>();
                list.add(examSchedulePOJO);
                groupedHashMap.put(hashMapKey, list);
            }
        }
        return groupedHashMap;
    }


}
