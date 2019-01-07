package com.sap.school;

import android.app.Application;
import android.content.Context;
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

import com.sap.school.PojoClass.NoticePojoClass;

import java.util.ArrayList;

public class NoticeScreenActivity extends AppCompatActivity implements View.OnClickListener{
        RecyclerView noticeRecyclerView;
        RelativeLayout backButton;
        ArrayList<NoticePojoClass>noticePojoClassArrayList;
        NoticeAdapter noticeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_screen);
        initView();
        setListner();
        noticeInfo();
    }

    private void noticeInfo() {
        noticePojoClassArrayList.add(new NoticePojoClass("01","26 January,2019","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        noticePojoClassArrayList.add(new NoticePojoClass("01","27 January,2019","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        noticePojoClassArrayList.add(new NoticePojoClass("01","28 January,2019","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        noticePojoClassArrayList.add(new NoticePojoClass("01","29 January,2019","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        noticePojoClassArrayList.add(new NoticePojoClass("01","29 January,2019","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        noticeAdapter = new NoticeAdapter(getApplication(), noticePojoClassArrayList);
        noticeRecyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 1));
        noticeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        noticeRecyclerView.setAdapter(noticeAdapter);
    }

    private void setListner() {
        backButton.setOnClickListener(this);
    }

    private void initView() {
        noticeRecyclerView=(RecyclerView)findViewById(R.id.noticeRecyclerView);
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        noticePojoClassArrayList=new ArrayList<NoticePojoClass>();
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

    private class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolderClass>{
        Context context;ArrayList<NoticePojoClass> noticePojoClassArrayList;
        public NoticeAdapter(Context context, ArrayList<NoticePojoClass> noticePojoClassArrayList) {
            this.context=context;this.noticePojoClassArrayList=noticePojoClassArrayList;
        }

        @NonNull
        @Override
        public NoticeAdapter.MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notice_page_item, parent, false);
            return new NoticeAdapter.MyViewHolderClass(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull NoticeAdapter.MyViewHolderClass myViewHolderClass, int i) {
            myViewHolderClass.messageTextView.setText(noticePojoClassArrayList.get(i).getInformation());
            myViewHolderClass.dateTextView.setText(noticePojoClassArrayList.get(i).getDateinfo());
        }

        @Override
        public int getItemCount() {
            return noticePojoClassArrayList.size();
        }

        public class MyViewHolderClass extends RecyclerView.ViewHolder {
            TextView messageTextView,dateTextView;
            public MyViewHolderClass(@NonNull View itemView) {
                super(itemView);
                messageTextView=(TextView)itemView.findViewById(R.id.messageTextView);
                dateTextView=(TextView)itemView.findViewById(R.id.dateTextView);
            }
        }
    }
}
