package com.sap.school.PojoClass;

public class ClassRoutinePojoClass {
    public String getSubject() {
        return subject;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public String getTiming() {
        return timing;
    }

    String subject, classInfo,  timing;
    public ClassRoutinePojoClass(String subject, String classInfo, String timing) {
        this.subject=subject;this.classInfo=classInfo;this.timing=timing;
    }
}
