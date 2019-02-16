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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sap.handler.IWSCallHandler;
import com.sap.handler.ResponseStatus;
import com.sap.handler.ServerComHandler;
import com.sap.school.PojoClass.SelectPojoClass;
import com.sap.school.PojoClass.StudentInfoPojoClass;
import com.sap.utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectClassActivity extends BaseActivity implements View.OnClickListener{
    RecyclerView selectInfoRecyclerView;
    SelectInfoReyclerViewAdapter selectInfoReyclerViewAdapter;
    RelativeLayout backButton;
    TextView textViewHeading;
    ArrayList<SelectPojoClass>selectPojoClassArrayList;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_class);
        initView();
        setListner();
        SelectClassInfo();
        String user_id = SPUtils.getInstance().getString("user_id");
        String roll_id;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SelectClassActivity.this);
        roll_id = sharedPref.getString("roll_id", null); // getting String
        if (StringUtils.isEmpty(roll_id)){
            roll_id = "2";
        }

        getClassAndSection(user_id,roll_id);
    }

    private void SelectClassInfo() {
        selectInfoReyclerViewAdapter = new SelectInfoReyclerViewAdapter(getApplication(), selectPojoClassArrayList);
        selectInfoRecyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 3));
        selectInfoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        selectInfoRecyclerView.setAdapter(selectInfoReyclerViewAdapter);

    }
    public void update(ArrayList<SelectPojoClass> data) {
        selectPojoClassArrayList.clear();
        selectPojoClassArrayList.addAll(data);
        selectInfoRecyclerView.removeAllViews();
        selectInfoRecyclerView.invalidate();
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
                            int count = jsonArray.length();
                            for (int i = 0; i < count; i++) {
                                JSONObject jsonObjItm = jsonArray.getJSONObject(i);
                                Log.d("Json is ","jsonObjItm is"+jsonObjItm);
                                JSONArray jsonArraySection = jsonObjItm.getJSONArray("section");
                                int countSection = jsonArraySection.length();
                                //mArrayList.add(new SelectPojoClass(jsonObjItm.getString("class_id"),jsonObjItm.getString("class_name"),"Class","01","A")/*jsonObjItm.getString("curclass"*/);
//                            SelectPojoClass selectPojoClass = new SelectPojoClass();
                                for (int j = 0; j < countSection; j++) {
                                    JSONObject jsonObjItmSection = jsonArraySection.getJSONObject(j);
                                    mArrayList.add(new SelectPojoClass(jsonObjItm.getString("class_id"),jsonObjItm.getString("class_name"),"Class",jsonObjItmSection.getString("section_id"),jsonObjItmSection.getString("section_name"))/*jsonObjItm.getString("curclass"*/);

//                            SelectPojoClass selectPojoClass = new SelectPojoClass();

                                }

                            }
                            SelectClassActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissProgressUI();
                                    update(mArrayList);

                                }
                            });
                            SelectClassActivity.this.runOnUiThread(new Runnable() {
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
                        SelectClassActivity.this.runOnUiThread(new Runnable() {
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

    private void setListner() {
        backButton.setOnClickListener(this);
    }

    private void initView() {
        selectInfoRecyclerView=(RecyclerView)findViewById(R.id.selectInfoRecyclerView);
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        selectPojoClassArrayList=new ArrayList<SelectPojoClass>();
        textViewHeading = (TextView)findViewById(R.id.textViewClass);

        type = getIntent().getStringExtra("type");
        if (type.equals("Student")){
            textViewHeading.setText("Student");
        }
        else if(type.equals("Attendence")){
            textViewHeading.setText("Attendence");
        }
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
        public void onBindViewHolder(@NonNull SelectInfoReyclerViewAdapter.MyViewHolderClass myViewHolderClass, final int i) {
            myViewHolderClass.classInfoTextView.setText(selectPojoClassArrayList.get(i).getTitle());
            final String classAndSection = selectPojoClassArrayList.get(i).getClassInfo()+" ("+selectPojoClassArrayList.get(i).getSectionName()+ ")";
            myViewHolderClass.classTextView.setText(classAndSection);

            myViewHolderClass.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(type.equals("ClassLog"))
                    {
                       // Intent j = new Intent(SelectClassActivity.this, SubjectPlanDetailsActivity.class);
                        Intent j = new Intent(SelectClassActivity.this, ViewMoreClassPlan.class);
                        j.putExtra("type","ClassLog");
                        j.putExtra("class_id",selectPojoClassArrayList.get(i).getId());
                        j.putExtra("section_id",selectPojoClassArrayList.get(i).getSectionId());

//                        j.putExtra("classId",selectPojoClassArrayList.get(i).getId());
//                        j.putExtra("sectionId",selectPojoClassArrayList.get(i).getSectionId());
                        startActivity(j);

                    }
                    if(type.equals("classPlan"))
                    {
                        Intent j = new Intent(SelectClassActivity.this, ViewMoreClassPlan.class);
//                        j.putExtra("className",classAndSection);
                        j.putExtra("classId",selectPojoClassArrayList.get(i).getId());
                        j.putExtra("sectionId",selectPojoClassArrayList.get(i).getSectionId());
                        startActivity(j);

                    }
                    else if(type.equals("Student"))
                    {
                        //if(i==0)
                        {
                            Intent j = new Intent(SelectClassActivity.this, StudentScreenActivity.class);
                            j.putExtra("className",classAndSection);
                            j.putExtra("classId",selectPojoClassArrayList.get(i).getId());
                            j.putExtra("sectionId",selectPojoClassArrayList.get(i).getSectionId());
                            startActivity(j);
                        }
                    }else if(type.equals("Attendence"))
                    {
                       // if(i==0)
                        {
                            Intent k = new Intent(SelectClassActivity.this, AttendenceScreenActivity.class);
                            k.putExtra("className",classAndSection);
                            k.putExtra("classId",selectPojoClassArrayList.get(i).getId());
                            k.putExtra("sectionId",selectPojoClassArrayList.get(i).getSectionId());
                            startActivity(k);
                        }
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
