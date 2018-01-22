package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/22.
 */

public class Lab {
    String labName;
    String labCollege;
    String labLevel;
    String labHazard;
    String labTeacher;
    String labSafePerson;

    public Lab(String labName,String labCollege, String labLevel,String labHazard,   String labSafePerson, String labTeacher) {
        this.labCollege = labCollege;
        this.labHazard = labHazard;
        this.labLevel = labLevel;
        this.labName = labName;
        this.labSafePerson = labSafePerson;
        this.labTeacher = labTeacher;
    }

    public String getLabCollege() {
        return labCollege;
    }

    public void setLabCollege(String labCollege) {
        this.labCollege = labCollege;
    }

    public String getLabHazard() {
        return labHazard;
    }

    public void setLabHazard(String labHazard) {
        this.labHazard = labHazard;
    }

    public String getLabLevel() {
        return labLevel;
    }

    public void setLabLevel(String labLevel) {
        this.labLevel = labLevel;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getLabSafePerson() {
        return labSafePerson;
    }

    public void setLabSafePerson(String labSafePerson) {
        this.labSafePerson = labSafePerson;
    }

    public String getLabTeacher() {
        return labTeacher;
    }

    public void setLabTeacher(String labTeacher) {
        this.labTeacher = labTeacher;
    }
}
