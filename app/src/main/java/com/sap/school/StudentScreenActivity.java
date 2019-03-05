package com.sap.school;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.prashantsolanki.secureprefmanager.SecurePrefManager;
import com.sap.handler.GenWSHandler;
import com.sap.handler.IWSCallHandler;
import com.sap.handler.ResponseStatus;
import com.sap.handler.ServerComHandler;
import com.sap.school.PojoClass.StudentInfoPojoClass;
import com.sap.utils.AppConstants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class StudentScreenActivity extends BaseActivity implements View.OnClickListener{
    RecyclerView studentRecyclerView;
    String className,classId,sectionId;
    @BindView(R.id.searchEditText) EditText searchEditText;
    RelativeLayout backButton;
    ArrayList<StudentInfoPojoClass>studentInfoPojoClassArrayList;
    ArrayList<StudentInfoPojoClass>mArrayFromJSON;
    StudentInfoRecyclerViewAdapter studentInfoRecyclerViewAdapter;
    LinearLayout selectClassButton,selectSectionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_screen);
        initView();
        ButterKnife.bind(this);
        setListner();
        StudentInfo();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            className= getIntent().getStringExtra("className");
            classId= getIntent().getStringExtra("classId");
            sectionId= getIntent().getStringExtra("sectionId");
        }
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
        getAllStudentsDetails(user_id,roll_id);
    }
    private void StudentInfo() {
//        studentInfoPojoClassArrayList.add(new StudentInfoPojoClass(R.drawable.student1,"Rupankar Das","Class - V"));
//        studentInfoPojoClassArrayList.add(new StudentInfoPojoClass(R.drawable.student2,"Arpita Ghosh","Class - V"));
//        studentInfoPojoClassArrayList.add(new StudentInfoPojoClass(R.drawable.student3,"Rudrajit Guha","Class - V"));
//        studentInfoPojoClassArrayList.add(new StudentInfoPojoClass(R.drawable.student4,"Nivedita Roy","Class - V"));
//        studentInfoPojoClassArrayList.add(new StudentInfoPojoClass(R.drawable.student6,"Sumit Kar","Class - V"));
        studentInfoRecyclerViewAdapter = new StudentInfoRecyclerViewAdapter(getApplication(), studentInfoPojoClassArrayList);
        studentRecyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 1));
        studentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        studentInfoRecyclerViewAdapter.notifyDataSetChanged();
        studentRecyclerView.setAdapter(studentInfoRecyclerViewAdapter);
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
        if (searchText.length() > 0) {
            final ArrayList mArrayList = new ArrayList<>();
        for (StudentInfoPojoClass d : mArrayFromJSON) {
            if (searchText!= null) {
                if (d.getName().contains(searchText)||d.getClassInfo().contains(searchText)) {
                    //something here
                    mArrayList.add(d);
                }
            }
        }
            StudentScreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dismissProgressUI();
                    update(mArrayList);

                }
            });
        }
        else{
            StudentScreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dismissProgressUI();
                    update(mArrayFromJSON);

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
    private void updateData(ArrayList array) {

//        studentInfoRecyclerViewAdapter = new StudentInfoRecyclerViewAdapter(getApplication(), array);
//        studentRecyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 1));
//        studentRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        studentRecyclerView.setAdapter(studentInfoRecyclerViewAdapter);
    }
        private void setListner() {
        backButton.setOnClickListener(this);
        selectClassButton.setOnClickListener(this);
        selectSectionButton.setOnClickListener(this);
    }

    private void initView() {
        studentRecyclerView=(RecyclerView)findViewById(R.id.studentRecyclerView);
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        studentInfoPojoClassArrayList=new ArrayList<>();
        mArrayFromJSON=new ArrayList<>();
        selectClassButton=(LinearLayout)findViewById(R.id.selectClassButton);
        selectSectionButton=(LinearLayout)findViewById(R.id.selectSectionButton);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backButton:
                finish();
                break;
            case R.id.selectClassButton:
                SelectSection();
                break;
            case R.id.selectSectionButton:
                SelectClass();
                break;
        }

    }

    public void update(ArrayList<StudentInfoPojoClass> data) {
        studentInfoPojoClassArrayList.clear();
        studentInfoPojoClassArrayList.addAll(data);
        studentRecyclerView.removeAllViews();
         updateData(studentInfoPojoClassArrayList);
        studentRecyclerView.invalidate();
    }


    //sendPushNotify
    private void getAllStudents(String text)
    {
        showProgressUI(AppConstants.Loading);
        String wsLink = AppConstants.WS_StudentsDetails;
        //web method call
        ServerComHandler.getInstance().wsCallByGet(wsLink, new IWSCallHandler() {
            @Override
            public void responseStatus(final int status, final Object data) {
                if (status == 200) {
                    // List<NotificationItem> arrTmp = new ArrayList<>();
                    try{
                        ResponseStatus responseStatus = new ResponseStatus((String)data);
                        if (responseStatus.isSuccess()) {
                            dismissProgressUI();
                            mArrayFromJSON=new ArrayList<>();
                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("student_list");
                            int count = jsonArray.length();
                            for (int i = 0; i < count; i++) {
                                JSONObject jsonObjItm = jsonArray.getJSONObject(i);
                                Log.d("Json is ","jsonObjItm is"+jsonObjItm);
                                mArrayFromJSON.add(new StudentInfoPojoClass(R.drawable.student1,jsonObjItm.getString("studentname"),jsonObjItm.getString("curclass")/*jsonObjItm.getString("curclass"*/));
                            }
                            StudentScreenActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissProgressUI();
                                update(mArrayFromJSON);

                                }
                            });
                        }
                    }catch (Exception ex){ex.printStackTrace();}


                }else{
                    Log.d("Webservice","failed");
                    dismissProgressUI();
                }
            }
        });
    }
    private void getAllStudentsDetails(String user_id, String role_id)
    {
        showProgressUI(AppConstants.Loading);
        String wsLink = AppConstants.BaseURL+"StudentList";
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
                    try{// salman
                        ResponseStatus responseStatus = new ResponseStatus((String)data);
                        if (responseStatus.isSuccess()) {
                            dismissProgressUI();
                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("result");
                            int count = jsonArray.length();
                            mArrayFromJSON=new ArrayList<>();

                            for (int i = 0; i < count; i++) {
                                JSONObject jsonObjItm = jsonArray.getJSONObject(i);
                                Log.d("Json is ","jsonObjItm is"+jsonObjItm);
                                mArrayFromJSON.add(new StudentInfoPojoClass(R.drawable.student1,jsonObjItm.getString("name"),className/*jsonObjItm.getString("curclass"*/));
                            }
                            StudentScreenActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissProgressUI();
                                    update(mArrayFromJSON);

                                }
                            });
                        }
                        else {
                            ToastUtils.showShort(responseStatus.response_message);
                            dismissProgressUI();
                        }
                        StudentScreenActivity.this.runOnUiThread(new Runnable() {
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

    //getRequestMyBooking
    private RequestBody getRequestNotifications(String text) {

        //String msg = User.getInstance(getCurrContext()).getFullName() +": "+ text;

        FormBody.Builder formBuilder = new FormBody.Builder();
        try {
            formBuilder.add("action", "chat_push_notify");
            //formBuilder.add("sender_id", qbChatDialog.getUserId().toString());
            //formBuilder.add("recipient_id", qbChatDialog.getRecipientId().toString());
            formBuilder.add("title", "GoLuxury X");
           // formBuilder.add("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

       // System.out.println("getRequestNotifications "+ qbChatDialog.getUserId().toString() + ": " + qbChatDialog.getRecipientId().toString());

        return formBuilder.build();
    }
    private void SelectClass() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(StudentScreenActivity.this);
        LayoutInflater inflater = LayoutInflater.from(StudentScreenActivity.this);
        final View convertView = (View) inflater.inflate(R.layout.custom_popup_select_class_page_item, null);
        //Button goToDashBoardButton,makeOtherPlanButton;
        alertDialog.setView(convertView);
        final AlertDialog ad = alertDialog.show();
        //goToDashBoardButton=(Button)convertView.findViewById(R.id.goToDashBoardButton);
       // makeOtherPlanButton=(Button)convertView.findViewById(R.id.makeOtherPlanButton);
//        goToDashBoardButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //finish();
//                startActivity(new Intent(getApplication(),HomeScreenActivity.class));
//                finish();
//
//            }
//        });
    }
    private void SelectSection() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(StudentScreenActivity.this);
        LayoutInflater inflater = LayoutInflater.from(StudentScreenActivity.this);
        final View convertView = (View) inflater.inflate(R.layout.custom_popup_select_section_page_item, null);
        //Button goToDashBoardButton,makeOtherPlanButton;
        alertDialog.setView(convertView);
        final AlertDialog ad = alertDialog.show();
        //goToDashBoardButton=(Button)convertView.findViewById(R.id.goToDashBoardButton);
        // makeOtherPlanButton=(Button)convertView.findViewById(R.id.makeOtherPlanButton);
//        goToDashBoardButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //finish();
//                startActivity(new Intent(getApplication(),HomeScreenActivity.class));
//                finish();
//
//            }
//        });
    }

    private class StudentInfoRecyclerViewAdapter extends RecyclerView.Adapter<StudentInfoRecyclerViewAdapter.MyViewHolderClass>{
        Context context;ArrayList<StudentInfoPojoClass> studentInfoPojoClassArrayList;
        public StudentInfoRecyclerViewAdapter(Context context, ArrayList<StudentInfoPojoClass> studentInfoPojoClassArrayList) {
            this.context=context;this.studentInfoPojoClassArrayList=studentInfoPojoClassArrayList;
        }
        @NonNull
        @Override
        public StudentInfoRecyclerViewAdapter.MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_student_info_page_item, parent, false);
            return new StudentInfoRecyclerViewAdapter.MyViewHolderClass(itemView);
        }
        @Override
        public void onBindViewHolder(@NonNull StudentInfoRecyclerViewAdapter.MyViewHolderClass myViewHolderClass, int i) {
            myViewHolderClass.nameTextView.setText(studentInfoPojoClassArrayList.get(i).getName());
            myViewHolderClass.classTextView.setText("Class - "+studentInfoPojoClassArrayList.get(i).getClassInfo());
            Picasso.with(getApplication()).load(R.drawable.avatar_student).into(myViewHolderClass.cirleImageView);
        }
        @Override
        public int getItemCount() { return studentInfoPojoClassArrayList.size(); }
        public class MyViewHolderClass extends RecyclerView.ViewHolder {
            CircleImageView cirleImageView;
            TextView nameTextView,classTextView;
            public MyViewHolderClass(@NonNull View itemView) {
                super(itemView);
                cirleImageView=(CircleImageView)itemView.findViewById(R.id.cirleImageView);
                nameTextView=(TextView)itemView.findViewById(R.id.nameTextView);
                classTextView=(TextView)itemView.findViewById(R.id.classTextView);

            }
        }
    }
}
