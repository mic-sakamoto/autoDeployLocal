package model;

import java.util.ArrayList;
import java.util.HashMap;

public class TopModel extends CommonModel {
    
    private String successFlg;
    private String message;
    private String errormessage;
    private String userId;
    private String userName;
    private String roleType;
    private ArrayList<HashMap<String, Object>> result;
    
    private String displayAuthority;
    private String notifyScreeningResult;
    private String notifyMainResult;
    private String notifyKbn;
    private String mailAddress;
    private String telNumber;
    
    
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
    public String getRoleType() {
        return roleType;
    }
    public void setRoleType(String roleType) {
        this.roleType = roleType;
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
    public ArrayList<HashMap<String, Object>> getResult() {
        return result;
    }
    public void setResult(ArrayList<HashMap<String, Object>> result) {
        this.result = result;
    }
    public String getNotifyScreeningResult() {
        return notifyScreeningResult;
    }
    public void setNotifyScreeningResult(String notifyScreeningResult) {
        this.notifyScreeningResult = notifyScreeningResult;
    }
    public String getNotifyMainResult() {
        return notifyMainResult;
    }
    public void setNotifyMainResult(String notifyMainResult) {
        this.notifyMainResult = notifyMainResult;
    }
    public String getNotifyKbn() {
        return notifyKbn;
    }
    public void setNotifyKbn(String notifyKbn) {
        this.notifyKbn = notifyKbn;
    }
    public String getMailAddress() {
        return mailAddress;
    }
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
    public String getTelNumber() {
        return telNumber;
    }
    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
    public String getDisplayAuthority() {
        return displayAuthority;
    }
    public void setDisplayAuthority(String displayAuthority) {
        this.displayAuthority = displayAuthority;
    }
    
    
    
}