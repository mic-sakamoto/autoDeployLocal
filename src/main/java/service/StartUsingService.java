package service;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.StartUsingDao;
import model.StartUsingModel;
import utils.CommonUtil;
import utils.ObicApiUtill;
import utils.StringUtil;
import utils.log4j.LogUtil;

public class StartUsingService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    StartUsingDao dao = new StartUsingDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(StartUsingModel model) throws Exception {
        return true;
    }

    // SMS送信処理
    public boolean sendSMS(StartUsingModel model) throws Exception {

        Connection con = dao.getCon();
        con.setAutoCommit(false);

        boolean result = true;

        try {
            // 入力内容確認
            String errStr = checkInput(model);
            if (CommonUtil.chkNotNullEmpty(errStr)) {
                model.setSuccessFlg("9");
                model.setErrormessage(errStr);
                return false;
            }

            // OBIC契約照会API
            ObicApiUtill.KeiyakuShokaiReqData keiyakuShokaiReqData = new ObicApiUtill.KeiyakuShokaiReqData();
            keiyakuShokaiReqData.setKeiyakuno(model.getContract_number());
            HashMap<String, Object> jsonMap = ObicApiUtill.KeiyakuShokaiApi(as, keiyakuShokaiReqData);
            if (jsonMap != null) {
                // 生年月日比較
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("birthYear", model.getBirth_year());
                map.put("birthMonth", model.getBirth_month());
                map.put("birthDay", model.getBirth_date());
                map.put("telNo1", model.getTel_number1());
                map.put("telNo2", model.getTel_number2());
                map.put("telNo3", model.getTel_number3());
                // 契約番号&入力内容に該当する会員
                if (comSvc.checkApiResponse(map, jsonMap)) {

                    // TODO MessageSendテーブルへの登録処理
                    // String moshikomiKbn = model.getMoshikomiKbn();
                    // String number = model.getLicenseNumber();
                    // String sendMethod = model.getSendMethod();
                    // String mail = model.getMail();
                    // String tel = model.getMobileNum1() + model.getMobileNum2() + model.getMobileNum3();
                    // comSvc.sendLoginMethod(moshikomiKbn, number, sendMethod, mail, tel, con);

                    // // 顧客のUserIdを取得
                    // String customerId = comdao.getCustomerIdFromContactNo(model.getContract_number());
                    // // ワンタイムパスワードを生成
                    // String onetimePass = comSvc.generateOnetimePassword();
                    // // UserInfoのOnetimePasswordとLimitを更新
                    // comdao.updateUserOnetimepassword(onetimePass, customerId, con);
                    // // SendMessageに登録
                    // String telNo = model.getTel_number1() + model.getTel_number2() + model.getTel_number3();
                    // comSvc.insertMessageSend(1, telNo, "【USS-SSオートローン】ログイン用パスワード設定通知", makeSmsBody(onetimePass), con);
                } else {
                    throw new Exception("契約番号[" + model.getContract_number() + "]と入力データに紐づく顧客データ無し");
                }
            } else {
                // 該当会員なし
                throw new Exception("API実行結果がNULL");
            }
        } catch (Exception e) {
            LogUtil.error(e);
            result = false;
            model.setSuccessFlg("9");
            model.setErrormessage("契約番号に誤りがあります");
            con.rollback();
        } finally {
            con.setAutoCommit(true);
            con.close();
        }

        return result;
    }

    public String makeSmsBody(String onetimePassword) {

        String body = "以下のURLにてUSS-SSオートローン専用WEBサイトのログインパスワード設定をお願いします。";
        body += "https://uss-ss-al.net/password-set?AuthCode=" + onetimePassword;

        return body;
    }

    /**
     * 契約照会APIのレスポンスと入力値を比較し結果を返す
     * 
     * @param model
     * @param apiRes
     * @return
     */
    public boolean checkApiResponse(StartUsingModel model, HashMap<String, Object> apiRes) {
        boolean result = true;

        try {
            // 生年月日
            String birthDate = StringUtil.dataToString(apiRes.get("kjn_birthday"));
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyMMdd");
            LocalDate date = LocalDate.parse(birthDate, inputFormatter);
            String birthYear = StringUtil.IntegerToString(date.getYear());
            String birthMonth = StringUtil.IntegerToString(date.getMonthValue());
            String birthDay = StringUtil.IntegerToString(date.getDayOfMonth());
            // 電話番号
            String telNo1 = StringUtil.dataToString(apiRes.get("kjn_home_telno1_1"));
            String telNo2 = StringUtil.dataToString(apiRes.get("kjn_home_telno1_2"));
            String telNo3 = StringUtil.dataToString(apiRes.get("kjn_home_telno1_3"));

            // 生年月日比較
            if (!model.getBirth_year().equals(birthYear))
                throw new Exception("生年月日：年不一致");
            if (!model.getBirth_month().equals(birthMonth))
                throw new Exception("生年月日：月不一致");
            if (!model.getBirth_date().equals(birthDay))
                throw new Exception("生年月日：日不一致");
            // 電話番号比較
            if (!model.getTel_number1().equals(telNo1))
                throw new Exception("電話番号１不一致");
            if (!model.getTel_number2().equals(telNo2))
                throw new Exception("電話番号２不一致");
            if (!model.getTel_number3().equals(telNo3))
                throw new Exception("電話番号３不一致");

        } catch (Exception e) {
            result = false;
            LogUtil.error(className, e);
        }

        return result;
    }

    /**
     * 入力内容のチェック
     * 
     * @param model
     * @return
     * @throws Exception
     */
    public String checkInput(StartUsingModel model) throws Exception {
        StringBuffer errStr = new StringBuffer();

        try {

            if (!CommonUtil.chkNotNullEmpty(model.getContract_number())) {
                errStr.append("契約番号を入力してください<br>");
            }
            if (!CommonUtil.chkNotNullEmpty(model.getLast_name_kana())) {
                errStr.append("セイを入力してください<br>");
            }
            if (!CommonUtil.chkNotNullEmpty(model.getFirst_name_kana())) {
                errStr.append("メイを入力してください<br>");
            }
            if (!CommonUtil.chkNotNullEmpty(model.getBirth_year())) {
                errStr.append("年を入力してください<br>");
            }
            if (!CommonUtil.chkNotNullEmpty(model.getBirth_month())) {
                errStr.append("月を入力してください<br>");
            }
            if (!CommonUtil.chkNotNullEmpty(model.getBirth_date())) {
                errStr.append("日を入力してください<br>");
            }
            if (!CommonUtil.chkNotNullEmpty(model.getTel_number1()) || !CommonUtil.chkNotNullEmpty(model.getTel_number2()) || !CommonUtil.chkNotNullEmpty(model.getTel_number3())) {
                errStr.append("携帯電話番号を入力してください<br>");
            }
        } catch (Exception e) {
            LogUtil.error(e);
            throw e;
        }

        return errStr.toString();
    }

}