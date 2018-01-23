package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/22.
 */

public class LabInfo {
    String labLevelId;
    String buildId;
    String departmentId;
    String labName;
    String labAddr;
    String denoterInfor;
    String area;
    String responseName;
    String responsePhone;
    String managerName;
    String managerPhone;
    String labStatus;
    String submitPersonName;

    public LabInfo() {
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

    public String getLabLevelId() {
        return labLevelId;
    }

    public void setLabLevelId(String labLevelId) {
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

    public String getResponseName() {
        return responseName;
    }

    public void setResponseName(String responseName) {
        this.responseName = responseName;
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
                + ", \"labLevelId\":\"" + labLevelId + "\""
                + ", \"buildId\":\"" + buildId + "\""
                + ", \"departmentId\":\"" + departmentId + "\""
                + ", \"labName\":\"" + labName + "\""
                + ", \"denoterInfor\":\"" + denoterInfor + "\""
                + ", \"responseName\":\"" + responseName + "\""
                + ", \"responsePhone\":\"" + responsePhone + "\""
                + ", \"managerName\":\"" + managerName + "\""
                + ", \"managerPhone\":\"" + managerPhone + "\""
                + ", \"labStatus\":\"" + labStatus + "\""
                + ", \"submitPersonName\":\"" + submitPersonName + "\""
                + "}}";
    }
}
