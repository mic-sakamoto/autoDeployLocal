package model;

public class PasswordSetModel extends CommonModel {
    
    private String successFlg;
    private String message;
    private String errormessage;

    private String userId;
    private String password;
    
    private String agreedFlg;
    
    public String getSuccessFlg() {
        return successFlg;
    }
    public void setSuccessFlg(String successFlg) {
        this.successFlg = successFlg;
    }
    public String getAgreedFlg() {
        return agreedFlg;
    }
    public void setAgreedFlg(String agreedFlg) {
        this.agreedFlg = agreedFlg;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
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



}