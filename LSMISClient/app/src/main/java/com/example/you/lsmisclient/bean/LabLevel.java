package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/21.
 */

public class LabLevel {
    String levelName;
    String labCount;
    int labLevel;

    public LabLevel(String levelName,String labCount ) {
        this.labCount = labCount;
        this.levelName = levelName;
    }

    public LabLevel(String labCount, int labLevel, String levelName) {
        this.labCount = labCount;
        this.labLevel = labLevel;
        this.levelName = levelName;
    }

    public LabLevel(String levelName) {
        this.levelName = levelName;
    }

    public int getLabLevel() {
        return labLevel;
    }

    public void setLabLevel(int labLevel) {
        this.labLevel = labLevel;
    }

    public String getLabCount() {
        return labCount;
    }

    public void setLabCount(String labCount) {
        this.labCount = labCount;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
