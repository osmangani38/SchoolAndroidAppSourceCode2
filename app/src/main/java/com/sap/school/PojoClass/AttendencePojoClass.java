package com.sap.school.PojoClass;

public class AttendencePojoClass {
    public int getStudent_image() {
        return student_image;
    }

    public int getAttendence_info() {
        return attendence_info;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    int student_image,attendence_info;
    String name,id;
    public AttendencePojoClass(String id, String name, int student_image, int attendence_info) {
        this.id=id;this.name=name;this.student_image=student_image;this.attendence_info=attendence_info;
    }
}
