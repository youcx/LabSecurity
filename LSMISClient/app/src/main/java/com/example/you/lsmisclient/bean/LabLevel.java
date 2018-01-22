package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/21.
 */

public class LabLevel {
    String levelName;
    String labCount;

    public LabLevel(String levelName, String labCount) {
        this.labCount = labCount;
        this.levelName = levelName;
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
