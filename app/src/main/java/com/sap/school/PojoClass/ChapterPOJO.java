package com.sap.school.PojoClass;

import android.os.Parcel;
import android.os.Parcelable;

public class ChapterPOJO implements Parcelable {

  private String topic_id, topic_name;


  public ChapterPOJO(String topic_id, String topic_name) {
    this.topic_id = topic_id;
    this.topic_name = topic_name;
  }

  private ChapterPOJO(Parcel in) {
    topic_name = in.readString();
    topic_id = in.readString();
  }


  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(topic_name);
    dest.writeString(topic_id);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ChapterPOJO> CREATOR = new Creator<ChapterPOJO>() {
    @Override
    public ChapterPOJO createFromParcel(Parcel in) {
      return new ChapterPOJO(in);
    }

    @Override
    public ChapterPOJO[] newArray(int size) {
      return new ChapterPOJO[size];
    }
  };

  public String getTopic_id() {
    return topic_id;
  }

  public void setTopic_id(String topic_id) {
    this.topic_id = topic_id;
  }

  public String getTopic_name() {
    return topic_name;
  }

  public void setTopic_name(String topic_name) {
    this.topic_name = topic_name;
  }

}

