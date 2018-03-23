package com.example.you.lsmisclient.rectification.bean;

/**
 * Created by You on 2018/3/4.
 */

/**
 * changeId	        int	整改意见的id
    questionDesc	string	问题描述
    changeAdvice	string	整改意见
    adviceChangeTime	string	建议整改时间
    resultId	        int	所属项检查结果的id
     pubTime	        string	发布日期
    picFilenameList	    json	图片的文件的名称
    videoFilename	    string	视频的文件的名称
    recordMemberId	    string	上传人员的id
    recordMemberName	string	上传人员的姓名
    labId	int	实验室的id
    titleSerialNumber	string	对应的检查项的序号
    checkTitle	        string	对应的检查项的标题
 */

public class ReformDetail {
    private int changeId;
    private String questionDesc;
    private String changeAdvice;
    private String adviceChangeTime;
    private int resultId;
    private String pubTime;
    private String[] picFilenameList;
    private String videoFielname;
    private String recordMemberId;
    private String recordMemberName;
    private int labId;
    private String titleSerialNumber;
    private String checkTitle;


    public String getAdviceChangeTime() {
        return adviceChangeTime;
    }

    public void setAdviceChangeTime(String adviceChangeTime) {
        this.adviceChangeTime = adviceChangeTime;
    }

    public String getChangeAdvice() {
        return changeAdvice;
    }

    public void setChangeAdvice(String changeAdvice) {
        this.changeAdvice = changeAdvice;
    }

    public int getChangeId() {
        return changeId;
    }

    public void setChangeId(int changeId) {
        this.changeId = changeId;
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    public String[] getPicFilenameList() {
        return picFilenameList;
    }

    public void setPicFilenameList(String[] picFilenameList) {
        this.picFilenameList = picFilenameList;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public String getRecordMemberId() {
        return recordMemberId;
    }

    public void setRecordMemberId(String recordMemberId) {
        this.recordMemberId = recordMemberId;
    }

    public String getRecordMemberName() {
        return recordMemberName;
    }

    public void setRecordMemberName(String recordMemberName) {
        this.recordMemberName = recordMemberName;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public String getVideoFielname() {
        return videoFielname;
    }

    public void setVideoFielname(String videoFielname) {
        this.videoFielname = videoFielname;
    }

    public String getCheckTitle() {
        return checkTitle;
    }

    public void setCheckTitle(String checkTitle) {
        this.checkTitle = checkTitle;
    }

    public String getTitleSerialNumber() {
        return titleSerialNumber;
    }

    public void setTitleSerialNumber(String titleSerialNumber) {
        this.titleSerialNumber = titleSerialNumber;
    }
}
