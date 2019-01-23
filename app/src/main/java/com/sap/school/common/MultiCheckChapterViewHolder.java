package com.sap.school.common;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.sap.school.R;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;


public class MultiCheckChapterViewHolder extends CheckableChildViewHolder {

  private CheckedTextView childCheckedTextView;

  public MultiCheckChapterViewHolder(View itemView) {
    super(itemView);
    childCheckedTextView =
        (CheckedTextView) itemView.findViewById(R.id.list_item_multicheck_chapter_name);
  }

  @Override
  public Checkable getCheckable() {
    return childCheckedTextView;
  }

  public void setChapterName(String chapterName) {
    childCheckedTextView.setText(chapterName);
  }
}
