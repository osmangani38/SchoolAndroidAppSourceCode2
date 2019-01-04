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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sap.school.PojoClass.FeedbackMessagePojoClass;
import com.sap.school.PojoClass.FeedbackPojoClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedbackMessageActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView sendMessageRecyclerView;
    RelativeLayout backButton;
    SendMessageRecyclerViewAdapter sendMessageRecyclerViewAdapter;
    ArrayList<FeedbackMessagePojoClass>feedbackPojoClassArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_message);
        initView();
        setListner();
        FeedBackInfo();
    }

    private void setListner() {
        backButton.setOnClickListener(this);
    }

    private void FeedBackInfo() {
        feedbackPojoClassArrayList.add(new FeedbackMessagePojoClass("01","Sumit","Hello how are you sir?","Amit","I am fyn..tell me"));
        feedbackPojoClassArrayList.add(new FeedbackMessagePojoClass("02","Sumit","whats going on","Amit","nothing"));
        sendMessageRecyclerViewAdapter = new SendMessageRecyclerViewAdapter(getApplication(), feedbackPojoClassArrayList);
        sendMessageRecyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 1));
        sendMessageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        sendMessageRecyclerView.setAdapter(sendMessageRecyclerViewAdapter);
    }

    private void initView() {
        sendMessageRecyclerView=(RecyclerView)findViewById(R.id.sendMessageRecyclerView);
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        feedbackPojoClassArrayList=new ArrayList<FeedbackMessagePojoClass>();
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

    private class SendMessageRecyclerViewAdapter extends RecyclerView.Adapter<SendMessageRecyclerViewAdapter.MyViewHolderClass>{
        Context context;ArrayList<FeedbackMessagePojoClass> feedbackPojoClassArrayList;
        public SendMessageRecyclerViewAdapter(Context context, ArrayList<FeedbackMessagePojoClass> feedbackPojoClassArrayList) {
            this.context=context;this.feedbackPojoClassArrayList=feedbackPojoClassArrayList;
        }

        @NonNull
        @Override
        public SendMessageRecyclerViewAdapter.MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_messages_patient_page_item, parent, false);
            return new SendMessageRecyclerViewAdapter.MyViewHolderClass(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull SendMessageRecyclerViewAdapter.MyViewHolderClass holder, int i) {
            holder.incomingMsgTextView.setText(feedbackPojoClassArrayList.get(i).getSend_msg());
            holder.incomingProfileTextView.setText(feedbackPojoClassArrayList.get(i).getSend());
            holder.textNameTypeView.setText(feedbackPojoClassArrayList.get(i).getReceiver());
            holder.outgoingMsgTextView.setText(feedbackPojoClassArrayList.get(i).getReceiver_msg());
            //Picasso.with(getApplication()).load(feedbackPojoClassArrayList.get(i).get()).into(myViewHolderClass.cirleImageView);
           // Picasso.with(getApplication()).load(feedbackPojoClassArrayList.get(i).getTiltle()).into(myViewHolderClass.cirleImageView);

        }

        @Override
        public int getItemCount() {
            return feedbackPojoClassArrayList.size();
        }

        public class MyViewHolderClass extends RecyclerView.ViewHolder {
            CircleImageView incomingProfileImageView,outgoingProfileImageView;
            TextView incomingProfileTextView,incomingMsgTextView;
            TextView textNameTypeView,outgoingMsgTextView;
            public MyViewHolderClass(@NonNull View itemView) {
                super(itemView);
                incomingProfileImageView=(CircleImageView)itemView.findViewById(R.id.incomingProfileImageView);
                outgoingProfileImageView=(CircleImageView)itemView.findViewById(R.id.outgoingProfileImageView);
                incomingProfileTextView=(TextView)itemView.findViewById(R.id.incomingProfileTextView);
                incomingMsgTextView=(TextView)itemView.findViewById(R.id.incomingMsgTextView);
                textNameTypeView=(TextView)itemView.findViewById(R.id.textNameTypeView);
                outgoingMsgTextView=(TextView)itemView.findViewById(R.id.outgoingMsgTextView);


            }
        }
    }
}
