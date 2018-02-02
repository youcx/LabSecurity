package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/25.
 */

/**
 * taskId	int	任务的id
 * taskTitle	string	任务的标题
 * taskCode	string	任务编码（忽略这个字段）
 * taskDesc	string	任务描述
 * checkBeginTime	string	任务的开始时间
 * checkEndTime	string	任务的截至时间
 * pubMemberId	string	发布人员的id
 * pubTime	string	发布时间
 * incLabCoubt	int	任务涉及实验室的数量
 * checkedLabCount	int	已经检查了的实验室的数量
 * totalCheckTitleCount	int	检查的项的数量
 * unMatchTitleCount	int	不符合项的数量
 * changingTitleCount	int	正在整改的项的数量
 * typeId	int	任务类型
 */

public class CheckMission {
    int taskId;
    String taskTitle;
    String taskCode;
    String taskDesc;
    //任务时间
    String checkBeginTime;
    String checkEndTime;

    String pubMemberId;
    String pubTiem;

    int incLabCount;
    int checkedLabCount;
    int totalCheckTitleCount;
    int unMatchTitleCount;
    int changingTitleCount;
    int typeId;

    int leftDay;


    public CheckMission() {

    }

    public int getLeftDay() {
        return leftDay;
    }

    public void setLeftDay(int leftDay) {
        this.leftDay = leftDay;
    }

    public int getChangingTitleCount() {
        return changingTitleCount;
    }

    public void setChangingTitleCount(int changingTitleCount) {
        this.changingTitleCount = changingTitleCount;
    }

    public String getCheckBeginTime() {
        return checkBeginTime;
    }

    public void setCheckBeginTime(String checkBeginTime) {
        this.checkBeginTime = checkBeginTime;
    }

    public int getCheckedLabCount() {
        return checkedLabCount;
    }

    public void setCheckedLabCount(int checkedLabCount) {
        this.checkedLabCount = checkedLabCount;
    }

    public String getCheckEndTime() {
        return checkEndTime;
    }

    public void setCheckEndTime(String checkEndTime) {
        this.checkEndTime = checkEndTime;
    }

    public int getIncLabCount() {
        return incLabCount;
    }

    public void setIncLabCount(int incLabCoubt) {
        this.incLabCount = incLabCoubt;
    }

    public String getPubMemberId() {
        return pubMemberId;
    }

    public void setPubMemberId(String pubMemberId) {
        this.pubMemberId = pubMemberId;
    }

    public String getPubTiem() {
        return pubTiem;
    }

    public void setPubTiem(String pubTiem) {
        this.pubTiem = pubTiem;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public int getTotalCheckTitleCount() {
        return totalCheckTitleCount;
    }

    public void setTotalCheckTitleCount(int totalCheckTitleCount) {
        this.totalCheckTitleCount = totalCheckTitleCount;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getUnMatchTitleCount() {
        return unMatchTitleCount;
    }

    public void setUnMatchTitleCount(int unMatchTitleCount) {
        this.unMatchTitleCount = unMatchTitleCount;
    }

    @Override
    public String toString() {
        return "{\"CheckMission\":{"
                + "\"changingTitleCount\":\"" + changingTitleCount + "\""
                + ", \"taskId\":\"" + taskId + "\""
                + ", \"taskTitle\":\"" + taskTitle + "\""
                + ", \"taskCode\":\"" + taskCode + "\""
                + ", \"taskDesc\":\"" + taskDesc + "\""
                + ", \"checkBeginTime\":\"" + checkBeginTime + "\""
                + ", \"checkEndTime\":\"" + checkEndTime + "\""
                + ", \"pubMemberId\":\"" + pubMemberId + "\""
                + ", \"pubTiem\":\"" + pubTiem + "\""
                + ", \"incLabCount\":\"" + incLabCount + "\""
                + ", \"checkedLabCount\":\"" + checkedLabCount + "\""
                + ", \"totalCheckTitleCount\":\"" + totalCheckTitleCount + "\""
                + ", \"unMatchTitleCount\":\"" + unMatchTitleCount + "\""
                + ", \"typeId\":\"" + typeId + "\""
                + "}}";
    }
}
