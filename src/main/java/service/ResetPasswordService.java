package service;

import java.sql.Connection;
import java.util.HashMap;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.ResetPasswordDao;
import model.ResetPasswordModel;
import utils.CommonUtil;
import utils.ObicApiUtill;
import utils.log4j.LogUtil;

public class ResetPasswordService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    ResetPasswordDao dao = new ResetPasswordDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(ResetPasswordModel model) throws Exception {

        return true;

    }

    /**
     * ログインIDから顧客を特定しパスワード設定画面のURLをSMSまたはメールで送信
     * 
     * @param model
     * @return
     * @throws Exception
     */
    public boolean resetPasswordWithId(ResetPasswordModel model) throws Exception {

        // デモサイトでは処理しない
        if (as.getText("DEMO-FLG").equals("1")) {
            model.setSuccessFlg("1");
            return true;
        }

        Connection con = dao.getCon();
        con.setAutoCommit(false);

        try {
            String mail = model.getEmail();
            String telNo = model.getTel_number1() + model.getTel_number2() + model.getTel_number3();
            String customerId = model.getLoginId();
            comSvc.sendLoginGuide(customerId, model.getSendMethod(), mail, telNo, con);
            model.setSuccessFlg("1");
        } catch (Exception e) {
            LogUtil.error(e);
            model.setSuccessFlg("9");
            model.setMessage(e.getMessage());
            con.rollback();
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
        return true;
    }

    /**
     * 生年月日・電話番号をOBICの契約内容照会APIのレスポンスと比較、
     * 顧客を特定しパスワード設定画面のURLをSMSで送信
     * 
     * @param model
     * @return
     * @throws Exception
     */
    public boolean resetPasswordWithCustomerInfo(ResetPasswordModel model) throws Exception {

        // デモサイトでは処理しない
        if (as.getText("DEMO-FLG").equals("1")) {
            model.setSuccessFlg("1");
            return true;
        }

        Connection con = dao.getCon();
        con.setAutoCommit(false);

        try {
            // OBIC契約照会API
            ObicApiUtill.KeiyakuShokaiReqData keiyakuShokaiReqData = new ObicApiUtill.KeiyakuShokaiReqData();
            keiyakuShokaiReqData.setKeiyakuno(model.getContract_number());
            HashMap<String, Object> jsonMap = ObicApiUtill.KeiyakuShokaiApi(as, keiyakuShokaiReqData);
            if (jsonMap != null) {
                // 契約番号&入力内容に該当する会員
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("birthYear", comSvc.convertSeirekiToWareki(model.getBirth_year()).get("year"));
                map.put("birthMonth", model.getBirth_month());
                map.put("birthDay", model.getBirth_date());
                map.put("telNo1", model.getCustomer_tel_number1());
                map.put("telNo2", model.getCustomer_tel_number2());
                map.put("telNo3", model.getCustomer_tel_number3());
                if (comSvc.checkApiResponse(map, jsonMap)) {
                    // 契約番号から顧客IDを取得
                    String customerId = comdao.getCustomerIdFromContactNo(model.getContract_number());
                    String telNo = model.getCustomer_tel_number1() + model.getCustomer_tel_number2() + model.getCustomer_tel_number3();
                    if (CommonUtil.chkNotNullEmpty(customerId)) {
                        comSvc.sendLoginGuide(customerId, "sms", "", telNo, con);
                        model.setSuccessFlg("1");
                    } else {
                        throw new Exception("契約番号[" + model.getContract_number() + "]に紐づく顧客データ無し");
                    }
                } else {
                    throw new Exception("入力データに紐づくOBICデータ無し");
                }
            } else {
                throw new Exception("契約番号[" + model.getContract_number() + "]に紐づくOBICデータ無し");
            }
        } catch (Exception e) {
            LogUtil.error(e);
            model.setSuccessFlg("9");
            model.setMessage(e.getMessage());
            con.rollback();
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
        return true;

    }

    public String maskEmail(String email) {

        // メールアドレスを「ユーザー名」と「ドメイン」に分割
        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];

        // ユーザー名のマスキング
        String maskedUsername;
        if (username.length() <= 2) {
            // ユーザー名が2文字以下の場合、そのまま表示
            maskedUsername = username;
        } else {
            // 最初の2文字を保持し、残りを「*」で埋める
            maskedUsername = username.substring(0, 2) + "*".repeat(username.length() - 2);
        }

        // マスキングされたメールアドレスを返す
        return maskedUsername + "@" + domain;
    }

}