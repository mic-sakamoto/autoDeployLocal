package model;

import java.util.ArrayList;
import java.util.HashMap;

public class AnnouncementMgmtModel extends CommonModel {
    
    private String successFlg;
    private String message;
    private String errormessage;

    private String userId;
    private String password;
    
    private String agreedFlg;
    
    private String seq;
    private ArrayList<HashMap<String, Object>> result;
    
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
    public ArrayList<HashMap<String, Object>> getResult() {
        return result;
    }
    public void setResult(ArrayList<HashMap<String, Object>> result) {
        this.result = result;
    }
    public String getSeq() {
        return seq;
    }
    public void setSeq(String seq) {
        this.seq = seq;
    }
    



}