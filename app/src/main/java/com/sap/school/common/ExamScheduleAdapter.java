package com.sap.school.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sap.school.PojoClass.DateItem;
import com.sap.school.PojoClass.GeneralItem;
import com.sap.school.PojoClass.ListItem;
import com.sap.school.R;

import java.util.ArrayList;
import java.util.List;

public class ExamScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    List<ListItem> consolidatedList = new ArrayList<>();

    public ExamScheduleAdapter(Context context, List<ListItem> consolidatedList) {
        this.consolidatedList = consolidatedList;
        this.mContext = context;


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {

            case ListItem.TYPE_GENERAL:
                View v1 = inflater.inflate(R.layout.custom_exam_page, parent,
                        false);
                viewHolder = new GeneralViewHolder(v1);
                break;

            case ListItem.TYPE_DATE:
                View v2 = inflater.inflate(R.layout.custom_exam_page_header, parent, false);
                viewHolder = new DateViewHolder(v2);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {

            case ListItem.TYPE_GENERAL:

                GeneralItem generalItem   = (GeneralItem) consolidatedList.get(position);
                GeneralViewHolder generalViewHolder= (GeneralViewHolder) viewHolder;
                generalViewHolder.subjectTextView.setText(generalItem.getExamSchedulePOJO().getSubject());
                generalViewHolder.timingTextView.setText(generalItem.getExamSchedulePOJO().getTiming());

                break;

            case ListItem.TYPE_DATE:
                DateItem dateItem = (DateItem) consolidatedList.get(position);
                DateViewHolder dateViewHolder = (DateViewHolder) viewHolder;

                dateViewHolder.examDate.setText(dateItem.getDate());
                // Populate date item data here

                break;
        }
    }





    // ViewHolder for date row item
    class DateViewHolder extends RecyclerView.ViewHolder {
        protected TextView examDate;

        public DateViewHolder(View v) {
            super(v);
            examDate=(TextView)itemView.findViewById(R.id.examDate);

        }
    }

    // View holder for general row item
    class GeneralViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTextView,timingTextView;
        LinearLayout linearLayout;

        public GeneralViewHolder(View v) {
            super(v);
            subjectTextView=(TextView)itemView.findViewById(R.id.subjectTextView);
            timingTextView=(TextView)itemView.findViewById(R.id.timingTextView);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return consolidatedList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return consolidatedList != null ? consolidatedList.size() : 0;
    }

}
