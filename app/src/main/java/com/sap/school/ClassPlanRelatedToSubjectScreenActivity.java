package com.sap.school;

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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sap.handler.IWSCallHandler;
import com.sap.handler.ResponseStatus;
import com.sap.handler.ServerComHandler;
import com.sap.school.PojoClass.ClassPlanSubjectPojoClass;
import com.sap.school.PojoClass.StudentInfoPojoClass;
import com.sap.utils.AppConstants;
import com.sap.utils.JSONSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassPlanRelatedToSubjectScreenActivity extends BaseActivity implements View.OnClickListener{
    RecyclerView classPlanRecyclerview;
    RelativeLayout backButton;
    TextView headerText;
    String type;
    ClassPlassSubjectRecyclerViewAdapter classPlassSubjectRecyclerViewAdapter;
    ArrayList<ClassPlanSubjectPojoClass>classPlanSubjectPojoClassArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_plan_related_to_subject_screen);
        initView();
        setListenr();
        SubjectInfo();
        String user_id = SPUtils.getInstance().getString("user_id");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ClassPlanRelatedToSubjectScreenActivity.this);
        String roll_id = sharedPref.getString("roll_id", null); // getting String
        if (roll_id.equals("2")) {
            getClassAndSection(user_id, roll_id);
        }
    }

    private void SubjectInfo() {
        classPlanSubjectPojoClassArrayList.add(new ClassPlanSubjectPojoClass("01","Bengali"));
        classPlanSubjectPojoClassArrayList.add(new ClassPlanSubjectPojoClass("01","English"));
        classPlanSubjectPojoClassArrayList.add(new ClassPlanSubjectPojoClass("01","Geogrphy"));
        classPlanSubjectPojoClassArrayList.add(new ClassPlanSubjectPojoClass("01","Life Science"));
        classPlanSubjectPojoClassArrayList.add(new ClassPlanSubjectPojoClass("01","Physices"));
        classPlassSubjectRecyclerViewAdapter = new ClassPlassSubjectRecyclerViewAdapter(getApplication(), classPlanSubjectPojoClassArrayList);
        classPlanRecyclerview.setLayoutManager(new GridLayoutManager(getApplication(), 1));
        classPlanRecyclerview.setItemAnimator(new DefaultItemAnimator());
        classPlanRecyclerview.setAdapter(classPlassSubjectRecyclerViewAdapter);


    }
    private void getClassAndSection(String user_id, String role_id)
    {
        showProgressUI(AppConstants.Loading);
        String wsLink = AppConstants.BaseURL+"TeacherMasterList";
        //web method call
        JSONObject loginJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            loginJson.put("user_id", user_id);
            loginJson.put("role_id", role_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(loginJson);
        JSONObject ws_dataObj = new JSONObject();
        try {
            ws_dataObj.put("WS_DATA", jsonArray);
            ws_dataObj.put("WS_CODE", "120");
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
                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("result");
                            JSONSharedPreferences.saveJSONArray(ClassPlanRelatedToSubjectScreenActivity.this, "teacherJSON", "classPlanList", jsonArray);
                            int count = jsonArray.length();

                            for (int i = 0; i < count; i++) {
                                JSONObject jsonObjItm = jsonArray.getJSONObject(i);
                                //JSONObject subjects = jsonObjItm.getJSONObject("subject");
                                Log.d("Json is ", "jsonObjItm is" + jsonObjItm);
                                JSONArray jsonArraySection = jsonObjItm.getJSONArray("subject");
                                int countSection = jsonArraySection.length();
//                                mArrayList.add(new SelectPojoClass(jsonObjItm.getString("class_id"),jsonObjItm.getString("class_name"),"Class","01","A")*//*jsonObjItm.getString("curclass"*//*);
//                                SelectPojoClass selectPojoClass = new SelectPojoClass();
                                for (int j = 0; j < countSection; j++) {
                                    JSONObject jsonObjItmSubjects = jsonArraySection.getJSONObject(j);

                                    mArrayList.add(new ClassPlanSubjectPojoClass(jsonObjItmSubjects.getString("subject_id"),jsonObjItmSubjects.getString("subject_name")));//*jsonObjItm.getString("curclass"*//*);
//
//                                 SelectPojoClass selectPojoClass = new SelectPojoClass();
//
//                                }

                                }
                            }
                            ClassPlanRelatedToSubjectScreenActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    update(mArrayList);
                                    dismissProgressUI();

                                }
                            });
                        }
                        else {
                            ToastUtils.showShort(responseStatus.response_message);
                            dismissProgressUI();
                        }
                        ClassPlanRelatedToSubjectScreenActivity.this.runOnUiThread(new Runnable() {
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
    public void update(ArrayList<ClassPlanSubjectPojoClass> data) {
        classPlanSubjectPojoClassArrayList.clear();
        classPlanSubjectPojoClassArrayList.addAll(data);
        classPlanRecyclerview.removeAllViews();
        classPlanRecyclerview.invalidate();
    }

    private void setListenr() {
        backButton.setOnClickListener(this);
    }

    private void initView() {
        headerText = (TextView) findViewById(R.id.headerText);
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        classPlanRecyclerview=(RecyclerView)findViewById(R.id.classPlanRecyclerview);
        classPlanSubjectPojoClassArrayList=new ArrayList<ClassPlanSubjectPojoClass>();
        Intent intent = getIntent();
        String str = intent.getStringExtra("isHomeTask");
        type = intent.getStringExtra("type");

        if(isNullOrEmpty(str)){

        }
        else {
            if (str.equals("Yes")) {
                headerText.setText("Home Task");
            }
        }

    }
    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
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

    private class ClassPlassSubjectRecyclerViewAdapter extends RecyclerView.Adapter<ClassPlassSubjectRecyclerViewAdapter.MyViewHolderClass>{
        Context context;ArrayList<ClassPlanSubjectPojoClass> classPlanSubjectPojoClassArrayList;
        public ClassPlassSubjectRecyclerViewAdapter(Context context, ArrayList<ClassPlanSubjectPojoClass> classPlanSubjectPojoClassArrayList) {
            this.context=context;this.classPlanSubjectPojoClassArrayList=classPlanSubjectPojoClassArrayList;
        }

        @NonNull
        @Override
        public MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_plan_related_to_subject_class_page_item, parent, false);
            return new MyViewHolderClass(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderClass myViewHolderClass, final int i) {
            myViewHolderClass.titleTextView.setText(classPlanSubjectPojoClassArrayList.get(i).getTitle());
            myViewHolderClass.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if(i==3)
                    {
                        Intent intent = getIntent();
                        String str = intent.getStringExtra("isHomeTask");

                        String type = intent.getStringExtra("type");
                        if (type.equals("ClassLog")) {
                            String class_id =  intent.getStringExtra("class_id");
                            String section_id =  intent.getStringExtra("section_id");
                            Intent j = new Intent(getApplication(), SubjectPlanDetailsActivity.class);
                            j.putExtra("type","ClassLog");
                            j.putExtra("subject_id",classPlanSubjectPojoClassArrayList.get(i).getId());
                            j.putExtra("class_id",class_id);
                            j.putExtra("section_id",section_id);
                            startActivity(j);

                        }
                        else
                        {
                        Boolean goToClassPlan = true;
                        if (isNullOrEmpty(str)) {

                        } else {
                            if (str.equals("Yes")) {
                                goToClassPlan = false;
                                headerText.setText("Home Task");
                            }
                        }
                        if (goToClassPlan) {
                            Intent goNext = new Intent(getApplication(), SubjectPlanDetailsActivity.class);
                            goNext.putExtra("type", type);
                            startActivity(goNext);
                        }
                    }
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return classPlanSubjectPojoClassArrayList.size();
        }

        public class MyViewHolderClass extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView titleTextView;
            LinearLayout linearLayout;
            public MyViewHolderClass(@NonNull View itemView) {
                super(itemView);
                titleTextView=(TextView)itemView.findViewById(R.id.titleTextView);
                linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);
            }
        }
    }
}
