package com.sap.school;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
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
import com.prashantsolanki.secureprefmanager.SecurePrefManager;
import com.sap.handler.IWSCallHandler;
import com.sap.handler.ResponseStatus;
import com.sap.handler.ServerComHandler;
import com.sap.school.Fragment.RoutinFragment;
import com.sap.school.PojoClass.ClassOverViewPojoClass;
import com.sap.school.PojoClass.ClassRoutinePojoClass;
import com.sap.school.PojoClass.InfoPojoClass;
import com.sap.utils.AppConstants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TeacherDashBoardActivity extends BaseActivity implements View.OnClickListener{
    RecyclerView infoRecyclerView,classOverviewRecyclerView,classRoutineRecyclerView;
    InfoRecyclerViewAdapter infoRecyclerViewAdapter;
    TextView schoolName;
    ClassOverviewRecyclerviewAdapter classOverviewRecyclerviewAdapter;
    ClassRoutineRecyclerViewAdapter classRoutineRecyclerViewAdapter;
    RelativeLayout student2TV;
    ArrayList<InfoPojoClass>infoPojoClassArrayList;
    ArrayList<ClassOverViewPojoClass>classOverViewPojoClassArrayList;
    ArrayList<ClassRoutinePojoClass>classRoutinePojoClassArrayList;
    TextView fullName;
    private DrawerLayout drawer;
    RelativeLayout toggleButton;
    RelativeLayout dahboardRelativeLayout,studentNavigationButton,attendenceNavigationButton,healthInfoNavigationButton,myProfile,gameAndSportsNavigationButton,eventsNavigationButton,coCurricularNavigationButton,classPlanNavigationButton,markSheetEntryNavigationButton,classLogNavigationButton,questionBankNavigationButton,reportIncidentNavigationButton,leaveOfAbsenseNavigationButton,schemesNavigationButton,feedbackNavigationButton,logOutNavigationButton,ralativeLayoutStudent,relativeAcademic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        initView();
        setListener();
        getDayId();
        Information();
        ClassOverView();
        ClassRoutinMethod();
    }

    private void setListener() {
        toggleButton.setOnClickListener(this);
        attendenceNavigationButton.setOnClickListener(this);
        studentNavigationButton.setOnClickListener(this);
        dahboardRelativeLayout.setOnClickListener(this);
        healthInfoNavigationButton.setOnClickListener(this);
        myProfile.setOnClickListener(this);
        gameAndSportsNavigationButton.setOnClickListener(this);
        eventsNavigationButton.setOnClickListener(this);
        coCurricularNavigationButton.setOnClickListener(this);
        classPlanNavigationButton.setOnClickListener(this);
        markSheetEntryNavigationButton.setOnClickListener(this);
        classLogNavigationButton.setOnClickListener(this);
        questionBankNavigationButton.setOnClickListener(this);
        reportIncidentNavigationButton.setOnClickListener(this);
        leaveOfAbsenseNavigationButton.setOnClickListener(this);
        schemesNavigationButton.setOnClickListener(this);
        feedbackNavigationButton.setOnClickListener(this);
        logOutNavigationButton.setOnClickListener(this);
    }

    private void ClassRoutinMethod() {
//        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("MatheMatices","Class - V | Section - B","11am - 11:45am"));
//        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("HINDI","Class - V | Section - B","11am - 11:45am"));
//        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("ENGLISH","Class - V | Section - B","11am - 11:45am"));
//        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("HINDI","Class - V | Section - B","11am - 11:45am"));
       classRoutineRecyclerViewAdapter=new ClassRoutineRecyclerViewAdapter(getApplicationContext(),classRoutinePojoClassArrayList);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(TeacherDashBoardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        classRoutineRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        classRoutineRecyclerView.setAdapter(classRoutineRecyclerViewAdapter);
    }

    private void ClassOverView() {
        classOverViewPojoClassArrayList.add(new ClassOverViewPojoClass("30%","CLASS -V","ENGLISH"));
        classOverViewPojoClassArrayList.add(new ClassOverViewPojoClass("70%","CLASS -VI","HINDI"));
        classOverViewPojoClassArrayList.add(new ClassOverViewPojoClass("80%","CLASS -VII","ENGLISH"));
        classOverViewPojoClassArrayList.add(new ClassOverViewPojoClass("70%","CLASS -VI","HINDI"));
        classOverviewRecyclerviewAdapter=new ClassOverviewRecyclerviewAdapter(getApplicationContext(),classOverViewPojoClassArrayList);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(TeacherDashBoardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        classOverviewRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        classOverviewRecyclerView.setAdapter(classOverviewRecyclerviewAdapter);
    }

    private void Information() {
        infoPojoClassArrayList.add(new InfoPojoClass("01",R.drawable.student_ic,getResources().getString(R.string.studentText)));
        infoPojoClassArrayList.add(new InfoPojoClass("02",R.drawable.attendance_ic_dashboard,getResources().getString(R.string.attendenceText)));
        infoPojoClassArrayList.add(new InfoPojoClass("03",R.drawable.create_plan_ic_dashboard,getResources().getString(R.string.classPlanText)));
        infoPojoClassArrayList.add(new InfoPojoClass("04",R.drawable.class_log_dashboard,getResources().getString(R.string.classLogText)));
        infoPojoClassArrayList.add(new InfoPojoClass("05",R.drawable.class_routine,getResources().getString(R.string.classRoutineText)));
       // infoPojoClassArrayList.add(new InfoPojoClass("06",R.drawable.events_ic_dashboard,getResources().getString(R.string.event)));
        infoRecyclerViewAdapter = new InfoRecyclerViewAdapter(getApplication(), infoPojoClassArrayList);
        infoRecyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 3));
        infoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        infoRecyclerView.setAdapter(infoRecyclerViewAdapter);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( TeacherDashBoardActivity.this);
        String userName = SecurePrefManager.with(this)
                .get("username")
                .defaultValue("unknown")
                .go();
        fullName.setText(userName);
    }

    private void initView() {
        infoRecyclerView=(RecyclerView)findViewById(R.id.infoRecyclerView);
        classOverviewRecyclerView=(RecyclerView)findViewById(R.id.classOverviewRecyclerView);
        classRoutineRecyclerView=(RecyclerView)findViewById(R.id.classRoutineRecyclerView);
        infoRecyclerView.setNestedScrollingEnabled(false);
        classOverviewRecyclerView.setNestedScrollingEnabled(false);
        classRoutineRecyclerView.setNestedScrollingEnabled(false);
        infoPojoClassArrayList=new ArrayList<InfoPojoClass>();
        classOverViewPojoClassArrayList=new ArrayList<ClassOverViewPojoClass>();
        classRoutinePojoClassArrayList=new ArrayList<ClassRoutinePojoClass>();
        drawer=(DrawerLayout)findViewById(R.id.drawer);
        toggleButton=(RelativeLayout)findViewById(R.id.toggleButton);

        attendenceNavigationButton=(RelativeLayout)findViewById(R.id.attendenceNavigationButton);
        studentNavigationButton=(RelativeLayout)findViewById(R.id.studentRelativeLayout);
        dahboardRelativeLayout=(RelativeLayout)findViewById(R.id.dashboardRelativeLayout);
        healthInfoNavigationButton=(RelativeLayout)findViewById(R.id.healthInfoNavigationButton);
        healthInfoNavigationButton.setVisibility(View.GONE);
       // ralativeLayoutStudent = (RelativeLayout) findViewById(R.id.student2);
        //ralativeLayoutStudent.setVisibility(View.GONE);

        myProfile=(RelativeLayout)findViewById(R.id.myProfile);
        schoolName = (TextView)findViewById(R.id.schoolName);
        gameAndSportsNavigationButton=(RelativeLayout)findViewById(R.id.gameAndSportsNavigationButton);
        healthInfoNavigationButton.setVisibility(View.GONE);

        eventsNavigationButton=(RelativeLayout)findViewById(R.id.eventsNavigationButton);

        coCurricularNavigationButton=(RelativeLayout)findViewById(R.id.coCurricularNavigationButton);
        coCurricularNavigationButton.setVisibility(View.GONE);

        classPlanNavigationButton=(RelativeLayout)findViewById(R.id.classPlanNavigationButton);
        markSheetEntryNavigationButton=(RelativeLayout)findViewById(R.id.markSheetEntryNavigationButton);
        classLogNavigationButton=(RelativeLayout)findViewById(R.id.classLogNavigationButton);
        questionBankNavigationButton=(RelativeLayout)findViewById(R.id.questionBankNavigationButton);
        reportIncidentNavigationButton=(RelativeLayout)findViewById(R.id.reportIncidentNavigationButton);
        leaveOfAbsenseNavigationButton=(RelativeLayout)findViewById(R.id.leaveOfAbsenseNavigationButton);
        schemesNavigationButton=(RelativeLayout)findViewById(R.id.schemesNavigationButton);
        feedbackNavigationButton=(RelativeLayout)findViewById(R.id.feedbackNavigationButton);
        logOutNavigationButton=(RelativeLayout)findViewById(R.id.logOutNavigationButton);
        //relativeAcademic=(RelativeLayout)findViewById(R.id.relativeAcademic);

        fullName = (TextView) findViewById(R.id.fullName);
        markSheetEntryNavigationButton.setVisibility(View.GONE);
        questionBankNavigationButton.setVisibility(View.GONE);
        reportIncidentNavigationButton.setVisibility(View.GONE);
        leaveOfAbsenseNavigationButton.setVisibility(View.GONE);
        schemesNavigationButton.setVisibility(View.GONE);
        feedbackNavigationButton.setVisibility(View.GONE);
        gameAndSportsNavigationButton.setVisibility(View.GONE);
        eventsNavigationButton.setVisibility(View.GONE);
      //  relativeAcademic.setVisibility(View.GONE)
        classOverviewRecyclerView.setVisibility(View.GONE);
        getClassRoutine();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.toggleButton:
                openDrawer();
                break;
            case R.id.dashboardRelativeLayout:
                Intent gonext2=new Intent(getApplication(),TeacherDashBoardActivity.class);
                startActivity(new Intent(gonext2));
                closeDrawer();
                break;
            case R.id.studentRelativeLayout:
                Intent gonext1=new Intent(getApplication(),SelectClassActivity.class);
                gonext1.putExtra("type","Student");
                startActivity(new Intent(gonext1));
                closeDrawer();
                break;
            case R.id.attendenceNavigationButton:
                Intent gonext=new Intent(getApplication(),SelectClassActivity.class);
                gonext.putExtra("type","Attendence");
                startActivity(new Intent(gonext));
                closeDrawer();
                break;
            case R.id.healthInfoNavigationButton:
                closeDrawer();
                break;
            case R.id.myProfile:
                startActivity(new Intent(getApplicationContext(),MyProfileActivity.class));
                closeDrawer();
                break;
            case R.id.gameAndSportsNavigationButton:
                closeDrawer();
                break;
            case R.id.eventsNavigationButton:
                //startActivity(new Intent(getApplicationContext(),NoticeScreenActivity.class));
                closeDrawer();
                break;
            case R.id.coCurricularNavigationButton:
                closeDrawer();
                break;
            case R.id.classPlanNavigationButton:
              //  startActivity(new Intent(getApplication(),ClassPlanActivity.class));
                startActivity(new Intent(getApplication(),ViewTodaysClassPlan.class));
                closeDrawer();
                break;
                case R.id.markSheetEntryNavigationButton:
                closeDrawer();
                break;
            case R.id.classLogNavigationButton:
                //startActivity(new Intent(getApplication(),ClassPlanActivity.class));
                startActivity(new Intent(getApplication(),ViewTodaysClassPlan.class));
                closeDrawer();
                break;
            case R.id.questionBankNavigationButton:
                closeDrawer();
                break;
            case R.id.reportIncidentNavigationButton:
                closeDrawer();
                break;
            case R.id.leaveOfAbsenseNavigationButton:
                closeDrawer();
                break;
            case R.id.schemesNavigationButton:
                closeDrawer();
                break;
            case R.id.feedbackNavigationButton:
                startActivity(new Intent(getApplicationContext(),FeedBackActivity.class));
                closeDrawer();
                break;
            case R.id.logOutNavigationButton:
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                closeDrawer();
                break;
        }
    }
    private void getClassRoutine()
    {
        String roll_id;
        roll_id = SecurePrefManager.with(this)
                .get("roll_id")
                .defaultValue("unknown")
                .go(); // getting String
        String wsLink = AppConstants.BaseURL+"TeacherRoutine";
        //web method call
        if (roll_id.equals("4")) {
            wsLink = AppConstants.BaseURL+"ClassRoutine";
        }
        String user_id = SecurePrefManager.with(this)
                .get("user_id")
                .defaultValue("unknown")
                .go();
        showProgressUI("Loading...");
        JSONObject loginJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String classId = SPUtils.getInstance().getString("class");
        String sectionId = SPUtils.getInstance().getString("section");
        if (StringUtils.isEmpty(classId)) {
            classId = "6";
        }
        if (StringUtils.isEmpty(sectionId)) {
            sectionId = "2";
        }
        try {
            loginJson.put("user_id", user_id);
            loginJson.put("role_id", roll_id);
            if (roll_id.equals("2")) {
                loginJson.put("class_id", "0");
                loginJson.put("section_id", "0");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(loginJson);
        JSONObject ws_dataObj = new JSONObject();
        try {
            ws_dataObj.put("WS_DATA", jsonArray);
            if (roll_id.equals("4")) {
                ws_dataObj.put("WS_CODE", "230");
            }
            else if (roll_id.equals("2")){

                ws_dataObj.put("WS_CODE", "130");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = ws_dataObj.toString();

        ServerComHandler.getInstance().wsCallJsonBy(wsLink, json, new IWSCallHandler() {
            @Override
            public void responseStatus(final int status, final Object data) {
                if (status == 200) {
                    // List<NotificationItem> arrTmp = new ArrayList<>();
                    try{
                        ResponseStatus responseStatus = new ResponseStatus((String)data);
                        if (responseStatus.isSuccess()) {
                            // dismissProgressUI();
                            final ArrayList mArrayList=new ArrayList<>();
                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("result");
                            int count = jsonArray.length();
                            if (jsonArray !=null) {
                                for (int i = 0; i < count; i++) {
                                    JSONObject jsonObjItm = jsonArray.getJSONObject(i);
                                    String classSection = "Class - " + jsonObjItm.getString("class") + " | " + "Section - " + jsonObjItm.getString("section");
                                    String day = SPUtils.getInstance().getString("day");
                                    if (StringUtils.equalsIgnoreCase(day, jsonObjItm.getString("day_id"))) {
                                        //classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("MatheMatices","Class - V | Section - B","11am - 11:45am"));

                                        mArrayList.add(new ClassRoutinePojoClass(jsonObjItm.getString("subject"), classSection, jsonObjItm.getString("period_time")));
                                    }
                                }

                            }
                            else {
                                ToastUtils.showShort("Data not found");
                            }
                            TeacherDashBoardActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissProgressUI();
                                    update(mArrayList);
                                }
                            });

                        }
                        else {
                            ToastUtils.showShort(responseStatus.response_message);
                            dismissProgressUI();
                        }
                        TeacherDashBoardActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissProgressUI();
                            }
                        });
                    }catch (Exception ex){ex.printStackTrace();}


                }else{
                    // dismissProgressUI();
                }
            }
        });

    }
    private void getDayId(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        if(dayOfTheWeek.equals("Sunday")){
            SPUtils.getInstance().put("day","0");
        }
        else  if(dayOfTheWeek.equals("Monday")){
            SPUtils.getInstance().put("day","1");
        }
        else  if(dayOfTheWeek.equals("Tuesday")){
            SPUtils.getInstance().put("day","2");
        }
        else  if(dayOfTheWeek.equals("Wednesday")){
            SPUtils.getInstance().put("day","3");
        }
        else  if(dayOfTheWeek.equals("Thursday")){
            SPUtils.getInstance().put("day","4");
        }
        else  if(dayOfTheWeek.equals("Friday")){
            SPUtils.getInstance().put("day","5");
        }
        else  if(dayOfTheWeek.equals("Saturday")){
            SPUtils.getInstance().put("day","6");
        }
    }

    public void update(ArrayList<ClassRoutinePojoClass> data) {
        classRoutinePojoClassArrayList.clear();
        classRoutinePojoClassArrayList.addAll(data);
        classRoutineRecyclerView.removeAllViews();
        classRoutineRecyclerView.invalidate();
    }

    private class InfoRecyclerViewAdapter extends RecyclerView.Adapter<InfoRecyclerViewAdapter.MyViewHolderClass>{
        Context context; ArrayList<InfoPojoClass> infoPojoClassArrayList;
        public InfoRecyclerViewAdapter(Context context, ArrayList<InfoPojoClass> infoPojoClassArrayList) {
            this.context=context;this.infoPojoClassArrayList=infoPojoClassArrayList;
        }

        @NonNull
        @Override
        public MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_class_information_page_item, parent, false);
            return new MyViewHolderClass(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderClass holder, final int position) {
            holder.titleTextView.setText(infoPojoClassArrayList.get(position).getTitle());
            Picasso.with(getApplication()).load(infoPojoClassArrayList.get(position).getImage()).into(holder.imageView);
            if(position==0) {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#0593db"));
            }else if(position==1) {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#8854d0"));
            }else if(position==2) {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#20bf6b"));
            }else if(position==3) {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#e74c3c"));
            }else if(position==4) {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#e7a418"));
            }else if(position==5) {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#bf209f"));
            }
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0)
                    {
                        //startActivity(new Intent(getApplication(),StudentScreenActivity.class));
                        Intent gonext=new Intent(getApplication(),SelectClassActivity.class);
                        gonext.putExtra("type","Student");
                        startActivity(new Intent(gonext));
                    }else if(position==1)
                    {
                        Intent gonext=new Intent(getApplication(),SelectClassActivity.class);
                        gonext.putExtra("type","Attendence");
                        startActivity(new Intent(gonext));
                        //startActivity(new Intent(getApplication(),AttendenceScreenActivity.class));
                    }else if(position==2)
                    {
                        startActivity(new Intent(getApplication(),ViewTodaysClassPlan.class));

                       // startActivity(new Intent(getApplication(),ClassPlanActivity.class));
                    }else if(position==3)
                    {
                        Intent gonext=new Intent(getApplication(),ViewMoreClassPlan.class);
                        gonext.putExtra("type","ClassLog");
                        startActivity(new Intent(gonext));

                    }else if(position==4)
                    {
                        startActivity(new Intent(getApplication(),RoutinScreenActivity.class));
                    }

                }
            });

        }
        @Override
        public int getItemCount() {
            return infoPojoClassArrayList.size();
        }

        public class MyViewHolderClass extends RecyclerView.ViewHolder {
            LinearLayout linearLayout;
            ImageView imageView;
            TextView titleTextView;
            public MyViewHolderClass(@NonNull View itemView) {
                super(itemView);
                imageView=(ImageView)itemView.findViewById(R.id.imageView);
                titleTextView=(TextView)itemView.findViewById(R.id.titleTextView);
                linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);


            }
        }
    }

    private class ClassOverviewRecyclerviewAdapter extends RecyclerView.Adapter<ClassOverviewRecyclerviewAdapter.MyViewHolderClass>{
        Context context;ArrayList<ClassOverViewPojoClass> classOverViewPojoClasses;
        public ClassOverviewRecyclerviewAdapter(Context context, ArrayList<ClassOverViewPojoClass> classOverViewPojoClasses) {
            this.context=context;this.classOverViewPojoClasses=classOverViewPojoClasses;
        }

        @NonNull
        @Override
        public MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_class_overview_page_item, parent, false);
            return new MyViewHolderClass(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderClass holder, final int position) {
            holder.perTextView.setText(classOverViewPojoClasses.get(position).getPlan());
            holder.classTitleTextView.setText(classOverViewPojoClasses.get(position).getClassInfo());
            holder.subjectTextView.setText(classOverViewPojoClasses.get(position).getSubject());
            if(position==0) {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#02a949"));
            }else if(position==1) {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#dc3545"));
            }else if(position==2) {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#02a949"));
            }else if(position==3) {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#dc3545"));
            }
        }
        @Override
        public int getItemCount() { return classOverViewPojoClasses.size(); }
        public class MyViewHolderClass extends RecyclerView.ViewHolder {
            LinearLayout linearLayout;
            TextView perTextView,classTitleTextView,subjectTextView;
            public MyViewHolderClass(@NonNull View itemView) {
                super(itemView);
                linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);
                perTextView=(TextView)itemView.findViewById(R.id.perTextView);
                classTitleTextView=(TextView)itemView.findViewById(R.id.classTitleTextView);
                subjectTextView=(TextView)itemView.findViewById(R.id.subjectTextView);
                }
        }
    }

    private class ClassRoutineRecyclerViewAdapter extends RecyclerView.Adapter<ClassRoutineRecyclerViewAdapter.MYViewHolderClass>{
        Context context;ArrayList<ClassRoutinePojoClass> classRoutinePojoClassArrayList;
        public ClassRoutineRecyclerViewAdapter(Context context, ArrayList<ClassRoutinePojoClass> classRoutinePojoClassArrayList) {
            this.context=context;this.classRoutinePojoClassArrayList=classRoutinePojoClassArrayList;
        }

        @NonNull
        @Override
        public MYViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_class_routine_page_item, parent, false);
            return new MYViewHolderClass(itemView);
          }

        @Override
        public void onBindViewHolder(@NonNull MYViewHolderClass holder, int position) {
            holder.subjectTextView.setText(classRoutinePojoClassArrayList.get(position).getSubject());
            holder.sectionTextView.setText(classRoutinePojoClassArrayList.get(position).getClassInfo());
            holder.timingTextView.setText(classRoutinePojoClassArrayList.get(position).getTiming());
        }

        @Override
        public int getItemCount() {
            return classRoutinePojoClassArrayList.size();
        }

        public class MYViewHolderClass extends RecyclerView.ViewHolder {
            TextView subjectTextView,sectionTextView,timingTextView;
            LinearLayout linearLayout;
            public MYViewHolderClass(@NonNull View itemView) {
                super(itemView);
                subjectTextView=(TextView)itemView.findViewById(R.id.subjectTextView);
                sectionTextView=(TextView)itemView.findViewById(R.id.sectionTextView);
                timingTextView=(TextView)itemView.findViewById(R.id.timingTextView);
                linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);
            }
        }
    }
    public void closeDrawer() { drawer.closeDrawers(); }
    public void openDrawer() {
        drawer.openDrawer(Gravity.LEFT);
    }
}
