package model;

public class StatusModel extends CommonModel {
    
    private String successFlg;
    private String message;
    private String errormessage;
    private String userId;
    private String userName;
    private String roleType;
    
    private String contractId;
    private String webAppId;
    private String viewType;
    private String status;
    private String screeningAppType;
    
    private String statusText;
    private String statusColor;
    private String limitDate;
    private String firstScreeningAppDate;
    private String screeningAppDate;
    private String mainAppDate;
    private String screeningAppResDate;
    private String screeningAppRes;
    private String screeningAppResReason;
    private String verifiedDate;
    private String loanDate;
    
    private String mccsStatusText;
    private String mccsAttachDate;
    
    private String mccsShippingDate;
    private String mccsAttachScheduleDate;
    private String mccsSlipNumber;
    
    private String contractNumber;
    private String customerId;
    private String inputRoleKbn;
    private String isCustomerInputDone;
    private String proxyInput;
    private String staffId;
    private String staffName;
    
    private String contractJson;
    
    // 契約エリア
    private String MoshikomiYear;
    private String MoshikomiMonth;
    private String MoshikomiDay;
    private String BaibaikeiyakuDateYear;
    private String BaibaikeiyakuDateMonth;
    private String BaibaikeiyakuDateDay;
    private String MoshikomiKbn;
    private String MoshikomiKbnText;
    private String MoshikomiNameSei;
    private String MoshikomiNameMei;
    private String MoshikomiNameSeiKana;
    private String MoshikomiNameMeiKana;
    private String MoshikomiSeibetsuKbn;
    private String MoshikomiSeibetsuKbnText;
    private String MoshikomiAge;
    private String MoshikomiBirthDateYear;
    private String MoshikomiBirthDateMonth;
    private String MoshikomiBirthDateDay;
    private String MoshikomiMobile1;
    private String MoshikomiMobile2;
    private String MoshikomiMobile3;
    private String MoshikomiTel1;
    private String MoshikomiTel2;
    private String MoshikomiTel3;
    private String MoshikomiPost;
    private String MoshikomiAddress1;
    private String MoshikomiAddress2;
    private String MoshikomiAddressKana;
    private String MoshikomiHaigushaKbn;
    private String MoshikomiHaigushaKbnText;
    private String JukyoKbn;
    private String JukyoKbnText;
    private String MoshikomiLoanKbn;
    private String MoshikomiLoanKbnText;
    private String MoshikomiDokyoNinzu;
    private String MoshikomiKyojuYear;
    private String MoshikomiKyojuMonth;
    private String MoshikomiNenshu;
    private String MoshikomiShokugyoKbn;
    private String MoshikomiShokugyoKbnText;
    private String MoshikomiKinmusaki;
    private String MoshikomiKinmusakiTel1;
    private String MoshikomiKinmusakiTel2;
    private String MoshikomiKinmusakiTel3;
    private String MoshikomiKinmusakiPost;
    private String MoshikomiKinmusakiAddress1;
    private String MoshikomiKinmusakiAddress2;
    private String MoshikomiKinzokuYear;
    private String MoshikomiKinzokuMonth;
    private String MoshikomiKinmusakiYakushokuKbn;
    private String MoshikomiKinmusakiYakushokuKbnText;
    private String MoshikomiKinmusakiShozoku;
    private String MoshikomiKinmusakiJugyoin;
    private String MoshikomiGyoshuKbn;
    private String MoshikomiGyoshuKbnText;
    private String MoshikomiYokinZandaka;
    private String MoshikomiSetainushiNameSei;
    private String MoshikomiSetainushiNameMei;
    private String MoshikomiSetainushiRelateKbn;
    private String MoshikomiSetainushiRelateKbnText;
    private String MoshikomiKyojuKbn;
    private String MoshikomiKyojuKbnText;
    private String MoshikomiSetainushiNenshu;
    private String MoshikomiSetainushiCreditSaimu;
    private String MoshikomiNenkinJukyuKbn;
    private String MoshikomiNenkinJukyuKbnText;
    private String MoshikomiNenkinJukyuKbnSonota;
    private String MoshikomiNenkinJukyuPrice;
    private String MoshikomiVerifyTimeFrom;
    private String MoshikomiVerifyTimeTo;
    private String HoshoninNameSei;
    private String HoshoninNameMei;
    private String HoshoninNameSeiKana;
    private String HoshoninNameMeiKana;
    private String HoshoninSeibetsuKbn;
    private String HoshoninSeibetsuKbnText;
    private String HoshoninAge;
    private String HoshoninBirthDateYear;
    private String HoshoninBirthDateMonth;
    private String HoshoninBirthDateDay;
    private String HoshoninMobile1;
    private String HoshoninMobile2;
    private String HoshoninMobile3;
    private String HoshoninTel1;
    private String HoshoninTel2;
    private String HoshoninTel3;
    private String HoshoninPost;
    private String HoshoninAddress1;
    private String HoshoninAddress2;
    private String HoshoninAddressKana;
    private String HoshoninNenshu;
    private String HoshoninJukyoKbn;
    private String HoshoninJukyoKbnText;
    private String HoshoninLoanKbn;
    private String HoshoninLoanKbnText;
    private String HoshoninHaigushaKbn;
    private String HoshoninHaigushaKbnText;
    private String HoshoninDokyoNinzu;
    private String HoshoninMoshikomiRelateKbn;
    private String HoshoninMoshikomiRelateKbnText;
    private String HoshoninMoshikomiRelateKbnSonota;
    private String HoshoninShokugyoKbn;
    private String HoshoninShokugyoKbnText;
    private String HoshoninKinmusaki;
    private String HoshoninKinmusakiTel1;
    private String HoshoninKinmusakiTel2;
    private String HoshoninKinmusakiTel3;
    private String HoshoninKinmusakiPost;
    private String HoshoninKinmusakiAddress1;
    private String HoshoninKinmusakiAddress2;
    private String HoshoninKinzokuYear;
    private String HoshoninKinzokuMonth;
    private String HoshoninKinmusakiYakushokuKbn;
    private String HoshoninKinmusakiYakushokuKbnText;
    private String HoshoninKinmusakiShozoku;
    private String HoshoninKinmusakiJugyoin;
    private String HoshoninGyoshuKbn;
    private String HoshoninGyoshuKbnText;
    private String HoshoninNenkinJukyuKbn;
    private String HoshoninNenkinJukyuKbnText;
    private String HoshoninVerifyDate;
    private String HoshoninVerifyTimeFrom;
    private String HoshoninVerifyTimeTo;
    private String MccsMoshikomiKbn;
    private String MccsMoshikomiKbnText;
    private String ShiftKbn;
    private String ShiftKbnText;
    private String RemoteStarterKbn;
    private String RemoteStarterKbnText;
    private String HanbaiJokenKbn;
    private String HanbaiJokenKbnText;
    private String ShiyoMokutekiKbn;
    private String ShiyoMokutekiKbnText;
    private String UsedKbn;
    private String UsedKbnText;
    private String EngineStartKbn;
    private String EngineStartKbnText;
    private String Maker;
    private String Shamei;
    private String Katashiki;
    private String ShonendoYear;
    private String ShonendoMonth;
    private String ShameiML;
    private String KatashikiML;
    private String ShonendYearML;
    private String ShonendMonthML;
    private String Grade;
    private String ShadaiNum;
    private String SokoKyori;
    private String TorokuNum;
    private String Color;
    private String Haikiryo;
    private String Owner;
    private String CarPrice;
    private String MccsAttachPrice;
    private String FuzokuhinPrice;
    private String OtherPrice;
    private String ShakenPrice;
    private String TotalPrice;
    private String AppPrice;
    private String ShitadoriPrice;
    private String RemainPrice;
    private String ShiharaiStartYear;
    private String ShiharaiStartMonth;
    private String ShiharaiEndYear;
    private String ShiharaiEndMonth;
    private String ShiharaiKaisu;
    private String BonusKasanMonthKbn;
    private String BonusKasanMonthKbnText;
    private String BonusKasanKaisu;
    private String BonusKasanPrice;
    private String BunkatsuShiharai1;
    private String BunkatsuShiharai2;
    private String BunkatsuKaisu;
    private String BunkatsuTesuryo;
    private String BunkatsuShiharaiTotal;
    private String TotalShiharai;
    private String ShoyukenRyuhoPrice;
    private String NoshaDateKbn;
    private String NoshaDateKbnText;
    private String NoshaDateYear;
    private String NoshaDateMonth;
    private String NoshaDateDay;
    private String StoreRenrakuJiko;
    private String StoreId;
    private String JokenCode;
    private String StoreName;
    private String StoreDaihyoName;
    private String StoreAddress;
    private String StoreTel1;
    private String StoreTel2;
    private String StoreTel3;
    private String StoreTantoName;
    private String StoreTantoTel1;
    private String StoreTantoTel2;
    private String StoreTantoTel3;
    private String DaikoMoshikomiKbn;
    private String DaikoMoshikomiKbnText;
    private String MccsSofusakiName;
    private String MccsSofusakiPost;
    private String MccsSofusakiAddress1;
    private String MccsSofusakiAddress2;
    private String MccsSofusakiAddress3;
    private String MccsSofusakiTel1;
    private String MccsSofusakiTel2;
    private String MccsSofusakiTel3;
    private String MccsUketsukeTantoName;
    private String MccsUketsukeTantoTel1;
    private String MccsUketsukeTantoTel2;
    private String MccsUketsukeTantoTel3;
    private String MccsToritsukeTantoName;
    private String MccsToritsukeTantoTel1;
    private String MccsToritsukeTantoTel2;
    private String MccsToritsukeTantoTel3;

    
    
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatusText() {
        return statusText;
    }
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
    public String getFirstScreeningAppDate() {
        return firstScreeningAppDate;
    }
    public void setFirstScreeningAppDate(String firstScreeningAppDate) {
        this.firstScreeningAppDate = firstScreeningAppDate;
    }
    public String getScreeningAppDate() {
        return screeningAppDate;
    }
    public void setScreeningAppDate(String screeningAppDate) {
        this.screeningAppDate = screeningAppDate;
    }
    public String getMainAppDate() {
        return mainAppDate;
    }
    public void setMainAppDate(String mainAppDate) {
        this.mainAppDate = mainAppDate;
    }
    public String getScreeningAppResDate() {
        return screeningAppResDate;
    }
    public void setScreeningAppResDate(String screeningAppResDate) {
        this.screeningAppResDate = screeningAppResDate;
    }
    public String getScreeningAppRes() {
        return screeningAppRes;
    }
    public void setScreeningAppRes(String screeningAppRes) {
        this.screeningAppRes = screeningAppRes;
    }
    public String getVerifiedDate() {
        return verifiedDate;
    }
    public void setVerifiedDate(String verifiedDate) {
        this.verifiedDate = verifiedDate;
    }
    public String getLoanDate() {
        return loanDate;
    }
    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }
    public String getMccsStatusText() {
        return mccsStatusText;
    }
    public void setMccsStatusText(String mccsStatusText) {
        this.mccsStatusText = mccsStatusText;
    }
    public String getMccsAttachDate() {
        return mccsAttachDate;
    }
    public void setMccsAttachDate(String mccsAttachDate) {
        this.mccsAttachDate = mccsAttachDate;
    }
    public String getMccsShippingDate() {
        return mccsShippingDate;
    }
    public void setMccsShippingDate(String mccsShippingDate) {
        this.mccsShippingDate = mccsShippingDate;
    }
    public String getMccsAttachScheduleDate() {
        return mccsAttachScheduleDate;
    }
    public void setMccsAttachScheduleDate(String mccsAttachScheduleDate) {
        this.mccsAttachScheduleDate = mccsAttachScheduleDate;
    }
    public String getMccsSlipNumber() {
        return mccsSlipNumber;
    }
    public void setMccsSlipNumber(String mccsSlipNumber) {
        this.mccsSlipNumber = mccsSlipNumber;
    }
    public String getContractNumber() {
        return contractNumber;
    }
    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getInputRoleKbn() {
        return inputRoleKbn;
    }
    public void setInputRoleKbn(String inputRoleKbn) {
        this.inputRoleKbn = inputRoleKbn;
    }
    public String getIsCustomerInputDone() {
        return isCustomerInputDone;
    }
    public void setIsCustomerInputDone(String isCustomerInputDone) {
        this.isCustomerInputDone = isCustomerInputDone;
    }
    public String getProxyInput() {
        return proxyInput;
    }
    public void setProxyInput(String proxyInput) {
        this.proxyInput = proxyInput;
    }
    public String getStaffId() {
        return staffId;
    }
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
    public String getStaffName() {
        return staffName;
    }
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
    public String getWebAppId() {
        return webAppId;
    }
    public void setWebAppId(String webAppId) {
        this.webAppId = webAppId;
    }
    public String getViewType() {
        return viewType;
    }
    public void setViewType(String viewType) {
        this.viewType = viewType;
    }
    public String getLimitDate() {
        return limitDate;
    }
    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
    }
    public String getScreeningAppResReason() {
        return screeningAppResReason;
    }
    public void setScreeningAppResReason(String screeningAppResReason) {
        this.screeningAppResReason = screeningAppResReason;
    }
    public String getScreeningAppType() {
        return screeningAppType;
    }
    public void setScreeningAppType(String screeningAppType) {
        this.screeningAppType = screeningAppType;
    }
    public String getStatusColor() {
        return statusColor;
    }
    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }
    public String getContractJson() {
        return contractJson;
    }
    public void setContractJson(String contractJson) {
        this.contractJson = contractJson;
    }
    public String getBaibaikeiyakuDateYear() {
        return BaibaikeiyakuDateYear;
    }
    public void setBaibaikeiyakuDateYear(String baibaikeiyakuDateYear) {
        BaibaikeiyakuDateYear = baibaikeiyakuDateYear;
    }
    public String getBaibaikeiyakuDateMonth() {
        return BaibaikeiyakuDateMonth;
    }
    public void setBaibaikeiyakuDateMonth(String baibaikeiyakuDateMonth) {
        BaibaikeiyakuDateMonth = baibaikeiyakuDateMonth;
    }
    public String getBaibaikeiyakuDateDay() {
        return BaibaikeiyakuDateDay;
    }
    public void setBaibaikeiyakuDateDay(String baibaikeiyakuDateDay) {
        BaibaikeiyakuDateDay = baibaikeiyakuDateDay;
    }
    public String getMoshikomiKbn() {
        return MoshikomiKbn;
    }
    public void setMoshikomiKbn(String moshikomiKbn) {
        MoshikomiKbn = moshikomiKbn;
    }
    public String getMoshikomiNameSei() {
        return MoshikomiNameSei;
    }
    public void setMoshikomiNameSei(String moshikomiNameSei) {
        MoshikomiNameSei = moshikomiNameSei;
    }
    public String getMoshikomiNameMei() {
        return MoshikomiNameMei;
    }
    public void setMoshikomiNameMei(String moshikomiNameMei) {
        MoshikomiNameMei = moshikomiNameMei;
    }
    public String getMoshikomiNameSeiKana() {
        return MoshikomiNameSeiKana;
    }
    public void setMoshikomiNameSeiKana(String moshikomiNameSeiKana) {
        MoshikomiNameSeiKana = moshikomiNameSeiKana;
    }
    public String getMoshikomiNameMeiKana() {
        return MoshikomiNameMeiKana;
    }
    public void setMoshikomiNameMeiKana(String moshikomiNameMeiKana) {
        MoshikomiNameMeiKana = moshikomiNameMeiKana;
    }
    public String getMoshikomiSeibetsuKbn() {
        return MoshikomiSeibetsuKbn;
    }
    public void setMoshikomiSeibetsuKbn(String moshikomiSeibetsuKbn) {
        MoshikomiSeibetsuKbn = moshikomiSeibetsuKbn;
    }
    public String getMoshikomiAge() {
        return MoshikomiAge;
    }
    public void setMoshikomiAge(String moshikomiAge) {
        MoshikomiAge = moshikomiAge;
    }
    public String getMoshikomiBirthDateYear() {
        return MoshikomiBirthDateYear;
    }
    public void setMoshikomiBirthDateYear(String moshikomiBirthDateYear) {
        MoshikomiBirthDateYear = moshikomiBirthDateYear;
    }
    public String getMoshikomiBirthDateMonth() {
        return MoshikomiBirthDateMonth;
    }
    public void setMoshikomiBirthDateMonth(String moshikomiBirthDateMonth) {
        MoshikomiBirthDateMonth = moshikomiBirthDateMonth;
    }
    public String getMoshikomiBirthDateDay() {
        return MoshikomiBirthDateDay;
    }
    public void setMoshikomiBirthDateDay(String moshikomiBirthDateDay) {
        MoshikomiBirthDateDay = moshikomiBirthDateDay;
    }
    public String getMoshikomiMobile1() {
        return MoshikomiMobile1;
    }
    public void setMoshikomiMobile1(String moshikomiMobile1) {
        MoshikomiMobile1 = moshikomiMobile1;
    }
    public String getMoshikomiMobile2() {
        return MoshikomiMobile2;
    }
    public void setMoshikomiMobile2(String moshikomiMobile2) {
        MoshikomiMobile2 = moshikomiMobile2;
    }
    public String getMoshikomiMobile3() {
        return MoshikomiMobile3;
    }
    public void setMoshikomiMobile3(String moshikomiMobile3) {
        MoshikomiMobile3 = moshikomiMobile3;
    }
    public String getMoshikomiTel1() {
        return MoshikomiTel1;
    }
    public void setMoshikomiTel1(String moshikomiTel1) {
        MoshikomiTel1 = moshikomiTel1;
    }
    public String getMoshikomiTel2() {
        return MoshikomiTel2;
    }
    public void setMoshikomiTel2(String moshikomiTel2) {
        MoshikomiTel2 = moshikomiTel2;
    }
    public String getMoshikomiTel3() {
        return MoshikomiTel3;
    }
    public void setMoshikomiTel3(String moshikomiTel3) {
        MoshikomiTel3 = moshikomiTel3;
    }
    public String getMoshikomiPost() {
        return MoshikomiPost;
    }
    public void setMoshikomiPost(String moshikomiPost) {
        MoshikomiPost = moshikomiPost;
    }
    public String getMoshikomiAddress1() {
        return MoshikomiAddress1;
    }
    public void setMoshikomiAddress1(String moshikomiAddress1) {
        MoshikomiAddress1 = moshikomiAddress1;
    }
    public String getMoshikomiAddress2() {
        return MoshikomiAddress2;
    }
    public void setMoshikomiAddress2(String moshikomiAddress2) {
        MoshikomiAddress2 = moshikomiAddress2;
    }
    public String getMoshikomiAddressKana() {
        return MoshikomiAddressKana;
    }
    public void setMoshikomiAddressKana(String moshikomiAddressKana) {
        MoshikomiAddressKana = moshikomiAddressKana;
    }
    public String getMoshikomiHaigushaKbn() {
        return MoshikomiHaigushaKbn;
    }
    public void setMoshikomiHaigushaKbn(String moshikomiHaigushaKbn) {
        MoshikomiHaigushaKbn = moshikomiHaigushaKbn;
    }
    public String getJukyoKbn() {
        return JukyoKbn;
    }
    public void setJukyoKbn(String jukyoKbn) {
        JukyoKbn = jukyoKbn;
    }
    public String getMoshikomiLoanKbn() {
        return MoshikomiLoanKbn;
    }
    public void setMoshikomiLoanKbn(String moshikomiLoanKbn) {
        MoshikomiLoanKbn = moshikomiLoanKbn;
    }
    public String getMoshikomiDokyoNinzu() {
        return MoshikomiDokyoNinzu;
    }
    public void setMoshikomiDokyoNinzu(String moshikomiDokyoNinzu) {
        MoshikomiDokyoNinzu = moshikomiDokyoNinzu;
    }
    public String getMoshikomiKyojuYear() {
        return MoshikomiKyojuYear;
    }
    public void setMoshikomiKyojuYear(String moshikomiKyojuYear) {
        MoshikomiKyojuYear = moshikomiKyojuYear;
    }
    public String getMoshikomiKyojuMonth() {
        return MoshikomiKyojuMonth;
    }
    public void setMoshikomiKyojuMonth(String moshikomiKyojuMonth) {
        MoshikomiKyojuMonth = moshikomiKyojuMonth;
    }
    public String getMoshikomiNenshu() {
        return MoshikomiNenshu;
    }
    public void setMoshikomiNenshu(String moshikomiNenshu) {
        MoshikomiNenshu = moshikomiNenshu;
    }
    public String getMoshikomiShokugyoKbn() {
        return MoshikomiShokugyoKbn;
    }
    public void setMoshikomiShokugyoKbn(String moshikomiShokugyoKbn) {
        MoshikomiShokugyoKbn = moshikomiShokugyoKbn;
    }
    public String getMoshikomiKinmusaki() {
        return MoshikomiKinmusaki;
    }
    public void setMoshikomiKinmusaki(String moshikomiKinmusaki) {
        MoshikomiKinmusaki = moshikomiKinmusaki;
    }
    public String getMoshikomiKinmusakiTel1() {
        return MoshikomiKinmusakiTel1;
    }
    public void setMoshikomiKinmusakiTel1(String moshikomiKinmusakiTel1) {
        MoshikomiKinmusakiTel1 = moshikomiKinmusakiTel1;
    }
    public String getMoshikomiKinmusakiTel2() {
        return MoshikomiKinmusakiTel2;
    }
    public void setMoshikomiKinmusakiTel2(String moshikomiKinmusakiTel2) {
        MoshikomiKinmusakiTel2 = moshikomiKinmusakiTel2;
    }
    public String getMoshikomiKinmusakiTel3() {
        return MoshikomiKinmusakiTel3;
    }
    public void setMoshikomiKinmusakiTel3(String moshikomiKinmusakiTel3) {
        MoshikomiKinmusakiTel3 = moshikomiKinmusakiTel3;
    }
    public String getMoshikomiKinmusakiPost() {
        return MoshikomiKinmusakiPost;
    }
    public void setMoshikomiKinmusakiPost(String moshikomiKinmusakiPost) {
        MoshikomiKinmusakiPost = moshikomiKinmusakiPost;
    }
    public String getMoshikomiKinmusakiAddress1() {
        return MoshikomiKinmusakiAddress1;
    }
    public void setMoshikomiKinmusakiAddress1(String moshikomiKinmusakiAddress1) {
        MoshikomiKinmusakiAddress1 = moshikomiKinmusakiAddress1;
    }
    public String getMoshikomiKinmusakiAddress2() {
        return MoshikomiKinmusakiAddress2;
    }
    public void setMoshikomiKinmusakiAddress2(String moshikomiKinmusakiAddress2) {
        MoshikomiKinmusakiAddress2 = moshikomiKinmusakiAddress2;
    }
    public String getMoshikomiKinzokuYear() {
        return MoshikomiKinzokuYear;
    }
    public void setMoshikomiKinzokuYear(String moshikomiKinzokuYear) {
        MoshikomiKinzokuYear = moshikomiKinzokuYear;
    }
    public String getMoshikomiKinzokuMonth() {
        return MoshikomiKinzokuMonth;
    }
    public void setMoshikomiKinzokuMonth(String moshikomiKinzokuMonth) {
        MoshikomiKinzokuMonth = moshikomiKinzokuMonth;
    }
    public String getMoshikomiKinmusakiYakushokuKbn() {
        return MoshikomiKinmusakiYakushokuKbn;
    }
    public void setMoshikomiKinmusakiYakushokuKbn(String moshikomiKinmusakiYakushokuKbn) {
        MoshikomiKinmusakiYakushokuKbn = moshikomiKinmusakiYakushokuKbn;
    }
    public String getMoshikomiKinmusakiShozoku() {
        return MoshikomiKinmusakiShozoku;
    }
    public void setMoshikomiKinmusakiShozoku(String moshikomiKinmusakiShozoku) {
        MoshikomiKinmusakiShozoku = moshikomiKinmusakiShozoku;
    }
    public String getMoshikomiKinmusakiJugyoin() {
        return MoshikomiKinmusakiJugyoin;
    }
    public void setMoshikomiKinmusakiJugyoin(String moshikomiKinmusakiJugyoin) {
        MoshikomiKinmusakiJugyoin = moshikomiKinmusakiJugyoin;
    }
    public String getMoshikomiGyoshuKbn() {
        return MoshikomiGyoshuKbn;
    }
    public void setMoshikomiGyoshuKbn(String moshikomiGyoshuKbn) {
        MoshikomiGyoshuKbn = moshikomiGyoshuKbn;
    }
    public String getMoshikomiYokinZandaka() {
        return MoshikomiYokinZandaka;
    }
    public void setMoshikomiYokinZandaka(String moshikomiYokinZandaka) {
        MoshikomiYokinZandaka = moshikomiYokinZandaka;
    }
    public String getMoshikomiSetainushiNameSei() {
        return MoshikomiSetainushiNameSei;
    }
    public void setMoshikomiSetainushiNameSei(String moshikomiSetainushiNameSei) {
        MoshikomiSetainushiNameSei = moshikomiSetainushiNameSei;
    }
    public String getMoshikomiSetainushiNameMei() {
        return MoshikomiSetainushiNameMei;
    }
    public void setMoshikomiSetainushiNameMei(String moshikomiSetainushiNameMei) {
        MoshikomiSetainushiNameMei = moshikomiSetainushiNameMei;
    }
    public String getMoshikomiSetainushiRelateKbn() {
        return MoshikomiSetainushiRelateKbn;
    }
    public void setMoshikomiSetainushiRelateKbn(String moshikomiSetainushiRelateKbn) {
        MoshikomiSetainushiRelateKbn = moshikomiSetainushiRelateKbn;
    }
    public String getMoshikomiKyojuKbn() {
        return MoshikomiKyojuKbn;
    }
    public void setMoshikomiKyojuKbn(String moshikomiKyojuKbn) {
        MoshikomiKyojuKbn = moshikomiKyojuKbn;
    }
    public String getMoshikomiSetainushiNenshu() {
        return MoshikomiSetainushiNenshu;
    }
    public void setMoshikomiSetainushiNenshu(String moshikomiSetainushiNenshu) {
        MoshikomiSetainushiNenshu = moshikomiSetainushiNenshu;
    }
    public String getMoshikomiSetainushiCreditSaimu() {
        return MoshikomiSetainushiCreditSaimu;
    }
    public void setMoshikomiSetainushiCreditSaimu(String moshikomiSetainushiCreditSaimu) {
        MoshikomiSetainushiCreditSaimu = moshikomiSetainushiCreditSaimu;
    }
    public String getMoshikomiNenkinJukyuKbn() {
        return MoshikomiNenkinJukyuKbn;
    }
    public void setMoshikomiNenkinJukyuKbn(String moshikomiNenkinJukyuKbn) {
        MoshikomiNenkinJukyuKbn = moshikomiNenkinJukyuKbn;
    }
    public String getMoshikomiNenkinJukyuKbnSonota() {
        return MoshikomiNenkinJukyuKbnSonota;
    }
    public void setMoshikomiNenkinJukyuKbnSonota(String moshikomiNenkinJukyuKbnSonota) {
        MoshikomiNenkinJukyuKbnSonota = moshikomiNenkinJukyuKbnSonota;
    }
    public String getMoshikomiNenkinJukyuPrice() {
        return MoshikomiNenkinJukyuPrice;
    }
    public void setMoshikomiNenkinJukyuPrice(String moshikomiNenkinJukyuPrice) {
        MoshikomiNenkinJukyuPrice = moshikomiNenkinJukyuPrice;
    }
    public String getMoshikomiVerifyTimeFrom() {
        return MoshikomiVerifyTimeFrom;
    }
    public void setMoshikomiVerifyTimeFrom(String moshikomiVerifyTimeFrom) {
        MoshikomiVerifyTimeFrom = moshikomiVerifyTimeFrom;
    }
    public String getMoshikomiVerifyTimeTo() {
        return MoshikomiVerifyTimeTo;
    }
    public void setMoshikomiVerifyTimeTo(String moshikomiVerifyTimeTo) {
        MoshikomiVerifyTimeTo = moshikomiVerifyTimeTo;
    }
    public String getHoshoninNameSei() {
        return HoshoninNameSei;
    }
    public void setHoshoninNameSei(String hoshoninNameSei) {
        HoshoninNameSei = hoshoninNameSei;
    }
    public String getHoshoninNameMei() {
        return HoshoninNameMei;
    }
    public void setHoshoninNameMei(String hoshoninNameMei) {
        HoshoninNameMei = hoshoninNameMei;
    }
    public String getHoshoninNameSeiKana() {
        return HoshoninNameSeiKana;
    }
    public void setHoshoninNameSeiKana(String hoshoninNameSeiKana) {
        HoshoninNameSeiKana = hoshoninNameSeiKana;
    }
    public String getHoshoninNameMeiKana() {
        return HoshoninNameMeiKana;
    }
    public void setHoshoninNameMeiKana(String hoshoninNameMeiKana) {
        HoshoninNameMeiKana = hoshoninNameMeiKana;
    }
    public String getHoshoninSeibetsuKbn() {
        return HoshoninSeibetsuKbn;
    }
    public void setHoshoninSeibetsuKbn(String hoshoninSeibetsuKbn) {
        HoshoninSeibetsuKbn = hoshoninSeibetsuKbn;
    }
    public String getHoshoninAge() {
        return HoshoninAge;
    }
    public void setHoshoninAge(String hoshoninAge) {
        HoshoninAge = hoshoninAge;
    }
    public String getHoshoninBirthDateYear() {
        return HoshoninBirthDateYear;
    }
    public void setHoshoninBirthDateYear(String hoshoninBirthDateYear) {
        HoshoninBirthDateYear = hoshoninBirthDateYear;
    }
    public String getHoshoninBirthDateMonth() {
        return HoshoninBirthDateMonth;
    }
    public void setHoshoninBirthDateMonth(String hoshoninBirthDateMonth) {
        HoshoninBirthDateMonth = hoshoninBirthDateMonth;
    }
    public String getHoshoninBirthDateDay() {
        return HoshoninBirthDateDay;
    }
    public void setHoshoninBirthDateDay(String hoshoninBirthDateDay) {
        HoshoninBirthDateDay = hoshoninBirthDateDay;
    }
    public String getHoshoninMobile1() {
        return HoshoninMobile1;
    }
    public void setHoshoninMobile1(String hoshoninMobile1) {
        HoshoninMobile1 = hoshoninMobile1;
    }
    public String getHoshoninMobile2() {
        return HoshoninMobile2;
    }
    public void setHoshoninMobile2(String hoshoninMobile2) {
        HoshoninMobile2 = hoshoninMobile2;
    }
    public String getHoshoninMobile3() {
        return HoshoninMobile3;
    }
    public void setHoshoninMobile3(String hoshoninMobile3) {
        HoshoninMobile3 = hoshoninMobile3;
    }
    public String getHoshoninTel1() {
        return HoshoninTel1;
    }
    public void setHoshoninTel1(String hoshoninTel1) {
        HoshoninTel1 = hoshoninTel1;
    }
    public String getHoshoninTel2() {
        return HoshoninTel2;
    }
    public void setHoshoninTel2(String hoshoninTel2) {
        HoshoninTel2 = hoshoninTel2;
    }
    public String getHoshoninTel3() {
        return HoshoninTel3;
    }
    public void setHoshoninTel3(String hoshoninTel3) {
        HoshoninTel3 = hoshoninTel3;
    }
    public String getHoshoninPost() {
        return HoshoninPost;
    }
    public void setHoshoninPost(String hoshoninPost) {
        HoshoninPost = hoshoninPost;
    }
    public String getHoshoninAddress1() {
        return HoshoninAddress1;
    }
    public void setHoshoninAddress1(String hoshoninAddress1) {
        HoshoninAddress1 = hoshoninAddress1;
    }
    public String getHoshoninAddress2() {
        return HoshoninAddress2;
    }
    public void setHoshoninAddress2(String hoshoninAddress2) {
        HoshoninAddress2 = hoshoninAddress2;
    }
    public String getHoshoninAddressKana() {
        return HoshoninAddressKana;
    }
    public void setHoshoninAddressKana(String hoshoninAddressKana) {
        HoshoninAddressKana = hoshoninAddressKana;
    }
    public String getHoshoninNenshu() {
        return HoshoninNenshu;
    }
    public void setHoshoninNenshu(String hoshoninNenshu) {
        HoshoninNenshu = hoshoninNenshu;
    }
    public String getHoshoninJukyoKbn() {
        return HoshoninJukyoKbn;
    }
    public void setHoshoninJukyoKbn(String hoshoninJukyoKbn) {
        HoshoninJukyoKbn = hoshoninJukyoKbn;
    }
    public String getHoshoninLoanKbn() {
        return HoshoninLoanKbn;
    }
    public void setHoshoninLoanKbn(String hoshoninLoanKbn) {
        HoshoninLoanKbn = hoshoninLoanKbn;
    }
    public String getHoshoninHaigushaKbn() {
        return HoshoninHaigushaKbn;
    }
    public void setHoshoninHaigushaKbn(String hoshoninHaigushaKbn) {
        HoshoninHaigushaKbn = hoshoninHaigushaKbn;
    }
    public String getHoshoninDokyoNinzu() {
        return HoshoninDokyoNinzu;
    }
    public void setHoshoninDokyoNinzu(String hoshoninDokyoNinzu) {
        HoshoninDokyoNinzu = hoshoninDokyoNinzu;
    }
    public String getHoshoninMoshikomiRelateKbn() {
        return HoshoninMoshikomiRelateKbn;
    }
    public void setHoshoninMoshikomiRelateKbn(String hoshoninMoshikomiRelateKbn) {
        HoshoninMoshikomiRelateKbn = hoshoninMoshikomiRelateKbn;
    }
    public String getHoshoninMoshikomiRelateKbnSonota() {
        return HoshoninMoshikomiRelateKbnSonota;
    }
    public void setHoshoninMoshikomiRelateKbnSonota(String hoshoninMoshikomiRelateKbnSonota) {
        HoshoninMoshikomiRelateKbnSonota = hoshoninMoshikomiRelateKbnSonota;
    }
    public String getHoshoninShokugyoKbn() {
        return HoshoninShokugyoKbn;
    }
    public void setHoshoninShokugyoKbn(String hoshoninShokugyoKbn) {
        HoshoninShokugyoKbn = hoshoninShokugyoKbn;
    }
    public String getHoshoninKinmusaki() {
        return HoshoninKinmusaki;
    }
    public void setHoshoninKinmusaki(String hoshoninKinmusaki) {
        HoshoninKinmusaki = hoshoninKinmusaki;
    }
    public String getHoshoninKinmusakiTel1() {
        return HoshoninKinmusakiTel1;
    }
    public void setHoshoninKinmusakiTel1(String hoshoninKinmusakiTel1) {
        HoshoninKinmusakiTel1 = hoshoninKinmusakiTel1;
    }
    public String getHoshoninKinmusakiTel2() {
        return HoshoninKinmusakiTel2;
    }
    public void setHoshoninKinmusakiTel2(String hoshoninKinmusakiTel2) {
        HoshoninKinmusakiTel2 = hoshoninKinmusakiTel2;
    }
    public String getHoshoninKinmusakiTel3() {
        return HoshoninKinmusakiTel3;
    }
    public void setHoshoninKinmusakiTel3(String hoshoninKinmusakiTel3) {
        HoshoninKinmusakiTel3 = hoshoninKinmusakiTel3;
    }
    public String getHoshoninKinmusakiPost() {
        return HoshoninKinmusakiPost;
    }
    public void setHoshoninKinmusakiPost(String hoshoninKinmusakiPost) {
        HoshoninKinmusakiPost = hoshoninKinmusakiPost;
    }
    public String getHoshoninKinmusakiAddress1() {
        return HoshoninKinmusakiAddress1;
    }
    public void setHoshoninKinmusakiAddress1(String hoshoninKinmusakiAddress1) {
        HoshoninKinmusakiAddress1 = hoshoninKinmusakiAddress1;
    }
    public String getHoshoninKinmusakiAddress2() {
        return HoshoninKinmusakiAddress2;
    }
    public void setHoshoninKinmusakiAddress2(String hoshoninKinmusakiAddress2) {
        HoshoninKinmusakiAddress2 = hoshoninKinmusakiAddress2;
    }
    public String getHoshoninKinzokuYear() {
        return HoshoninKinzokuYear;
    }
    public void setHoshoninKinzokuYear(String hoshoninKinzokuYear) {
        HoshoninKinzokuYear = hoshoninKinzokuYear;
    }
    public String getHoshoninKinzokuMonth() {
        return HoshoninKinzokuMonth;
    }
    public void setHoshoninKinzokuMonth(String hoshoninKinzokuMonth) {
        HoshoninKinzokuMonth = hoshoninKinzokuMonth;
    }
    public String getHoshoninKinmusakiYakushokuKbn() {
        return HoshoninKinmusakiYakushokuKbn;
    }
    public void setHoshoninKinmusakiYakushokuKbn(String hoshoninKinmusakiYakushokuKbn) {
        HoshoninKinmusakiYakushokuKbn = hoshoninKinmusakiYakushokuKbn;
    }
    public String getHoshoninKinmusakiShozoku() {
        return HoshoninKinmusakiShozoku;
    }
    public void setHoshoninKinmusakiShozoku(String hoshoninKinmusakiShozoku) {
        HoshoninKinmusakiShozoku = hoshoninKinmusakiShozoku;
    }
    public String getHoshoninKinmusakiJugyoin() {
        return HoshoninKinmusakiJugyoin;
    }
    public void setHoshoninKinmusakiJugyoin(String hoshoninKinmusakiJugyoin) {
        HoshoninKinmusakiJugyoin = hoshoninKinmusakiJugyoin;
    }
    public String getHoshoninGyoshuKbn() {
        return HoshoninGyoshuKbn;
    }
    public void setHoshoninGyoshuKbn(String hoshoninGyoshuKbn) {
        HoshoninGyoshuKbn = hoshoninGyoshuKbn;
    }
    public String getHoshoninNenkinJukyuKbn() {
        return HoshoninNenkinJukyuKbn;
    }
    public void setHoshoninNenkinJukyuKbn(String hoshoninNenkinJukyuKbn) {
        HoshoninNenkinJukyuKbn = hoshoninNenkinJukyuKbn;
    }
    public String getHoshoninVerifyDate() {
        return HoshoninVerifyDate;
    }
    public void setHoshoninVerifyDate(String hoshoninVerifyDate) {
        HoshoninVerifyDate = hoshoninVerifyDate;
    }
    public String getHoshoninVerifyTimeFrom() {
        return HoshoninVerifyTimeFrom;
    }
    public void setHoshoninVerifyTimeFrom(String hoshoninVerifyTimeFrom) {
        HoshoninVerifyTimeFrom = hoshoninVerifyTimeFrom;
    }
    public String getHoshoninVerifyTimeTo() {
        return HoshoninVerifyTimeTo;
    }
    public void setHoshoninVerifyTimeTo(String hoshoninVerifyTimeTo) {
        HoshoninVerifyTimeTo = hoshoninVerifyTimeTo;
    }
    public String getMccsMoshikomiKbn() {
        return MccsMoshikomiKbn;
    }
    public void setMccsMoshikomiKbn(String mccsMoshikomiKbn) {
        MccsMoshikomiKbn = mccsMoshikomiKbn;
    }
    public String getShiftKbn() {
        return ShiftKbn;
    }
    public void setShiftKbn(String shiftKbn) {
        ShiftKbn = shiftKbn;
    }
    public String getRemoteStarterKbn() {
        return RemoteStarterKbn;
    }
    public void setRemoteStarterKbn(String remoteStarterKbn) {
        RemoteStarterKbn = remoteStarterKbn;
    }
    public String getHanbaiJokenKbn() {
        return HanbaiJokenKbn;
    }
    public void setHanbaiJokenKbn(String hanbaiJokenKbn) {
        HanbaiJokenKbn = hanbaiJokenKbn;
    }
    public String getShiyoMokutekiKbn() {
        return ShiyoMokutekiKbn;
    }
    public void setShiyoMokutekiKbn(String shiyoMokutekiKbn) {
        ShiyoMokutekiKbn = shiyoMokutekiKbn;
    }
    public String getUsedKbn() {
        return UsedKbn;
    }
    public void setUsedKbn(String usedKbn) {
        UsedKbn = usedKbn;
    }
    public String getEngineStartKbn() {
        return EngineStartKbn;
    }
    public void setEngineStartKbn(String engineStartKbn) {
        EngineStartKbn = engineStartKbn;
    }
    public String getMaker() {
        return Maker;
    }
    public void setMaker(String maker) {
        Maker = maker;
    }
    public String getShamei() {
        return Shamei;
    }
    public void setShamei(String shamei) {
        Shamei = shamei;
    }
    public String getKatashiki() {
        return Katashiki;
    }
    public void setKatashiki(String katashiki) {
        Katashiki = katashiki;
    }
    public String getShonendoYear() {
        return ShonendoYear;
    }
    public void setShonendoYear(String shonendoYear) {
        ShonendoYear = shonendoYear;
    }
    public String getShonendoMonth() {
        return ShonendoMonth;
    }
    public void setShonendoMonth(String shonendoMonth) {
        ShonendoMonth = shonendoMonth;
    }
    public String getShameiML() {
        return ShameiML;
    }
    public void setShameiML(String shameiML) {
        ShameiML = shameiML;
    }
    public String getKatashikiML() {
        return KatashikiML;
    }
    public void setKatashikiML(String katashikiML) {
        KatashikiML = katashikiML;
    }
    public String getShonendYearML() {
        return ShonendYearML;
    }
    public void setShonendYearML(String shonendYearML) {
        ShonendYearML = shonendYearML;
    }
    public String getShonendMonthML() {
        return ShonendMonthML;
    }
    public void setShonendMonthML(String shonendMonthML) {
        ShonendMonthML = shonendMonthML;
    }
    public String getGrade() {
        return Grade;
    }
    public void setGrade(String grade) {
        Grade = grade;
    }
    public String getShadaiNum() {
        return ShadaiNum;
    }
    public void setShadaiNum(String shadaiNum) {
        ShadaiNum = shadaiNum;
    }
    public String getSokoKyori() {
        return SokoKyori;
    }
    public void setSokoKyori(String sokoKyori) {
        SokoKyori = sokoKyori;
    }
    public String getTorokuNum() {
        return TorokuNum;
    }
    public void setTorokuNum(String torokuNum) {
        TorokuNum = torokuNum;
    }
    public String getColor() {
        return Color;
    }
    public void setColor(String color) {
        Color = color;
    }
    public String getHaikiryo() {
        return Haikiryo;
    }
    public void setHaikiryo(String haikiryo) {
        Haikiryo = haikiryo;
    }
    public String getOwner() {
        return Owner;
    }
    public void setOwner(String owner) {
        Owner = owner;
    }
    public String getCarPrice() {
        return CarPrice;
    }
    public void setCarPrice(String carPrice) {
        CarPrice = carPrice;
    }
    public String getMccsAttachPrice() {
        return MccsAttachPrice;
    }
    public void setMccsAttachPrice(String mccsAttachPrice) {
        MccsAttachPrice = mccsAttachPrice;
    }
    public String getFuzokuhinPrice() {
        return FuzokuhinPrice;
    }
    public void setFuzokuhinPrice(String fuzokuhinPrice) {
        FuzokuhinPrice = fuzokuhinPrice;
    }
    public String getOtherPrice() {
        return OtherPrice;
    }
    public void setOtherPrice(String otherPrice) {
        OtherPrice = otherPrice;
    }
    public String getShakenPrice() {
        return ShakenPrice;
    }
    public void setShakenPrice(String shakenPrice) {
        ShakenPrice = shakenPrice;
    }
    public String getTotalPrice() {
        return TotalPrice;
    }
    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }
    public String getAppPrice() {
        return AppPrice;
    }
    public void setAppPrice(String appPrice) {
        AppPrice = appPrice;
    }
    public String getShitadoriPrice() {
        return ShitadoriPrice;
    }
    public void setShitadoriPrice(String shitadoriPrice) {
        ShitadoriPrice = shitadoriPrice;
    }
    public String getRemainPrice() {
        return RemainPrice;
    }
    public void setRemainPrice(String remainPrice) {
        RemainPrice = remainPrice;
    }
    public String getShiharaiStartYear() {
        return ShiharaiStartYear;
    }
    public void setShiharaiStartYear(String shiharaiStartYear) {
        ShiharaiStartYear = shiharaiStartYear;
    }
    public String getShiharaiStartMonth() {
        return ShiharaiStartMonth;
    }
    public void setShiharaiStartMonth(String shiharaiStartMonth) {
        ShiharaiStartMonth = shiharaiStartMonth;
    }
    public String getShiharaiEndYear() {
        return ShiharaiEndYear;
    }
    public void setShiharaiEndYear(String shiharaiEndYear) {
        ShiharaiEndYear = shiharaiEndYear;
    }
    public String getShiharaiEndMonth() {
        return ShiharaiEndMonth;
    }
    public void setShiharaiEndMonth(String shiharaiEndMonth) {
        ShiharaiEndMonth = shiharaiEndMonth;
    }
    public String getShiharaiKaisu() {
        return ShiharaiKaisu;
    }
    public void setShiharaiKaisu(String shiharaiKaisu) {
        ShiharaiKaisu = shiharaiKaisu;
    }
    public String getBonusKasanMonthKbn() {
        return BonusKasanMonthKbn;
    }
    public void setBonusKasanMonthKbn(String bonusKasanMonthKbn) {
        BonusKasanMonthKbn = bonusKasanMonthKbn;
    }
    public String getBonusKasanKaisu() {
        return BonusKasanKaisu;
    }
    public void setBonusKasanKaisu(String bonusKasanKaisu) {
        BonusKasanKaisu = bonusKasanKaisu;
    }
    public String getBonusKasanPrice() {
        return BonusKasanPrice;
    }
    public void setBonusKasanPrice(String bonusKasanPrice) {
        BonusKasanPrice = bonusKasanPrice;
    }
    public String getBunkatsuShiharai1() {
        return BunkatsuShiharai1;
    }
    public void setBunkatsuShiharai1(String bunkatsuShiharai1) {
        BunkatsuShiharai1 = bunkatsuShiharai1;
    }
    public String getBunkatsuShiharai2() {
        return BunkatsuShiharai2;
    }
    public void setBunkatsuShiharai2(String bunkatsuShiharai2) {
        BunkatsuShiharai2 = bunkatsuShiharai2;
    }
    public String getBunkatsuKaisu() {
        return BunkatsuKaisu;
    }
    public void setBunkatsuKaisu(String bunkatsuKaisu) {
        BunkatsuKaisu = bunkatsuKaisu;
    }
    public String getBunkatsuTesuryo() {
        return BunkatsuTesuryo;
    }
    public void setBunkatsuTesuryo(String bunkatsuTesuryo) {
        BunkatsuTesuryo = bunkatsuTesuryo;
    }
    public String getBunkatsuShiharaiTotal() {
        return BunkatsuShiharaiTotal;
    }
    public void setBunkatsuShiharaiTotal(String bunkatsuShiharaiTotal) {
        BunkatsuShiharaiTotal = bunkatsuShiharaiTotal;
    }
    public String getTotalShiharai() {
        return TotalShiharai;
    }
    public void setTotalShiharai(String totalShiharai) {
        TotalShiharai = totalShiharai;
    }
    public String getShoyukenRyuhoPrice() {
        return ShoyukenRyuhoPrice;
    }
    public void setShoyukenRyuhoPrice(String shoyukenRyuhoPrice) {
        ShoyukenRyuhoPrice = shoyukenRyuhoPrice;
    }
    public String getNoshaDateKbn() {
        return NoshaDateKbn;
    }
    public void setNoshaDateKbn(String noshaDateKbn) {
        NoshaDateKbn = noshaDateKbn;
    }
    public String getNoshaDateYear() {
        return NoshaDateYear;
    }
    public void setNoshaDateYear(String noshaDateYear) {
        NoshaDateYear = noshaDateYear;
    }
    public String getNoshaDateMonth() {
        return NoshaDateMonth;
    }
    public void setNoshaDateMonth(String noshaDateMonth) {
        NoshaDateMonth = noshaDateMonth;
    }
    public String getNoshaDateDay() {
        return NoshaDateDay;
    }
    public void setNoshaDateDay(String noshaDateDay) {
        NoshaDateDay = noshaDateDay;
    }
    public String getStoreRenrakuJiko() {
        return StoreRenrakuJiko;
    }
    public void setStoreRenrakuJiko(String storeRenrakuJiko) {
        StoreRenrakuJiko = storeRenrakuJiko;
    }
    public String getStoreId() {
        return StoreId;
    }
    public void setStoreId(String storeId) {
        StoreId = storeId;
    }
    public String getJokenCode() {
        return JokenCode;
    }
    public void setJokenCode(String jokenCode) {
        JokenCode = jokenCode;
    }
    public String getStoreName() {
        return StoreName;
    }
    public void setStoreName(String storeName) {
        StoreName = storeName;
    }
    public String getStoreDaihyoName() {
        return StoreDaihyoName;
    }
    public void setStoreDaihyoName(String storeDaihyoName) {
        StoreDaihyoName = storeDaihyoName;
    }
    public String getStoreAddress() {
        return StoreAddress;
    }
    public void setStoreAddress(String storeAddress) {
        StoreAddress = storeAddress;
    }
    public String getStoreTel1() {
        return StoreTel1;
    }
    public void setStoreTel1(String storeTel1) {
        StoreTel1 = storeTel1;
    }
    public String getStoreTel2() {
        return StoreTel2;
    }
    public void setStoreTel2(String storeTel2) {
        StoreTel2 = storeTel2;
    }
    public String getStoreTel3() {
        return StoreTel3;
    }
    public void setStoreTel3(String storeTel3) {
        StoreTel3 = storeTel3;
    }
    public String getStoreTantoName() {
        return StoreTantoName;
    }
    public void setStoreTantoName(String storeTantoName) {
        StoreTantoName = storeTantoName;
    }
    public String getStoreTantoTel1() {
        return StoreTantoTel1;
    }
    public void setStoreTantoTel1(String storeTantoTel1) {
        StoreTantoTel1 = storeTantoTel1;
    }
    public String getStoreTantoTel2() {
        return StoreTantoTel2;
    }
    public void setStoreTantoTel2(String storeTantoTel2) {
        StoreTantoTel2 = storeTantoTel2;
    }
    public String getStoreTantoTel3() {
        return StoreTantoTel3;
    }
    public void setStoreTantoTel3(String storeTantoTel3) {
        StoreTantoTel3 = storeTantoTel3;
    }
    public String getDaikoMoshikomiKbn() {
        return DaikoMoshikomiKbn;
    }
    public void setDaikoMoshikomiKbn(String daikoMoshikomiKbn) {
        DaikoMoshikomiKbn = daikoMoshikomiKbn;
    }
    public String getMccsSofusakiName() {
        return MccsSofusakiName;
    }
    public void setMccsSofusakiName(String mccsSofusakiName) {
        MccsSofusakiName = mccsSofusakiName;
    }
    public String getMccsSofusakiPost() {
        return MccsSofusakiPost;
    }
    public void setMccsSofusakiPost(String mccsSofusakiPost) {
        MccsSofusakiPost = mccsSofusakiPost;
    }
    public String getMccsSofusakiAddress1() {
        return MccsSofusakiAddress1;
    }
    public void setMccsSofusakiAddress1(String mccsSofusakiAddress1) {
        MccsSofusakiAddress1 = mccsSofusakiAddress1;
    }
    public String getMccsSofusakiAddress2() {
        return MccsSofusakiAddress2;
    }
    public void setMccsSofusakiAddress2(String mccsSofusakiAddress2) {
        MccsSofusakiAddress2 = mccsSofusakiAddress2;
    }
    public String getMccsSofusakiAddress3() {
        return MccsSofusakiAddress3;
    }
    public void setMccsSofusakiAddress3(String mccsSofusakiAddress3) {
        MccsSofusakiAddress3 = mccsSofusakiAddress3;
    }
    public String getMccsSofusakiTel1() {
        return MccsSofusakiTel1;
    }
    public void setMccsSofusakiTel1(String mccsSofusakiTel1) {
        MccsSofusakiTel1 = mccsSofusakiTel1;
    }
    public String getMccsSofusakiTel2() {
        return MccsSofusakiTel2;
    }
    public void setMccsSofusakiTel2(String mccsSofusakiTel2) {
        MccsSofusakiTel2 = mccsSofusakiTel2;
    }
    public String getMccsSofusakiTel3() {
        return MccsSofusakiTel3;
    }
    public void setMccsSofusakiTel3(String mccsSofusakiTel3) {
        MccsSofusakiTel3 = mccsSofusakiTel3;
    }
    public String getMccsUketsukeTantoName() {
        return MccsUketsukeTantoName;
    }
    public void setMccsUketsukeTantoName(String mccsUketsukeTantoName) {
        MccsUketsukeTantoName = mccsUketsukeTantoName;
    }
    public String getMccsUketsukeTantoTel1() {
        return MccsUketsukeTantoTel1;
    }
    public void setMccsUketsukeTantoTel1(String mccsUketsukeTantoTel1) {
        MccsUketsukeTantoTel1 = mccsUketsukeTantoTel1;
    }
    public String getMccsUketsukeTantoTel2() {
        return MccsUketsukeTantoTel2;
    }
    public void setMccsUketsukeTantoTel2(String mccsUketsukeTantoTel2) {
        MccsUketsukeTantoTel2 = mccsUketsukeTantoTel2;
    }
    public String getMccsUketsukeTantoTel3() {
        return MccsUketsukeTantoTel3;
    }
    public void setMccsUketsukeTantoTel3(String mccsUketsukeTantoTel3) {
        MccsUketsukeTantoTel3 = mccsUketsukeTantoTel3;
    }
    public String getMccsToritsukeTantoName() {
        return MccsToritsukeTantoName;
    }
    public void setMccsToritsukeTantoName(String mccsToritsukeTantoName) {
        MccsToritsukeTantoName = mccsToritsukeTantoName;
    }
    public String getMccsToritsukeTantoTel1() {
        return MccsToritsukeTantoTel1;
    }
    public void setMccsToritsukeTantoTel1(String mccsToritsukeTantoTel1) {
        MccsToritsukeTantoTel1 = mccsToritsukeTantoTel1;
    }
    public String getMccsToritsukeTantoTel2() {
        return MccsToritsukeTantoTel2;
    }
    public void setMccsToritsukeTantoTel2(String mccsToritsukeTantoTel2) {
        MccsToritsukeTantoTel2 = mccsToritsukeTantoTel2;
    }
    public String getMccsToritsukeTantoTel3() {
        return MccsToritsukeTantoTel3;
    }
    public void setMccsToritsukeTantoTel3(String mccsToritsukeTantoTel3) {
        MccsToritsukeTantoTel3 = mccsToritsukeTantoTel3;
    }
    public String getContractId() {
        return contractId;
    }
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
    public String getMoshikomiYear() {
        return MoshikomiYear;
    }
    public void setMoshikomiYear(String moshikomiYear) {
        MoshikomiYear = moshikomiYear;
    }
    public String getMoshikomiMonth() {
        return MoshikomiMonth;
    }
    public void setMoshikomiMonth(String moshikomiMonth) {
        MoshikomiMonth = moshikomiMonth;
    }
    public String getMoshikomiDay() {
        return MoshikomiDay;
    }
    public void setMoshikomiDay(String moshikomiDay) {
        MoshikomiDay = moshikomiDay;
    }
    public String getMoshikomiKbnText() {
        return MoshikomiKbnText;
    }
    public void setMoshikomiKbnText(String moshikomiKbnText) {
        MoshikomiKbnText = moshikomiKbnText;
    }
    public String getMoshikomiSeibetsuKbnText() {
        return MoshikomiSeibetsuKbnText;
    }
    public void setMoshikomiSeibetsuKbnText(String moshikomiSeibetsuKbnText) {
        MoshikomiSeibetsuKbnText = moshikomiSeibetsuKbnText;
    }
    public String getMoshikomiHaigushaKbnText() {
        return MoshikomiHaigushaKbnText;
    }
    public void setMoshikomiHaigushaKbnText(String moshikomiHaigushaKbnText) {
        MoshikomiHaigushaKbnText = moshikomiHaigushaKbnText;
    }
    public String getJukyoKbnText() {
        return JukyoKbnText;
    }
    public void setJukyoKbnText(String jukyoKbnText) {
        JukyoKbnText = jukyoKbnText;
    }
    public String getMoshikomiLoanKbnText() {
        return MoshikomiLoanKbnText;
    }
    public void setMoshikomiLoanKbnText(String moshikomiLoanKbnText) {
        MoshikomiLoanKbnText = moshikomiLoanKbnText;
    }
    public String getMoshikomiShokugyoKbnText() {
        return MoshikomiShokugyoKbnText;
    }
    public void setMoshikomiShokugyoKbnText(String moshikomiShokugyoKbnText) {
        MoshikomiShokugyoKbnText = moshikomiShokugyoKbnText;
    }
    public String getMoshikomiKinmusakiYakushokuKbnText() {
        return MoshikomiKinmusakiYakushokuKbnText;
    }
    public void setMoshikomiKinmusakiYakushokuKbnText(String moshikomiKinmusakiYakushokuKbnText) {
        MoshikomiKinmusakiYakushokuKbnText = moshikomiKinmusakiYakushokuKbnText;
    }
    public String getMoshikomiGyoshuKbnText() {
        return MoshikomiGyoshuKbnText;
    }
    public void setMoshikomiGyoshuKbnText(String moshikomiGyoshuKbnText) {
        MoshikomiGyoshuKbnText = moshikomiGyoshuKbnText;
    }
    public String getMoshikomiSetainushiRelateKbnText() {
        return MoshikomiSetainushiRelateKbnText;
    }
    public void setMoshikomiSetainushiRelateKbnText(String moshikomiSetainushiRelateKbnText) {
        MoshikomiSetainushiRelateKbnText = moshikomiSetainushiRelateKbnText;
    }
    public String getMoshikomiKyojuKbnText() {
        return MoshikomiKyojuKbnText;
    }
    public void setMoshikomiKyojuKbnText(String moshikomiKyojuKbnText) {
        MoshikomiKyojuKbnText = moshikomiKyojuKbnText;
    }
    public String getMoshikomiNenkinJukyuKbnText() {
        return MoshikomiNenkinJukyuKbnText;
    }
    public void setMoshikomiNenkinJukyuKbnText(String moshikomiNenkinJukyuKbnText) {
        MoshikomiNenkinJukyuKbnText = moshikomiNenkinJukyuKbnText;
    }
    public String getHoshoninSeibetsuKbnText() {
        return HoshoninSeibetsuKbnText;
    }
    public void setHoshoninSeibetsuKbnText(String hoshoninSeibetsuKbnText) {
        HoshoninSeibetsuKbnText = hoshoninSeibetsuKbnText;
    }
    public String getHoshoninJukyoKbnText() {
        return HoshoninJukyoKbnText;
    }
    public void setHoshoninJukyoKbnText(String hoshoninJukyoKbnText) {
        HoshoninJukyoKbnText = hoshoninJukyoKbnText;
    }
    public String getHoshoninLoanKbnText() {
        return HoshoninLoanKbnText;
    }
    public void setHoshoninLoanKbnText(String hoshoninLoanKbnText) {
        HoshoninLoanKbnText = hoshoninLoanKbnText;
    }
    public String getHoshoninHaigushaKbnText() {
        return HoshoninHaigushaKbnText;
    }
    public void setHoshoninHaigushaKbnText(String hoshoninHaigushaKbnText) {
        HoshoninHaigushaKbnText = hoshoninHaigushaKbnText;
    }
    public String getHoshoninMoshikomiRelateKbnText() {
        return HoshoninMoshikomiRelateKbnText;
    }
    public void setHoshoninMoshikomiRelateKbnText(String hoshoninMoshikomiRelateKbnText) {
        HoshoninMoshikomiRelateKbnText = hoshoninMoshikomiRelateKbnText;
    }
    public String getHoshoninShokugyoKbnText() {
        return HoshoninShokugyoKbnText;
    }
    public void setHoshoninShokugyoKbnText(String hoshoninShokugyoKbnText) {
        HoshoninShokugyoKbnText = hoshoninShokugyoKbnText;
    }
    public String getHoshoninKinmusakiYakushokuKbnText() {
        return HoshoninKinmusakiYakushokuKbnText;
    }
    public void setHoshoninKinmusakiYakushokuKbnText(String hoshoninKinmusakiYakushokuKbnText) {
        HoshoninKinmusakiYakushokuKbnText = hoshoninKinmusakiYakushokuKbnText;
    }
    public String getHoshoninGyoshuKbnText() {
        return HoshoninGyoshuKbnText;
    }
    public void setHoshoninGyoshuKbnText(String hoshoninGyoshuKbnText) {
        HoshoninGyoshuKbnText = hoshoninGyoshuKbnText;
    }
    public String getHoshoninNenkinJukyuKbnText() {
        return HoshoninNenkinJukyuKbnText;
    }
    public void setHoshoninNenkinJukyuKbnText(String hoshoninNenkinJukyuKbnText) {
        HoshoninNenkinJukyuKbnText = hoshoninNenkinJukyuKbnText;
    }
    public String getMccsMoshikomiKbnText() {
        return MccsMoshikomiKbnText;
    }
    public void setMccsMoshikomiKbnText(String mccsMoshikomiKbnText) {
        MccsMoshikomiKbnText = mccsMoshikomiKbnText;
    }
    public String getShiftKbnText() {
        return ShiftKbnText;
    }
    public void setShiftKbnText(String shiftKbnText) {
        ShiftKbnText = shiftKbnText;
    }
    public String getRemoteStarterKbnText() {
        return RemoteStarterKbnText;
    }
    public void setRemoteStarterKbnText(String remoteStarterKbnText) {
        RemoteStarterKbnText = remoteStarterKbnText;
    }
    public String getHanbaiJokenKbnText() {
        return HanbaiJokenKbnText;
    }
    public void setHanbaiJokenKbnText(String hanbaiJokenKbnText) {
        HanbaiJokenKbnText = hanbaiJokenKbnText;
    }
    public String getShiyoMokutekiKbnText() {
        return ShiyoMokutekiKbnText;
    }
    public void setShiyoMokutekiKbnText(String shiyoMokutekiKbnText) {
        ShiyoMokutekiKbnText = shiyoMokutekiKbnText;
    }
    public String getUsedKbnText() {
        return UsedKbnText;
    }
    public void setUsedKbnText(String usedKbnText) {
        UsedKbnText = usedKbnText;
    }
    public String getEngineStartKbnText() {
        return EngineStartKbnText;
    }
    public void setEngineStartKbnText(String engineStartKbnText) {
        EngineStartKbnText = engineStartKbnText;
    }
    public String getBonusKasanMonthKbnText() {
        return BonusKasanMonthKbnText;
    }
    public void setBonusKasanMonthKbnText(String bonusKasanMonthKbnText) {
        BonusKasanMonthKbnText = bonusKasanMonthKbnText;
    }
    public String getNoshaDateKbnText() {
        return NoshaDateKbnText;
    }
    public void setNoshaDateKbnText(String noshaDateKbnText) {
        NoshaDateKbnText = noshaDateKbnText;
    }
    public String getDaikoMoshikomiKbnText() {
        return DaikoMoshikomiKbnText;
    }
    public void setDaikoMoshikomiKbnText(String daikoMoshikomiKbnText) {
        DaikoMoshikomiKbnText = daikoMoshikomiKbnText;
    }

    
    
}