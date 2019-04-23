package com.sap.school.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.sap.school.AttendenceScreenActivity;
import com.sap.school.PojoClass.SelectableItem;
import com.sap.school.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectableViewHolder extends RecyclerView.ViewHolder {

    public static final int MULTI_SELECTION = 2;
    public static final int SINGLE_SELECTION = 1;
    TextView rollNoTextView, nameTextView, classTextView;
    CircleImageView cirleImageView;
    CheckedTextView imageView;
    SelectableItem mItem;
    OnItemSelectedListener itemSelectedListener;

    public SelectableViewHolder(@NonNull final View itemView, OnItemSelectedListener listener) {
        super(itemView);
        itemSelectedListener = listener;
        rollNoTextView = (TextView) itemView.findViewById(R.id.rollNoTextView);
        nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
        classTextView = (TextView) itemView.findViewById(R.id.classTextView);
        cirleImageView = (CircleImageView) itemView.findViewById(R.id.cirleImageView);
        imageView = itemView.findViewById(R.id.imageView);
                /*if (imageView.isSelected()){
                    imageView.setSelected(false);
                    imageView.setImageResource(R.drawable.tick_ic);
                }
                else {
                    imageView.setSelected(true);
                    imageView.setImageResource(R.drawable.cross_btn);
                }*/
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (mItem.isSelected() && getItemViewType() == MULTI_SELECTION) {
                    setChecked(false);
                } else {
                    setChecked(true);
                }
                itemSelectedListener.onItemSelected(mItem);

            }
        });
    }

    public void setChecked(boolean value) {
        if (value) {
            imageView.setCheckMarkDrawable(R.drawable.tick_ic);
        } else {
            imageView.setCheckMarkDrawable(R.drawable.cross_btn);
        }
        mItem.setSelected(value);
        imageView.setChecked(value);
    }

    public interface OnItemSelectedListener {

        void onItemSelected(SelectableItem item);
    }

}