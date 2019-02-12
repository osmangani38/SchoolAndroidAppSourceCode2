package com.sap.school;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sap.handler.IWSCallHandler;
import com.sap.handler.ResponseStatus;
import com.sap.handler.ServerComHandler;
import com.sap.school.PojoClass.DateItem;
import com.sap.school.PojoClass.TodaysClassPlanPOJO;
import com.sap.school.PojoClass.ExamSchedulePOJO;
import com.sap.school.PojoClass.ListItem;
import com.sap.school.common.TodaysClassPlan;
import com.sap.school.common.TodaysPlanRecyclerAdapter;
import com.sap.utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class ViewTodaysClassPlan extends BaseActivity implements View.OnClickListener {
    
    RecyclerView routinRecyclerView;
    RelativeLayout backButton;
    Button btnMore;
    Button btnAdd;
    ArrayList<ExamSchedulePOJO> classRoutinePojoClassArrayList;


    private TodaysPlanRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_classplan_screen);
        initView();

        String user_id = SPUtils.getInstance().getString("user_id");
        String roll_id;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ViewTodaysClassPlan.this);
        roll_id = sharedPref.getString("roll_id", null); // getting String
        if (StringUtils.isEmpty(roll_id)){
            roll_id = "2";
        }

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);

        croutinInfo(user_id, roll_id, "0", "0", formattedDate, formattedDate);
        setListner();
    }

    private void setListner() {
        backButton.setOnClickListener(this);
        btnMore.setOnClickListener(this);
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
                break;

        }
    }


   /* private void croutinInfo() {
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

    }*/

    private void initView() {
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        routinRecyclerView = (RecyclerView)findViewById(R.id.examRecyclerView);
        btnMore = (Button)findViewById(R.id.btnMore);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        classRoutinePojoClassArrayList=new ArrayList<>();
        routinRecyclerView.setHasFixedSize(true);
    }

    private void croutinInfo(String user_id, String role_id, String class_id, String section_id,
                             String date_from, String date_to )
    {
        showProgressUI(AppConstants.Loading);
        String wsLink = AppConstants.BaseURL+"ClassPlan";
        //web method call
        JSONObject loginJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            loginJson.put("user_id", user_id);
            loginJson.put("role_id", role_id);
            loginJson.put("class_id", class_id);
            loginJson.put("section_id", section_id);
            loginJson.put("date_from", date_from);
            loginJson.put("date_to", date_to);
            loginJson.put("subject_id", "0");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(loginJson);
        JSONObject ws_dataObj = new JSONObject();
        try {
            ws_dataObj.put("WS_DATA", jsonArray);
            ws_dataObj.put("WS_CODE", "160");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = ws_dataObj.toString();
        ServerComHandler.getInstance().wsCallJsonBy(wsLink,json, new IWSCallHandler() {
            @Override
            public void responseStatus(final int status, final Object data) {
                if (status == 200) {
                    // List<NotificationItem> arrTmp = new ArrayList<>();
                    try{
                        ResponseStatus responseStatus = new ResponseStatus((String)data);
                        if (responseStatus.isSuccess()) {
                            dismissProgressUI();
                            final ArrayList<TodaysClassPlanPOJO> mArrayList=new ArrayList<>();
                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("result");
                            int count = jsonArray.length();
                            for (int i = 0; i < count; i++) {
                                JSONObject jsonObjItm = jsonArray.getJSONObject(i);
                                Log.d("Json is ","jsonObjItm is"+jsonObjItm);
                                TodaysClassPlanPOJO todaysClassPlanPOJO = new TodaysClassPlanPOJO();

                                todaysClassPlanPOJO.setClass_date(jsonObjItm.getString("date"));
                                todaysClassPlanPOJO.setSubject(jsonObjItm.getString("subject"));
                                todaysClassPlanPOJO.setRemarks(jsonObjItm.getString("remarks"));

                                JSONArray jsonArraySection = jsonObjItm.getJSONArray("lesson");
                                int countSection = jsonArraySection.length();
                                String final_text = "";
                                for (int j = 0; j < countSection; j++) {
                                    JSONObject jsonObjItmSection = jsonArraySection.getJSONObject(j);
                                    todaysClassPlanPOJO.setLesson_name(jsonObjItmSection.getString("name"));
                                    JSONArray jsonObjItmSectionJSONArray = jsonObjItmSection.getJSONArray("topic");
                                    for (int k = 0; k < jsonObjItmSectionJSONArray.length(); k++) {
                                        JSONObject jsonArraySectionJSONObject = jsonObjItmSectionJSONArray.getJSONObject(k);
                                        String topic_name = jsonArraySectionJSONObject.getString("name");
                                        System.out.println("topic" +topic_name);

                                        final_text = final_text + topic_name + ", ";

                                    }
                                    final_text = final_text.replaceAll(", $", "");
                                    todaysClassPlanPOJO.setTopic_name(final_text);
                                }

                                mArrayList.add(todaysClassPlanPOJO);

                            }

                            ViewTodaysClassPlan.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                adapter = new TodaysPlanRecyclerAdapter(ViewTodaysClassPlan.this, mArrayList);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(ViewTodaysClassPlan.this);
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                routinRecyclerView.setLayoutManager(layoutManager);
                                routinRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                routinRecyclerView.setAdapter(adapter);
                                }
                            });
                        }
                        else {
                            ToastUtils.showShort(responseStatus.response_message);
                            dismissProgressUI();
                        }
                        ViewTodaysClassPlan.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissProgressUI();
                            }
                        });
                    }catch (Exception ex){ex.printStackTrace();}


                }else{
                    Log.d("Webservice","failed");
                    dismissProgressUI();
                }
            }
        });

    }




}
