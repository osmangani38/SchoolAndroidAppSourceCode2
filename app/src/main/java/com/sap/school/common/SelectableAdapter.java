package com.sap.school.common;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.sap.school.ApplicationContextProvider;
import com.sap.school.PojoClass.AttendancePogoClass;
import com.sap.school.PojoClass.SelectableItem;
import com.sap.school.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SelectableAdapter extends RecyclerView.Adapter implements SelectableViewHolder.OnItemSelectedListener {

    private final ArrayList<SelectableItem> mValues;
    SelectableViewHolder.OnItemSelectedListener listener;
    String className;
    private boolean isMultiSelectionEnabled = false;

    public SelectableAdapter(SelectableViewHolder.OnItemSelectedListener listener,
                              List<AttendancePogoClass> items, String className, boolean isMultiSelectionEnabled) {
        this.listener = listener;
        this.className = className;
        this.isMultiSelectionEnabled = isMultiSelectionEnabled;
        mValues = new ArrayList<>();
        for (AttendancePogoClass item : items) {
            mValues.add(new SelectableItem(item, false));
        }
    }

    @Override
    public SelectableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_attendence_page_item, parent, false);

        return new SelectableViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        SelectableViewHolder holder = (SelectableViewHolder) viewHolder;
        SelectableItem selectableItem = mValues.get(position);

        String roll_number = selectableItem.getStudentRollNumber();
        if (roll_number.length()>0){
            holder.rollNoTextView.setText(selectableItem.getStudentRollNumber());
        }
        else {
            holder.rollNoTextView.setText(selectableItem.getId());
        }
        holder.nameTextView.setText(selectableItem.getName());

        holder.classTextView.setText("Class - "+ className);

        holder.imageView.setTag(position);
        // myViewHolderClass.classTextView.setText(attendencePojoClasses.get(i).getId());
        Picasso.with(ApplicationContextProvider.getContext()).load(R.drawable.avatar_student).into(holder.cirleImageView);

        if (isMultiSelectionEnabled) {
            TypedValue value = new TypedValue();
            holder.imageView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.imageView.setCheckMarkDrawable(checkMarkDrawableResId);
            if (selectableItem.getAttendence_info() == 1){

                selectableItem.setSelected(true);

            }else {
                selectableItem.setSelected(false);
            }
        } else {
            TypedValue value = new TypedValue();
            holder.imageView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorSingle, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.imageView.setCheckMarkDrawable(checkMarkDrawableResId);
        }


        holder.mItem = selectableItem;

        holder.setChecked(holder.mItem.isSelected());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public ArrayList<AttendancePogoClass> getSelectedItems() {

        ArrayList<AttendancePogoClass> selectedItems = new ArrayList<>();
        for (SelectableItem item : mValues) {
            if (item.isSelected()) {
                selectedItems.add(item);

            }
        }
        return selectedItems;
    }

    @Override
    public int getItemViewType(int position) {
        if(isMultiSelectionEnabled){
            return SelectableViewHolder.MULTI_SELECTION;
        }
        else{
            return SelectableViewHolder.SINGLE_SELECTION;
        }
    }


    @Override
    public void onItemSelected(SelectableItem item) {
        if (!isMultiSelectionEnabled) {

            for (SelectableItem selectableItem : mValues) {
                if (!selectableItem.equals(item)
                        && selectableItem.isSelected()) {
                    selectableItem.setSelected(false);
                } else if (selectableItem.equals(item)
                        && item.isSelected()) {
                    selectableItem.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        listener.onItemSelected(item);
    }
}