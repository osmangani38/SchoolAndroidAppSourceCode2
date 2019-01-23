package com.sap.school.common;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.sap.school.PojoClass.MultiCheckLesson;
import com.sap.school.PojoClass.LessonPOJO;
import com.sap.school.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class LessonViewHolder extends GroupViewHolder {

  private TextView lessonName;
  private ImageView arrow;

  public LessonViewHolder(View itemView) {
    super(itemView);
    lessonName = (TextView) itemView.findViewById(R.id.list_item_lesson_name);
    arrow = (ImageView) itemView.findViewById(R.id.list_item_lesson_arrow);
  }

  public void setLessonTitle(ExpandableGroup lessonTitle) {
    if (lessonTitle instanceof LessonPOJO) {
      lessonName.setText(lessonTitle.getTitle());
    }
    if (lessonTitle instanceof MultiCheckLesson) {
      lessonName.setText(lessonTitle.getTitle());
    }

  }

  @Override
  public void expand() {
    animateExpand();
  }

  @Override
  public void collapse() {
    animateCollapse();
  }

  private void animateExpand() {
    RotateAnimation rotate =
        new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
    rotate.setDuration(300);
    rotate.setFillAfter(true);
    arrow.setAnimation(rotate);
  }

  private void animateCollapse() {
    RotateAnimation rotate =
        new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
    rotate.setDuration(300);
    rotate.setFillAfter(true);
    arrow.setAnimation(rotate);
  }
}
