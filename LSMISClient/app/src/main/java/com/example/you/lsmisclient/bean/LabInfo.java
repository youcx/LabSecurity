package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/22.
 */

public class LabInfo {
    int ID;
    //实验室具体等级id
    int labLevelId;
    String labName;
    String buildId;
    //实验室具体等级
    String detailLevelName;
    //实验室等级《国家级等》
    String mainLevelName;
    //所属学院id和名称
    String departmentId;
    String departmentName;
    String labAddr;
    String denoterInfor;
    String area;
    String responserName;
    String responsePhone;
    String managerName;
    String managerPhone;
    String labStatus;
    String submitPersonName;

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
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLabAddr() {
        return labAddr;
    }

    public void setLabAddr(String labAddr) {
        this.labAddr = labAddr;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getDenoterInfor() {
        return denoterInfor;
    }

    public void setDenoterInfor(String denoterInfor) {
        this.denoterInfor = denoterInfor;
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

    public String getSubmitPersonName() {
        return submitPersonName;
    }

    public void setSubmitPersonName(String submitPersonName) {
        this.submitPersonName = submitPersonName;
    }

    @Override
    public String toString() {
        return "{\"LabInfo\":{"
                + "\"area\":\"" + area + "\""
                + ", \"ID\":\"" + ID + "\""
                + ", \"labLevelId\":\"" + labLevelId + "\""
                + ", \"labName\":\"" + labName + "\""
                + ", \"buildId\":\"" + buildId + "\""
                + ", \"detailLevelName\":\"" + detailLevelName + "\""
                + ", \"mainLevelName\":\"" + mainLevelName + "\""
                + ", \"departmentId\":\"" + departmentId + "\""
                + ", \"departmentName\":\"" + departmentName + "\""
                + ", \"labAddr\":\"" + labAddr + "\""
                + ", \"denoterInfor\":\"" + denoterInfor + "\""
                + ", \"responserName\":\"" + responserName + "\""
                + ", \"responsePhone\":\"" + responsePhone + "\""
                + ", \"managerName\":\"" + managerName + "\""
                + ", \"managerPhone\":\"" + managerPhone + "\""
                + ", \"labStatus\":\"" + labStatus + "\""
                + ", \"submitPersonName\":\"" + submitPersonName + "\""
                + "}}";
    }
}
