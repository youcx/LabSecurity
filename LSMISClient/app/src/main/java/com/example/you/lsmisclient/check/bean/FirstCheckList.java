package com.example.you.lsmisclient.check.bean;

/**
 * Created by You on 2018/2/3.
 */

/**
 * 参数名	类型	说明
 *checkGroupId	int	当前目录的id
 *groupLevel	int	0表示第一级别目录
 *groupLevelName	string	当前目录的名称
 *groupSerialNumber	string	当前目录的序号
 *versionId	int	当前目录所属的版本的id
 */
public class FirstCheckList {

    int checkGroupId;
    int groupLevel;
    String groupLevelName;
    String groupSerialNumber;
    int versionId;

    public int getCheckGroupId() {
        return checkGroupId;
    }

    public void setCheckGroupId(int checkGroupId) {
        this.checkGroupId = checkGroupId;
    }

    public int getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(int groupLevel) {
        this.groupLevel = groupLevel;
    }

    public String getGroupLevelName() {
        return groupLevelName;
    }

    public void setGroupLevelName(String groupLevelName) {
        this.groupLevelName = groupLevelName;
    }

    public String getGroupSerialNumber() {
        return groupSerialNumber;
    }

    public void setGroupSerialNumber(String groupSerialNumber) {
        this.groupSerialNumber = groupSerialNumber;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }
}
