package com.sap.school.PojoClass;

public class ExamSchedulePOJO {
    public String getSubject() {
        return subject;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public String getTiming() {
        return timing;
    }

    String subject;
    String classInfo;
    String timing;

    public String getExamdate() {
        return examdate;
    }

    String examdate;
    public ExamSchedulePOJO(String subject, String classInfo, String timing, String examdate) {
        this.subject=subject;this.classInfo=classInfo;this.timing=timing; this.examdate=examdate;
    }
}
