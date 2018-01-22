package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/21.
 */

public class LabHazard {
    String hazardName;
    String labCount;

    public LabHazard(String hazardName, String labCount) {
        this.hazardName = hazardName;
        this.labCount = labCount;
    }

    public String getHazardName() {
        return hazardName;
    }

    public void setHazardName(String hazardName) {
        this.hazardName = hazardName;
    }

    public String getLabCount() {
        return labCount;
    }

    public void setLabCount(String labCount) {
        this.labCount = labCount;
    }
}
