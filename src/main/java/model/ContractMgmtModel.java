package model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ContractMgmtModel extends CommonModel {
    private String successFlg;
    private String message;
    private String errormessage;
    private String roleType;
    
    
    private String conditionWebAppId;
    private String conditionStatus;
    private String conditionScreeningAppDateFrom;
    private String conditionScreeningAppDateTo;
    private String conditionVerifiedDateFrom;
    private String conditionVerifiedDateTo;
    private String conditionStoreChargeName;
    private String conditionMainAppDateFrom;
    private String conditionMainAppDateTo;
    private String conditionLoanDateFrom;
    private String conditionLoanDateTo;
    private String conditionInputKbn;
    private String conditionUserId;
    private String conditionUserName;
    private String conditionProxyInput;
    private String conditionStoreId;
    private String conditionStoreName;

    private String contractId;
    private String webAppId;
    private String status;
    
    private ArrayList<HashMap<String, Object>> searchList;

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


    public String getConditionStatus() {
        return conditionStatus;
    }

    public void setConditionStatus(String conditionStatus) {
        this.conditionStatus = conditionStatus;
    }

    public String getConditionScreeningAppDateFrom() {
        return conditionScreeningAppDateFrom;
    }

    public void setConditionScreeningAppDateFrom(String conditionScreeningAppDateFrom) {
        this.conditionScreeningAppDateFrom = conditionScreeningAppDateFrom;
    }

    public String getConditionScreeningAppDateTo() {
        return conditionScreeningAppDateTo;
    }

    public void setConditionScreeningAppDateTo(String conditionScreeningAppDateTo) {
        this.conditionScreeningAppDateTo = conditionScreeningAppDateTo;
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

    public String getConditionStoreChargeName() {
        return conditionStoreChargeName;
    }

    public void setConditionStoreChargeName(String conditionStoreChargeName) {
        this.conditionStoreChargeName = conditionStoreChargeName;
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

    public String getConditionLoanDateFrom() {
        return conditionLoanDateFrom;
    }

    public void setConditionLoanDateFrom(String conditionLoanDateFrom) {
        this.conditionLoanDateFrom = conditionLoanDateFrom;
    }

    public String getConditionLoanDateTo() {
        return conditionLoanDateTo;
    }

    public void setConditionLoanDateTo(String conditionLoanDateTo) {
        this.conditionLoanDateTo = conditionLoanDateTo;
    }

    public String getConditionInputKbn() {
        return conditionInputKbn;
    }

    public void setConditionInputKbn(String conditionInputKbn) {
        this.conditionInputKbn = conditionInputKbn;
    }

    public String getConditionUserId() {
        return conditionUserId;
    }

    public void setConditionUserId(String conditionUserId) {
        this.conditionUserId = conditionUserId;
    }

    public String getConditionUserName() {
        return conditionUserName;
    }

    public void setConditionUserName(String conditionUserName) {
        this.conditionUserName = conditionUserName;
    }

    public String getConditionProxyInput() {
        return conditionProxyInput;
    }

    public void setConditionProxyInput(String conditionProxyInput) {
        this.conditionProxyInput = conditionProxyInput;
    }

    public String getConditionStoreId() {
        return conditionStoreId;
    }

    public void setConditionStoreId(String conditionStoreId) {
        this.conditionStoreId = conditionStoreId;
    }

    public String getConditionStoreName() {
        return conditionStoreName;
    }

    public void setConditionStoreName(String conditionStoreName) {
        this.conditionStoreName = conditionStoreName;
    }

    public ArrayList<HashMap<String, Object>> getSearchList() {
        return searchList;
    }

    public void setSearchList(ArrayList<HashMap<String, Object>> searchList) {
        this.searchList = searchList;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConditionWebAppId() {
        return conditionWebAppId;
    }

    public void setConditionWebAppId(String conditionWebAppId) {
        this.conditionWebAppId = conditionWebAppId;
    }

    public String getWebAppId() {
        return webAppId;
    }

    public void setWebAppId(String webAppId) {
        this.webAppId = webAppId;
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

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    
}