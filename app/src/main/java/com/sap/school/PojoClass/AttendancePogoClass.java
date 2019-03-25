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
    public String getStudentRollNumber() {
        return roll_number;
    }

    public String getId() {
        return id;
    }

    int student_image,attendence_info;
    String name,id,studentDailyAttenceId,roll_number;
    public AttendancePogoClass(String id, String name, int student_image, int attendence_info,String studentDailyAttenceId,String roll_number) {
        this.id=id;this.name=name;this.student_image=student_image;this.attendence_info=attendence_info;
        this.studentDailyAttenceId = studentDailyAttenceId;
        this.roll_number = roll_number;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        AttendancePogoClass itemCompare = (AttendancePogoClass) obj;
        if(itemCompare.getName().equals(this.getName()))
            return true;

        return false;
    }
}
