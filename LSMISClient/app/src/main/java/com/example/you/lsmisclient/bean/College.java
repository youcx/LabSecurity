package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/21.
 */

public class College {
    //学院名、实验室总数、学院责任人
    String collegeName;
    String labCount;
    String collegeManager;

    public College(String collegeName,String labCount, String collegeManager ) {
        this.labCount = labCount;
        this.collegeManager = collegeManager;
        this.collegeName = collegeName;
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
