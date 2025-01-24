package service;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.StatusDao;
import model.StatusModel;
import utils.CommonUtil;
import utils.ObicApiUtill;
import utils.StringUtil;

public class StatusService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    StatusDao dao = new StatusDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(StatusModel model) throws Exception {
        
        // 審査・契約状況
        shinsaJokyo(model);
        
        return true;
    }
    
    public boolean shinsaJokyo(StatusModel model) throws Exception {

        // OBIC審査状況API
        ObicApiUtill.ShinsaKeiyakuJokyoReqData shinsaKeiyakuJokyoReqData = new ObicApiUtill.ShinsaKeiyakuJokyoReqData();
//        shinsaKeiyakuJokyoReqData.setWebmousikomiid(model.getWebAppId());
        // DEMO用
        shinsaKeiyakuJokyoReqData.setWebmousikomiid("2020011024001");
        HashMap<String, String> sinsakeiyakujoukyouMap = ObicApiUtill.ShinsaKeiyakuJokyoApi(as, shinsaKeiyakuJokyoReqData);
//        
//        // APIから取得
//        if (sinsakeiyakujoukyouMap != null) {
//            
//            // 契約番号
//            String keiyakuno = StringUtil.dataToString(sinsakeiyakujoukyouMap.get("mkk_kei_keiyakuno"));
//            // OBIC契約内容照会API
//            ObicApiUtill.KeiyakuShokaiReqDate keiyakuShokaiReqDate = new ObicApiUtill.KeiyakuShokaiReqDate();
//            keiyakuShokaiReqDate.setKeiyakuno(keiyakuno);
//            HashMap<String, Object> keiyakunaiyousyoukaiMap = ObicApiUtill.KeiyakuShokaiApi(as, keiyakuShokaiReqDate);
//            
//            // ステータス決定
//            String status = comSvc.contractStatus(sinsakeiyakujoukyouMap, keiyakunaiyousyoukaiMap);
//            
//            HashMap<String, Object> statusMap = comSvc.getStatusText(status, model.getRoleType());
//            
//            model.setStatus(status);
//            model.setStatusText(StringUtil.dataToString(statusMap.get("StatusText")));
//            
//            
//            // 結果回答日時
//            model.setScreeningAppResDate(StringUtil.nullToEmptyChar(StringUtil.dataToString(sinsakeiyakujoukyouMap.get("ukj_kekkakaitoudatetime"))));
//        // 審査結果区分
//        String msi_hunoukubun = StringUtil.nullToEmptyChar(StringUtil.dataToString(sinsakeiyakujoukyouMap.get("msi_hunoukubun")));
//        // 審査結果備考
//        String msi_hunouriyuubikou = StringUtil.nullToEmptyChar(StringUtil.dataToString(sinsakeiyakujoukyouMap.get("msi_hunouriyuubikou")));
        
        // 審査結果
//        model.setScreeningAppRes(msi_hunoukubun + msi_hunouriyuubikou);
//            // 審査結果理由
//            model.setScreeningAppResReason(StringUtil.nullToEmptyChar(StringUtil.dataToString(sinsakeiyakujoukyouMap.get("msi_hunouriyuukubun"))));
//            // 契約日
//            model.setVerifiedDate(StringUtil.nullToEmptyChar(StringUtil.dataToString(sinsakeiyakujoukyouMap.get("mkk_kei_keiyakudate"))));
//            model.setLoanDate(StringUtil.nullToEmptyChar(StringUtil.dataToString(sinsakeiyakujoukyouMap.get("mkk_kas_kasitukedate"))));
//            
//            model.setMccsStatusText(StringUtil.nullToEmptyChar(StringUtil.dataToString(sinsakeiyakujoukyouMap.get("mcc_mccsstatuskubun"))));
//            model.setMccsAttachDate(StringUtil.nullToEmptyChar(StringUtil.dataToString(sinsakeiyakujoukyouMap.get("mcc_mccstoritukedate"))));
//            
//            model.setMccsShippingDate(StringUtil.nullToEmptyChar(StringUtil.dataToString(sinsakeiyakujoukyouMap.get("mcc_hassoudate"))));
//            model.setMccsAttachScheduleDate(StringUtil.nullToEmptyChar(StringUtil.dataToString(sinsakeiyakujoukyouMap.get("mcc_sagyoutentoritukeyoteidate"))));
//            model.setMccsSlipNumber(StringUtil.nullToEmptyChar(StringUtil.dataToString(sinsakeiyakujoukyouMap.get("mcc_hassoudenpyoubangou"))));
//            
//            
//            
//            model.setContractNumber(StringUtil.nullToEmptyChar(StringUtil.dataToString(sinsakeiyakujoukyouMap.get("mkk_kei_keiyakuno"))));
//           
//            
//        }
        
/*
 * 契約状況エリア
 */
HashMap<String, Object> statusMap = comSvc.getStatusText(model.getStatus(), model.getRoleType());
model.setStatusText(StringUtil.dataToString(statusMap.get("StatusText")));
model.setStatusColor(StringUtil.dataToString(statusMap.get("StatusColor")));

model.setScreeningAppResDate("2024/12/12");
model.setScreeningAppRes("結果区分＋備考");
model.setScreeningAppResReason("審査結果の履修");
model.setVerifiedDate("2024/12/12");
model.setLoanDate("2024/12/12");


/*
 * MCCSエリア
 */
model.setMccsStatusText("申込情報の確認");
model.setLimitDate("2024/04/01 15:00");
model.setMccsAttachDate("2024/12/12");

model.setMccsShippingDate("2024/12/12");
model.setMccsAttachScheduleDate("2024/12/12");
model.setMccsSlipNumber("111111111");
model.setContractNumber("123456789");
      
      
      
        /*
         * 基本情報エリア
         */
        // DBから契約情報取得
        ArrayList<HashMap<String, Object>> contractList = dao.getContract(model.getWebAppId());
        if (contractList != null) {
            
            HashMap<String, Object> contractMap = contractList.get(0);
            
            String firstScreeningAppDate = StringUtil.dataToString(contractMap.get("FirstScreeningAppDate"));
            String screeningAppDate = StringUtil.dataToString(contractMap.get("ScreeningAppDate"));
            String mainAppDate = StringUtil.dataToString(contractMap.get("MainAppDate"));
            
            // 本契約
            if (CommonUtil.chkNotNullEmpty(mainAppDate)) {
                model.setScreeningAppType("3");
            }
            // 再審査（初回と申請日が違う）
            else if (CommonUtil.chkNotNullEmpty(firstScreeningAppDate)
                            && !firstScreeningAppDate.equals(screeningAppDate)) {
                model.setScreeningAppType("2");
            }
            // 新規審査
            else if (CommonUtil.chkNotNullEmpty(firstScreeningAppDate)
                            && firstScreeningAppDate.equals(screeningAppDate)) {
                model.setScreeningAppType("1");
            }
            
            
            model.setFirstScreeningAppDate(firstScreeningAppDate);
            model.setScreeningAppDate(screeningAppDate);
            model.setMainAppDate(mainAppDate);
            
            model.setCustomerId(StringUtil.dataToString(contractMap.get("CustomerId")));
            
            String inputRoleKbn = StringUtil.dataToString(contractMap.get("InputRoleKbn"));
            if ("2".equals(inputRoleKbn)) {
                model.setInputRoleKbn("加盟店");
            }
            if ("3".equals(inputRoleKbn)) {
                model.setInputRoleKbn("顧客");
            }
            String isCustomerInputDone = StringUtil.dataToString(contractMap.get("IsCustomerInputDone"));
            if ("0".equals(isCustomerInputDone)) {
                model.setIsCustomerInputDone("未");
            }
            if ("1".equals(isCustomerInputDone)) {
                model.setIsCustomerInputDone("済");
            }
            
            String isProxyInput = StringUtil.dataToString(contractMap.get("IsProxyInput"));
            if ("1".equals(isProxyInput)) {
                model.setProxyInput("代行入力");
            }
            
            model.setStaffId(StringUtil.dataToString(contractMap.get("StaffId")));
            model.setStaffName(StringUtil.dataToString(contractMap.get("StaffName")));
            
            
        }
        
        /*
         * 申込・契約内容エリア
         */
        if (sinsakeiyakujoukyouMap.size() > 0) {
            HashMap<String, Object> map = comSvc.makeDataFromApi(sinsakeiyakujoukyouMap);
            
            comSvc.setKbnToText(map);
            
            CommonUtil.mapToBean(map, model);
            
            Gson gson = new Gson();
            String jsonString = gson.toJson(map);
            model.setContractJson(jsonString);
        }
        
        
        return true;
    }
    
}