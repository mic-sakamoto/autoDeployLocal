package model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class PaymentDetailMgmtModel extends CommonModel {
    
    private String successFlg;
    private String message;
    private String errormessage;
    private String userId;
    private String userName;
    private String roleType;
    
    private ArrayList<HashMap<String, Object>> result;
    
    private String conditionPaymentDateFrom;
    private String conditionPaymentDateTo;
    
    /** PDF用・EXCEL用 **/
    private String fileName;
    private InputStream inputStream;
    private long contentLength;
    
    
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
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public InputStream getInputStream() {
        return inputStream;
    }
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    public long getContentLength() {
        return contentLength;
    }
    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }
    public String getConditionPaymentDateFrom() {
        return conditionPaymentDateFrom;
    }
    public void setConditionPaymentDateFrom(String conditionPaymentDateFrom) {
        this.conditionPaymentDateFrom = conditionPaymentDateFrom;
    }
    public String getConditionPaymentDateTo() {
        return conditionPaymentDateTo;
    }
    public void setConditionPaymentDateTo(String conditionPaymentDateTo) {
        this.conditionPaymentDateTo = conditionPaymentDateTo;
    }

    
    
    

    
    
}