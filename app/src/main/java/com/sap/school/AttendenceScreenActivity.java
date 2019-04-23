package com.sap.school;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.prashantsolanki.secureprefmanager.SecurePrefManager;
import com.sap.handler.IWSCallHandler;
import com.sap.handler.ResponseStatus;
import com.sap.handler.ServerComHandler;
import com.sap.school.PojoClass.AttendancePogoClass;
import com.sap.school.PojoClass.SelectableItem;
import com.sap.school.PojoClass.StudentInfoPojoClass;
import com.sap.school.common.SelectableAdapter;
import com.sap.school.common.SelectableViewHolder;
import com.sap.utils.AppConstants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AttendenceScreenActivity extends BaseActivity implements View.OnClickListener, SelectableViewHolder.OnItemSelectedListener  {
    RecyclerView attendenceRecyclerView;
    String className,classId,sectionId;
    @BindView(R.id.searchEditText)
    EditText searchEditText;

    SelectableAdapter selectableAdapter;
    ArrayList<AttendancePogoClass> attendencePojoClasses;
    ArrayList<AttendancePogoClass>mArrayFromJSON;
    ArrayList mArraySelectedStudents;
    ArrayList mArrayNotSelectedStudents;
    RelativeLayout backButton, attendenceButton;
    LinearLayout selectClassButton,selectSectionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_screen);
        initView();
        setListner();

        String user_id = SecurePrefManager.with(this)
                .get("user_id")
                .defaultValue("unknown")
                .go();;
        String roll_id;
        roll_id = SecurePrefManager.with(this)
                .get("roll_id")
                .defaultValue("unknown")
                .go();; // getting String
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            className= getIntent().getStringExtra("className");
            classId= getIntent().getStringExtra("classId");
            sectionId= getIntent().getStringExtra("sectionId");
        }
        if (StringUtils.isEmpty(roll_id)){
            roll_id = "2";
        }
        getAllStudentsDetails(user_id,roll_id);

    }
    private void getAllStudentsDetails(String user_id, String role_id)
    {
        showProgressUI(AppConstants.Loading);
        String wsLink = AppConstants.BaseURL+"StudentAttendanceDaily";
        //web method call
        JSONObject loginJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            loginJson.put("user_id", user_id);
            loginJson.put("role_id", role_id);
            loginJson.put("class_id", classId);
            loginJson.put("section_id", sectionId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(loginJson);
        JSONObject ws_dataObj = new JSONObject();
        try {
            ws_dataObj.put("WS_DATA", jsonArray);
            ws_dataObj.put("WS_CODE", "150");
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
                              mArrayFromJSON=new ArrayList<>();
                            mArraySelectedStudents = new ArrayList();
                            mArrayNotSelectedStudents = new ArrayList();
                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("result");
                            int count = jsonArray.length();
                            for (int i = 0; i < count; i++) {
                                JSONObject jsonObjItm = jsonArray.getJSONObject(i);
                                    mArrayNotSelectedStudents.add(jsonObjItm.getString("id"));
                                    mArrayFromJSON.add(new AttendancePogoClass(jsonObjItm.getString("id"), jsonObjItm.getString("name"), R.drawable.student1,1,"",jsonObjItm.getString("roll_no"))/*jsonObjItm.getString("curclass"*/);
                            }
                            AttendenceScreenActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissProgressUI();
                                    attendenceButton.setVisibility(View.VISIBLE);
                                    //System.out.println("Attnd"+mArrayFromJSON.size());
                                    update(mArrayFromJSON);
                                    studentInfo();

                                }
                            });
                        }
                        else {
                            AttendenceScreenActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    attendenceButton.setVisibility(View.GONE);
                                    dismissProgressUI();
                                    ToastUtils.showShort("Data Not Found.");                                }
                            });

                        }
                        AttendenceScreenActivity.this.runOnUiThread(new Runnable() {
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
    private void submitAttendence(String user_id, String role_id)
    {
        showProgressUI(AppConstants.Loading);
        String wsLink = AppConstants.BaseURL+"SaveStudentDailyAttendance";
        //web method call
        JSONObject loginJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            loginJson.put("user_id", user_id);
            loginJson.put("role_id", role_id);
            loginJson.put("class_id", classId);
            loginJson.put("section_id", sectionId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray studentList = new JSONArray();
        for (int i = 0;i<mArraySelectedStudents.size();i++){
           String student_id = mArrayFromJSON.get(i).getId();
            studentList.put(student_id);
            String student_attendance_daily_id = mArrayFromJSON.get(i).getStudentDailyAttenceId();
            JSONObject studentAttenceObject = new JSONObject();
            try {
                studentAttenceObject.put("student_id", student_id);
               // studentAttenceObject.put("student_attendance_daily_id", "0");
                if (mArraySelectedStudents.contains(student_id)) {
                    studentAttenceObject.put("attendance", "1");
                }
                else {
                    studentAttenceObject.put("attendance", "0");
                }
                //studentList.put(studentAttenceObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //String result = TextUtils.join(",", studentList);
        //String replaceString=result.replace("\"","");
        //studentList.clear();
        //studentList.add(replaceString);
        jsonArray.put(loginJson);
        if (studentList.length()>0){
            try {
                loginJson.put("student", studentList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject ws_dataObj = new JSONObject();
        try {
            ws_dataObj.put("WS_DATA", jsonArray);
            ws_dataObj.put("WS_CODE", "171");
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
                        final ResponseStatus responseStatus = new ResponseStatus((String)data);
                        if (responseStatus.isSuccess()) {
                            dismissProgressUI();

                            AttendenceScreenActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissProgressUI();
                                    ToastUtils.showShort("Attendence Submitted Successfully.");
                                }
                            });
                        }
                        else {
                            AttendenceScreenActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //attendenceButton.setVisibility(View.GONE);
                                    dismissProgressUI();
                                    ToastUtils.showShort(responseStatus.response_message);                                }
                            });

                        }
                        AttendenceScreenActivity.this.runOnUiThread(new Runnable() {
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

    public void update(ArrayList<AttendancePogoClass> data) {
        attendencePojoClasses.clear();
        attendencePojoClasses.addAll(data);
        attendenceRecyclerView.removeAllViews();
        attendenceRecyclerView.invalidate();
    }
    private void studentInfo() {
        selectableAdapter = new SelectableAdapter(AttendenceScreenActivity.this, attendencePojoClasses, className);
        attendenceRecyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 1));
        attendenceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        attendenceRecyclerView.setAdapter(selectableAdapter);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

    }
    private void performSearch() {

        String searchText = searchEditText.getText().toString().toUpperCase();
        if (searchText.length() > 0 || !searchText.equals(className)) {
            final ArrayList mArrayList = new ArrayList<>();
            for (AttendancePogoClass d : mArrayFromJSON) {
                if (searchText!= null) {
                    if (d.getName().contains(searchText)) {
                        //something here
                        mArrayList.add(d);
                    }
                }
            }
            AttendenceScreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dismissProgressUI();
                    update(mArrayList);
                    studentInfo();

                }
            });
        }
        else{
            AttendenceScreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dismissProgressUI();
                    update(mArrayFromJSON);
                    studentInfo();

                }
            });
        }
        InputMethodManager inputManager =
                (InputMethodManager) this.getCurrentContext().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

    }


    private void setListner() {
        backButton.setOnClickListener(this);
        attendenceButton.setOnClickListener(this);
    }

    private void initView() {
        attendenceRecyclerView = (RecyclerView) findViewById(R.id.attendenceRecyclerView);
        backButton = (RelativeLayout) findViewById(R.id.backButton);
        attendenceButton = (RelativeLayout) findViewById(R.id.attendenceButton);
        searchEditText = (EditText)findViewById(R.id.searchEditText);
        attendencePojoClasses = new ArrayList<AttendancePogoClass>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.attendenceButton:
                String user_id = SecurePrefManager.with(this)
                        .get("user_id")
                        .defaultValue("unknown")
                        .go();
                String roll_id;
                roll_id = SecurePrefManager.with(this)
                        .get("roll_id")
                        .defaultValue("unknown")
                        .go();; // getting String
                if (StringUtils.isEmpty(roll_id)){
                    roll_id = "2";
                }
                submitAttendence(user_id,roll_id);
                break;
        }

    }
    @Override
    public void onItemSelected(SelectableItem selectableItem) {

        List<AttendancePogoClass> selectedItems = selectableAdapter.getSelectedItems();

        String student_id = selectableItem.getId();
        if (mArraySelectedStudents.contains(student_id)){
            mArraySelectedStudents.remove(student_id);
        }
        else {
            mArraySelectedStudents.add(student_id);
        }

    }

}
