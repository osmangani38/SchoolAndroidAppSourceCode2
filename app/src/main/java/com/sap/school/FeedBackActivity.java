package com.sap.school;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sap.school.PojoClass.FeedbackPojoClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView feedBackRecyclerView;
    RelativeLayout backButton;
    ArrayList<FeedbackPojoClass>feedbackPojoClassArrayList;
    FeedbackRecyclerAdapter feedbackRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        initView();
        setListner();
        feedbackInfo();
    }

    private void feedbackInfo() {
        feedbackPojoClassArrayList.add(new FeedbackPojoClass("01","Sumit",R.drawable.student1,"Hello Sir,Can you help me"));
        feedbackPojoClassArrayList.add(new FeedbackPojoClass("01","Ram",R.drawable.student2,"Good Morning Sir"));
        feedbackPojoClassArrayList.add(new FeedbackPojoClass("01","Sumit",R.drawable.student3,"Please help me to solve"));
        feedbackRecyclerAdapter = new FeedbackRecyclerAdapter(getApplication(), feedbackPojoClassArrayList);
        feedBackRecyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 1));
        feedBackRecyclerView.setItemAnimator(new DefaultItemAnimator());
        feedBackRecyclerView.setAdapter(feedbackRecyclerAdapter);
    }

    private void setListner() {
        backButton.setOnClickListener(this);
    }

    private void initView() {
        feedBackRecyclerView=(RecyclerView)findViewById(R.id.feedBackRecyclerView);
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        feedbackPojoClassArrayList=new ArrayList<FeedbackPojoClass>();
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

    private class FeedbackRecyclerAdapter extends RecyclerView.Adapter<FeedbackRecyclerAdapter.MyViewHolderClass>{
        Context context; ArrayList<FeedbackPojoClass> feedbackPojoClassArrayList;
        public FeedbackRecyclerAdapter(Context context, ArrayList<FeedbackPojoClass> feedbackPojoClassArrayList) {
            this.context=context;this.feedbackPojoClassArrayList=feedbackPojoClassArrayList;
        }

        @NonNull
        @Override
        public FeedbackRecyclerAdapter.MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_feedback_page_item, parent, false);
            return new FeedbackRecyclerAdapter.MyViewHolderClass(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull FeedbackRecyclerAdapter.MyViewHolderClass holder, int position) {
                    holder.nameTextView.setText(feedbackPojoClassArrayList.get(position).getName());
            holder.messageTextView.setText(feedbackPojoClassArrayList.get(position).getMessage());
            Picasso.with(getApplication()).load(feedbackPojoClassArrayList.get(position).getImage()).into(holder.cirleImageView);
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplication(),FeedbackMessageActivity.class));
                }
            });
            }
        @Override
        public int getItemCount() {
            return feedbackPojoClassArrayList.size();
        }

        public class MyViewHolderClass extends RecyclerView.ViewHolder {
            CircleImageView cirleImageView;
            TextView nameTextView,messageTextView;
            LinearLayout linearLayout;
            public MyViewHolderClass(@NonNull View itemView) {
                super(itemView);
                cirleImageView=(CircleImageView)itemView.findViewById(R.id.cirleImageView);
                nameTextView=(TextView)itemView.findViewById(R.id.nameTextView);
                messageTextView=(TextView)itemView.findViewById(R.id.messageTextView);
                linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);

            }
        }
    }
}
