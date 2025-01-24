package model;

import java.util.ArrayList;
import java.util.HashMap;

public class VehicleMgmtModel extends CommonModel {
    
    private String successFlg;
    private String message;
    private String errormessage;
    private String userId;
    private String userName;
    private String roleType;
    
    
    private String conditionMaker;
    private String conditionCarName;
    private String conditionCarType;
    private String conditionDateFrom;
    private String conditionDateTo;

    
    private ArrayList<HashMap<String, Object>> searchList;
    
    
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
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getRoleType() {
        return roleType;
    }
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
    public String getConditionMaker() {
        return conditionMaker;
    }
    public void setConditionMaker(String conditionMaker) {
        this.conditionMaker = conditionMaker;
    }
    public String getConditionCarName() {
        return conditionCarName;
    }
    public void setConditionCarName(String conditionCarName) {
        this.conditionCarName = conditionCarName;
    }
    public String getConditionCarType() {
        return conditionCarType;
    }
    public void setConditionCarType(String conditionCarType) {
        this.conditionCarType = conditionCarType;
    }
    public String getConditionDateFrom() {
        return conditionDateFrom;
    }
    public void setConditionDateFrom(String conditionDateFrom) {
        this.conditionDateFrom = conditionDateFrom;
    }
    public String getConditionDateTo() {
        return conditionDateTo;
    }
    public void setConditionDateTo(String conditionDateTo) {
        this.conditionDateTo = conditionDateTo;
    }
    public ArrayList<HashMap<String, Object>> getSearchList() {
        return searchList;
    }
    public void setSearchList(ArrayList<HashMap<String, Object>> searchList) {
        this.searchList = searchList;
    }

    
    
}