package com.sap.school.PojoClass;

public class SelectPojoClass {
    String id,  classInfo,  title;

    public String getId() {
        return id;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public String getTitle() {
        return title;
    }

    public SelectPojoClass(String id, String classInfo, String title) {
        this.id=id;this.classInfo=classInfo;this.title=title;
    }
}
