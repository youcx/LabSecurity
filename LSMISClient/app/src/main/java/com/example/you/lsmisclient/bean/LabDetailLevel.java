package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/26.
 */

public class LabDetailLevel {
    int levelId;
    String levelName;
    String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public String toString() {
        return "{\"LabDetailLevel\":{"
                + "\"description\":\"" + description + "\""
                + ", \"levelId\":\"" + levelId + "\""
                + ", \"levelName\":\"" + levelName + "\""
                + "}}";
    }
}
