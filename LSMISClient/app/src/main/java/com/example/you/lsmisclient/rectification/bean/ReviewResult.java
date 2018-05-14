package com.example.you.lsmisclient.rectification.bean;

/**
 * Created by You on 2018/3/13.
 */

public class ReviewResult {
    private String msg;
    private int status;
    private ChangeRecordDetail changeRecord;
    private CheckRecordDetail  advice;
    private String parentPath;
    private String adviceParentPath;

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getAdviceParentPath() {
        return adviceParentPath;
    }

    public void setAdviceParentPath(String adviceParentPath) {
        this.adviceParentPath = adviceParentPath;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ChangeRecordDetail getChangeRecord() {
        return changeRecord;
    }

    public void setChangeRecord(ChangeRecordDetail changeRecord) {
        this.changeRecord = changeRecord;
    }

    public CheckRecordDetail getAdvice() {
        return advice;
    }

    public void setAdvice(CheckRecordDetail advice) {
        this.advice = advice;
    }
}
