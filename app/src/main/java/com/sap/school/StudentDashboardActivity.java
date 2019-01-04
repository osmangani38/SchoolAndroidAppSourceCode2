package com.sap.school;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sap.school.PojoClass.ClassOverViewPojoClass;
import com.sap.school.PojoClass.ClassRoutinePojoClass;
import com.sap.school.PojoClass.InfoPojoClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StudentDashboardActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView infoRecyclerView,classOverviewRecyclerView,classRoutineRecyclerView;
    InfoRecyclerViewAdapter infoRecyclerViewAdapter;
  ClassOverviewRecyclerviewAdapter classOverviewRecyclerviewAdapter;
   ClassRoutineRecyclerViewAdapter classRoutineRecyclerViewAdapter;
    ArrayList<InfoPojoClass> infoPojoClassArrayList;
    ArrayList<ClassOverViewPojoClass>classOverViewPojoClassArrayList;
    ArrayList<ClassRoutinePojoClass>classRoutinePojoClassArrayList;
    private DrawerLayout drawer;
    RelativeLayout toggleButton;
    RelativeLayout attendenceNavigationButton,healthInfoNavigationButton,gameAndSportsNavigationButton,eventsNavigationButton,coCurricularNavigationButton,classPlanNavigationButton,markSheetEntryNavigationButton,classLogNavigationButton,questionBankNavigationButton,reportIncidentNavigationButton,leaveOfAbsenseNavigationButton,schemesNavigationButton,feedbackNavigationButton,logOutNavigationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        initView();
        setListener();
        Information();
        ClassOverView();
        ClassRoutinMethod();
    }

    private void setListener() {
        toggleButton.setOnClickListener(this);
        attendenceNavigationButton.setOnClickListener(this);
        healthInfoNavigationButton.setOnClickListener(this);
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
        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("MatheMatices","Class - V | Section - B","11am - 11:45am"));
        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("HINDI","Class - V | Section - B","11am - 11:45am"));
        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("ENGLISH","Class - V | Section - B","11am - 11:45am"));
        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("HINDI","Class - V | Section - B","11am - 11:45am"));
        classRoutineRecyclerViewAdapter=new ClassRoutineRecyclerViewAdapter(getApplicationContext(),classRoutinePojoClassArrayList);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        classRoutineRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        classRoutineRecyclerView.setAdapter(classRoutineRecyclerViewAdapter);
    }

    private void ClassOverView() {
        classOverViewPojoClassArrayList.add(new ClassOverViewPojoClass("30%","CLASS -V","ENGLISH"));
        classOverViewPojoClassArrayList.add(new ClassOverViewPojoClass("70%","CLASS -VI","HINDI"));
        classOverViewPojoClassArrayList.add(new ClassOverViewPojoClass("80%","CLASS -VII","ENGLISH"));
        classOverViewPojoClassArrayList.add(new ClassOverViewPojoClass("70%","CLASS -VI","HINDI"));
        classOverviewRecyclerviewAdapter=new ClassOverviewRecyclerviewAdapter(getApplicationContext(),classOverViewPojoClassArrayList);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        classOverviewRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        classOverviewRecyclerView.setAdapter(classOverviewRecyclerviewAdapter);
    }

    private void Information() {
        infoPojoClassArrayList.add(new InfoPojoClass("01",R.drawable.student_ic,"Home Task"));
        infoPojoClassArrayList.add(new InfoPojoClass("02",R.drawable.attendance_ic_dashboard,getResources().getString(R.string.attendenceText)));
        infoPojoClassArrayList.add(new InfoPojoClass("03",R.drawable.create_plan_ic_dashboard,getResources().getString(R.string.classPlanText)));
        infoPojoClassArrayList.add(new InfoPojoClass("04",R.drawable.class_log_dashboard,"Exam Shedule"));
        infoPojoClassArrayList.add(new InfoPojoClass("05",R.drawable.class_routine,getResources().getString(R.string.classRoutineText)));
        infoPojoClassArrayList.add(new InfoPojoClass("06",R.drawable.attendance_ic_dashboard,getResources().getString(R.string.noticeText)));
        infoPojoClassArrayList.add(new InfoPojoClass("07",R.drawable.create_plan_ic_dashboard,"Question Bank"));
        infoPojoClassArrayList.add(new InfoPojoClass("08",R.drawable.class_log_dashboard,"Feedback"));
        infoPojoClassArrayList.add(new InfoPojoClass("09",R.drawable.events_ic_dashboard,"Events"));
        infoRecyclerViewAdapter = new InfoRecyclerViewAdapter(getApplication(), infoPojoClassArrayList);
        infoRecyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 3));
        infoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        infoRecyclerView.setAdapter(infoRecyclerViewAdapter);
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
        healthInfoNavigationButton=(RelativeLayout)findViewById(R.id.healthInfoNavigationButton);
        gameAndSportsNavigationButton=(RelativeLayout)findViewById(R.id.gameAndSportsNavigationButton);
        eventsNavigationButton=(RelativeLayout)findViewById(R.id.eventsNavigationButton);
        coCurricularNavigationButton=(RelativeLayout)findViewById(R.id.coCurricularNavigationButton);
        classPlanNavigationButton=(RelativeLayout)findViewById(R.id.classPlanNavigationButton);
        markSheetEntryNavigationButton=(RelativeLayout)findViewById(R.id.markSheetEntryNavigationButton);
        classLogNavigationButton=(RelativeLayout)findViewById(R.id.classLogNavigationButton);
        questionBankNavigationButton=(RelativeLayout)findViewById(R.id.questionBankNavigationButton);
        reportIncidentNavigationButton=(RelativeLayout)findViewById(R.id.reportIncidentNavigationButton);
        leaveOfAbsenseNavigationButton=(RelativeLayout)findViewById(R.id.leaveOfAbsenseNavigationButton);
        schemesNavigationButton=(RelativeLayout)findViewById(R.id.schemesNavigationButton);
        feedbackNavigationButton=(RelativeLayout)findViewById(R.id.feedbackNavigationButton);
        logOutNavigationButton=(RelativeLayout)findViewById(R.id.logOutNavigationButton);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.toggleButton:
                openDrawer();
                break;
            case R.id.attendenceNavigationButton:
                Intent gonext=new Intent(getApplication(),MarkAttendenceActivity.class);
                startActivity(new Intent(gonext));
                closeDrawer();
                break;
            case R.id.healthInfoNavigationButton:

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
                startActivity(new Intent(getApplicationContext(),NoticeScreenActivity.class));
                closeDrawer();
                break;
            case R.id.classPlanNavigationButton:
                startActivity(new Intent(getApplication(),ClassPlanRelatedToSubjectScreenActivity.class));
                closeDrawer();
                break;
            case R.id.markSheetEntryNavigationButton:
                startActivity(new Intent(getApplication(),RoutinScreenActivity.class));
                closeDrawer();
                break;
            case R.id.classLogNavigationButton:
                startActivity(new Intent(getApplication(),ClassPlanRelatedToSubjectScreenActivity.class));
                closeDrawer();
                break;
            case R.id.questionBankNavigationButton:
                //startActivity(new Intent(getApplication(),ClassPlanRelatedToSubjectScreenActivity.class));
                closeDrawer();
                break;
            case R.id.reportIncidentNavigationButton:
                //ExamSchdule
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
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                closeDrawer();
                break;
        }
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
            }else if(position==6) {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#8854d0"));
            }else if(position==7) {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#8854d0"));
            }else if(position==8) {
                holder.linearLayout.setBackgroundColor(Color.parseColor("#0593db"));
            }
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0)
                    {
                        //startActivity(new Intent(getApplication(),StudentScreenActivity.class));
                        Intent gonext=new Intent(getApplication(),ClassPlanRelatedToSubjectScreenActivity.class);
                        startActivity(new Intent(gonext));
                    }else if(position==1)
                    {
                        Intent gonext=new Intent(getApplication(),MarkAttendenceActivity.class);
                        startActivity(new Intent(gonext));
                        //startActivity(new Intent(getApplication(),AttendenceScreenActivity.class));
                    }else if(position==2)
                    {
                        Intent gonext=new Intent(getApplication(),ClassPlanRelatedToSubjectScreenActivity.class);
                        startActivity(new Intent(gonext));
                    }else if(position==4)
                    {
                        startActivity(new Intent(getApplication(),RoutinScreenActivity.class));
                    }else if(position==5)
                    {
                        startActivity(new Intent(getApplication(),NoticeScreenActivity.class));
                    }else if(position==7)
                    {
                        startActivity(new Intent(getApplication(),FeedBackActivity.class));
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
