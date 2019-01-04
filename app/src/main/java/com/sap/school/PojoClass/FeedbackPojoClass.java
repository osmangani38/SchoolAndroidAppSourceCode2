package com.sap.school.PojoClass;

public class FeedbackPojoClass {


    public String getId() { return id; }
    public String getName() { return name; }
    public String getMessage() { return message; }
    public int getImage() { return image; }
    String id,  name,message;
    int image;
    public FeedbackPojoClass(String id, String name, int image, String message) {
        this.id=id;this.name=name;this.image=image;this.message=message;
    }
}
