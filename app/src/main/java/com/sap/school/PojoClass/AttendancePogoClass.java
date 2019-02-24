package com.sap.school.PojoClass;

public class AttendancePogoClass {
    public int getStudent_image() {
        return student_image;
    }

    public int getAttendence_info() {
        return attendence_info;
    }

    public String getName() {
        return name;
    }
    public String getStudentDailyAttenceId() {
        return studentDailyAttenceId;
    }

    public String getId() {
        return id;
    }

    int student_image,attendence_info;
    String name,id,studentDailyAttenceId;
    public AttendancePogoClass(String id, String name, int student_image, int attendence_info,String studentDailyAttenceId) {
        this.id=id;this.name=name;this.student_image=student_image;this.attendence_info=attendence_info;
        this.studentDailyAttenceId = studentDailyAttenceId;
    }
}
