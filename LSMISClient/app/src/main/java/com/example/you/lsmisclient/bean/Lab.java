package com.example.you.lsmisclient.bean;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by You on 2018/1/22.
 */

public class Lab {
    String labName;
    String departmentName;
    String mainLevelName;
    String labHazard;
    String managerName;
    String responserName;


    public Lab() {
    }

    public Lab(String labName,String departmentName, String detailLevelName,  String managerName, String responseName) {
        this.departmentName = departmentName;
        this.mainLevelName = detailLevelName;
        this.labName = labName;
        this.managerName = managerName;
        this.responserName = responseName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getMainLevelName() {
        return mainLevelName;
    }

    public void setMainLevelName(String mainLevelName) {
        this.mainLevelName = mainLevelName;
    }

    public String getLabHazard() {
        return labHazard;
    }

    public void setLabHazard(String labHazard) {
        this.labHazard = labHazard;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getResponserName() {
        return responserName;
    }

    public void setResponserName(String responserName) {
        this.responserName = responserName;
    }
}
