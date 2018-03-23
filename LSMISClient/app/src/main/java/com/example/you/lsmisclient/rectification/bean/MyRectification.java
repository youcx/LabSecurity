package com.example.you.lsmisclient.rectification.bean;

/**
 * Created by You on 2018/2/6.
 */

public class MyRectification {
    private     String questionDesc;
    private     String labName;
    private     int changeId;
    private     String adviceChangeTime;

    public MyRectification(String adviceChangeTime, int changeId, String labName, String questionDesc) {
        this.adviceChangeTime = adviceChangeTime;
        this.changeId = changeId;
        this.labName = labName;
        this.questionDesc = questionDesc;
    }

    public String getAdviceChangeTime() {
        return adviceChangeTime;
    }

    public void setAdviceChangeTime(String adviceChangeTime) {
        this.adviceChangeTime = adviceChangeTime;
    }

    public int getChangeId() {
        return changeId;
    }

    public void setChangeId(int changeId) {
        this.changeId = changeId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }
}
