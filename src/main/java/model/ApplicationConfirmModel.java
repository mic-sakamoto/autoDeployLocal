package model;

public class ApplicationConfirmModel extends CommonModel {
    
    private String successFlg;
    private String message;
    private String errormessage;
    private String userId;
    private String userName;
    private String roleType;
    
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

    
    
}