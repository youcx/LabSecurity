package com.example.you.lsmisclient.lab.bean;

/**
 * Created by You on 2018/3/8.
 */

public class LabBuild {
    private int buildId;
    private String buildName;
    private int labId;
    private String roomNumb;

    public int getBuildId() {
        return buildId;
    }

    public void setBuildId(int buildId) {
        this.buildId = buildId;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    public String getRoomNumb() {
        return roomNumb;
    }

    public void setRoomNumb(String roomNumb) {
        this.roomNumb = roomNumb;
    }
}
