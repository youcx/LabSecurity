package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/28.
 */

public class CheckMissionType {
    String typeName;

    public CheckMissionType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "{\"CheckMissionType\":{"
                + "\"typeName\":\"" + typeName + "\""
                + "}}";
    }
}
