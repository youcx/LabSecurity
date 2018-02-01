package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/21.
 */

public class College {
    //学院名、实验室总数、学院责任人
    String collegeName;
    String labCount;
    String collegeManager;
    int departmentId;

    public College() {
    }

    public College(String collegeName, String labCount, String collegeManager , int departmentId) {
        this.labCount = labCount;
        this.collegeManager = collegeManager;
        this.collegeName = collegeName;
        this.departmentId=departmentId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getLabCount() {
        return labCount;
    }

    public void setLabCount(String labCount) {
        this.labCount = labCount;
    }

    public String getCollegeManager() {
        return collegeManager;
    }

    public void setCollegeManager(String collegeManager) {
        this.collegeManager = collegeManager;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
}
