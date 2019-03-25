package com.sap.school.PojoClass;

public class SelectableItem extends AttendancePogoClass{
    private boolean isSelected = false;


    public SelectableItem(AttendancePogoClass item, boolean isSelected) {
        super(item.getId(),item.getName(), item.getStudent_image(), item.getAttendence_info(),
                item.studentDailyAttenceId, item.roll_number);
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
