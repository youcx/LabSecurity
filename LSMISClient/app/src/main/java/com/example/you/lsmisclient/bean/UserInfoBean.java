package com.example.you.lsmisclient.bean;

import java.util.Date;

/**
 * Created by chendian on 2018/1/27.
 */
public class UserInfoBean {


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getWorkPlaceID() {
        return workPlaceID;
    }

    public void setWorkPlaceID(int workPlaceID) {
        this.workPlaceID = workPlaceID;
    }

    public int getCollegeID() {
        return collegeID;
    }

    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Date getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(Date registeredTime) {
        this.registeredTime = registeredTime;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }
    private String userID;
    private int workPlaceID;
    private int collegeID;
    private String userName;
    private String phoneNum;
    private Date registeredTime;
    private String accountName;
    private String passwd;

    //0 未知，1男，2女
    private int  sex;
    private String email;
    private String photo;

    //可视信息
    private String college; //学院
    private String workPlace; // 职位　<----  workPlaceID
}
