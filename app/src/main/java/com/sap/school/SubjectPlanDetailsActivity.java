package com.sap.school;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


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

import static com.sap.school.PojoClass.LessonDataFactory.makeMultiCheckGenres;

public class SubjectPlanDetailsActivity extends BaseActivity implements View.OnClickListener{
  RelativeLayout backButton,submitClassButton;
  private MultiCheckLessonAdapter adapter;
  RecyclerView recyclerView;
  TextView subjectName;
  String classid="", subjectid="",subject_name = "",type = "",section_id = "0";
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

    subject_name = intent.getStringExtra("subject_name");
    String user_id = SPUtils.getInstance().getString("user_id");
    String roll_id;
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SubjectPlanDetailsActivity.this);
    roll_id = sharedPref.getString("roll_id", null); // getting String
    if (StringUtils.isEmpty(roll_id)){
      roll_id = "2";
    }
    getClassAndSection(user_id,roll_id,classid, subjectid);
    if (!StringUtils.isEmpty(subject_name)){
        subjectName.setText("Subject :"+subject_name);
    }
  }

  private void initView() {
    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    backButton=(RelativeLayout)findViewById(R.id.backButton);
    submitClassButton=(RelativeLayout)findViewById(R.id.submitClassButton);
    subjectName = (TextView)findViewById(R.id.subjectName);

    LinearLayoutManager layoutManager = new LinearLayoutManager(SubjectPlanDetailsActivity.this);
    adapter = new MultiCheckLessonAdapter(makeMultiCheckGenres());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);

    /*adapter.setOnGroupClickListener(new OnGroupClickListener() {
      @Override
      public boolean onGroupClick(int flatPos) {
        Toast.makeText(SubjectPlanDetailsActivity.this, "Parent"+adapter.getGroups().get(flatPos).getTitle(), Toast.LENGTH_SHORT).show();
        return false;
      }
    });*/

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
       wsLink = AppConstants.BaseURL+"ClassPlan";
    }
    else if (roll_id.equals("2") && type.equals("ClassLog")){
      wsLink = AppConstants.BaseURL+"ClassLog";
    }
    //web method call
    JSONObject loginJson = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    try {
      loginJson.put("user_id", user_id);
      loginJson.put("role_id", role_id);
      loginJson.put("class_id", classid);
      loginJson.put("section_id", section_id);
      loginJson.put("subject_id", subject_id);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    jsonArray.put(loginJson);
    JSONObject ws_dataObj = new JSONObject();
    try {
      ws_dataObj.put("WS_DATA", jsonArray);
      if (roll_id.equals("4")) {
        ws_dataObj.put("WS_CODE", "260");
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

    /*adapter.setChildClickListener(new OnCheckChildClickListener() {
      @Override
      public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {

        //Toast.makeText(SubjectPlanDetailsActivity.this, "child", Toast.LENGTH_SHORT).show();
      }
    });*/

  }

  private void SubmitInfo() {
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
}
