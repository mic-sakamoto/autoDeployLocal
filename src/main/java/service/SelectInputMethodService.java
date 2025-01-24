package service;

import java.sql.Connection;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.SelectInputMethodDao;
import model.SelectInputMethodModel;
import utils.CommonUtil;
import utils.StringUtil;
import utils.log4j.LogUtil;

public class SelectInputMethodService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    SelectInputMethodDao dao = new SelectInputMethodDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(SelectInputMethodModel model) throws Exception {

        return true;

    }

    /**
     * 運転免許証番号または法人番号のチェックデジットを計算
     */
    public boolean checkDigit(SelectInputMethodModel model) throws Exception {

        String moshikomiKbn = model.getInputSelectKbn();
        String licenseNumber = model.getLicenseNumber();

        // if (!CommonUtil.chkMailAddress(licenseNumber)) {
        // return true;
        // }

        // 運転免許証番号のバリデーションチェック
        if (!this.licenseNumberCheck(moshikomiKbn, licenseNumber, model)) {
            return false;
        }

        // チェックデジットが一致するか確認
        if (moshikomiKbn.equals("1")) {
            if (!this.checkDriversLicenseNumber(licenseNumber)) {
                model.setErrormessage("運転免許証番号のチェックデジットが一致しません。");
                model.setSuccessFlg("9");
                return false;
            }
        } else {
            if (!this.checkCorporateNumber(licenseNumber)) {
                model.setErrormessage("法人番号のチェックデジットが一致しません。");
                model.setSuccessFlg("9");
                return false;
            }
        }
        model.setSuccessFlg("1");
        return true;
    }

    /**
     * 運転免許証番号のバリデーションチェック
     */
    public boolean licenseNumberCheck(String inputSelectKbn, String licenseNumber, SelectInputMethodModel model) throws Exception {
        // licenseNumberが数字以外の場合エラー
        if (!licenseNumber.matches("\\d+")) {
            model.setErrormessage("数字のみで入力してください。");
            model.setSuccessFlg("9");
            return false;
        }

        // 半角数値に変換
        licenseNumber = StringUtil.numberZenToHan(licenseNumber);

        // 桁数チェック
        if (inputSelectKbn.equals("1")) {
            // 運転免許証番号
            if (licenseNumber.length() != 12) {
                model.setErrormessage("運転免許証番号は12桁を入力してください。");
                model.setSuccessFlg("9");
                return false;
            }
        } else {
            // 法人番号
            if (licenseNumber.length() != 13) {
                model.setErrormessage("法人番号は13桁を入力してください。");
                model.setSuccessFlg("9");
                return false;
            }
        }
        return true;
    }

    /**
     * 運転免許証番号用チェックデジット
     */
    public boolean checkDriversLicenseNumber(String licenseNumber) throws Exception {
        // 上位10桁を抽出
        int[] digits = new int[10];
        for (int i = 0; i < 10; i++) {
            digits[i] = Character.getNumericValue(licenseNumber.charAt(i));
        }

        // 計算式: (11 - (5a+4b+3c+2d+7e+6f+5g+4h+3i+2j) mod 11) mod 10
        int sum = 5 * digits[0] + 4 * digits[1] + 3 * digits[2] + 2 * digits[3] + 7 * digits[4] + 6 * digits[5] + 5 * digits[6] + 4 * digits[7] + 3 * digits[8] + 2 * digits[9];
        int checkDigit = (11 - (sum % 11)) % 10;

        // チェックデジットが一致するか確認
        int providedCheckDigit = Character.getNumericValue(licenseNumber.charAt(10));
        if (checkDigit != providedCheckDigit) {
            return false;
        }

        return true;
    }

    /**
     * 法人番号用チェックデジット
     */
    public boolean checkCorporateNumber(String licenseNumber) throws Exception {
        // 下位12桁を抽出
        int[] digits = new int[13];
        for (int i = 0; i < 13; i++) {
            digits[i] = Character.getNumericValue(licenseNumber.charAt(i));
        }

        // 奇数桁と偶数桁を分けて計算
        int oddSum = 0, evenSum = 0;
        for (int i = 0; i < 12; i++) {
            if (i % 2 == 0) {
                evenSum += digits[11 - i];
            } else {
                oddSum += digits[11 - i];
            }
        }

        // チェックデジットの計算
        int totalSum = evenSum * 2 + oddSum;
        int remainder = totalSum % 9;
        int checkDigit = 9 - remainder;

        // チェックデジットが一致するか確認
        int providedCheckDigit = digits[12]; // 最上位桁
        if (checkDigit != providedCheckDigit) {
            return false;
        }

        return true;
    }

    /**
     * 「ご契約者様専用ページのログイン手順送信」通知処理
     */
    public boolean sendLoginMethod(SelectInputMethodModel model) throws Exception {
        boolean result = true;

        Connection con = dao.getCon();
        con.setAutoCommit(false);

        try {
            // TODO 実装途中
            // String moshikomiKbn = model.getMoshikomiKbn();
            // String number = model.getLicenseNumber();
            // String sendMethod = model.getSendMethod();
            // String mail = model.getMail();
            // String tel = model.getMobileNum1() + model.getMobileNum2() + model.getMobileNum3();
            //
            // comSvc.sendLoginMethod(moshikomiKbn, number, sendMethod, mail, tel, con);

        } catch (Exception e) {
            con.rollback();
            result = false;
            LogUtil.error(e);
        } finally {
            con.setAutoCommit(true);
            con.close();
        }

        model.setSuccessFlg("1");
        return result;
    }

    /**
     * 顧客が登録済みの場合に他に「審査申込中〜ベリ結果出る前までの申込」がある場合はfalseを返す
     * 
     * @param model
     * @return
     * @throws Exception
     */
    public boolean checkCustomer(SelectInputMethodModel model) throws Exception {

        Connection con = dao.getCon();
        con.setAutoCommit(false);

        try {
            String inputSelectKbn = model.getInputSelectKbn();
            String licenseNumber = model.getLicenseNumber();

            String customerId = "";
            // 個人
            if (inputSelectKbn.equals("1")) {
                // 免許証番号で顧客チェック
                String licenseNumberHah = comSvc.getLicenseNumberHash(licenseNumber, as);
                customerId = comdao.getCustomerIdFromLicenseNumber(licenseNumberHah);
            }
            // 法人
            else {
                // 法人番号で顧客チェック
                customerId = comdao.getCustomerIdFromHojinNumber(licenseNumber);
            }
            if (CommonUtil.chkNotNullEmpty(customerId)) {
                // TODO ★同一顧客の他の「審査申込中〜ベリ結果出る前まで」の申込がある場合は、申込不可とする。
            }
            model.setCustomerId(customerId);
        } catch (Exception e) {
            con.rollback();
        } finally {
            con.setAutoCommit(true);
            con.close();
        }

        return true;
    }

}