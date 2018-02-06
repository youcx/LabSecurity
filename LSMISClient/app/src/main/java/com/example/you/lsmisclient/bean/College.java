package com.example.you.lsmisclient.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by You on 2018/1/21.
 */

public class College {
    //学院名、id、实验室总数、学院责任人
    String departmentName;
    int departmentId;
    String labNumb;
    String member;


    public College() {
    }

    public College(int departmentId, String departmentName, String member,String labNumb) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.labNumb = labNumb;
        this.member=member;

    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLabNumb() {
        return labNumb;
    }

    public void setLabNumb(String labNumb) {
        this.labNumb = labNumb;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
}
