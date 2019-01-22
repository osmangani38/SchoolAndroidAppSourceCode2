package com.sap.school;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.sap.handler.IWSCallHandler;
import com.sap.handler.ResponseStatus;
import com.sap.handler.ServerComHandler;
import com.sap.school.Fragment.MyDatePickerFragment;
import com.sap.school.PojoClass.CreatePlanPojoClass;
import com.sap.school.PojoClass.PlanSectionPOJO;
import com.sap.school.PojoClass.PlanSubjectPOJO;
import com.sap.school.PojoClass.SelectPojoClass;
import com.sap.utils.AppConstants;
import com.sap.utils.JSONSharedPreferences;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassPlanActivity extends BaseActivity implements View.OnClickListener{

    RelativeLayout backButton,makePlanButton;
    EditText edtSelectDate, edtSelectClass, edtSelectSection, edtSelectSubject;
    ArrayList<CreatePlanPojoClass> mArrayList;
    ArrayList<PlanSectionPOJO> planSectionPOJOS;
    ArrayList<PlanSubjectPOJO> planSubjectPOJOS;
    JSONArray jsonArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
        initView();
        setListner();
        String user_id = SPUtils.getInstance().getString("user_id");
        String roll_id;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ClassPlanActivity.this);
        roll_id = sharedPref.getString("roll_id", null); // getting String
        if (StringUtils.isEmpty(roll_id)){
            roll_id = "2";
        }
        getClassAndSection(user_id,roll_id);
    }
    private void setListner() {
        backButton.setOnClickListener(this);
        makePlanButton.setOnClickListener(this);
    }

    private void initView() {
        edtSelectDate = (EditText) findViewById(R.id.edtSelectDate);
        edtSelectClass = (EditText) findViewById(R.id.edtSelectClass);
        edtSelectSection = (EditText) findViewById(R.id.edtSelectSection);
        edtSelectSubject = (EditText) findViewById(R.id.edtSelectSubject);
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        makePlanButton=(RelativeLayout)findViewById(R.id.makePlanButton);
        mArrayList=new ArrayList<>();
        planSectionPOJOS=new ArrayList<>();
        planSubjectPOJOS=new ArrayList<>();

        try{
            jsonArray = JSONSharedPreferences.loadJSONArray(ClassPlanActivity.this, "teacherJSON", "classPlanList");
            if(jsonArray.length()>0){
                for(int i = 0; i<jsonArray.length();i++){
                    JSONObject obj=jsonArray.getJSONObject(i);

                    CreatePlanPojoClass createPlanPojoClass = new CreatePlanPojoClass();

                    createPlanPojoClass.setClassid(obj.getString("class_id"));
                    createPlanPojoClass.setClassname(obj.getString("class_name"));
                    createPlanPojoClass.setSection(obj.getJSONArray("section"));
                    createPlanPojoClass.setSubject(obj.getJSONArray("subject"));

                    mArrayList.add(createPlanPojoClass);
                }
            }

        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        edtSelectClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Select Class", mArrayList, edtSelectClass );
            }
        });

        edtSelectSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter<PlanSectionPOJO> adapter= new ArrayAdapter<>(ClassPlanActivity.this, android.
                        R.layout.simple_spinner_dropdown_item ,planSectionPOJOS);
                AlertDialog.Builder builder = new AlertDialog.Builder(ClassPlanActivity.this)
                        .setTitle("Select Section")
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                PlanSectionPOJO obj = planSectionPOJOS.get(which);
                                edtSelectSection.setText("Section "+ obj.getSection_name());

                                dialog.dismiss();
                            }
                        });



                AlertDialog alertdialog = builder.create();
                alertdialog.show();

            }
        });


        edtSelectSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayAdapter<PlanSubjectPOJO> adapter= new ArrayAdapter<>(ClassPlanActivity.this, android.
                        R.layout.simple_spinner_dropdown_item ,planSubjectPOJOS);
                AlertDialog.Builder builder = new AlertDialog.Builder(ClassPlanActivity.this)
                        .setTitle("Select Subject")
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                PlanSubjectPOJO obj = planSubjectPOJOS.get(which);
                                edtSelectSubject.setText(obj.getSubject_name());

                                dialog.dismiss();
                            }
                        });



                AlertDialog alertdialog = builder.create();
                alertdialog.show();

            }
        });

        edtSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

    }

    public void showDatePicker(View v) {
        DialogFragment newFragment = new MyDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
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
                            JSONSharedPreferences.saveJSONArray(ClassPlanActivity.this, "teacherJSON", "classPlanList", jsonArray);
                            /*int count = jsonArray.length();
                            for (int i = 0; i < count; i++) {
                                JSONObject jsonObjItm = jsonArray.getJSONObject(i);
                                Log.d("Json is ","jsonObjItm is"+jsonObjItm);
                                JSONArray jsonArraySection = jsonObjItm.getJSONArray("section");
                                int countSection = jsonArraySection.length();
                                mArrayList.add(new SelectPojoClass(jsonObjItm.getString("class_id"),jsonObjItm.getString("class_name"),"Class","01","A")*//*jsonObjItm.getString("curclass"*//*);
                                SelectPojoClass selectPojoClass = new SelectPojoClass();
                                for (int j = 0; j < countSection; j++) {
                                    JSONObject jsonObjItmSection = jsonArraySection.getJSONObject(j);
                                    mArrayList.add(new SelectPojoClass(jsonObjItm.getString("class_id"),jsonObjItm.getString("class_name"),"Class",jsonObjItmSection.getString("section_id"),jsonObjItmSection.getString("section_name"))*//*jsonObjItm.getString("curclass"*//*);

                                 SelectPojoClass selectPojoClass = new SelectPojoClass();

                                }

                            }*/
                            ClassPlanActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissProgressUI();

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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backButton:
                finish();
                break;
            case R.id.makePlanButton:
                Intent goNext = new Intent(getApplication(), SubjectClassPlanActivity.class);
                goNext.putExtra("type","Teacher");
                startActivity(goNext);
                //startActivity(new Intent(getApplicationContext(),SubjectClassPlanActivity.class));
                break;
        }

    }

    private void showDialog(String title, final ArrayList<CreatePlanPojoClass> createPlanPojoClasses, final EditText view) {
        ArrayAdapter<CreatePlanPojoClass> adapter= new ArrayAdapter<CreatePlanPojoClass>(this,android.
                R.layout.simple_spinner_dropdown_item ,createPlanPojoClasses);
        AlertDialog.Builder builder = new AlertDialog.Builder(ClassPlanActivity.this)
                .setTitle(title)
                .setAdapter(adapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        planSubjectPOJOS.clear(); planSectionPOJOS.clear();
                        edtSelectSection.setText(""); edtSelectSubject.setText("");
                        CreatePlanPojoClass obj = createPlanPojoClasses.get(which);
                        view.setText("Class "+ obj.getClassname());

                        JSONArray sectionJSON = obj.getSection();
                        JSONArray subjectJSON = obj.getSubject();
                        try{

                            if(sectionJSON.length()>0){
                                for(int i = 0; i<sectionJSON.length();i++){
                                    JSONObject jsonObject=sectionJSON.getJSONObject(i);

                                    PlanSectionPOJO planSectionPOJO = new PlanSectionPOJO();

                                    planSectionPOJO.setSection_id(jsonObject.getString("section_id"));
                                    planSectionPOJO.setSection_name(jsonObject.getString("section_name"));


                                    planSectionPOJOS.add(planSectionPOJO);
                                }
                            }

                            if(subjectJSON.length()>0){
                                for(int i = 0; i<subjectJSON.length();i++){
                                    JSONObject jsonObject=subjectJSON.getJSONObject(i);

                                    PlanSubjectPOJO planSubjectPOJO = new PlanSubjectPOJO();

                                    planSubjectPOJO.setSubject_id(jsonObject.getString("subject_id"));
                                    planSubjectPOJO.setSubject_name(jsonObject.getString("subject_name"));


                                    planSubjectPOJOS.add(planSubjectPOJO);
                                }
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }


                        dialog.dismiss();
                    }
                });



        AlertDialog alertdialog = builder.create();
        alertdialog.show();

    }

    /*private class CreatePlanRecyclerViewAdapter extends RecyclerView.Adapter<CreatePlanRecyclerViewAdapter.MyViewHolderClass>{
        Context context;ArrayList<CreatePlanPojoClass> createPlanPojoClassArrayList;
        public CreatePlanRecyclerViewAdapter(Context context, ArrayList<CreatePlanPojoClass> createPlanPojoClassArrayList) {
         this.context=context;this.createPlanPojoClassArrayList=createPlanPojoClassArrayList;
        }

        @NonNull
        @Override
        public CreatePlanRecyclerViewAdapter.MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_create_class, parent, false);
            return new CreatePlanRecyclerViewAdapter.MyViewHolderClass(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CreatePlanRecyclerViewAdapter.MyViewHolderClass myViewHolderClass, int i) {
            myViewHolderClass.titleTextView.setText(createPlanPojoClassArrayList.get(i).getTitle());
            Picasso.with(getApplication()).load(createPlanPojoClassArrayList.get(i).getImage()).into(myViewHolderClass.imageView);
        }
        @Override
        public int getItemCount() {
            return createPlanPojoClassArrayList.size();
        }

        public class MyViewHolderClass extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView titleTextView;
            public MyViewHolderClass(@NonNull View itemView) {
                super(itemView);
                imageView=(ImageView)itemView.findViewById(R.id.imageView);
                titleTextView=(TextView)itemView.findViewById(R.id.titleTextView);
            }
        }
    }*/
}
