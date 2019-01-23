package com.sap.school.PojoClass;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;

public class LessonPOJO extends ExpandableGroup<ChapterPOJO> {

  private String lesson_id, lesson_name;

  public LessonPOJO(String lesson_name, List<ChapterPOJO> items, String lesson_id) {
    super(lesson_name, items);
    this.lesson_id = lesson_id;
    this.lesson_name = lesson_name;

  }

  public String getLesson_id() {
    return lesson_id;
  }

  public void setLesson_id(String lesson_id) {
    this.lesson_id = lesson_id;
  }

  public String getLesson_name() {
    return lesson_name;
  }

  public void setLesson_name(String lesson_name) {
    this.lesson_name = lesson_name;
  }

}



