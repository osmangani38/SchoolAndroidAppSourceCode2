package com.sap.school.PojoClass;

import org.json.JSONArray;

public class CreatePlanPojoClass {

    String classid,classname;
    JSONArray section, subject;

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public JSONArray getSection() {
        return section;
    }

    public void setSection(JSONArray section) {
        this.section = section;
    }

    public JSONArray getSubject() {
        return subject;
    }

    public void setSubject(JSONArray subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Class "+classname;
    }
}
