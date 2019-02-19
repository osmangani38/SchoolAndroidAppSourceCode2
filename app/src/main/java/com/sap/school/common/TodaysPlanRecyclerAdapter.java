package com.sap.school.common;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.blankj.utilcode.util.SPUtils;
import com.sap.school.PojoClass.TodaysClassPlanPOJO;
import com.sap.school.R;
import com.sap.school.SubjectPlanDetailsActivity;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TodaysPlanRecyclerAdapter extends RecyclerView.Adapter<TodaysPlanRecyclerAdapter.MyViewHolderClass>{
    Context context;
    ArrayList<TodaysClassPlanPOJO> todaysClassPlanPOJOArrayList;
    public TodaysPlanRecyclerAdapter(Context context, ArrayList<TodaysClassPlanPOJO> todaysClassPlanPOJOArrayList) {
        this.context=context;
        this.todaysClassPlanPOJOArrayList=todaysClassPlanPOJOArrayList;
    }
    @Override
    public TodaysPlanRecyclerAdapter.MyViewHolderClass onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_todays_class_plan, parent, false);
        return new TodaysPlanRecyclerAdapter.MyViewHolderClass(itemView);
    }
    @Override
    public void onBindViewHolder(TodaysPlanRecyclerAdapter.MyViewHolderClass holder, final int position) {
        holder.subjectTextView.setText(todaysClassPlanPOJOArrayList.get(position).getSubject());
        holder.lessonNameTV.setText(todaysClassPlanPOJOArrayList.get(position).getLesson_name());
        holder.timingTextView.setText(todaysClassPlanPOJOArrayList.get(position).getTopic_name());
        String start_dt = todaysClassPlanPOJOArrayList.get(position).getClass_date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date)formatter.parse(start_dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
        String finalFromString = newFormat.format(date);
        holder.sectionTextView.setText("Class - "+todaysClassPlanPOJOArrayList.get(position).getClass_name()+ " | Section - "+
        todaysClassPlanPOJOArrayList.get(position).getSection_name()+" | "+ finalFromString);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goNext = new Intent(context, SubjectPlanDetailsActivity.class);
                if (SPUtils.getInstance().getString("type").equals("ClassLog")){
                    goNext.putExtra("type","ClassLog");
                   // SPUtils.getInstance().remove("type");
                }
                else{
                    goNext.putExtra("type","TodaysClassPlan");
                }
                goNext.putExtra("class_id", todaysClassPlanPOJOArrayList.get(position).getClass_id());
                goNext.putExtra("subject_id", todaysClassPlanPOJOArrayList.get(position).getSubject_id());
                goNext.putExtra("subject_name", todaysClassPlanPOJOArrayList.get(position).getSubject());
                goNext.putExtra("plan_date", todaysClassPlanPOJOArrayList.get(position).getClass_date());
                goNext.putExtra("section_id", todaysClassPlanPOJOArrayList.get(position).getSection_id());
                goNext.putExtra("plan_id", todaysClassPlanPOJOArrayList.get(position).getPlan_id());
                context.startActivity(goNext);
            }
        });
    }
    @Override
    public int getItemCount() {
        return todaysClassPlanPOJOArrayList.size();
    }
    public class MyViewHolderClass extends RecyclerView.ViewHolder {

        TextView subjectTextView,timingTextView, lessonNameTV, sectionTextView;
        LinearLayout linearLayout;
        public MyViewHolderClass(View itemView) {
            super(itemView);
            lessonNameTV = (TextView)itemView.findViewById(R.id.lessonNameTV);
            subjectTextView=(TextView)itemView.findViewById(R.id.subjectTextView);
            timingTextView=(TextView)itemView.findViewById(R.id.timingTextView);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);
            sectionTextView = (TextView) itemView.findViewById(R.id.sectionTextView);

        }
    }
}