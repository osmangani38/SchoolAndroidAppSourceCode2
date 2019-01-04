package com.sap.school.PojoClass;

public class ClassOverViewPojoClass {
    public String getPlan() { return plan; }
    public String getClassInfo() { return classInfo; }
    public String getSubject() { return subject; }
    String plan,classInfo,subject;
    public ClassOverViewPojoClass(String plan, String classInfo, String subject) {
        this.plan=plan;this.classInfo=classInfo;this.subject=subject;
    }
}
