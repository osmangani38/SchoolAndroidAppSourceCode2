package com.sap.school.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sap.school.PojoClass.LessonPOJO;
import com.sap.school.PojoClass.MultiCheckLesson;
import com.sap.school.PojoClass.ChapterPOJO;
import com.sap.school.R;
import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class MultiCheckLessonAdapter extends
    CheckableChildRecyclerViewAdapter<LessonViewHolder, MultiCheckChapterViewHolder> {

  public MultiCheckLessonAdapter(List<MultiCheckLesson> groups) {
    super(groups);
  }

  @Override
  public MultiCheckChapterViewHolder onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_child, parent, false);
    return new MultiCheckChapterViewHolder(view);
  }

  @Override
  public void onBindCheckChildViewHolder(MultiCheckChapterViewHolder holder, int position,
                                         CheckedExpandableGroup group, int childIndex) {
    final ChapterPOJO chapterPOJO = (ChapterPOJO) group.getItems().get(childIndex);
    holder.setChapterName(chapterPOJO.getTopic_name());// sub chapters name
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
