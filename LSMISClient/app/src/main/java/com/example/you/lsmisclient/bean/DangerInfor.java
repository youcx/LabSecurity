package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/3/8.
 * 危险源信息
 */

public class DangerInfor {
    private String dangerMainTypeName;
    private String dangerTypeName;
    private String dangerDetail;

    public String getDangerDetail() {
        return dangerDetail;
    }

    public void setDangerDetail(String dangerDetail) {
        this.dangerDetail = dangerDetail;
    }

    public String getDangerMainTypeName() {
        return dangerMainTypeName;
    }

    public void setDangerMainTypeName(String dangerMainTypeName) {
        this.dangerMainTypeName = dangerMainTypeName;
    }

    public String getDangerTypeName() {
        return dangerTypeName;
    }

    public void setDangerTypeName(String dangerTypeName) {
        this.dangerTypeName = dangerTypeName;
    }
}
