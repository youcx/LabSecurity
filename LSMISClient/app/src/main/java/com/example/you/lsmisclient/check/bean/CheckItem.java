package com.example.you.lsmisclient.check.bean;

/**
 * Created by You on 2018/2/5.
 */

import org.litepal.crud.DataSupport;

/**
 * 继承自DataSupport作为数据表操作
 * checkTitle	    string	检查项的名称
 *checkImportant	string	检查项的检查要点
 *titleId	         int	检查项的id
 *titleSerialNumber	 string	检查项的序号
 *useFlag	         int	1表示适用，0表示不适用
 */
public class CheckItem extends DataSupport{
   private String checkTitle;
   private String checkImportant;
   private int titleId;
   private String titleSerialNumber;
   private int useFlag;

    public String getCheckImportant() {
        return checkImportant;
    }

    public void setCheckImportant(String checkImportant) {
        this.checkImportant = checkImportant;
    }

    public String getCheckTitle() {
        return checkTitle;
    }

    public void setCheckTitle(String checkTitle) {
        this.checkTitle = checkTitle;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public String getTitleSerialNumber() {
        return titleSerialNumber;
    }

    public void setTitleSerialNumber(String titleSerialNumber) {
        this.titleSerialNumber = titleSerialNumber;
    }

    public int getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(int useFlag) {
        this.useFlag = useFlag;
    }

    @Override
    public String toString() {
        return "{\"CheckItem\":{"
                + "\"checkImportant\":\"" + checkImportant + "\""
                + ", \"checkTitle\":\"" + checkTitle + "\""
                + ", \"titleId\":\"" + titleId + "\""
                + ", \"titleSerialNumber\":\"" + titleSerialNumber + "\""
                + ", \"useFlag\":\"" + useFlag + "\""
                + "}}";
    }
}
