package com.sap.school.common;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.prashantsolanki.secureprefmanager.SecurePrefManager;
import com.sap.school.ApplicationContextProvider;
import com.sap.school.PojoClass.LessonPOJO;
import com.sap.school.PojoClass.MultiCheckLesson;
import com.sap.school.PojoClass.ChapterPOJO;
import com.sap.school.R;
import com.sap.utils.AppConstants;
import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildCheckChangedListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class MultiCheckLessonAdapter extends
    CheckableChildRecyclerViewAdapter<LessonViewHolder, MultiCheckChapterViewHolder> {
  String final_text="";
  public MultiCheckLessonAdapter(List<MultiCheckLesson> groups) {
    super(groups);
  }

  @Override
  public MultiCheckChapterViewHolder onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_child, parent, false);
    String roll_id = SecurePrefManager.with(ApplicationContextProvider.getContext())
            .get("roll_id")
            .defaultValue("unknown")
            .go();; // getting String
    String lesson;
    if (roll_id.equals("2")) {

    }
    else{
    }
    return new MultiCheckChapterViewHolder(view);
  }

  @Override
  public void onBindCheckChildViewHolder(MultiCheckChapterViewHolder holder, int position,
                                         CheckedExpandableGroup group, int childIndex) {
    final ChapterPOJO chapterPOJO = (ChapterPOJO) group.getItems().get(childIndex);
    holder.setChapterName(chapterPOJO.getTopic_name());// sub chapters name

    holder.setOnChildCheckedListener(new OnChildCheckChangedListener() {
      @Override
      public void onChildCheckChanged(View view, boolean checked, int flatPos) {
        if(checked){

          String topic_id = chapterPOJO.getTopic_id();
          final_text = final_text + topic_id + ",";
          //final_text = final_text.replaceAll(", $", "");
          AppConstants.GLOBAL_TOPIC_ID = final_text;
          //ToastUtils.showShort(AppConstants.GLOBAL_TOPIC_ID);
        }
      }
    });
  }

  @Override
  public LessonViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_parent, parent, false);
    return new LessonViewHolder(view);
  }

  @Override
  public void onBindGroupViewHolder(LessonViewHolder holder, int flatPosition,
                                    ExpandableGroup group) {
    holder.setLessonTitle(group);
  }
}
