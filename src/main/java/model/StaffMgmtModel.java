package model;

import java.util.ArrayList;
import java.util.HashMap;

public class StaffMgmtModel extends CommonModel {
    private String successFlg;
    private String message;
    private String errormessage;
    private String password;
    private String userId;
    private String isAdmin;
    private String userName;
    private String updateTime;
    private ArrayList<HashMap<String, Object>> newMemberRegistration;
    private String newpassword;
    private String newuserId;
    private Integer newisAdmin;
    private String newPassword;
    private String newUserId;
    private Integer newIsAdmin;
    private ArrayList<HashMap<String, Object>> searchList;
    private String listUserId;
    private String updatePassword;
    

    public String getSuccessFlg() {
        return successFlg;
    }
    public void setSuccessFlg(String successFlg) {
        this.successFlg = successFlg;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<HashMap<String, Object>> getNewMemberRegistration() {
        return newMemberRegistration;
    }
    public void setNewMemberRegistration(ArrayList<HashMap<String, Object>> newMemberRegistration) {
        this.newMemberRegistration = newMemberRegistration;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getErrormessage() {
        return errormessage;
    }
    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getIsAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String username) {
        this.userName = username;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public ArrayList<HashMap<String, Object>> getSearchList() {
        return searchList;
    }
    public void setSearchList(ArrayList<HashMap<String, Object>> searchList) {
        this.searchList = searchList;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getNewUserId() {
        return newUserId;
    }
    public void setNewUserId(String newUserId) {
        this.newUserId = newUserId;
    }
    public Integer getNewIsAdmin() {
        return newIsAdmin;
    }
    public void setNewIsAdmin(Integer newIsAdmin) {
        this.newIsAdmin = newIsAdmin;
    }
    public String getListUserId() {
        return listUserId;
    }
    public void setListUserId(String listUserId) {
        this.listUserId = listUserId;
    }
    public String getUpdatePassword() {
        return updatePassword;
    }
    public void setUpdatePassword(String updatePassword) {
        this.updatePassword = updatePassword;
    }
    public String getNewpassword() {
        return newpassword;
    }
    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
    public String getNewuserId() {
        return newuserId;
    }
    public void setNewuserId(String newuserId) {
        this.newuserId = newuserId;
    }
    public Integer getNewisAdmin() {
        return newisAdmin;
    }
    public void setNewisAdmin(Integer newisAdmin) {
        this.newisAdmin = newisAdmin;
    }


}