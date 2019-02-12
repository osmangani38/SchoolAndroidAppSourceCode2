package com.sap.school.PojoClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.sap.school.ApplicationContextProvider;
import com.sap.school.R;
import com.sap.school.RoutinScreenActivity;
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
        JSONArray chapters = new JSONArray() ;
        String roll_id;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ApplicationContextProvider.getContext());
        roll_id = sharedPref.getString("roll_id", null); // getting String
        String lesson;
        if (roll_id.equals("2")) {
          if (jsonObjItmSection.has("topic")) {
            chapters = jsonObjItmSection.getJSONArray("topic");
          }
        }
        else {
          //chapters.put(jsonObjItmSection.getString("topic"));
        }
        if (roll_id.equals("2") && jsonObjItmSection.has("topic")) {
          list.add(new MultiCheckLesson(jsonObjItmSection.getString("lesson"), makeRockArtists(chapters), jsonObjItmSection.getString("id")));

        }
        else {
          ArrayList getArrayOfLessons = new ArrayList<>();
///here
          //  Object intervention = jsonObjItmSection.get("lesson");
          Object aObj = jsonObjItmSection.get("lesson");
          if (aObj instanceof String){
            String lessonName = jsonObjItmSection.getString("lesson");
            String chapterName = jsonObjItmSection.getString("topic");
            JSONArray chaptersArray = new JSONArray() ;
            chaptersArray.put(chapterName);
            list.add(new MultiCheckLesson(lessonName, makeRockArtists(chaptersArray), String.valueOf(1)));
          }
          else {
            JSONArray lessonArray = jsonObjItmSection.getJSONArray("lesson");
            for (int j = 0; j < lessonArray.length(); j++) {
              JSONObject jsonObj = lessonArray.getJSONObject(j);

              getArrayOfLessons.add(jsonObj.getString("name"));
              String lesson_name = jsonObj.getString("name");

              ArrayList getArrayOfChapters = new ArrayList<>();

              JSONArray chaptersArray = jsonObj.getJSONArray("topic");
              for (int q = 0; q < chaptersArray.length(); q++) {
                JSONObject jsonObjCh = chaptersArray.getJSONObject(q);

                getArrayOfChapters.add(jsonObjCh.getString("name"));
              }
              JSONArray mJSONArray = new JSONArray(getArrayOfChapters);
              list.add(new MultiCheckLesson(lesson_name, makeRockArtists(mJSONArray), String.valueOf(j)));
            }
          }
          /*if(aObj instanceof JSONObject){
            JSONArray lessonArray = jsonObjItmSection.getJSONArray("lesson");
          for (int j = 0;j<lessonArray.length();j++) {
            JSONObject jsonObj = lessonArray.getJSONObject(j);

            getArrayOfLessons.add(jsonObj.getString("name"));
            String lesson_name = jsonObj.getString("name");

            ArrayList getArrayOfChapters = new ArrayList<>();

            JSONArray chaptersArray = jsonObj.getJSONArray("topic");
            for (int q = 0; q < chaptersArray.length(); q++) {
              JSONObject jsonObjCh = chaptersArray.getJSONObject(q);

              getArrayOfChapters.add(jsonObjCh.getString("name"));
            }
            JSONArray mJSONArray = new JSONArray(getArrayOfChapters);
            list.add(new MultiCheckLesson(lesson_name, makeRockArtists(mJSONArray), String.valueOf(j)));
          }
          }
          else if (aObj instanceof String){
            String lessonName = jsonObjItmSection.getString("lesson");
            String chapterName = jsonObjItmSection.getString("topic");
            JSONArray chaptersArray = new JSONArray() ;
            chaptersArray.put(chapterName);
            list.add(new MultiCheckLesson(lessonName, makeRockArtists(chaptersArray), String.valueOf(1)));
          }*/

        }
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
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ApplicationContextProvider.getContext());
        String roll_id = sharedPref.getString("roll_id", null); // getting String
        String lesson;
        if (roll_id.equals("2") && chapters.length()>1) {
          Object intervention = chapters.get(i);
          if (intervention instanceof JSONObject) {

            JSONObject jsonObjItmSection = chapters.getJSONObject(i);
            Log.d("", "try'");
            ChapterPOJO queen = new ChapterPOJO(jsonObjItmSection.getString("id"), jsonObjItmSection.getString("topic"));
            list.add(queen);
          }
          else {
            String id = String.valueOf(i);
            String chapterName = chapters.getString(i);
            ChapterPOJO queen = new ChapterPOJO(id, chapterName);
            list.add(queen);
          }
        }
        else {
          String  stringChapters = chapters.getString(i);
          Log.d("","try'");
          // ChapterPOJO queen = new ChapterPOJO(jsonObjItmSection.getString("id"), jsonObjItmSection.getString("topic"));
          ChapterPOJO queen = new ChapterPOJO("id", stringChapters);

          list.add(queen);

        }

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
