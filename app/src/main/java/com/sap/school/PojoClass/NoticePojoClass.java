package com.sap.school.PojoClass;

public class NoticePojoClass {
    public String getId() { return id; }
    public String getDateinfo() { return dateinfo; }
    public String getInformation() { return information; }
    String id,dateinfo,information;
    public NoticePojoClass(String id, String dateinfo, String information) {
        this.id=id;this.dateinfo=dateinfo;this.information=information;
    }
}
