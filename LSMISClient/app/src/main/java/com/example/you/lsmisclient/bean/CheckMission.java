package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/25.
 */

public class CheckMission {
    String missionName;
    String missionDescription;
    String missionStartTime;
    String missionEndTime;
    String missionRemainingTime;
    String missionAllLabs;
    String missionCheckedLabs;
    String missionRemainingLabs;
    String alreadlyCheckedCount;
    String inconformityCount;
    String alreadlyReformCount;
    String inReformCount;

    public CheckMission() {

    }

    public String getAlreadlyCheckedCount() {
        return alreadlyCheckedCount;
    }

    public void setAlreadlyCheckedCount(String alreadlyCheckedCount) {
        this.alreadlyCheckedCount = alreadlyCheckedCount;
    }

    public String getAlreadlyReformCount() {
        return alreadlyReformCount;
    }

    public void setAlreadlyReformCount(String alreadlyReformCount) {
        this.alreadlyReformCount = alreadlyReformCount;
    }

    public String getInconformityCount() {
        return inconformityCount;
    }

    public void setInconformityCount(String inconformityCount) {
        this.inconformityCount = inconformityCount;
    }

    public String getInReformCount() {
        return inReformCount;
    }

    public void setInReformCount(String inReformCount) {
        this.inReformCount = inReformCount;
    }

    public String getMissionAllLabs() {
        return missionAllLabs;
    }

    public void setMissionAllLabs(String missionAllLabs) {
        this.missionAllLabs = missionAllLabs;
    }

    public String getMissionCheckedLabs() {
        return missionCheckedLabs;
    }

    public void setMissionCheckedLabs(String missionCheckedLabs) {
        this.missionCheckedLabs = missionCheckedLabs;
    }

    public String getMissionDescription() {
        return missionDescription;
    }

    public void setMissionDescription(String missionDescription) {
        this.missionDescription = missionDescription;
    }

    public String getMissionEndTime() {
        return missionEndTime;
    }

    public void setMissionEndTime(String missionEndTime) {
        this.missionEndTime = missionEndTime;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getMissionRemainingLabs() {
        return missionRemainingLabs;
    }

    public void setMissionRemainingLabs(String missionRemainingLabs) {
        this.missionRemainingLabs = missionRemainingLabs;
    }

    public String getMissionRemainingTime() {
        return missionRemainingTime;
    }

    public void setMissionRemainingTime(String missionRemainingTime) {
        this.missionRemainingTime = missionRemainingTime;
    }

    public String getMissionStartTime() {
        return missionStartTime;
    }

    public void setMissionStartTime(String missionStartTime) {
        this.missionStartTime = missionStartTime;
    }

    @Override
    public String toString() {
        return "{\"CheckMission\":{"
                + "\"alreadlyCheckedCount\":\"" + alreadlyCheckedCount + "\""
                + ", \"missionName\":\"" + missionName + "\""
                + ", \"missionDescription\":\"" + missionDescription + "\""
                + ", \"missionStartTime\":\"" + missionStartTime + "\""
                + ", \"missionEndTime\":\"" + missionEndTime + "\""
                + ", \"missionRemainingTime\":\"" + missionRemainingTime + "\""
                + ", \"missionAllLabs\":\"" + missionAllLabs + "\""
                + ", \"missionCheckedLabs\":\"" + missionCheckedLabs + "\""
                + ", \"missionRemainingLabs\":\"" + missionRemainingLabs + "\""
                + ", \"inconformityCount\":\"" + inconformityCount + "\""
                + ", \"alreadlyReformCount\":\"" + alreadlyReformCount + "\""
                + ", \"inReformCount\":\"" + inReformCount + "\""
                + "}}";
    }
}
