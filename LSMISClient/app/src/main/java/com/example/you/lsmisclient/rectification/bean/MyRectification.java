package com.example.you.lsmisclient.rectification.bean;

/**
 * Created by You on 2018/2/6.
 */

public class MyRectification {
    private     String descrip;
    private     String remainTime;
    private     String checkTime;

    public MyRectification(String descrip, String remainTime,String checkTime) {
        this.checkTime = checkTime;
        this.descrip = descrip;
        this.remainTime = remainTime;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(String remainTime) {
        this.remainTime = remainTime;
    }
}
