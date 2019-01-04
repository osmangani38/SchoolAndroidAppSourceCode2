package com.sap.school.PojoClass;

public class CreatePlanPojoClass {
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    String id,title;
    int image;
    public CreatePlanPojoClass(String id, String title, int image) {
        this.id=id;this.title=title;this.image=image;
    }
}
