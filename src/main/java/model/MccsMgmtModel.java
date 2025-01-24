package model;

import java.util.ArrayList;
import java.util.HashMap;

public class MccsMgmtModel extends CommonModel {
    
    private String successFlg;
    private String message;
    private String errormessage;
    private String userId;
    private String userName;
    private String roleType;
    
    private ArrayList<HashMap<String, Object>> result;
    
    private String conditionUserName;
    private String conditionCustomerId;
    private String conditionModel;
    private String conditionGrade;
    private String conditionMccsStatusType;
    private String conditionWebAppId;
    private String conditionMccsAttachtDateFrom;
    private String conditionMccsAttachtDateTo;
    private String conditionMainAppDateFrom;
    private String conditionMainAppDateTo;
    private String conditionVerifiedDateFrom;
    private String conditionVerifiedDateTo;
    private String conditionUpdateDateFrom;
    private String conditionUpdateDateTo;
    
    
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
    public ArrayList<HashMap<String, Object>> getResult() {
        return result;
    }
    public void setResult(ArrayList<HashMap<String, Object>> result) {
        this.result = result;
    }
    public String getConditionUserName() {
        return conditionUserName;
    }
    public void setConditionUserName(String conditionUserName) {
        this.conditionUserName = conditionUserName;
    }
    public String getConditionCustomerId() {
        return conditionCustomerId;
    }
    public void setConditionCustomerId(String conditionCustomerId) {
        this.conditionCustomerId = conditionCustomerId;
    }
    public String getConditionModel() {
        return conditionModel;
    }
    public void setConditionModel(String conditionModel) {
        this.conditionModel = conditionModel;
    }
    public String getConditionMccsStatusType() {
        return conditionMccsStatusType;
    }
    public void setConditionMccsStatusType(String conditionMccsStatusType) {
        this.conditionMccsStatusType = conditionMccsStatusType;
    }
    public String getConditionWebAppId() {
        return conditionWebAppId;
    }
    public void setConditionWebAppId(String conditionWebAppId) {
        this.conditionWebAppId = conditionWebAppId;
    }
    public String getConditionMccsAttachtDateFrom() {
        return conditionMccsAttachtDateFrom;
    }
    public void setConditionMccsAttachtDateFrom(String conditionMccsAttachtDateFrom) {
        this.conditionMccsAttachtDateFrom = conditionMccsAttachtDateFrom;
    }
    public String getConditionMccsAttachtDateTo() {
        return conditionMccsAttachtDateTo;
    }
    public void setConditionMccsAttachtDateTo(String conditionMccsAttachtDateTo) {
        this.conditionMccsAttachtDateTo = conditionMccsAttachtDateTo;
    }
    public String getConditionMainAppDateFrom() {
        return conditionMainAppDateFrom;
    }
    public void setConditionMainAppDateFrom(String conditionMainAppDateFrom) {
        this.conditionMainAppDateFrom = conditionMainAppDateFrom;
    }
    public String getConditionMainAppDateTo() {
        return conditionMainAppDateTo;
    }
    public void setConditionMainAppDateTo(String conditionMainAppDateTo) {
        this.conditionMainAppDateTo = conditionMainAppDateTo;
    }
    public String getConditionVerifiedDateFrom() {
        return conditionVerifiedDateFrom;
    }
    public void setConditionVerifiedDateFrom(String conditionVerifiedDateFrom) {
        this.conditionVerifiedDateFrom = conditionVerifiedDateFrom;
    }
    public String getConditionVerifiedDateTo() {
        return conditionVerifiedDateTo;
    }
    public void setConditionVerifiedDateTo(String conditionVerifiedDateTo) {
        this.conditionVerifiedDateTo = conditionVerifiedDateTo;
    }
    public String getConditionUpdateDateFrom() {
        return conditionUpdateDateFrom;
    }
    public void setConditionUpdateDateFrom(String conditionUpdateDateFrom) {
        this.conditionUpdateDateFrom = conditionUpdateDateFrom;
    }
    public String getConditionUpdateDateTo() {
        return conditionUpdateDateTo;
    }
    public void setConditionUpdateDateTo(String conditionUpdateDateTo) {
        this.conditionUpdateDateTo = conditionUpdateDateTo;
    }
    public String getConditionGrade() {
        return conditionGrade;
    }
    public void setConditionGrade(String conditionGrade) {
        this.conditionGrade = conditionGrade;
    }
    
    
    
}