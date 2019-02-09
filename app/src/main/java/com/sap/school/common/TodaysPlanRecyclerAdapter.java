package com.sap.school.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.sap.school.PojoClass.TodaysClassPlanPOJO;
import com.sap.school.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    public void onBindViewHolder(TodaysPlanRecyclerAdapter.MyViewHolderClass holder, int position) {
        holder.subjectTextView.setText(todaysClassPlanPOJOArrayList.get(position).getSubject());
        holder.lessonNameTV.setText(todaysClassPlanPOJOArrayList.get(position).getLesson_name());
        holder.timingTextView.setText(todaysClassPlanPOJOArrayList.get(position).getTopic_name());

    }
    @Override
    public int getItemCount() {
        return todaysClassPlanPOJOArrayList.size();
    }
    public class MyViewHolderClass extends RecyclerView.ViewHolder {

        TextView subjectTextView,timingTextView, lessonNameTV;
        LinearLayout linearLayout;
        public MyViewHolderClass(View itemView) {
            super(itemView);
            lessonNameTV = (TextView)itemView.findViewById(R.id.lessonNameTV);
            subjectTextView=(TextView)itemView.findViewById(R.id.subjectTextView);
            timingTextView=(TextView)itemView.findViewById(R.id.timingTextView);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);


        }
    }
}