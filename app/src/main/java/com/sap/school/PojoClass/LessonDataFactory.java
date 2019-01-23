package com.sap.school.PojoClass;

import com.sap.school.R;

import java.util.Arrays;
import java.util.List;

public class LessonDataFactory {



  public static List<MultiCheckLesson> makeMultiCheckGenres() {
    return Arrays.asList(makeMultiCheckRockGenre(),
        makeMultiCheckJazzGenre(),
        makeMultiCheckClassicGenre(),
        makeMultiCheckSalsaGenre());
  }



  public static MultiCheckLesson makeMultiCheckRockGenre() {
    return new MultiCheckLesson("Rock", makeRockArtists(), "1");
  }


  public static List<ChapterPOJO> makeRockArtists() {
    ChapterPOJO queen = new ChapterPOJO("1", "Queen");
    ChapterPOJO styx = new ChapterPOJO("2", "Styx");
    ChapterPOJO reoSpeedwagon = new ChapterPOJO("3", "SpeedWagon");
    ChapterPOJO boston = new ChapterPOJO("4", "Boston");

    return Arrays.asList(queen, styx, reoSpeedwagon, boston);
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

