package service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import db.dao.ApplicationDao;
import db.dao.CommonDao;
import model.ApplicationModel;
import utils.CommonUtil;
import utils.ObicApiUtill;
import utils.ObicApiUtill.ShinsaKeiyakuJokyoReqData;
import utils.StringUtil;
import utils.log4j.LogUtil;

public class ApplicationService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    ApplicationDao dao = new ApplicationDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(ApplicationModel model) throws Exception {

        return true;

    }

    /**
     * WEB受付IDから申込内容を取得
     * 
     * @param model
     */
    public void getAppInfo(ApplicationModel model) throws Exception {

        // WEB受付IDがブランクの時は新規申し込みとして何もしない
        if (!CommonUtil.chkNotNullEmpty(model.getWebAppId())) {
            LogUtil.info("新規申し込み");
            model.setStatusKbn("1");
            model.setIsCustomerInputDone("0");
            return;
        } else {
            LogUtil.info("既存申し込み読み込み");
        }

        // OBIC審査状況API
        ShinsaKeiyakuJokyoReqData shinsaKeiyakuJokyoReqDate = new ShinsaKeiyakuJokyoReqData();
        // shinsaKeiyakuJokyoReqDate.setWebmousikomiid(model.getWebAppId());
        // TODO デモサイト用にAPIへのリクエストは固定値
        shinsaKeiyakuJokyoReqDate.setWebmousikomiid("2020011024001");
        HashMap<String, String> sinsakeiyakujoukyouMap = ObicApiUtill.ShinsaKeiyakuJokyoApi(as, shinsaKeiyakuJokyoReqDate);

        // 契約状況テーブルから必要情報を取得
        HashMap<String, Object> contractMap = dao.getContractInfo(model);
        model.setIsCustomerInputDone(CommonUtil.strNulltoBlank(contractMap.get("IsCustomerInputDone")));
        model.setInputModeKbn(CommonUtil.strNulltoBlank(contractMap.get("InputModeKbn")));
        model.setStatusKbn(CommonUtil.strNulltoBlank(contractMap.get("StatusKbn")));
        model.setLicenseNumber(CommonUtil.strNulltoBlank(contractMap.get("LicenseNumber")));
        model.setHojinNumber(CommonUtil.strNulltoBlank(contractMap.get("HojinNumber")));
        model.setIsTempExist("0");

        String customerId = CommonUtil.strNulltoBlank(contractMap.get("CustomerId"));
        String storeId = CommonUtil.strNulltoBlank(contractMap.get("StoreId"));

        Gson gson = new Gson();
        // 下書き取得
        HashMap<String, Object> shitagakiMap = dao.getScreeningTemp(model);

        // APIから値を取得
        String jsonString = "";
        HashMap<String, Object> jsonMap = new HashMap<String, Object>();
        if (!sinsakeiyakujoukyouMap.isEmpty()) {
            jsonMap = comSvc.makeDataFromApi(sinsakeiyakujoukyouMap);

            // 運転免許証・法人番号はAPIから取得
            model.setLicenseNumber(CommonUtil.strNulltoBlank(jsonMap.get("licenseNumber")));
            model.setHojinNumber(CommonUtil.strNulltoBlank(jsonMap.get("hojinNumber")));

            switch (model.getAppKbn()) {
                case "2":
                case "3":
                case "4":
                    // 再審査or本申込で一次審査が所有権留保付可決の場合は車の所有者固定
                    String shinsaKekkaKbn = StringUtil.dataToString(jsonMap.get("shinsaKekkaKbn"));
                    LogUtil.info("shinsaKekkaKbn:" + shinsaKekkaKbn);
                    if (shinsaKekkaKbn.equals("1")) {
                        jsonMap.put("Owner", "その他");
                        jsonMap.put("OwnerSonota", "USSサポートサービス");
                    }
                    break;
            }
        }
        // 下書きがあれば上書き
        if (!shitagakiMap.isEmpty()) {
            LogUtil.info(className, "下書きアリ");
            for (String key : shitagakiMap.keySet()) {
                jsonMap.put(key, shitagakiMap.get(key));
            }
        } else {
            LogUtil.info(className, "下書きナシ");
        }

        jsonString = gson.toJson(jsonMap);
        model.setInputMapJson(jsonString);

        // // 下書きがなければAPIから入力を取得
        // if (!jsonMap.isEmpty()) {
        // LogUtil.info(className, "下書きアリ");
        // String jsonString = gson.toJson(jsonMap);
        // model.setInputMapJson(jsonString);
        // model.setIsTempExist("1");
        // } else {
        // LogUtil.info(className, "下書きナシ");
        // if (sinsakeiyakujoukyouMap.size() > 0) {
        // HashMap<String, Object> map = comSvc.makeDataFromApi(sinsakeiyakujoukyouMap);
        // switch (model.getAppKbn()) {
        // case "2":
        // case "3":
        // case "4":
        // // 再審査or本申込で一次審査が所有権留保付可決の場合は車の所有者固定
        // String shinsaKekkaKbn = StringUtil.dataToString(map.get("shinsaKekkaKbn"));
        // LogUtil.info("shinsaKekkaKbn:" + shinsaKekkaKbn);
        // if (shinsaKekkaKbn.equals("1")) {
        // map.put("Owner", "その他");
        // map.put("OwnerSonota", "USSサポートサービス");
        // }
        // break;
        // }
        // String jsonString = gson.toJson(map);
        // model.setInputMapJson(jsonString);
        // }
        // }
    }

    /**
     * 入力内容をScreeningTempに保存
     * 新規申し込み時はWeb受付ID生成時に契約テーブルにも新規INSERT
     * 
     * @param model
     * @return
     * @throws Exception
     */
    public boolean saveAppInput(ApplicationModel model) throws Exception {
        boolean result = true;

        Connection con = dao.getCon();
        con.setAutoCommit(false);

        try {
            String sql_INST_CLM = "";
            String sql_INST_VAL = "";
            String sql_UPDT = "";
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, Object> dataMap = objectMapper.readValue(model.getInputMapJson(), HashMap.class);

            // 新規申し込み時はWeb受付IDを作成＆契約テーブルにINSERT
            if (!CommonUtil.chkNotNullEmpty(model.getWebAppId())) {
                // 現在の日付を取得
                String webAppId = comSvc.getWebAppId();
                LogUtil.info(className, "WebAppId : " + webAppId);
                model.setWebAppId(webAppId);
                // 契約管理テーブルに新規登録
                // TODO デモサイト用に更新しない
                // dao.registNewContarct(model, true,con);
            }

            sql_INST_CLM += "WebAppId";
            sql_INST_VAL += "'" + model.getWebAppId() + "'";

            for (String key : dataMap.keySet()) {
                String value = String.valueOf(dataMap.get(key));
                // 値のないものは何もしない
                if (!CommonUtil.chkNotNullEmpty(value))
                    continue;
                // INSERT用のカラム名
                sql_INST_CLM += ",";
                sql_INST_CLM += key;
                // INSERT用のVALUE
                sql_INST_VAL += ",";
                sql_INST_VAL += "'" + value + "'";
                // UPDATE用SQL
                if (!sql_UPDT.isEmpty())
                    sql_UPDT += ",";
                sql_UPDT += key + " = '" + dataMap.get(key) + "' ";
            }

            // TODO デモサイト用に更新しない
            // dao.saveAppInput(sql_INST_CLM, sql_INST_VAL, sql_UPDT, con);
            model.setMessage("入力内容が保存されました。");

            // OBIC審査状況API
            ShinsaKeiyakuJokyoReqData shinsaKeiyakuJokyoReqDate = new ShinsaKeiyakuJokyoReqData();
            shinsaKeiyakuJokyoReqDate.setWebmousikomiid(model.getWebAppId());
            HashMap<String, String> sinsakeiyakujoukyouMap = ObicApiUtill.ShinsaKeiyakuJokyoApi(as, shinsaKeiyakuJokyoReqDate);

            // KeiyakuShokaiReqData keiyakuShokaiReqData = new KeiyakuShokaiReqData();
            // keiyakuShokaiReqData.setKeiyakuno("0");
            // HashMap<String, Object> keiyakunaiyousyoukaiMap = ObicApiUtill.KeiyakuShokaiApi(as, keiyakuShokaiReqData);

            // 下書き時点では契約済みにはなっていないためカラで渡す
            String contractStatusKbn = comSvc.contractStatus(sinsakeiyakujoukyouMap, new HashMap<String, Object>());
            model.setStatusKbn(contractStatusKbn);
            LogUtil.info("ステータス区分:" + model.getStatusKbn());
            model.setSuccessFlg("1");

        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            model.setSuccessFlg("9");
            model.setMessage("入力内容の保存に失敗しました。 " + e.getMessage());
        } finally {
            con.setAutoCommit(true);
            con.close();
        }

        return result;
    }

    /**
     * 申込処理
     * 
     * @param model
     * @return
     */
    public boolean confirmApp(ApplicationModel model) throws Exception {
        boolean result = true;

        Connection con = dao.getCon();
        con.setAutoCommit(false);
        try {
            // 入力内容のチェック
            // TODO 要確認！不要なところにもチェック入ってる
            // try {
            // ArrayList<HashMap<String, String>> errorList = new ArrayList<HashMap<String, String>>();
            // if (!this.validattionCheck(model, errorList)) {
            // model.setMessage("入力内容に不備があります。");
            // model.setSuccessFlg("9");
            // model.setErrorList(errorList);
            // result = false;
            // return result;
            // }
            //
            // } catch (Exception e) {
            // model.setMessage("入力内容に不備があります。");
            // throw e;
            // }

            /**
             * 申込区分
             * 1:新規
             * 2:不備返却再入力
             * 3:審査結果×？再入力
             * 4:本申込
             */
            String appKbn = model.getAppKbn();

            String statusKbn = model.getStatusKbn();
            String inputModeKbn = model.getInputModeKbn();
            String inputRoleKbn = model.getInputRoleKbn();

            // WEB受付IDの発行
            String preWebAppId = "";// 下書き削除用のWEB受付ID保持変数
            String webAppId = "";// WEB受付ID
            try {
                // 新規審査申込
                if (appKbn.equals("1")) {
                    // WEB受付IDがなければ新規発行
                    webAppId = CommonUtil.chkNotNullEmpty(model.getWebAppId()) ? model.getWebAppId() : comSvc.getWebAppId();
                    preWebAppId = webAppId;
                }
                // 不備返却,再審査,本申込
                else {
                    // 枝番を更新したWEB受付ID発行
                    preWebAppId = model.getWebAppId();
                    webAppId = comSvc.getUpdateWebAppId(preWebAppId);
                }
                model.setWebAppId(webAppId);
            } catch (Exception e) {
                throw e;
            }

            // OBIC連携ファイル作成＆配置
            try {

            } catch (Exception e) {
                model.setMessage("連携ファイルの作成に失敗しました。");
                throw e;
            }

            // DB登録
            try {
                switch (appKbn) {
                    case "1": // 新規申し込み
                        if ("1".equals(statusKbn) || "2".equals(statusKbn) || "3".equals(statusKbn)) {// 審査申込前,審査申込前/未入力,審査申込前/入力済み
                            if ("0".equals(inputModeKbn)) { // 加盟店ページ
                                // ステータスを審査申込登録中に
                                model.setIsCustomerInputDone("1");
                                model.setStatusKbn("4");
                            } else { // 顧客専用ページ
                                if ("1".equals(statusKbn) && "2".equals(inputRoleKbn)) {// 審査申込前・加盟店入力
                                    // ステータスを審査申込前/未入力に
                                    model.setStatusKbn("2");
                                } else if ("1".equals(statusKbn) && "3".equals(inputRoleKbn)) {// 審査申込前・顧客入力
                                    // ステータスを審査申込前/入力済みに
                                    model.setStatusKbn("3");
                                } else if (("2".equals(statusKbn) && "3".equals(inputRoleKbn))) {// 審査申込前/未入力・顧客入力
                                    // ステータスを審査申込登録中に
                                    model.setStatusKbn("4");
                                } else if ("3".equals(statusKbn) && "3".equals(inputRoleKbn)) {// 審査申込前/入力済み・加盟店入力
                                    // ステータスを審査申込登録中に
                                    model.setStatusKbn("4");
                                }
                            }
                        }
                        break;
                    case "2": // 不備返却再入力
                    case "3": // 審査結果×再入力
                        model.setStatusKbn("4");
                        break;
                    case "4": // 本申込
                        if ("10".equals(statusKbn) && "3".equals(inputRoleKbn)) { // 審査申込可決・顧客入力
                            // ステータスを本申込前／入力済に
                            model.setWebAppId(preWebAppId);
                            model.setStatusKbn("14");

                        }
                        // TODO 加盟店で顧客が入力していない状態で申込とどうなる？
                        else if ("10".equals(statusKbn) && "2".equals(inputRoleKbn)) { // 審査申込可決・加盟店入力
                            throw new Exception("顧客の申し込みが完了していません");
                        } else if ("14".equals(statusKbn) && "2".equals(inputRoleKbn)) { // 本申込/入力済・加盟店入力
                            model.setStatusKbn("15");
                        }
                        break;

                    default:
                        // 必要に応じてデフォルト処理を追加
                        break;
                }

                if (CommonUtil.chkNotNullEmpty(model.getContractId())) {
                    // 契約管理テーブルを更新
                    dao.updateContract(model, con);
                } else {
                    // 契約管理テーブルに新規登録
                    dao.registContarct(model, false, con);
                }

                if (model.getStatusKbn().equals("15")) {
                    // TODO 本申込テーブルに登録処理
                }

                // 下書きがある場合は削除
                if (dao.deleteScreeningTemp(preWebAppId, con)) {
                    LogUtil.info("WEB受付ID：" + model.getWebAppId() + "の下書きを削除しました");
                }
            } catch (Exception e) {
                model.setMessage(e.getMessage());
                throw e;
            }
            model.setMessage("申込が完了しました。");
            model.setSuccessFlg("1");
        } catch (Exception e) {
            con.rollback();
            LogUtil.error(e);
            model.setSuccessFlg("9");
            result = false;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }

        return result;
    }

    /**
     * 顧客にログイン手順送信
     * 
     * @param model
     * @return
     */
    public boolean resendLoginMethod(ApplicationModel model) throws Exception {

        boolean result = true;
        Connection con = dao.getCon();
        con.setAutoCommit(false);

        try {
            // TODO 未実装
            // comSvc.sendLoginMethod(model.getMoshikomiKbn(), model.getLicenseNumber(), model.getResendLoginMethod(), model.getResendMailAddress(),
            // model.getResendMobileNum(), con);
            model.setSuccessFlg("1");
            model.setMessage("通知完了しました。");
        } catch (Exception e) {
            con.rollback();
            result = false;
            model.setSuccessFlg("9");
            model.setMessage(e.getMessage());
            LogUtil.error(e);
        } finally {
            con.setAutoCommit(true);
            con.close();
        }

        return result;
    }

    public void getMccsMaker(ApplicationModel model) {
        try {
            ArrayList<String> makerArr = comSvc.getMccsMaker();
            model.setMccsMakerList(makerArr);
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    public void getMccsShamei(ApplicationModel model) {
        try {
            ArrayList<String> shameiArr = comSvc.getMccsShamei(model.getMccsMaker());
            model.setMccsShameiList(shameiArr);
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    public void getMccsKatashiki(ApplicationModel model) {
        try {
            ArrayList<String> katashikiArr = comSvc.getMccsKatashiki(model.getMccsShamei());
            model.setMccsKatashikiList(katashikiArr);
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    public boolean validattionCheck(ApplicationModel model, ArrayList<HashMap<String, String>> errorList) throws Exception {
        boolean result = true;
        String jsonStr = model.getInputMapJson();
        Gson _gson = new Gson();
        HashMap<String, String> jsonlist = _gson.fromJson(jsonStr, new TypeToken<HashMap<String, String>>() {
        }.getType());
        HashMap<String, String> errorMap = new HashMap<String, String>();

        /**
         * お申込者様情報
         * 
         */
        // お名前 姓
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiNameSei"))) {
            errorMap.put("MoshikomiNameSei", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認(数字拒否)
            if (!StringUtil.isNoNumZenStr(jsonlist.get("MoshikomiNameSei"), false, false)) {
                errorMap.put("MoshikomiNameSei", "全角文字で入力してください");
                result = false;
            }
            if ((jsonlist.get("MoshikomiNameMei") != null ? jsonlist.get("MoshikomiNameMei").length() : 0) + (jsonlist.get("MoshikomiNameSei") != null ? jsonlist.get("MoshikomiNameSei").length() : 0) < 30) {
                errorMap.put("MoshikomiNameSei", "30文字以内で入力してください");
                result = false;
            }
        }
        // お名前 名
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiNameMei"))) {
            errorMap.put("MoshikomiNameMei", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認(数字拒否)
            if (!StringUtil.isNoNumZenStr(jsonlist.get("MoshikomiNameMei"), false, false)) {
                errorMap.put("MoshikomiNameMei", "全角文字で入力してください");
                result = false;
            }
            if ((jsonlist.get("MoshikomiNameMei") != null ? jsonlist.get("MoshikomiNameMei").length() : 0) + (jsonlist.get("MoshikomiNameSei") != null ? jsonlist.get("MoshikomiNameSei").length() : 0) < 30) {
                errorMap.put("MoshikomiNameMei", "30文字以内で入力してください");
                result = false;
            }
        }
        // お名前(フリガナ) 姓
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiNameSeiKana"))) {
            errorMap.put("MoshikomiNameSeiKana", "入力されていません");
            result = false;
        } else {
            // 半角カナ、記号文字列か確認
            if (!StringUtil.isKanaKigoHanStr(jsonlist.get("MoshikomiNameSeiKana"), false, false)) {
                errorMap.put("MoshikomiNameSeiKana", "半角文字で入力してください");
                result = false;
            }
            if ((jsonlist.get("MoshikomiNameSeiKana") != null ? jsonlist.get("MoshikomiNameSeiKana").length() : 0) + (jsonlist.get("MoshikomiNameMeiKana") != null ? jsonlist.get("MoshikomiNameMeiKana").length() : 0) < 40) {
                errorMap.put("MoshikomiNameSeiKana", "40文字以内で入力してください");
                result = false;
            }
        }
        // お名前(フリガナ) 名
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiNameMeiKana"))) {
            errorMap.put("MoshikomiNameMeiKana", "入力されていません");
            result = false;
        } else {
            // 半角カナ、記号文字列か確認
            if (!StringUtil.isKanaKigoHanStr(jsonlist.get("MoshikomiNameMeiKana"), false, false)) {
                errorMap.put("MoshikomiNameMeiKana", "半角文字で入力してください");
                result = false;
            }
            if ((jsonlist.get("MoshikomiNameSeiKana") != null ? jsonlist.get("MoshikomiNameSeiKana").length() : 0) + (jsonlist.get("MoshikomiNameMeiKana") != null ? jsonlist.get("MoshikomiNameMeiKana").length() : 0) < 40) {
                errorMap.put("MoshikomiNameMeiKana", "40文字以内で入力してください");
                result = false;
            }
        }
        // 性別
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiSeibetsuKbn"))) {
            errorMap.put("MoshikomiSeibetsuKbn", "入力されていません");
            result = false;
        }
        // 年齢
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiAge"))) {
            errorMap.put("MoshikomiAge", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiAge"), false, false)) {
                errorMap.put("MoshikomiAge", "半角数字で入力してください");
                result = false;
            }
        }
        // 生年月日
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiBirthDateYear"))) {
            errorMap.put("MoshikomiBirthDateYear", "入力されていません");
            result = false;
        }
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiBirthDateMonth"))) {
            errorMap.put("MoshikomiBirthDateMonth", "入力されていません");
            result = false;
        }
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiBirthDateDay"))) {
            errorMap.put("MoshikomiBirthDateDay", "入力されていません");
            result = false;
        }
        // 電話：携帯（連絡先）
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiMobile1"))) {
            errorMap.put("MoshikomiMobile1", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiMobile1"), false, false)) {
                errorMap.put("MoshikomiMobile1", "半角数字で入力してください");
                result = false;
            }
        }
        // 電話：携帯（連絡先）
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiMobile2"))) {
            errorMap.put("MoshikomiMobile2", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiMobile2"), false, false)) {
                errorMap.put("MoshikomiMobile2", "半角数字で入力してください");
                result = false;
            }
        }
        // 電話：携帯（連絡先）
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiMobile3"))) {
            errorMap.put("MoshikomiMobile3", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiMobile3"), false, false)) {
                errorMap.put("MoshikomiMobile3", "半角数字で入力してください");
                result = false;
            }
        }
        // 自宅電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiTel1"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiTel1"), false, false)) {
                errorMap.put("MoshikomiTel1", "半角数字で入力してください");
                result = false;
            }
        }
        // 自宅電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiTel2"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiTel2"), false, false)) {
                errorMap.put("MoshikomiTel2", "半角数字で入力してください");
                result = false;
            }
        }
        // 自宅電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiTel3"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiTel3"), false, false)) {
                errorMap.put("MoshikomiTel3", "半角数字で入力してください");
                result = false;
            }
        }
        // 郵便番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiPost"))) {
            errorMap.put("MoshikomiPost", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiPost"), false, false)) {
                errorMap.put("MoshikomiPost", "半角数字で入力してください");
                result = false;
            }
        }
        // 住所１
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiAddress1"))) {
            errorMap.put("MoshikomiAddress1", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("MoshikomiAddress1"), false, false)) {
                errorMap.put("MoshikomiAddress1", "全角文字で入力してください");
                result = false;
            }
            if (jsonlist.get("MoshikomiAddress1").length() <= 27) {
                errorMap.put("MoshikomiAddress1", "27文字以内で入力してください");
                result = false;
            }
        }
        // 住所２
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiAddress2"))) {
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("MoshikomiAddress2"), false, false)) {
                errorMap.put("MoshikomiAddress2", "全角文字で入力してください");
                result = false;
            }
        }
        // 住所(フリガナ)
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiAddressKana"))) {
            errorMap.put("MoshikomiAddressKana", "入力されていません");
            result = false;
        } else {
            // 半角文字のみ
            if (!StringUtil.isHanStr(jsonlist.get("MoshikomiAddressKana"), false, false)) {
                errorMap.put("MoshikomiAddressKana", "半角文字で入力してください");
                result = false;
            }
        }
        // 配偶者
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiHaigushaKbn"))) {
            errorMap.put("MoshikomiHaigushaKbn", "入力されていません");
            result = false;
        }
        // 住居区分
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("JukyoKbn"))) {
            errorMap.put("JukyoKbn", "入力されていません");
            result = false;
        }
        // 住宅ローン・家賃支払い（配偶者含む）
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiLoanKbn"))) {
            errorMap.put("MoshikomiLoanKbn", "入力されていません");
            result = false;
        }
        // 同居人数（本人含む）（生計をマイナスにする別居家族含む）
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiDokyoNinzu"))) {
            errorMap.put("MoshikomiDokyoNinzu", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiDokyoNinzu"), false, false)) {
                errorMap.put("MoshikomiDokyoNinzu", "半角数字で入力してください");
                result = false;
            }
        }
        // 居住年数 年
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiKyojuYear"))) {
            errorMap.put("MoshikomiKyojuYear", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiKyojuYear"), false, false)) {
                errorMap.put("MoshikomiKyojuYear", "半角数字で入力してください");
                result = false;
            }
        }
        // 居住年数 月
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiKyojuMonth"))) {
            errorMap.put("MoshikomiKyojuMonth", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiKyojuMonth"), false, false)) {
                errorMap.put("MoshikomiKyojuMonth", "半角数字で入力してください");
                result = false;
            }
        }
        // 税込年収
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiNenshu"))) {
            errorMap.put("MoshikomiNenshu", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiNenshu"), false, false)) {
                errorMap.put("MoshikomiNenshu", "半角数字で入力してください");
                result = false;
            }
        }
        // ご職業
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiShokugyoKbn"))) {
            errorMap.put("MoshikomiShokugyoKbn", "入力されていません");
            result = false;
        }
        // 勤務先・学校情報
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiKinmusaki"))) {
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("MoshikomiKinmusaki"), false, false)) {
                errorMap.put("MoshikomiKinmusaki", "全角文字で入力してください");
                result = false;
            }
        }
        // 電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiKinmusakiTel1"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiKinmusakiTel1"), false, false)) {
                errorMap.put("MoshikomiKinmusakiTel1", "半角数字で入力してください");
                result = false;
            }
        }
        // 電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiKinmusakiTel2"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiKinmusakiTel2"), false, false)) {
                errorMap.put("MoshikomiKinmusakiTel2", "半角数字で入力してください");
                result = false;
            }
        }
        // 電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiKinmusakiTel3"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiKinmusakiTel3"), false, false)) {
                errorMap.put("MoshikomiKinmusakiTel3", "半角数字で入力してください");
                result = false;
            }
        }
        // 所在地郵便番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiKinmusakiPost"))) {
            errorMap.put("MoshikomiKinmusakiPost", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiKinmusakiPost"), false, false)) {
                errorMap.put("MoshikomiKinmusakiPost", "半角数字で入力してください");
                result = false;
            }
        }
        // 所在地１
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiKinmusakiAddress1"))) {
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("MoshikomiKinmusakiAddress1"), false, false)) {
                errorMap.put("MoshikomiKinmusakiAddress1", "全角文字で入力してください");
                result = false;
            }
            if (jsonlist.get("MoshikomiKinmusakiAddress1").length() <= 27) {
                errorMap.put("MoshikomiKinmusakiAddress1", "27文字以内で入力してください");
                result = false;
            }
        }
        // 所在地２
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiKinmusakiAddress2"))) {
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("MoshikomiKinmusakiAddress2"), false, false)) {
                errorMap.put("MoshikomiKinmusakiAddress2", "全角文字で入力してください");
                result = false;
            }
        }
        // 勤務年数 年
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiKinzokuYear"))) {
            errorMap.put("MoshikomiKinzokuYear", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiKinzokuYear"), false, false)) {
                errorMap.put("MoshikomiKinzokuYear", "半角数字で入力してください");
                result = false;
            }
        }
        // 勤務年数 月
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiKinzokuMonth"))) {
            errorMap.put("MoshikomiKinzokuMonth", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiKinzokuMonth"), false, false)) {
                errorMap.put("MoshikomiKinzokuMonth", "半角数字で入力してください");
                result = false;
            }
        }
        // 所属（部署）
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiKinmusakiShozoku"))) {
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("MoshikomiKinmusakiShozoku"), false, false)) {
                errorMap.put("MoshikomiKinmusakiShozoku", "全角文字で入力してください");
                result = false;
            }
        }
        // 従業員数
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiKinmusakiJugyoin"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiKinmusakiJugyoin"), false, false)) {
                errorMap.put("MoshikomiKinmusakiJugyoin", "半角数字で入力してください");
                result = false;
            }
        }
        // 預金残高
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiYokinZandaka"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiYokinZandaka"), false, false)) {
                errorMap.put("MoshikomiYokinZandaka", "半角数字で入力してください");
                result = false;
            }
        }
        // 世帯主 姓
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiSetainushiNameSei"))) {
        } else {
            // 全角文字列か確認(数字拒否)
            if (!StringUtil.isNoNumZenStr(jsonlist.get("MoshikomiSetainushiNameSei"), false, false)) {
                errorMap.put("MoshikomiSetainushiNameSei", "全角文字で入力してください");
                result = false;
            }
        }
        // 世帯主 名
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiSetainushiNameMei"))) {
        } else {
            // 全角文字列か確認(数字拒否)
            if (!StringUtil.isNoNumZenStr(jsonlist.get("MoshikomiSetainushiNameMei"), false, false)) {
                errorMap.put("MoshikomiSetainushiNameMei", "全角文字で入力してください");
                result = false;
            }
        }
        // 世帯主の税込年収
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiSetainushiNenshu"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiSetainushiNenshu"), false, false)) {
                errorMap.put("MoshikomiSetainushiNenshu", "半角数字で入力してください");
                result = false;
            }
        }
        // 世帯主のクレジット月額債務
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiSetainushiCreditSaimu"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiSetainushiCreditSaimu"), false, false)) {
                errorMap.put("MoshikomiSetainushiCreditSaimu", "半角数字で入力してください");
                result = false;
            }
        }
        // 受給情報
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiNenkinJukyuKbn"))) {
            errorMap.put("MoshikomiNenkinJukyuKbn", "入力されていません");
            result = false;
        }
        // 受給情報 その他
        if (CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiNenkinJukyuKbnSonota")) && "4".equals(jsonlist.get("MoshikomiNenkinJukyuKbn"))) {
            errorMap.put("MoshikomiNenkinJukyuKbnSonota", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("MoshikomiNenkinJukyuKbnSonota"), false, false)) {
                errorMap.put("MoshikomiNenkinJukyuKbnSonota", "全角文字で入力してください");
                result = false;
            }
        }
        // 年間受給額
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MoshikomiNenkinJukyuPrice"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MoshikomiNenkinJukyuPrice"), false, false)) {
                errorMap.put("MoshikomiNenkinJukyuPrice", "半角数字で入力してください");
                result = false;
            }
        }
        /**
         * 連帯保証人様情報
         * 
         */
        // 連帯保証人
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninKbn"))) {
            errorMap.put("HoshoninKbn", "入力されていません");
            result = false;
        }
        // お名前 姓
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninNameSei"))) {
            errorMap.put("HoshoninNameSei", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認(数字拒否)
            if (!StringUtil.isNoNumZenStr(jsonlist.get("HoshoninNameSei"), false, false)) {
                errorMap.put("HoshoninNameSei", "全角文字で入力してください");
                result = false;
            }
            if ((jsonlist.get("HoshoninNameSei") != null ? jsonlist.get("HoshoninNameSei").length() : 0) + (jsonlist.get("HoshoninNameMei") != null ? jsonlist.get("HoshoninNameMei").length() : 0) < 30) {
                errorMap.put("HoshoninNameSei", "40文字以内で入力してください");
                result = false;
            }
        }
        // お名前 名
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninNameMei"))) {
            errorMap.put("HoshoninNameMei", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認(数字拒否)
            if (!StringUtil.isNoNumZenStr(jsonlist.get("HoshoninNameMei"), false, false)) {
                errorMap.put("HoshoninNameMei", "全角文字で入力してください");
                result = false;
            }
            if ((jsonlist.get("HoshoninNameSei") != null ? jsonlist.get("HoshoninNameSei").length() : 0) + (jsonlist.get("HoshoninNameMei") != null ? jsonlist.get("HoshoninNameMei").length() : 0) < 30) {
                errorMap.put("HoshoninNameMei", "40文字以内で入力してください");
                result = false;
            }
        }
        // お名前(フリガナ) 姓
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninNameSeiKana"))) {
            errorMap.put("HoshoninNameSeiKana", "入力されていません");
            result = false;
        } else {
            // 半角カナ、記号文字列か確認
            if (!StringUtil.isKanaKigoHanStr(jsonlist.get("HoshoninNameSeiKana"), false, false)) {
                errorMap.put("HoshoninNameSeiKana", "半角文字で入力してください");
                result = false;
            }
            if ((jsonlist.get("HoshoninNameSeiKana") != null ? jsonlist.get("HoshoninNameSeiKana").length() : 0) + (jsonlist.get("HoshoninNameMeiKana") != null ? jsonlist.get("HoshoninNameMeiKana").length() : 0) < 40) {
                errorMap.put("HoshoninNameSeiKana", "40文字以内で入力してください");
                result = false;
            }
        }
        // お名前(フリガナ) 名
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninNameMeiKana"))) {
            errorMap.put("HoshoninNameMeiKana", "入力されていません");
            result = false;
        } else {
            // 半角カナ、記号文字列か確認
            if (!StringUtil.isKanaKigoHanStr(jsonlist.get("HoshoninNameMeiKana"), false, false)) {
                errorMap.put("HoshoninNameMeiKana", "半角文字で入力してください");
                result = false;
            }
            if ((jsonlist.get("HoshoninNameSeiKana") != null ? jsonlist.get("HoshoninNameSeiKana").length() : 0) + (jsonlist.get("HoshoninNameMeiKana") != null ? jsonlist.get("HoshoninNameMeiKana").length() : 0) < 40) {
                errorMap.put("HoshoninNameMeiKana", "40文字以内で入力してください");
                result = false;
            }
        }
        // 性別
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninSeibetsuKbn"))) {
            errorMap.put("HoshoninSeibetsuKbn", "入力されていません");
            result = false;
        }
        // 年齢
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninAge"))) {
            errorMap.put("HoshoninAge", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninAge"), false, false)) {
                errorMap.put("HoshoninAge", "半角数字で入力してください");
                result = false;
            }
        }
        // 生年月日
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninBirthDateYear"))) {
            errorMap.put("HoshoninBirthDateYear", "入力されていません");
            result = false;
        }
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninBirthDateMonth"))) {
            errorMap.put("HoshoninBirthDateMonth", "入力されていません");
            result = false;
        }
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninBirthDateDay"))) {
            errorMap.put("HoshoninBirthDateDay", "入力されていません");
            result = false;
        }
        // 携帯電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninMobile1"))) {
            errorMap.put("HoshoninMobile1", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninMobile1"), false, false)) {
                errorMap.put("HoshoninMobile1", "半角数字で入力してください");
                result = false;
            }
        }
        // 携帯電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninMobile2"))) {
            errorMap.put("HoshoninMobile2", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninMobile2"), false, false)) {
                errorMap.put("HoshoninMobile2", "半角数字で入力してください");
                result = false;
            }
        }
        // 携帯電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninMobile3"))) {
            errorMap.put("HoshoninMobile3", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninMobile3"), false, false)) {
                errorMap.put("HoshoninMobile3", "半角数字で入力してください");
                result = false;
            }
        }
        // 自宅電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninTel1"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninTel1"), false, false)) {
                errorMap.put("HoshoninTel1", "半角数字で入力してください");
                result = false;
            }
        }
        // 自宅電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninTel2"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninTel2"), false, false)) {
                errorMap.put("HoshoninTel2", "半角数字で入力してください");
                result = false;
            }
        }
        // 自宅電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninTel3"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninTel3"), false, false)) {
                errorMap.put("HoshoninTel3", "半角数字で入力してください");
                result = false;
            }
        }
        // 郵便番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninPost"))) {
            errorMap.put("HoshoninPost", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninPost"), false, false)) {
                errorMap.put("HoshoninPost", "半角数字で入力してください");
                result = false;
            }
        }
        // 住所１
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninAddress1"))) {
            errorMap.put("HoshoninAddress1", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("HoshoninAddress1"), false, false)) {
                errorMap.put("HoshoninAddress1", "全角文字で入力してください");
                result = false;
            }
            if (jsonlist.get("HoshoninAddress1").length() <= 27) {
                errorMap.put("HoshoninAddress1", "27文字以内で入力してください");
                result = false;
            }
        }
        // 住所２
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninAddress2"))) {
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("HoshoninAddress2"), false, false)) {
                errorMap.put("HoshoninAddress2", "全角文字で入力してください");
                result = false;
            }
        }
        // 住所(フリガナ)
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninAddressKana"))) {
            errorMap.put("HoshoninAddressKana", "入力されていません");
            result = false;
        } else {
            // 半角文字のみ
            if (!StringUtil.isHanStr(jsonlist.get("HoshoninAddressKana"), false, false)) {
                errorMap.put("HoshoninAddressKana", "半角文字で入力してください");
                result = false;
            }
        }
        // 税込年収
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninNenshu"))) {
            errorMap.put("HoshoninNenshu", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninNenshu"), false, false)) {
                errorMap.put("HoshoninNenshu", "半角数字で入力してください");
                result = false;
            }
        }
        // 居住年数 年
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninKyojuYear"))) {
            errorMap.put("HoshoninKyojuYear", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninKyojuYear"), false, false)) {
                errorMap.put("HoshoninKyojuYear", "半角数字で入力してください");
                result = false;
            }
        }
        // 居住年数 月
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninKyojuMonth"))) {
            errorMap.put("HoshoninKyojuMonth", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninKyojuMonth"), false, false)) {
                errorMap.put("HoshoninKyojuMonth", "半角数字で入力してください");
                result = false;
            }
        }
        // 住居区分
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninJukyoKbn"))) {
            errorMap.put("HoshoninJukyoKbn", "入力されていません");
            result = false;
        }
        // 住宅ローン・家賃支払い（配偶者含む）
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninLoanKbn"))) {
            errorMap.put("HoshoninLoanKbn", "入力されていません");
            result = false;
        }
        // 配偶者
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninHaigushaKbn"))) {
            errorMap.put("HoshoninHaigushaKbn", "入力されていません");
            result = false;
        }
        // 同居人数（本人含む）（生計をマイナスにする別居家族含む）
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninDokyoNinzu"))) {
            errorMap.put("HoshoninDokyoNinzu", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninDokyoNinzu"), false, false)) {
                errorMap.put("HoshoninDokyoNinzu", "半角数字で入力してください");
                result = false;
            }
        }
        // 申込者とのご関係
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninMoshikomiRelateKbn"))) {
            errorMap.put("HoshoninMoshikomiRelateKbn", "入力されていません");
            result = false;
        }
        // お申込者とのご関係 その他
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninMoshikomiRelateKbnSonota")) && "11".equals(jsonlist.get("HoshoninMoshikomiRelateKbn"))) {
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("HoshoninMoshikomiRelateKbnSonota"), false, false)) {
                errorMap.put("HoshoninMoshikomiRelateKbnSonota", "全角文字で入力してください");
                result = false;
            }
        }
        // 就業形態
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninShokugyoKbn"))) {
            errorMap.put("HoshoninShokugyoKbn", "入力されていません");
            result = false;
        }
        // 勤務先・学校情報
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninKinmusaki"))) {
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("HoshoninKinmusaki"), false, false)) {
                errorMap.put("HoshoninKinmusaki", "全角文字で入力してください");
                result = false;
            }
        }
        // 電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninKinmusakiTel1"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninKinmusakiTel1"), false, false)) {
                errorMap.put("HoshoninKinmusakiTel1", "半角数字で入力してください");
                result = false;
            }
        }
        // 電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninKinmusakiTel2"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninKinmusakiTel2"), false, false)) {
                errorMap.put("HoshoninKinmusakiTel2", "半角数字で入力してください");
                result = false;
            }
        }
        // 電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninKinmusakiTel3"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninKinmusakiTel3"), false, false)) {
                errorMap.put("HoshoninKinmusakiTel3", "半角数字で入力してください");
                result = false;
            }
        }
        // 所在地郵便番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninKinmusakiPost"))) {
            errorMap.put("HoshoninKinmusakiPost", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninKinmusakiPost"), false, false)) {
                errorMap.put("HoshoninKinmusakiPost", "半角数字で入力してください");
                result = false;
            }
        }
        // 所在地１
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninKinmusakiAddress1"))) {
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("HoshoninKinmusakiAddress1"), false, false)) {
                errorMap.put("HoshoninKinmusakiAddress1", "全角文字で入力してください");
                result = false;
            }
            if (jsonlist.get("HoshoninKinmusakiAddress1").length() <= 27) {
                errorMap.put("HoshoninKinmusakiAddress1", "27文字以内で入力してください");
                result = false;
            }
        }
        // 所在地２
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninKinmusakiAddress2"))) {
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("HoshoninKinmusakiAddress2"), false, false)) {
                errorMap.put("HoshoninKinmusakiAddress2", "全角文字で入力してください");
                result = false;
            }
        }
        // 勤続年数（学年）
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninKinzokuYear"))) {
            errorMap.put("HoshoninKinzokuYear", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninKinzokuYear"), false, false)) {
                errorMap.put("HoshoninKinzokuYear", "半角数字で入力してください");
                result = false;
            }
        }
        // 勤続年数（学年）月
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninKinzokuMonth"))) {
            errorMap.put("HoshoninKinzokuMonth", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninKinzokuMonth"), false, false)) {
                errorMap.put("HoshoninKinzokuMonth", "半角数字で入力してください");
                result = false;
            }
        }
        // 所属
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninKinmusakiShozoku"))) {
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("HoshoninKinmusakiShozoku"), false, false)) {
                errorMap.put("HoshoninKinmusakiShozoku", "全角文字で入力してください");
                result = false;
            }
        }
        // 従業員数
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninKinmusakiJugyoin"))) {
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("HoshoninKinmusakiJugyoin"), false, false)) {
                errorMap.put("HoshoninKinmusakiJugyoin", "半角数字で入力してください");
                result = false;
            }
        }
        // 受給情報
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninNenkinJukyuKbn"))) {
            errorMap.put("HoshoninNenkinJukyuKbn", "入力されていません");
            result = false;
        }
        // 受給情報 その他
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HoshoninNenkinJukyuKbnSonota")) && "4".equals(jsonlist.get("HoshoninNenkinJukyuKbn"))) {
            errorMap.put("HoshoninNenkinJukyuKbnSonota", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("HoshoninNenkinJukyuKbnSonota"), false, false)) {
                errorMap.put("HoshoninNenkinJukyuKbnSonota", "全角文字で入力してください");
                result = false;
            }
        }
        /**
         * お車の情報
         * 
         */
        // Connected(MCCS)申込
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsMoshikomiKbn"))) {
            errorMap.put("MccsMoshikomiKbn", "入力されていません");
            result = false;
        }
        // シフト
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("ShiftKbn"))) {
            errorMap.put("ShiftKbn", "入力されていません");
            result = false;
        }
        // リモートスターター
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("RemoteStarterKbn"))) {
            errorMap.put("RemoteStarterKbn", "入力されていません");
            result = false;
        }
        // 販売の条件となっている商品・権利・役務の有無
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("HanbaiJokenKbn"))) {
            errorMap.put("HanbaiJokenKbn", "入力されていません");
            result = false;
        }
        // 主な使用目的
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("ShiyoMokutekiKbn"))) {
            errorMap.put("ShiyoMokutekiKbn", "入力されていません");
            result = false;
        }
        // 主な使用目的 その他
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("ShiyoMokutekiKbnSonota")) && "3".equals(jsonlist.get("ShiyoMokutekiKbn"))) {
            errorMap.put("ShiyoMokutekiKbnSonota", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("ShiyoMokutekiKbnSonota"), false, false)) {
                errorMap.put("ShiyoMokutekiKbnSonota", "全角文字で入力してください");
                result = false;
            }
        }
        // 新車・中古車
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("UsedKbn"))) {
            errorMap.put("UsedKbn", "入力されていません");
            result = false;
        }
        // エンジンスタート
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("EngineStartKbn"))) {
            errorMap.put("EngineStartKbn", "入力されていません");
            result = false;
        }
        // 車名
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("ShameiML")) && "0".equals(jsonlist.get("ShiyoMokutekiKbn")) || "1".equals(jsonlist.get("ManualInputCheck")) && "1".equals(jsonlist.get("ShiyoMokutekiKbn"))) {
            errorMap.put("ShameiML", "入力されていません");
            result = false;
        }
        // 型式
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("KatashikiML")) && "0".equals(jsonlist.get("ShiyoMokutekiKbn"))) {
            errorMap.put("KatashikiML", "入力されていません");
            result = false;
        } else {
            // 半角文字のみ
            if (!StringUtil.isHanStr(jsonlist.get("KatashikiML"), false, false)) {
                errorMap.put("KatashikiML", "半角文字で入力してください");
                result = false;
            }
        }
        // 年
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("ShonendYearML")) && "0".equals(jsonlist.get("ShiyoMokutekiKbn"))) {
            errorMap.put("ShonendYearML", "入力されていません");
            result = false;
        }
        // 月
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("ShonendMonthML")) && "0".equals(jsonlist.get("ShiyoMokutekiKbn"))) {
            errorMap.put("ShonendMonthML", "入力されていません");
            result = false;
        }
        // グレード
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("Grade"))) {
            errorMap.put("Grade", "入力されていません");
            result = false;
        } else {
            // 半角文字のみ
            if (!StringUtil.isHanStr(jsonlist.get("Grade"), false, false)) {
                errorMap.put("Grade", "半角文字で入力してください");
                result = false;
            }
        }
        // 車台番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("ShadaiNum"))) {
            errorMap.put("ShadaiNum", "入力されていません");
            result = false;
        } else {
            // 半角文字のみ
            if (!StringUtil.isHanStr(jsonlist.get("ShadaiNum"), false, false)) {
                errorMap.put("ShadaiNum", "半角文字で入力してください");
                result = false;
            }
        }
        // 走行距離
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("SokoKyori"))) {
            errorMap.put("SokoKyori", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("SokoKyori"), false, false)) {
                errorMap.put("SokoKyori", "半角数字で入力してください");
                result = false;
            }
        }
        // 登録番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("TorokuNum"))) {
            errorMap.put("TorokuNum", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("TorokuNum"), false, false)) {
                errorMap.put("TorokuNum", "全角文字で入力してください");
                result = false;
            }
        }
        // 車体色
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("Color"))) {
            errorMap.put("Color", "入力されていません");
            result = false;
        }
        // 排気量
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("Haikiryo"))) {
            errorMap.put("Haikiryo", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("Haikiryo"), false, false)) {
                errorMap.put("Haikiryo", "半角数字で入力してください");
                result = false;
            }
        }
        // 所有者
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("Owner"))) {
            errorMap.put("Owner", "入力されていません");
            result = false;
        }
        // 所有者 その他
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("OwnerSonota")) && "0".equals(jsonlist.get("Owner"))) {
            errorMap.put("OwnerSonota", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("OwnerSonota"), false, false)) {
                errorMap.put("OwnerSonota", "全角文字で入力してください");
                result = false;
            }
        }
        /**
         * 金額・その他の情報
         * 
         */
        // 車両本体価格
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("CarPrice"))) {
            errorMap.put("CarPrice", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("CarPrice"), false, false)) {
                errorMap.put("CarPrice", "半角数字で入力してください");
                result = false;
            }
        }
        // 付属品
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("FuzokuhinPrice"))) {
            errorMap.put("FuzokuhinPrice", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("FuzokuhinPrice"), false, false)) {
                errorMap.put("FuzokuhinPrice", "半角数字で入力してください");
                result = false;
            }
        }
        // 諸費用
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("OtherPrice"))) {
            errorMap.put("OtherPrice", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("OtherPrice"), false, false)) {
                errorMap.put("OtherPrice", "半角数字で入力してください");
                result = false;
            }
        }
        // 車検・整備費用
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("ShakenPrice"))) {
            errorMap.put("ShakenPrice", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("ShakenPrice"), false, false)) {
                errorMap.put("ShakenPrice", "半角数字で入力してください");
                result = false;
            }
        }
        // 頭金 現金（お申込金)
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("AppPrice"))) {
            errorMap.put("AppPrice", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("AppPrice"), false, false)) {
                errorMap.put("AppPrice", "半角数字で入力してください");
                result = false;
            }
        }
        // 頭金 下取車充当額
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("ShitadoriPrice"))) {
            errorMap.put("ShitadoriPrice", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("ShitadoriPrice"), false, false)) {
                errorMap.put("ShitadoriPrice", "半角数字で入力してください");
                result = false;
            }
        }
        // お支払回数
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("ShiharaiKaisu"))) {
            errorMap.put("ShiharaiKaisu", "入力されていません");
            result = false;
        }
        // ボーナス加算月
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("BonusKasanMonthKbn"))) {
            errorMap.put("BonusKasanMonthKbn", "入力されていません");
            result = false;
        }
        // 区分 DEBUG区分不明
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("NoshaDateKbn"))) {
            errorMap.put("NoshaDateKbn", "入力されていません");
            result = false;
        }
        // 販売店からの連絡事項
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("StoreRenrakuJiko"))) {
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("StoreRenrakuJiko"), false, false)) {
                errorMap.put("StoreRenrakuJiko", "全角文字で入力してください");
                result = false;
            }
        }
        // 販売担当者氏名
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("StoreTantoName"))) {
            errorMap.put("StoreTantoName", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("StoreTantoName"), false, false)) {
                errorMap.put("StoreTantoName", "全角文字で入力してください");
                result = false;
            }
        }
        // 電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("StoreTantoTel1"))) {
            errorMap.put("StoreTantoTel1", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("StoreTantoTel1"), false, false)) {
                errorMap.put("StoreTantoTel1", "半角数字で入力してください");
                result = false;
            }
        }
        // 電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("StoreTantoTel2"))) {
            errorMap.put("StoreTantoTel2", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("StoreTantoTel2"), false, false)) {
                errorMap.put("StoreTantoTel2", "半角数字で入力してください");
                result = false;
            }
        }
        // 電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("StoreTantoTel3"))) {
            errorMap.put("StoreTantoTel3", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("StoreTantoTel3"), false, false)) {
                errorMap.put("StoreTantoTel3", "半角数字で入力してください");
                result = false;
            }
        }
        // 送付先名称
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsSofusakiName"))) {
            errorMap.put("MccsSofusakiName", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("MccsSofusakiName"), false, false)) {
                errorMap.put("MccsSofusakiName", "全角文字で入力してください");
                result = false;
            }
        }
        // 送付先郵便番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsSofusakiPost"))) {
            errorMap.put("MccsSofusakiPost", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MccsSofusakiPost"), false, false)) {
                errorMap.put("MccsSofusakiPost", "半角数字で入力してください");
                result = false;
            }
        }
        // 住所１
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsSofusakiAddress1"))) {
            errorMap.put("\"MccsSofusakiAddress1", "入力されていません");
            result = false;
        }
        // 住所２
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsSofusakiAddress2"))) {
            errorMap.put("MccsSofusakiAddress2", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("MccsSofusakiAddress2"), false, false)) {
                errorMap.put("MccsSofusakiAddress2", "全角文字で入力してください");
                result = false;
            }
        }
        // 住所３
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsSofusakiAddress3"))) {
            errorMap.put("MccsSofusakiAddress3", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("MccsSofusakiAddress3"), false, false)) {
                errorMap.put("MccsSofusakiAddress3", "全角文字で入力してください");
                result = false;
            }
        }
        // 発送先電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsSofusakiTel1"))) {
            errorMap.put("MccsSofusakiTel1", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MccsSofusakiTel1"), false, false)) {
                errorMap.put("MccsSofusakiTel1", "半角数字で入力してください");
                result = false;
            }
        }
        // 発送先電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsSofusakiTel2"))) {
            errorMap.put("MccsSofusakiTel2", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MccsSofusakiTel2"), false, false)) {
                errorMap.put("MccsSofusakiTel2", "半角数字で入力してください");
                result = false;
            }
        }
        // 発送先電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsSofusakiTel3"))) {
            errorMap.put("MccsSofusakiTel3", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MccsSofusakiTel3"), false, false)) {
                errorMap.put("MccsSofusakiTel3", "半角数字で入力してください");
                result = false;
            }
        }
        // 受付担当者名
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsUketsukeTantoName"))) {
            errorMap.put("MccsUketsukeTantoName", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("MccsUketsukeTantoName"), false, false)) {
                errorMap.put("MccsUketsukeTantoName", "全角文字で入力してください");
                result = false;
            }
        }
        // 受付担当者電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsUketsukeTantoTel1"))) {
            errorMap.put("MccsUketsukeTantoTel1", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MccsUketsukeTantoTel1"), false, false)) {
                errorMap.put("MccsUketsukeTantoTel1", "半角数字で入力してください");
                result = false;
            }
        }
        // 受付担当者電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsUketsukeTantoTel2"))) {
            errorMap.put("MccsUketsukeTantoTel2", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MccsUketsukeTantoTel2"), false, false)) {
                errorMap.put("MccsUketsukeTantoTel2", "半角数字で入力してください");
                result = false;
            }
        }
        // 受付担当者電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsUketsukeTantoTel3"))) {
            errorMap.put("MccsUketsukeTantoTel3", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MccsUketsukeTantoTel3"), false, false)) {
                errorMap.put("MccsUketsukeTantoTel3", "半角数字で入力してください");
                result = false;
            }
        }
        // 取付担当者名
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsToritsukeTantoName"))) {
            errorMap.put("MccsToritsukeTantoName", "入力されていません");
            result = false;
        } else {
            // 全角文字列か確認
            if (!StringUtil.isZenStr(jsonlist.get("MccsToritsukeTantoName"), false, false)) {
                errorMap.put("MccsToritsukeTantoName", "全角文字で入力してください");
                result = false;
            }
        }
        // 取付担当者電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsToritsukeTantoTel1"))) {
            errorMap.put("MccsToritsukeTantoTel1", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MccsToritsukeTantoTel1"), false, false)) {
                errorMap.put("MccsToritsukeTantoTel1", "半角数字で入力してください");
                result = false;
            }
        }
        // 取付担当者電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsToritsukeTantoTel2"))) {
            errorMap.put("MccsToritsukeTantoTel2", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MccsToritsukeTantoTel2"), false, false)) {
                errorMap.put("MccsToritsukeTantoTel2", "半角数字で入力してください");
                result = false;
            }
        }
        // 取付担当者電話番号
        if (!CommonUtil.chkNotNullEmpty(jsonlist.get("MccsToritsukeTantoTel3"))) {
            errorMap.put("MccsToritsukeTantoTel3", "入力されていません");
            result = false;
        } else {
            // 半角数字
            if (!StringUtil.isNumStr2(jsonlist.get("MccsToritsukeTantoTel3"), false, false)) {
                errorMap.put("MccsToritsukeTantoTel3", "半角数字で入力してください");
                result = false;
            }
        }

        return result;
    }

}