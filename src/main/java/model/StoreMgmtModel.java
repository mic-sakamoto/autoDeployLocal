package model;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreMgmtModel extends CommonModel {
    
    private String successFlg;
    private String message;
    private String errormessage;
    private String userId;
    private String userName;
    private String roleType;
    
    
    private String conditionStoreName;
    private String conditionStoreId;

    
    private ArrayList<HashMap<String, Object>> searchList;
    
    private String proxyLoginUserId;
    
    
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
    public String getConditionStoreName() {
        return conditionStoreName;
    }
    public void setConditionStoreName(String conditionStoreName) {
        this.conditionStoreName = conditionStoreName;
    }
    public String getConditionStoreId() {
        return conditionStoreId;
    }
    public void setConditionStoreId(String conditionStoreId) {
        this.conditionStoreId = conditionStoreId;
    }
    public ArrayList<HashMap<String, Object>> getSearchList() {
        return searchList;
    }
    public void setSearchList(ArrayList<HashMap<String, Object>> searchList) {
        this.searchList = searchList;
    }
    public String getProxyLoginUserId() {
        return proxyLoginUserId;
    }
    public void setProxyLoginUserId(String proxyLoginUserId) {
        this.proxyLoginUserId = proxyLoginUserId;
    }

    
    
}