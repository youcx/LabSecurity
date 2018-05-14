package com.example.you.lsmisclient.rectification.bean;

/**
 * Created by You on 2018/4/3.
 */

/**
 * recordId	 int	整改记录的id
    changeId	int	所属整改意见的id
    changeContent	string	整改内容
    uploadMemberId	string	上传人员的id
    uploadMemberName	string	上传人员的姓名
    uploaTime	string	上传时间
    picFilenameList	    json	上传的图片文件的名称
    videoFilename	    string	视频的文件的名称
 */
public class ChangeRecordDetail {
    private int recordId;
    private int changeId;
    private String changeContent;
    private String uploadMemberId;
    private String uploadMemberName;
    private String uploadTime;
    private String[] picFilenameList;
    private String videoFilename;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getChangeId() {
        return changeId;
    }

    public void setChangeId(int changeId) {
        this.changeId = changeId;
    }

    public String getChangeContent() {
        return changeContent;
    }

    public void setChangeContent(String changeContent) {
        this.changeContent = changeContent;
    }

    public String getUploadMemberId() {
        return uploadMemberId;
    }

    public void setUploadMemberId(String uploadMemberId) {
        this.uploadMemberId = uploadMemberId;
    }

    public String getUploadMemberName() {
        return uploadMemberName;
    }

    public void setUploadMemberName(String uploadMemberName) {
        this.uploadMemberName = uploadMemberName;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String[] getPicFilenameList() {
        return picFilenameList;
    }

    public void setPicFilenameList(String[] picFilenameList) {
        this.picFilenameList = picFilenameList;
    }

    public String getVideoFilename() {
        return videoFilename;
    }

    public void setVideoFilename(String videoFilename) {
        this.videoFilename = videoFilename;
    }
}
