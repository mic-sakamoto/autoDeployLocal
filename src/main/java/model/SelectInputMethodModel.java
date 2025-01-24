package model;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectInputMethodModel extends CommonModel {

    private String successFlg;
    private String message;
    private String errormessage;

    private String userId;
    private String password;

    private String agreedFlg;

    private String inputSelectKbn;
    private String licenseNumber;

    private String inputMapJson;
    private HashMap<String, Object> inputMap;

    // MessageSend登録用
    private String customerId;
    private String sendMethod;
    private String mobileNum1;
    private String mobileNum2;
    private String mobileNum3;
    private String mail;


    public String getInputSelectKbn() {
        return inputSelectKbn;
    }

    public void setInputSelectKbn(String inputSelectKbn) {
        this.inputSelectKbn = inputSelectKbn;
    }

    public String getSendMethod() {
        return sendMethod;
    }

    public void setSendMethod(String sendMethod) {
        this.sendMethod = sendMethod;
    }

    public String getMobileNum1() {
        return mobileNum1;
    }

    public void setMobileNum1(String mobileNum1) {
        this.mobileNum1 = mobileNum1;
    }

    public String getMobileNum2() {
        return mobileNum2;
    }

    public void setMobileNum2(String mobileNum2) {
        this.mobileNum2 = mobileNum2;
    }

    public String getMobileNum3() {
        return mobileNum3;
    }

    public void setMobileNum3(String mobileNum3) {
        this.mobileNum3 = mobileNum3;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getInputMapJson() {
        return inputMapJson;
    }

    public void setInputMapJson(String inputMapJson) {
        this.inputMapJson = inputMapJson;
    }

    public HashMap<String, Object> getInputMap() {
        return inputMap;
    }

    public void setInputMap(HashMap<String, Object> inputMap) {
        this.inputMap = inputMap;
    }

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

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

}