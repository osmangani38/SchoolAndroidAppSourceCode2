package com.sap.school.PojoClass;

public class StudentInfoPojoClass {
    public int getTiltle() {
        return tiltle;
    }

    public String getName() {
        return name;
    }
    public String getStudentsImage() {
        return studentsImage;
    }

    public String getClassInfo() {
        return classInfo;
    }

    int tiltle;String name,  classInfo,  studentsImage;
    public StudentInfoPojoClass(int tiltle, String name, String classInfo) {
        this.tiltle=tiltle;this.name=name;this.classInfo=classInfo;
        this.studentsImage = studentsImage;
    }
}
