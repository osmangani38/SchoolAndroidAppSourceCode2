package com.sap.school.common;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.prashantsolanki.secureprefmanager.SecurePrefManager;
import com.sap.school.ApplicationContextProvider;
import com.sap.school.R;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;


public class MultiCheckChapterViewHolder extends CheckableChildViewHolder {

  private CheckedTextView childCheckedTextView;

  public MultiCheckChapterViewHolder(View itemView) {
    super(itemView);
    childCheckedTextView =
        (CheckedTextView) itemView.findViewById(R.id.list_item_multicheck_chapter_name);
    String roll_id = SecurePrefManager.with(ApplicationContextProvider.getContext())
            .get("roll_id")
            .defaultValue("unknown")
            .go();; // getting String
    String lesson;
    if (roll_id.equals("2")) {
      //childCheckedTextView.setVisibility(View.VISIBLE);
    }
    else{
      childCheckedTextView.setCheckMarkDrawable(null);
    }
  }

  @Override
  public Checkable getCheckable() {
    return childCheckedTextView;
  }

  public void setChapterName(String chapterName) {
    childCheckedTextView.setText(chapterName);
  }
}
