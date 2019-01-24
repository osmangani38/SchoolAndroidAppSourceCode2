package com.sap.school.PojoClass;

import android.content.Context;
import android.util.Log;

import com.sap.school.ApplicationContextProvider;
import com.sap.school.R;
import com.sap.school.SubjectPlanDetailsActivity;
import com.sap.utils.JSONSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LessonDataFactory {
  private Context context;



  public static List<MultiCheckLesson> makeMultiCheckGenres() {
     ArrayList<MultiCheckLesson> list = new ArrayList<MultiCheckLesson>();
    JSONArray savedArray;
     try{
       savedArray = JSONSharedPreferences.loadJSONArray(ApplicationContextProvider.getContext(), "teacherJSON", "subjectChapters");
       Log.d("","try'");
    }
     catch (Exception e) {
       // This will catch any exception, because they are all descended from Exception
       System.out.println("Error " + e.getMessage());
       return null;
     }
    int count = savedArray.length();

    for(int i =0; i<count; i++)

    {
      try{
        JSONObject jsonObjItmSection = savedArray.getJSONObject(i);
        Log.d("","try'");
        JSONArray chapters = jsonObjItmSection.getJSONArray("topic");
        list.add (new MultiCheckLesson(jsonObjItmSection.getString("lesson"), makeRockArtists(chapters), jsonObjItmSection.getString("id")));
      }
      catch (Exception e) {
        // This will catch any exception, because they are all descended from Exception
        System.out.println("Error " + e.getMessage());
        return null;
      }

    }
    return list;
  }

  public LessonDataFactory(Context context){
    this.context=context;
  }




  public static List<ChapterPOJO> makeRockArtists(JSONArray chapters) {
    ArrayList<ChapterPOJO> list = new ArrayList<ChapterPOJO>();

    int count = chapters.length();

    for(int i =0; i<count; i++)

    {
      try{
        JSONObject jsonObjItmSection = chapters.getJSONObject(i);
        Log.d("","try'");
        ChapterPOJO queen = new ChapterPOJO(jsonObjItmSection.getString("id"), jsonObjItmSection.getString("topic"));
        list.add(queen);
      }
      catch (Exception e) {
        // This will catch any exception, because they are all descended from Exception
        System.out.println("Error " + e.getMessage());
        return null;
      }

    }

//    ChapterPOJO queen = new ChapterPOJO("1", "Queen");
//    ChapterPOJO styx = new ChapterPOJO("2", "Styx");
//    ChapterPOJO reoSpeedwagon = new ChapterPOJO("3", "SpeedWagon");
//    ChapterPOJO boston = new ChapterPOJO("4", "Boston");

    return (list);
  }


  public static MultiCheckLesson makeMultiCheckJazzGenre() {
    return new MultiCheckLesson("Jazz", makeJazzArtists(), "2");
  }


  public static List<ChapterPOJO> makeJazzArtists() {
    ChapterPOJO milesDavis = new ChapterPOJO("1", "Miles Davis");
    ChapterPOJO ellaFitzgerald = new ChapterPOJO("2", "Ella Fitzgerald");
    ChapterPOJO billieHoliday = new ChapterPOJO("3", "Billie Holiday");

    return Arrays.asList(milesDavis, ellaFitzgerald, billieHoliday);
  }


  public static MultiCheckLesson makeMultiCheckClassicGenre() {
    return new MultiCheckLesson("Classic", makeClassicArtists(), "3");
  }



  public static List<ChapterPOJO> makeClassicArtists() {
    ChapterPOJO beethoven = new ChapterPOJO("1", "Ludwig van Beethoven");
    ChapterPOJO bach = new ChapterPOJO("2", "Johann Sebastian Bach");
    ChapterPOJO brahms = new ChapterPOJO("3", "Johannes Brahms");
    ChapterPOJO puccini = new ChapterPOJO("4", "Giacomo Puccini");

    return Arrays.asList(beethoven, bach, brahms, puccini);
  }


  public static MultiCheckLesson makeMultiCheckSalsaGenre() {
    return new MultiCheckLesson("Salsa", makeSalsaArtists(), "4");
  }


  public static List<ChapterPOJO> makeSalsaArtists() {
    ChapterPOJO hectorLavoe = new ChapterPOJO("1", "Hector Lavoe");
    ChapterPOJO celiaCruz = new ChapterPOJO("2", "Celia Cruz");
    ChapterPOJO willieColon = new ChapterPOJO("3", "Willie Colon");
    ChapterPOJO marcAnthony = new ChapterPOJO("4", "Marc Anthony");

    return Arrays.asList(hectorLavoe, celiaCruz, willieColon, marcAnthony);
  }


}

