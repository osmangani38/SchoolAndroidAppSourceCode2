package com.sap.school;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sap.handler.IWSCallHandler;
import com.sap.handler.ResponseStatus;
import com.sap.handler.ServerComHandler;
import com.sap.school.Fragment.MyDatePickerFragment;
import com.sap.school.PojoClass.DateItem;
import com.sap.school.PojoClass.TodaysClassPlanPOJO;
import com.sap.school.common.ExamScheduleAdapter;
import com.sap.school.PojoClass.ExamSchedulePOJO;
import com.sap.school.PojoClass.GeneralItem;
import com.sap.school.PojoClass.ListItem;
import com.sap.school.R;
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


public class ViewMoreClassPlan extends BaseActivity implements View.OnClickListener {
    
    RecyclerView routinRecyclerView;
    RelativeLayout backButton;
    Button btnAdd, btnGo;
    TextView fromTextView, toDateTV, tvHeader;
    ArrayList<ExamSchedulePOJO> classRoutinePojoClassArrayList;
    private List<ExamSchedulePOJO> myOptions = new ArrayList<>();
    List<ListItem> consolidatedList = new ArrayList<>();
    String type, sectionId = "1", class_id = "6";
    private TodaysPlanRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_classplan_screen);
        initView();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type= getIntent().getStringExtra("type");
            sectionId= getIntent().getStringExtra("section_id");
            class_id = getIntent().getStringExtra("class_id");
        }

        if(type.equals("ClassLog")){
            tvHeader.setText(R.string.classLogText);
        }
        setListner();
    }

    private void setListner() {
        backButton.setOnClickListener(this);
       // btnMore.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnGo.setOnClickListener(this);
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
                if(type.equals("ClassLog")){
                    Intent j = new Intent(getApplicationContext(), SelectClassActivity.class);
//                i.putExtra("page", "1");
                    j.putExtra("type","ClassLog");
                    startActivity(j);
                }else{
                    Intent j = new Intent(getApplicationContext(), ClassPlanActivity.class);
//                i.putExtra("page", "1");
                    j.putExtra("type","classPlan");
                    startActivity(j);
                }

                break;
            case R.id.btnGo:
                String user_id = SPUtils.getInstance().getString("user_id");
                String roll_id;
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ViewMoreClassPlan.this);
                roll_id = sharedPref.getString("roll_id", null); // getting String
                if (StringUtils.isEmpty(roll_id)){
                    roll_id = "2";
                }

                if(type.equals("ClassLog")){
                    if(StringUtils.isEmpty(class_id) && StringUtils.isEmpty(sectionId)){
                        class_id = "6";
                        sectionId = "1";
                    }
                    croutinInfo(user_id, roll_id, class_id, sectionId, fromTextView.getText().toString(), toDateTV.getText().toString());
                }else{
                    croutinInfo(user_id, roll_id, "0", "0", fromTextView.getText().toString(), toDateTV.getText().toString());
                }


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
       // btnMore = (Button)findViewById(R.id.btnMore);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnGo = (Button) findViewById(R.id.btnGo);
        tvHeader = (TextView)findViewById(R.id.tvHeader);
        fromTextView = (TextView)findViewById(R.id.fromTextView);
        toDateTV = (TextView) findViewById(R.id.toDateTV);
        classRoutinePojoClassArrayList=new ArrayList<>();
        routinRecyclerView.setHasFixedSize(true);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener fromdate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                fromTextView.setText(sdf.format(myCalendar.getTime()));
            }

        };

        final DatePickerDialog.OnDateSetListener todate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                toDateTV.setText(sdf.format(myCalendar.getTime()));
            }

        };
        fromTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ViewMoreClassPlan.this, fromdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        toDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ViewMoreClassPlan.this, todate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void croutinInfo(String user_id, String role_id, String class_id, String section_id,
                             String date_from, String date_to )
    {
        final ArrayList<TodaysClassPlanPOJO> mArrayList=new ArrayList<>();
        showProgressUI(AppConstants.Loading);
        String wsLink = AppConstants.BaseURL+"ClassPlan";

        if(type.equals("ClassLog")){
            wsLink = AppConstants.BaseURL+"ClassLog";
        }
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
            if(type.equals("ClassLog")){
                ws_dataObj.put("WS_CODE", "165");
            }else{
                ws_dataObj.put("WS_CODE", "160");
            }

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
                            mArrayList.clear();
                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("result");
                            int count = jsonArray.length();
                            for (int i = 0; i < count; i++) {
                                JSONObject jsonObjItm = jsonArray.getJSONObject(i);
                                Log.d("Json is ","jsonObjItm is"+jsonObjItm);
                                TodaysClassPlanPOJO todaysClassPlanPOJO = new TodaysClassPlanPOJO();
                                if(type.equals("ClassLog")){
                                    todaysClassPlanPOJO.setPlan_id(jsonObjItm.getString("plan_id"));
                                    todaysClassPlanPOJO.setClass_id("6");
                                }else{
                                    todaysClassPlanPOJO.setPlan_id(jsonObjItm.getString("id"));
                                    todaysClassPlanPOJO.setClass_id(jsonObjItm.getString("class_id"));
                                    todaysClassPlanPOJO.setSection_id(jsonObjItm.getString("section_id"));
                                }

                                todaysClassPlanPOJO.setClass_date(jsonObjItm.getString("date"));
                                todaysClassPlanPOJO.setSubject(jsonObjItm.getString("subject"));
                                todaysClassPlanPOJO.setRemarks(jsonObjItm.getString("remarks"));
                                todaysClassPlanPOJO.setClass_name(jsonObjItm.getString("class"));
                                todaysClassPlanPOJO.setSection_name(jsonObjItm.getString("section"));
                                todaysClassPlanPOJO.setSubject_id(jsonObjItm.getString("subject_id"));

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

                            ViewMoreClassPlan.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = new TodaysPlanRecyclerAdapter(ViewMoreClassPlan.this, mArrayList);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(ViewMoreClassPlan.this);
                                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                    routinRecyclerView.setLayoutManager(layoutManager);
                                    routinRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                    routinRecyclerView.setAdapter(adapter);
                                    ToastUtils.showShort(mArrayList.size());
                                }
                            });
                        }
                        else {
                            ToastUtils.showShort(responseStatus.response_message);
                            dismissProgressUI();
                        }
                        ViewMoreClassPlan.this.runOnUiThread(new Runnable() {
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
