package com.sap.school.PojoClass;

public class GeneralItem extends ListItem {
    private ExamSchedulePOJO examSchedulePOJO;

    public ExamSchedulePOJO getExamSchedulePOJO() {
        return examSchedulePOJO;
    }

    public void setExamSchedulePOJO(ExamSchedulePOJO examSchedulePOJO) {
        this.examSchedulePOJO = examSchedulePOJO;
    }

    @Override
    public int getType() {
        return TYPE_GENERAL;
    }


}
