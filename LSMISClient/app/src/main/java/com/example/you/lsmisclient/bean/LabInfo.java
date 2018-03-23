package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/22.
 * 实验室信息
 */

public class LabInfo {
    int iD;
    //校区
    private int campusId;
    private String campusName;
    //实验室具体等级id
    int labLevelId;
    String labName;
    //实验室具体等级
    String detailLevelName;
    //实验室等级《国家级等》
    String mainLevelName;
    //所属学院id和名称
    String departmentId;
    String departmentName;
    //负责人
    String responserName;
    String responsePhone;
    //管理员
    String managerName;
    String managerPhone;
    String labStatus;
    //实验室图片地址
    private String labPictureUrl;

    public LabInfo() {
    }

    public String getMainLevelName() {
        return mainLevelName;
    }

    public void setMainLevelName(String mainLevelName) {
        this.mainLevelName = mainLevelName;
    }

    public String getDetailLevelName() {
        return detailLevelName;
    }

    public void setDetailLevelName(String detailLevelName) {
        this.detailLevelName = detailLevelName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getID() {
        return iD;
    }

    public void setID(int ID) {
        this.iD = ID;
    }


    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public int getLabLevelId() {
        return labLevelId;
    }

    public void setLabLevelId(int labLevelId) {
        this.labLevelId = labLevelId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getLabStatus() {
        return labStatus;
    }

    public void setLabStatus(String labStatus) {
        this.labStatus = labStatus;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getResponserName() {
        return responserName;
    }

    public void setResponserName(String responserName) {
        this.responserName = responserName;
    }

    public String getResponsePhone() {
        return responsePhone;
    }

    public void setResponsePhone(String responsePhone) {
        this.responsePhone = responsePhone;
    }

    public int getCampusId() {
        return campusId;
    }

    public void setCampusId(int campusId) {
        this.campusId = campusId;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getLabPictureUrl() {
        return labPictureUrl;
    }

    public void setLabPictureUrl(String labPictureUrl) {
        this.labPictureUrl = labPictureUrl;
    }
}
