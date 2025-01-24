package model;

import java.util.ArrayList;
import java.util.HashMap;

public class InquiryMgmtModel extends CommonModel {
    
    private String successFlg;
    private String message;
    private String errormessage;
    private String userId;
    private String userName;
    private String roleType;
    
    private ArrayList<HashMap<String, Object>> result;
    
    private String conditionInquireDateFrom;
    private String conditionInquireDateTo;
    private String conditionAnswerDateFrom;
    private String conditionAnswerDateTo;
    private String conditionBodyKbn;
    private String conditionInquirerKbn;
    private String conditionInquirerName;
    private String conditionInquirerId;
    private String conditionAnswererKbn;
    private String conditionAnswererName;
    private String conditionAnswererId;
    private String conditionAgreementBodyKbn;
    private String conditionAgreementDateFlg;
    
    private String seq;
    private String InquirerId;
    private String InquireBody;
    private String InquireAttachedFile1;
    private String InquireAttachedFile2;
    private String AnswererId;
    private String AnswerBody;
    private String AnswerAttachedFile1;
    private String AnswerAttachedFile2;
    private String IsAgreement;
    private String IsReplyed;
    private String AgreementBodyKbn;
    private String webAppId;
    
    
    
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
    public String getConditionInquireDateFrom() {
        return conditionInquireDateFrom;
    }
    public void setConditionInquireDateFrom(String conditionInquireDateFrom) {
        this.conditionInquireDateFrom = conditionInquireDateFrom;
    }
    public String getConditionInquireDateTo() {
        return conditionInquireDateTo;
    }
    public void setConditionInquireDateTo(String conditionInquireDateTo) {
        this.conditionInquireDateTo = conditionInquireDateTo;
    }
    public String getConditionAnswerDateFrom() {
        return conditionAnswerDateFrom;
    }
    public void setConditionAnswerDateFrom(String conditionAnswerDateFrom) {
        this.conditionAnswerDateFrom = conditionAnswerDateFrom;
    }
    public String getConditionAnswerDateTo() {
        return conditionAnswerDateTo;
    }
    public void setConditionAnswerDateTo(String conditionAnswerDateTo) {
        this.conditionAnswerDateTo = conditionAnswerDateTo;
    }
    public String getConditionBodyKbn() {
        return conditionBodyKbn;
    }
    public void setConditionBodyKbn(String conditionBodyKbn) {
        this.conditionBodyKbn = conditionBodyKbn;
    }
    public String getConditionInquirerKbn() {
        return conditionInquirerKbn;
    }
    public void setConditionInquirerKbn(String conditionInquirerKbn) {
        this.conditionInquirerKbn = conditionInquirerKbn;
    }
    public String getConditionInquirerName() {
        return conditionInquirerName;
    }
    public void setConditionInquirerName(String conditionInquirerName) {
        this.conditionInquirerName = conditionInquirerName;
    }
    public String getConditionInquirerId() {
        return conditionInquirerId;
    }
    public void setConditionInquirerId(String conditionInquirerId) {
        this.conditionInquirerId = conditionInquirerId;
    }
    public String getConditionAnswererKbn() {
        return conditionAnswererKbn;
    }
    public void setConditionAnswererKbn(String conditionAnswererKbn) {
        this.conditionAnswererKbn = conditionAnswererKbn;
    }
    public String getConditionAnswererName() {
        return conditionAnswererName;
    }
    public void setConditionAnswererName(String conditionAnswererName) {
        this.conditionAnswererName = conditionAnswererName;
    }
    public String getConditionAnswererId() {
        return conditionAnswererId;
    }
    public void setConditionAnswererId(String conditionAnswererId) {
        this.conditionAnswererId = conditionAnswererId;
    }
    public String getConditionAgreementBodyKbn() {
        return conditionAgreementBodyKbn;
    }
    public void setConditionAgreementBodyKbn(String conditionAgreementBodyKbn) {
        this.conditionAgreementBodyKbn = conditionAgreementBodyKbn;
    }
    public String getConditionAgreementDateFlg() {
        return conditionAgreementDateFlg;
    }
    public void setConditionAgreementDateFlg(String conditionAgreementDateFlg) {
        this.conditionAgreementDateFlg = conditionAgreementDateFlg;
    }
    public String getInquirerId() {
        return InquirerId;
    }
    public void setInquirerId(String inquirerId) {
        InquirerId = inquirerId;
    }
    public String getInquireBody() {
        return InquireBody;
    }
    public void setInquireBody(String inquireBody) {
        InquireBody = inquireBody;
    }
    public String getInquireAttachedFile1() {
        return InquireAttachedFile1;
    }
    public void setInquireAttachedFile1(String inquireAttachedFile1) {
        InquireAttachedFile1 = inquireAttachedFile1;
    }
    public String getInquireAttachedFile2() {
        return InquireAttachedFile2;
    }
    public void setInquireAttachedFile2(String inquireAttachedFile2) {
        InquireAttachedFile2 = inquireAttachedFile2;
    }
    public String getSeq() {
        return seq;
    }
    public void setSeq(String seq) {
        this.seq = seq;
    }
    public String getAnswererId() {
        return AnswererId;
    }
    public void setAnswererId(String answererId) {
        AnswererId = answererId;
    }
    public String getIsReplyed() {
        return IsReplyed;
    }
    public void setIsReplyed(String isReplyed) {
        IsReplyed = isReplyed;
    }
    public String getIsAgreement() {
        return IsAgreement;
    }
    public void setIsAgreement(String isAgreement) {
        IsAgreement = isAgreement;
    }
    public String getAnswerBody() {
        return AnswerBody;
    }
    public void setAnswerBody(String answerBody) {
        AnswerBody = answerBody;
    }
    public String getAnswerAttachedFile1() {
        return AnswerAttachedFile1;
    }
    public void setAnswerAttachedFile1(String answerAttachedFile1) {
        AnswerAttachedFile1 = answerAttachedFile1;
    }
    public String getAnswerAttachedFile2() {
        return AnswerAttachedFile2;
    }
    public void setAnswerAttachedFile2(String answerAttachedFile2) {
        AnswerAttachedFile2 = answerAttachedFile2;
    }
    public String getAgreementBodyKbn() {
        return AgreementBodyKbn;
    }
    public void setAgreementBodyKbn(String agreementBodyKbn) {
        AgreementBodyKbn = agreementBodyKbn;
    }
    public String getWebAppId() {
        return webAppId;
    }
    public void setWebAppId(String webAppId) {
        this.webAppId = webAppId;
    }

    
    
}