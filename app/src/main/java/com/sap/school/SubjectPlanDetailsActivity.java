package com.sap.school;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.prashantsolanki.secureprefmanager.SecurePrefManager;
import com.sap.handler.IWSCallHandler;
import com.sap.handler.ResponseStatus;
import com.sap.handler.ServerComHandler;
import com.sap.school.PojoClass.ChapterPOJO;
import com.sap.school.PojoClass.MultiCheckLesson;
import com.sap.school.common.MultiCheckLessonAdapter;
import com.sap.utils.AppConstants;
import com.sap.utils.JSONSharedPreferences;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.sap.school.PojoClass.LessonDataFactory.makeMultiCheckGenres;

public class SubjectPlanDetailsActivity extends BaseActivity implements View.OnClickListener{
  RelativeLayout backButton,submitClassButton;
  LinearLayout selectDateLayout;
  TextView fromTextView, toDateTV;
  Button btnGo;
  TextView submitClassTextView;
  private MultiCheckLessonAdapter adapter;
  RecyclerView recyclerView;
  TextView subjectName;
  String stringSubjectName;
  String fromDate,topic_name,lesson_name;
  String toDate;
  String user_id, roll_id, classid="", subjectid="0",subject_name = "",type = "",section_id = "0",
          plan_date, log_date, plan_id = "";
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_subject_plan_details);
    initView();
    setListner();

    Intent intent = getIntent();
    type = intent.getStringExtra("type");
    classid = intent.getStringExtra("class_id");
    subjectid = intent.getStringExtra("subject_id");
    section_id = intent.getStringExtra("section_id");
    lesson_name = intent.getStringExtra("lesson_name");
    if (lesson_name.length()>0) {
      AppConstants.SUBJECT_LESSON_ID = lesson_name;
    }
    topic_name = intent.getStringExtra("topic_name");

    plan_date = intent.getStringExtra("plan_date");
    subject_name = intent.getStringExtra("subject_name");
    user_id = SecurePrefManager.with(this)
            .get("user_id")
            .defaultValue("unknown")
            .go();

    log_date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH.getDefault()).format(new Date());
    fromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH.getDefault()).format(new Date());
    toDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH.getDefault()).format(new Date());

    if(type.equals("TodaysClassPlan")){
      plan_id = intent.getStringExtra("plan_id");
      log_date = plan_date;
    }

    roll_id = SecurePrefManager.with(this)
            .get("roll_id")
            .defaultValue("unknown")
            .go(); // getting String
    if (StringUtils.isEmpty(roll_id)){
      roll_id = "2";
    }
    getClassAndSection(user_id,roll_id,classid, subjectid);
    if (!StringUtils.isEmpty(subject_name)){
      subjectName.setText("Subject : "+subject_name);
    }
    if ((roll_id.equals("4") && type.equals("StudentClassPlan")||(roll_id.equals("4") && type.equals("StudentClassLog")))) {
      selectDateLayout.setVisibility(View.VISIBLE);
      submitClassButton.setVisibility(View.GONE);
    }
    else  if (roll_id.equals("4")){
      submitClassButton.setVisibility(View.GONE);
    }
    else if (roll_id.equals("2") && type.equals("ClassLog")) {
      submitClassTextView.setText("Submit Class Log");
      selectDateLayout.setVisibility(View.INVISIBLE);

    }else if (roll_id.equals("2") && type.equals("TodaysClassPlan")) {
      submitClassTextView.setText("Submit Class Log");
    }
  }

  private void initView() {
    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    backButton=(RelativeLayout)findViewById(R.id.backButton);
    submitClassButton=(RelativeLayout)findViewById(R.id.submitClassButton);
    subjectName = (TextView)findViewById(R.id.subjectName);
    submitClassTextView = (TextView)findViewById(R.id.submitClassTextView);
    selectDateLayout =(LinearLayout) findViewById(R.id.selectDate);
    fromTextView = (TextView)findViewById(R.id.fromTextView);
    toDateTV = (TextView) findViewById(R.id.toDateTV);
    btnGo = (Button) findViewById(R.id.goButton);

    LinearLayoutManager layoutManager = new LinearLayoutManager(SubjectPlanDetailsActivity.this);
    adapter = new MultiCheckLessonAdapter(makeMultiCheckGenres());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
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
        new DatePickerDialog(SubjectPlanDetailsActivity.this, fromdate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
      }
    });

    toDateTV.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        new DatePickerDialog(SubjectPlanDetailsActivity.this, todate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
      }
    });

  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    adapter.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    adapter.onRestoreInstanceState(savedInstanceState);
  }

  private void setListner() {
    backButton.setOnClickListener(this);
    submitClassButton.setOnClickListener(this);
    btnGo.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId())
    {
      case R.id.backButton:
        finish();
        break;

      case R.id.submitClassButton:
        SubmitInfo();
        break;

      case R.id.goButton:
        if (StringUtils.isEmpty(toDateTV.getText().toString()) && StringUtils.isEmpty(fromTextView.getText().toString())){
          ToastUtils.showShort("Please Enter Dates.");
      }
      else {
          fromDate = fromTextView.getText().toString();
          toDate = toDateTV.getText().toString();
          getClassAndSection(user_id,roll_id,classid, subjectid);
        }
        break;
    }
  }

  private void getClassAndSection(String user_id, String role_id, String class_id, String subject_id)
  {
    showProgressUI(AppConstants.Loading);
    String wsLink = AppConstants.BaseURL+"Syllabus";
    String roll_id;
    roll_id = SecurePrefManager.with(this)
            .get("roll_id")
            .defaultValue("unknown")
            .go();; // getting String
    if (roll_id.equals("4")) {
      if (type.equals("StudentClassPlan")) {
        wsLink = AppConstants.BaseURL + "ClassPlan";
      } else if (type.equals("StudentClassLog"))
      {
        wsLink = AppConstants.BaseURL + "ClassLog";
      }
    }
    else if (roll_id.equals("2") && type.equals("ClassLog")){
      wsLink = AppConstants.BaseURL+"ClassLog";
    }
    //web method call
    JSONObject loginJson = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    try {
      loginJson.put("user_id", user_id);
      //loginJson.put("user_id", "11");

      loginJson.put("role_id", role_id);
//      loginJson.put("class_id", classid);
//      loginJson.put("section_id", section_id);
      if (roll_id.equals("2")) {
        if (wsLink.contains("Syllabus")||wsLink.contains("ClassLog")) {
          loginJson.put("class_id", class_id);
          loginJson.put("section_id", section_id);
        }
        else {
          loginJson.put("class_id", "");
          loginJson.put("section_id", "");
        }
      }
      //if (!StringUtils.isEmpty(subject_id)) {
      if (wsLink.contains("Syllabus")) {
        loginJson.put("subject_id", subject_id);
      }
      else {
        if (StringUtils.isEmpty(subject_id)){
          loginJson.put("subject_id", "0");
        }
        else {
          loginJson.put("subject_id", subject_id);
        }
      }
      //}
      if (roll_id.equals("2") && type.equals("ClassLog")) {
//             loginJson.put("date_from", fromDate);
//             loginJson.put("date_to", toDate);

        if (plan_date.length()>0){
          loginJson.put("date_from", plan_date);
          loginJson.put("date_to", plan_date);
        }
        else {
          loginJson.put("date_from", fromDate);
          loginJson.put("date_to", toDate);
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    jsonArray.put(loginJson);
    JSONObject ws_dataObj = new JSONObject();
    try {
      ws_dataObj.put("WS_DATA", jsonArray);
      if (roll_id.equals("4")) {
        if (type.equals("StudentClassPlan")) {
//             loginJson.put("date_from", fromDate);
//             loginJson.put("date_to", toDate);
          ws_dataObj.put("WS_CODE", "260");
          loginJson.put("date_from", fromDate);
          loginJson.put("date_to", toDate);
        }
        else {
          loginJson.put("date_from", fromDate);
          loginJson.put("date_to", toDate);
          ws_dataObj.put("WS_CODE", "265");
        }
      }
      else if (roll_id.equals("2") && type.equals("ClassLog")){

        ws_dataObj.put("WS_CODE", "165");
      }
      else {
        ws_dataObj.put("WS_CODE", "10");
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
              final ArrayList<ChapterPOJO> mArrayList=new ArrayList<>();
              JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("result");
              JSONObject jsonObject = jsonArray.getJSONObject(0);
              if (jsonObject.has("subject") && !jsonObject.isNull("subject")) {
                stringSubjectName = jsonObject.getString("subject");
              }
              JSONSharedPreferences.saveJSONArray(SubjectPlanDetailsActivity.this, "teacherJSON", "subjectChapters", jsonArray);
              JSONArray savedArray = JSONSharedPreferences.loadJSONArray(SubjectPlanDetailsActivity.this, "teacherJSON", "subjectChapters");

              SubjectPlanDetailsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  recyclerView.setVisibility(View.VISIBLE);
                  dismissProgressUI();
                  if(type.equals("ClassPlan") || type.equals("TodaysClassPlan") ){
                    subjectName.setText("Subject : "+subject_name);
                  }else{
                  subjectName.setText(stringSubjectName);}
                  recyclerView.setAdapter(null);
                  recyclerView.setLayoutManager(null);
                  recyclerView.removeAllViewsInLayout();
                  LinearLayoutManager layoutManager = new LinearLayoutManager(SubjectPlanDetailsActivity.this);
                  adapter = new MultiCheckLessonAdapter(makeMultiCheckGenres());
                  recyclerView.setLayoutManager(layoutManager);
                  recyclerView.setAdapter(adapter);
                  adapter.notifyDataSetChanged();
                }
              });
            }
            else {
              ToastUtils.showShort(responseStatus.response_message);
              SubjectPlanDetailsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  dismissProgressUI();
                  subjectName.setText("");
                  recyclerView.setVisibility(View.GONE);
                }
              });
            }
            SubjectPlanDetailsActivity.this.runOnUiThread(new Runnable() {
              @Override
              public void run() {
                dismissProgressUI();
              }
            });
          }catch (Exception ex){ex.printStackTrace();}


        }else{
          dismissProgressUI();
        }
      }
    });
  }

  private void SubmitInfo() {

    String topic_id = AppConstants.GLOBAL_TOPIC_ID;
    if (StringUtils.isTrimEmpty(topic_id)){
      ToastUtils.showShort("Please select topics");
    }
    else {
      topic_id = topic_id.replaceAll(",$", "");
      if (type.equals("ClassPlan")) {
        submitClassPlan(user_id, roll_id, log_date, plan_id, plan_date, classid, section_id, subjectid, topic_id, "testing save data");
      } else if (type.equals("ClassLog")) {
        submitClassPlan(user_id, roll_id, log_date, plan_id, plan_date, classid, section_id, subjectid, topic_id, "testing save data");
      } else if (type.equals("TodaysClassPlan")) {
        submitClassPlan(user_id, roll_id, log_date, plan_id, plan_date, classid, section_id, subjectid, topic_id, "testing save data");
      }

    }
  }

  private void submitClassPlan(String user_id, String role_id, String log_date, String plan_id,
                               String plan_date, String class_id, String section_id, String subject_id,
                               String topic_id, String remarks)
  {
    String wsLink="";
    showProgressUI(AppConstants.Loading);
    if(type.equals("ClassPlan")){
      wsLink = AppConstants.BaseURL+"SaveClassPlan";
    }else if(type.equals("ClassLog")){
      wsLink = AppConstants.BaseURL+"SaveClassLog";
    }else if(type.equals("TodaysClassPlan")){
      wsLink = AppConstants.BaseURL+"SaveClassLog";
    }

    //web method call
    JSONObject loginJson = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    try {
      loginJson.put("user_id", user_id);
      loginJson.put("role_id", role_id);
      if(type.equals("ClassPlan")){
        loginJson.put("plan_date", plan_date);
        loginJson.put("class_id", class_id);
        loginJson.put("section_id", section_id);
        loginJson.put("subject_id", subject_id);
      }
      if(type.equals("ClassLog")){
        loginJson.put("log_date", log_date);
        loginJson.put("plan_id", plan_id);

      }
      if(type.equals("TodaysClassPlan")){
        loginJson.put("log_date", log_date);
        loginJson.put("plan_id", plan_id);

      }
      loginJson.put("topic_id", topic_id);
      loginJson.put("remarks", remarks);

    } catch (JSONException e) {
      e.printStackTrace();
    }
    jsonArray.put(loginJson);
    JSONObject ws_dataObj = new JSONObject();
    try {
      ws_dataObj.put("WS_DATA", jsonArray);
      if(type.equals("ClassPlan")){
        ws_dataObj.put("WS_CODE", "161");
      }else if(type.equals("ClassLog")){
        ws_dataObj.put("WS_CODE", "166");
      }else if(type.equals("TodaysClassPlan")){
        ws_dataObj.put("WS_CODE", "166");
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
              final ArrayList mArrayList=new ArrayList<>();
              if(responseStatus.jsonObject.getBoolean("status")){

                SubjectPlanDetailsActivity.this.runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SubjectPlanDetailsActivity.this);
                    LayoutInflater inflater = LayoutInflater.from(SubjectPlanDetailsActivity.this);
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
                  }
                });

              }
              SubjectPlanDetailsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  dismissProgressUI();

                }
              });
            }
            else {
              ToastUtils.showShort(responseStatus.response_message);
              finish();
              dismissProgressUI();
            }
            SubjectPlanDetailsActivity.this.runOnUiThread(new Runnable() {
              @Override
              public void run() {
                dismissProgressUI();
              }
            });
          }catch (Exception ex){ex.printStackTrace();}


        }else{
          dismissProgressUI();
        }
      }
    });

  }
}