package com.sap.school;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sap.handler.GenWSHandler;
import com.sap.handler.IWSCallHandler;
import com.sap.handler.ResponseStatus;
import com.sap.handler.ServerComHandler;
import com.sap.school.PojoClass.StudentInfoPojoClass;
import com.sap.utils.AppConstants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class StudentScreenActivity extends BaseActivity implements View.OnClickListener{
    RecyclerView studentRecyclerView;
    RelativeLayout backButton;
    ArrayList<StudentInfoPojoClass>studentInfoPojoClassArrayList;
    StudentInfoRecyclerViewAdapter studentInfoRecyclerViewAdapter;
    LinearLayout selectClassButton,selectSectionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_screen);
        initView();
        setListner();
        StudentInfo();
        //getAllStudents("");
    }
    private void StudentInfo() {
        studentInfoPojoClassArrayList.add(new StudentInfoPojoClass(R.drawable.student1,"Rupankar Das","Class - V"));
        studentInfoPojoClassArrayList.add(new StudentInfoPojoClass(R.drawable.student2,"Arpita Ghosh","Class - V"));
        studentInfoPojoClassArrayList.add(new StudentInfoPojoClass(R.drawable.student3,"Rudrajit Guha","Class - V"));
        studentInfoPojoClassArrayList.add(new StudentInfoPojoClass(R.drawable.student4,"Nivedita Roy","Class - V"));
        studentInfoPojoClassArrayList.add(new StudentInfoPojoClass(R.drawable.student6,"Sumit Kar","Class - V"));
        studentInfoRecyclerViewAdapter = new StudentInfoRecyclerViewAdapter(getApplication(), studentInfoPojoClassArrayList);
        studentRecyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 1));
        studentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        studentInfoRecyclerViewAdapter.notifyDataSetChanged();
        studentRecyclerView.setAdapter(studentInfoRecyclerViewAdapter);
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
        // updateData(studentInfoPojoClassArrayList);
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
                            final ArrayList mArrayList=new ArrayList<>();

                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("student_list");
                            int count = jsonArray.length();
                            for (int i = 0; i < count; i++) {
                                JSONObject jsonObjItm = jsonArray.getJSONObject(i);
                                Log.d("Json is ","jsonObjItm is"+jsonObjItm);
                                mArrayList.add(new StudentInfoPojoClass(R.drawable.student1,jsonObjItm.getString("studentname"),jsonObjItm.getString("curclass")/*jsonObjItm.getString("curclass"*/));
                            }
                            StudentScreenActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissProgressUI();
                                update(mArrayList);

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
            myViewHolderClass.classTextView.setText(studentInfoPojoClassArrayList.get(i).getClassInfo());
            Picasso.with(getApplication()).load(studentInfoPojoClassArrayList.get(i).getTiltle()).into(myViewHolderClass.cirleImageView);
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
