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
  String fromDate;
  String toDate;
  String user_id, roll_id, classid="", subjectid="0",subject_name = "",type = "",section_id = "0",
          plan_date, log_date, plan_id;
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
    plan_date = intent.getStringExtra("plan_date");
    subject_name = intent.getStringExtra("subject_name");
    user_id = SPUtils.getInstance().getString("user_id");
    plan_id = "62";
    log_date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH.getDefault()).format(new Date());
    fromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH.getDefault()).format(new Date());
    toDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH.getDefault()).format(new Date());

    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SubjectPlanDetailsActivity.this);
    roll_id = sharedPref.getString("roll_id", null); // getting String
    if (StringUtils.isEmpty(roll_id)){
      roll_id = "2";
    }
    getClassAndSection(user_id,roll_id,classid, subjectid);
    if (!StringUtils.isEmpty(subject_name)){
      subjectName.setText("Subject :"+subject_name);
    }
    if (roll_id.equals("4") && type.equals("StudentClassPlan")) {
      selectDateLayout.setVisibility(View.VISIBLE);
      submitClassButton.setVisibility(View.GONE);
    }
    else  if (roll_id.equals("4")){
      submitClassButton.setVisibility(View.GONE);
    }
    else if (roll_id.equals("2") && type.equals("ClassLog")) {
      submitClassTextView.setText("Submit Class Log");
      selectDateLayout.setVisibility(View.VISIBLE);

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
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( SubjectPlanDetailsActivity.this);
    roll_id = sharedPref.getString("roll_id", null); // getting String
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
        loginJson.put("date_from", fromDate);
        loginJson.put("date_to", toDate);
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
              Log.d("gg","aaa");
              /*int count = jsonArray.length();
              for (int i = 0; i < count; i++) {
                JSONObject jsonObjItm = jsonArray.getJSONObject(i);
                Log.d("Json is ","jsonObjItm is"+jsonObjItm);
                JSONArray jsonArraySection = jsonObjItm.getJSONArray("topic");
                int countSection = jsonArraySection.length();

                for (int j = 0; j < countSection; j++) {
                  JSONObject jsonObjItmSection = jsonArraySection.getJSONObject(j);
                  ChapterPOJO chapterPOJO = new ChapterPOJO(jsonObjItmSection.getString("id"),
                          jsonObjItmSection.getString("topic"));
                  mArrayList.add(chapterPOJO);
                }
                new MultiCheckLesson(jsonObjItm.getString("lesson"), mArrayList ,  jsonObjItm.getString("id"));
              }
*/

              SubjectPlanDetailsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  dismissProgressUI();
                  subjectName.setText(stringSubjectName);
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
          Log.d("Webservice","failed");
          dismissProgressUI();
        }
      }
    });

    /*adapter.setChildClickListener(new OnCheckChildClickListener() {
      @Override
      public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {

        //Toast.makeText(SubjectPlanDetailsActivity.this, "child", Toast.LENGTH_SHORT).show();
      }
    });*/

  }

  private void SubmitInfo() {

    String topic_id = AppConstants.GLOBAL_TOPIC_ID;
    topic_id = topic_id.replaceAll(",$", "");
    //ToastUtils.showShort(topic_id);

    if(type.equals("ClassPlan")){
      submitClassPlan(user_id, roll_id, log_date, plan_id, plan_date, classid, section_id, subjectid, topic_id, "testing save data");
    }else if(type.equals("ClassLog")){
      submitClassPlan(user_id, roll_id, log_date, plan_id, plan_date,classid, section_id, subjectid, topic_id, "testing save data");
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
          Log.d("Webservice","failed");
          dismissProgressUI();
        }
      }
    });

  }
}