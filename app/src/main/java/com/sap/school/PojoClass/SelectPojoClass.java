package com.sap.school.PojoClass;

public class SelectPojoClass {
    String id,  classInfo,sectionId,sectionName,  title;

    public String getId() {
        return id;
    }
    public String getSectionId() {
        return sectionId;
    }
    public String getSectionName() {
        return sectionName;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public String getTitle() {
        return title;
    }

    public SelectPojoClass(String id, String classInfo, String title,String sectionId, String sectionName) {
        this.id=id;this.classInfo=classInfo;this.title=title;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
    }
}
