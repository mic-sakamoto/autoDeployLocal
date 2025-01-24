package model;

import java.util.ArrayList;
import java.util.HashMap;

public class ApplicationModel extends CommonModel {

    private String successFlg;
    private String message;
    private String errormessage;
    private ArrayList<HashMap<String,String>> errorList;

    private String daikoStaffId;
    
    private String contractId;

    // 申込区分（審査申込[1] 不備返却再入力[2] 審査結果×再入力[3] 本申込[4]）
    private String appKbn;
    // 申込者のロール
    private String inputRoleKbn;

    // 申込入力方法選択画面 申込人タイプ（個人[0]or法人[1]）
    private String moshikomiKbn;
    // 申込入力方法選択画面 申込者免許証番号
    private String licenseNumber;
    // 申込入力方法選択画面 法人番号
    private String hojinNumber;
    // 申込入力方法選択画面 申込モード（加盟店入力[0]or顧客入力[1]）
    private String inputModeKbn;
    // 顧客入力済みフラグ（未：0 済：1）
    private String isCustomerInputDone;

    // WEB受付ID
    private String webAppId;
    // 契約ステータス区分
    private String statusKbn;
    // 加盟店ID
    private String storeId;
    // 顧客ID
    private String customerId;
    // 下書き存在フラグ
    private String isTempExist;

    // 顧客にログイン手順送信用
    private String resendLoginMethod;
    private String resendMailAddress;
    private String resendMobileNum;

    // 入力項目の保持用
    private String inputMapJson;
    private HashMap<String, Object> inputMap;

    // MCCS車両情報プルダウン用
    private ArrayList<String> mccsMakerList;
    private ArrayList<String> mccsShameiList;
    private ArrayList<String> mccsKatashikiList;
    private String mccsMaker;
    private String mccsShamei;

    public ArrayList<String> getMccsMakerList() {
        return mccsMakerList;
    }

    public void setMccsMakerList(ArrayList<String> mccsMakerList) {
        this.mccsMakerList = mccsMakerList;
    }

    public String getMccsMaker() {
        return mccsMaker;
    }

    public void setMccsMaker(String mccsMaker) {
        this.mccsMaker = mccsMaker;
    }

    public String getMccsShamei() {
        return mccsShamei;
    }

    public void setMccsShamei(String mccsShamei) {
        this.mccsShamei = mccsShamei;
    }

    public ArrayList<String> getMccsShameiList() {
        return mccsShameiList;
    }

    public void setMccsShameiList(ArrayList<String> mccsShameiList) {
        this.mccsShameiList = mccsShameiList;
    }

    public ArrayList<String> getMccsKatashikiList() {
        return mccsKatashikiList;
    }

    public void setMccsKatashikiList(ArrayList<String> mccsKatashikiList) {
        this.mccsKatashikiList = mccsKatashikiList;
    }

    public String getIsCustomerInputDone() {
        return isCustomerInputDone;
    }

    public void setIsCustomerInputDone(String isCustomerInputDone) {
        this.isCustomerInputDone = isCustomerInputDone;
    }

    public String getIsTempExist() {
        return isTempExist;
    }

    public void setIsTempExist(String isTempExist) {
        this.isTempExist = isTempExist;
    }

    public String getDaikoStaffId() {
        return daikoStaffId;
    }

    public void setDaikoStaffId(String daikoStaffId) {
        this.daikoStaffId = daikoStaffId;
    }

    public String getResendLoginMethod() {
        return resendLoginMethod;
    }

    public void setResendLoginMethod(String resendLoginMethod) {
        this.resendLoginMethod = resendLoginMethod;
    }

    public String getResendMailAddress() {
        return resendMailAddress;
    }

    public void setResendMailAddress(String resendMailAddress) {
        this.resendMailAddress = resendMailAddress;
    }

    public String getResendMobileNum() {
        return resendMobileNum;
    }

    public void setResendMobileNum(String resendMobileNum) {
        this.resendMobileNum = resendMobileNum;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStatusKbn() {
        return statusKbn;
    }

    public void setStatusKbn(String statusKbn) {
        this.statusKbn = statusKbn;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getHojinNumber() {
        return hojinNumber;
    }

    public void setHojinNumber(String hojinNumber) {
        this.hojinNumber = hojinNumber;
    }

    public String getWebAppId() {
        return webAppId;
    }

    public void setWebAppId(String webAppId) {
        this.webAppId = webAppId;
    }

    public String getAppKbn() {
        return appKbn;
    }

    public void setAppKbn(String appKbn) {
        this.appKbn = appKbn;
    }

    public String getInputRoleKbn() {
        return inputRoleKbn;
    }

    public void setInputRoleKbn(String inputRoleKbn) {
        this.inputRoleKbn = inputRoleKbn;
    }

    public String getInputModeKbn() {
        return inputModeKbn;
    }

    public void setInputModeKbn(String inputModeKbn) {
        this.inputModeKbn = inputModeKbn;
    }

    public String getMoshikomiKbn() {
        return moshikomiKbn;
    }

    public void setMoshikomiKbn(String moshikomiKbn) {
        this.moshikomiKbn = moshikomiKbn;
    }

    public String getInputMapJson() {
        return inputMapJson;
    }

    public void setInputMapJson(String inputMapJson) {
        this.inputMapJson = inputMapJson;
    }

    public String getSuccessFlg() {
        return successFlg;
    }

    public void setSuccessFlg(String successFlg) {
        this.successFlg = successFlg;
    }

    public HashMap<String, Object> getInputMap() {
        return inputMap;
    }

    public void setInputMap(HashMap<String, Object> inputMap) {
        this.inputMap = inputMap;
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

    public ArrayList<HashMap<String, String>> getErrorList() {
        return errorList;
    }

    public void setErrorList(ArrayList<HashMap<String, String>> errorList) {
        this.errorList = errorList;
    }
    
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
}