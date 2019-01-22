package com.sap.school;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sap.handler.IWSCallHandler;
import com.sap.handler.ResponseStatus;
import com.sap.handler.ServerComHandler;
import com.sap.school.PojoClass.AttendancePogoClass;
import com.sap.school.PojoClass.StudentInfoPojoClass;
import com.sap.utils.AppConstants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AttendenceScreenActivity extends BaseActivity implements View.OnClickListener {
    RecyclerView attendenceRecyclerView;
    String className,classId,sectionId;
    AttendenceRecyclerViewAdapter attendenceRecyclerViewAdapter;
    ArrayList<AttendancePogoClass> attendencePojoClasses;
    RelativeLayout backButton, attendenceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_screen);
        initView();
        setListner();
        studentInfo();
        String user_id = SPUtils.getInstance().getString("user_id");
        String roll_id;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( AttendenceScreenActivity.this);
        roll_id = sharedPref.getString("roll_id", null); // getting String
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
                                mArrayList.add(new AttendancePogoClass(jsonObjItm.getString("id"), jsonObjItm.getString("name"), R.drawable.student1, R.drawable.cross_btn)/*jsonObjItm.getString("curclass"*/);
                            }
                            AttendenceScreenActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissProgressUI();
                                    update(mArrayList);

                                }
                            });
                            AttendenceScreenActivity.this.runOnUiThread(new Runnable() {
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
    public void update(ArrayList<AttendancePogoClass> data) {
        attendencePojoClasses.clear();
        attendencePojoClasses.addAll(data);
        attendenceRecyclerView.removeAllViews();
        attendenceRecyclerView.invalidate();
    }
    private void studentInfo() {
        attendencePojoClasses.add(new AttendancePogoClass("01", "Rupankar Das", R.drawable.student1, R.drawable.cross_btn));
        attendencePojoClasses.add(new AttendancePogoClass("02", "Arpita Ghosh", R.drawable.student2, R.drawable.cross_btn));
        attendencePojoClasses.add(new AttendancePogoClass("03", "Rudrajit Guha", R.drawable.student3, R.drawable.cross_btn));
        attendencePojoClasses.add(new AttendancePogoClass("04", "Nivedita Roy", R.drawable.student4, R.drawable.cross_btn));
        attendencePojoClasses.add(new AttendancePogoClass("05", "Sumit Kar", R.drawable.student6, R.drawable.cross_btn));
        attendencePojoClasses.add(new AttendancePogoClass("06", "Arijit Ghosh", R.drawable.student3, R.drawable.cross_btn));
        attendenceRecyclerViewAdapter = new AttendenceRecyclerViewAdapter(getApplication(), attendencePojoClasses);
        attendenceRecyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 1));
        attendenceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        attendenceRecyclerView.setAdapter(attendenceRecyclerViewAdapter);
    }

    private void setListner() {
        backButton.setOnClickListener(this);
        attendenceButton.setOnClickListener(this);
    }

    private void initView() {
        attendenceRecyclerView = (RecyclerView) findViewById(R.id.attendenceRecyclerView);
        backButton = (RelativeLayout) findViewById(R.id.backButton);
        attendenceButton = (RelativeLayout) findViewById(R.id.attendenceButton);
        attendencePojoClasses = new ArrayList<AttendancePogoClass>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.attendenceButton:
                startActivity(new Intent(getApplication(), MarkAttendenceActivity.class));
                break;
        }

    }

    private class AttendenceRecyclerViewAdapter extends RecyclerView.Adapter<AttendenceRecyclerViewAdapter.MyViewHolderClass> {
        Context context;
        ArrayList<AttendancePogoClass> attendencePojoClasses;

        public AttendenceRecyclerViewAdapter(Context context, ArrayList<AttendancePogoClass> attendencePojoClasses) {
            this.context = context;
            this.attendencePojoClasses = attendencePojoClasses;
        }

        @NonNull
        @Override
        public AttendenceRecyclerViewAdapter.MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_attendence_page_item, parent, false);
            return new AttendenceRecyclerViewAdapter.MyViewHolderClass(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AttendenceRecyclerViewAdapter.MyViewHolderClass myViewHolderClass, int i) {
            myViewHolderClass.rollNoTextView.setText(attendencePojoClasses.get(i).getId());
            myViewHolderClass.nameTextView.setText(attendencePojoClasses.get(i).getName());
            myViewHolderClass.classTextView.setText("Class - "+className);

            myViewHolderClass.imageView.setTag(i);
            // myViewHolderClass.classTextView.setText(attendencePojoClasses.get(i).getId());
            Picasso.with(getApplication()).load(attendencePojoClasses.get(i).getStudent_image()).into(myViewHolderClass.cirleImageView);
            Picasso.with(getApplication()).load(attendencePojoClasses.get(i).getAttendence_info()).into(myViewHolderClass.imageView);
        }

        @Override
        public int getItemCount() {
            return attendencePojoClasses.size();
        }

        public class MyViewHolderClass extends RecyclerView.ViewHolder {
            TextView rollNoTextView, nameTextView, classTextView;
            CircleImageView cirleImageView;
            ImageView imageView;

            public MyViewHolderClass(@NonNull View itemView) {
                super(itemView);
                rollNoTextView = (TextView) itemView.findViewById(R.id.rollNoTextView);
                nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
                classTextView = (TextView) itemView.findViewById(R.id.classTextView);
                cirleImageView = (CircleImageView) itemView.findViewById(R.id.cirleImageView);
                imageView = (ImageView) itemView.findViewById(R.id.imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //v.getId() will give you the image id
                       // ToastUtils.showShort("clicked on" + v.getTag());
                        if (v.isSelected()){
                            v.setSelected(false);
                            imageView.setImageResource(R.drawable.cross_btn);
                        }
                        else {
                            v.setSelected(true);
                            imageView.setImageResource(R.drawable.tick_ic);
                        }
                    }
                });
            }
        }
    }
}
