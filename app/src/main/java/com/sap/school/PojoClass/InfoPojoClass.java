package com.sap.school.PojoClass;

public class InfoPojoClass {
    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getImage() { return image; }
    String id,title;
    int image;
    public InfoPojoClass(String id, int image, String title) {
        this.id=id;this.image=image;this.title=title;
    }
}
