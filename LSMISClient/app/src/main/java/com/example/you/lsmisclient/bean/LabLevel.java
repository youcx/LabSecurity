package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/21.
 */

public class LabLevel {
    String levelName;
    String labNumb;
    //实验室大等级ID
    int labLevel;

    public LabLevel(String levelName,String labNumb) {
        this.labNumb = labNumb;
        this.levelName = levelName;
    }

    public LabLevel(String labNumb, int labLevel, String levelName) {
        this.labNumb = labNumb;
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

    public String getLabNumb() {
        return labNumb;
    }

    public void setLabNumb(String labNumb) {
        this.labNumb = labNumb;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
