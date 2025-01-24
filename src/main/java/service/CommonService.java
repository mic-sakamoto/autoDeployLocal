package service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import model.CommonModel;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import utils.CommonUtil;
import utils.MailUtil;
import utils.StringUtil;
import utils.log4j.LogUtil;

public class CommonService extends ActionSupport {

    public static ActionSupport as;

    CommonDao comdao = new CommonDao();

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();

    /***
     * 現在日時をStringで返す
     * "yyyy-MM-dd hh:mm:ss"
     * 
     * @return
     */
    public String today_date() {

        Date date = new Date();

        String now = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);

        return now;
    }

    /**
     * 現在日時をStringで返す
     * "yyyyMMddHHmmssSSS"
     * 
     * @return
     * @throws Exception
     */
    public String getDate() throws Exception {

        String date = "";

        // 現在日時を取得する
        Calendar c = Calendar.getInstance();

        // フォーマットパターンを指定してmodelにセット
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        date = (sdf.format(c.getTime()));

        return date;
    }

    /**
     * 日付入力チェック1(yyyy-MM-dd HH:mm)
     *
     * @param from
     * @param to
     * @return
     */
    boolean check_date1(String from, String to) {

        try {

            from = from.replace("：", ":");
            from = from.replace("　", " ");
            to = to.replace("：", ":");
            to = to.replace("　", " ");

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            df.format(df.parse(from));
            df.format(df.parse(to));

        } catch (ParseException p) {
            // p.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 日付入力チェック2(yyyy/MM/dd HH:mm)
     *
     * @param from
     * @param to
     * @return
     */
    boolean check_date2(String from, String to) {

        try {

            from = from.replace("：", ":");
            from = from.replace("　", " ");
            to = to.replace("：", ":");
            to = to.replace("　", " ");

            DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");

            df.format(df.parse(from));
            df.format(df.parse(to));

        } catch (ParseException p) {
            // p.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 日付入力チェック(yyyy-MM-dd)
     *
     * @param day
     * @return
     */
    boolean check_date1(String day) {

        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            df.format(df.parse(day));

        } catch (ParseException p) {
            // p.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 日付入力チェック2(yyyy/MM/dd)
     *
     * @param day
     * @return
     */
    boolean check_date2(String day) {

        try {

            if (day.length() != 10) {
                return false;
            }

            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

            df.format(df.parse(day));

        } catch (ParseException p) {
            // p.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * ディレクトリ削除
     *
     * @throws Exception
     */
    public void removeDir(String path) throws Exception {

        File dir = new File(path);
        if (dir.exists() == false) {
            return;
        }

        if (dir.isFile()) {
            dir.delete();

        } else if (dir.isDirectory()) {

            File[] files = dir.listFiles();

            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
            dir.delete();
        }
    }

    /**
     * ディレクトリ以下全削除（サブディレクトリ含む）
     * 
     * @param file
     * @throws Exception
     */
    public void recursiveDeleteFile(final File file) throws Exception {
        // 存在しない場合は処理終了
        if (!file.exists()) {
            return;
        }
        // 対象がディレクトリの場合は再帰処理
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                recursiveDeleteFile(child);
            }
        }
        // 対象がファイルもしくは配下が空のディレクトリの場合は削除する
        file.delete();
    }

    /**
     * 都道府県名取得
     */
    public String prefName(String prefCode) {

        String rName = "";

        ArrayList<HashMap<String, String>> prefList = CommonUtil.getPrefectureList();
        for (HashMap<String, String> map : prefList) {
            if (!CommonUtil.chkNotNullEmpty(rName) && prefCode.equals(StringUtil.dataToString(map.get("code")))) {
                rName = map.get("name");
            }
        }

        return rName;
    }

    /**
     * パスワードのハッシュ値を返す
     * 
     * @param password
     * @param as
     * @return
     */
    public String getPasswordHash(String password, ActionSupport as) {

        String hash = "";

        try {

            // 暗号化の秘密鍵
            String key = as.getText("ALWEB-HASH-KEY");

            // 使用する暗号化アルゴリズム
            String algorithm = "BLOWFISH";

            // 暗号化
            SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, sksSpec);

            byte[] encrypted = cipher.doFinal(password.getBytes());

            encrypted.toString();

            hash = Base64.encodeBase64String(encrypted);

        } catch (Exception e) {
            LogUtil.info(e.getStackTrace());
        }

        return hash;
    }

    /**
     * 免許証番号（下一桁を除く）のハッシュ値を返す
     * 
     * @param licenseNumber
     * @param as
     * @return
     */
    public String getLicenseNumberHash(String licenseNumber, ActionSupport as) {

        String hash = "";

        try {

            licenseNumber = licenseNumber.substring(0, licenseNumber.length() - 1);

            // 暗号化の秘密鍵
            String key = as.getText("ALWEB-HASH-KEY");

            // 使用する暗号化アルゴリズム
            String algorithm = "BLOWFISH";

            // 暗号化
            SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, sksSpec);

            byte[] encrypted = cipher.doFinal(licenseNumber.getBytes());

            encrypted.toString();

            hash = Base64.encodeBase64String(encrypted);

        } catch (Exception e) {
            LogUtil.error(e);
        }

        return hash;
    }

    /**
     * 携帯電話番号の入力チェック(半角数字のみ、10桁または11桁のみ)
     * 
     * @param telNumber
     */
    boolean check_telNumber(String telNumber) {
        String pattern = "^\\d{10,11}$";
        if (telNumber != null && telNumber.matches(pattern)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * メールアドレスの入力チェック
     * 半角英数字記号
     * 文字列に "@" が含まれている
     * 
     * @param mailAddress
     */
    boolean check_mailAddress(String mailAddress) {
        if (mailAddress == null || mailAddress.isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        return mailAddress.matches(emailRegex);
    }

    public String contractStatus(HashMap<String, String> sinsakeiyakujoukyouMap, HashMap<String, Object> keiyakunaiyousyoukaiMap) {

        String status = "0";

        // 状態区分
        String msi_jyoutaicode = StringUtil.dataToString(sinsakeiyakujoukyouMap.get("msi_jyoutaicode"));
        // 審査結果区分
        String msi_hunoukubun = StringUtil.dataToString(sinsakeiyakujoukyouMap.get("msi_hunoukubun"));
        // 結果回答日時
        String ukj_kekkakaitoudatetime = StringUtil.dataToString(sinsakeiyakujoukyouMap.get("ukj_kekkakaitoudatetime"));
        // 受付完了FLG
        String ukj_uketukekanryouflg = StringUtil.dataToString(sinsakeiyakujoukyouMap.get("ukj_uketukekanryouflg"));
        // 審査結果備考
        String msi_hunouriyuubikou = StringUtil.dataToString(sinsakeiyakujoukyouMap.get("msi_hunouriyuubikou"));
        // 決裁日
        String msi_hunouketteidate = StringUtil.dataToString(sinsakeiyakujoukyouMap.get("msi_hunouketteidate"));
        // 審査結果理由
        String msi_hunouriyuukubun = StringUtil.dataToString(sinsakeiyakujoukyouMap.get("msi_hunouriyuukubun"));
        // 契約日
        String mkk_kei_keiyakudate = StringUtil.dataToString(sinsakeiyakujoukyouMap.get("mkk_kei_keiyakudate"));
        // 契約番号
        String mkk_kei_keiyakuno = StringUtil.dataToString(sinsakeiyakujoukyouMap.get("mkk_kei_keiyakuno"));
        // 立替日
        String mkk_kas_kasitukedate = StringUtil.dataToString(sinsakeiyakujoukyouMap.get("mkk_kas_kasitukedate"));

        // 完済区分
        int kzt_jyukekka_kansaikubun = StringUtil.dataToInteger(keiyakunaiyousyoukaiMap.get("kzt_jyukekka_kansaikubun"));

        // 19 契約キャンセル
        // 立替日 ：ブランク
        // 状況区分：70:立替前キャンセル
        // 審査結果理由：90〜92
        // ＜条件２ 立替後＞
        // 立替日 ：あり
        // 契約内容照会APIの完済区分
        // ：160〜179
        if ((!CommonUtil.chkNotNullEmpty(mkk_kas_kasitukedate) && "70".equals(msi_jyoutaicode) && ("90".equals(msi_hunouriyuukubun) || "91".equals(msi_hunouriyuukubun) || "92".equals(msi_hunouriyuukubun)))
                        || (CommonUtil.chkNotNullEmpty(mkk_kas_kasitukedate) && (160 <= kzt_jyukekka_kansaikubun && kzt_jyukekka_kansaikubun <= 179))) {
            status = "19";
        }

        // 18 ベリファイNG
        // 審査結果 ：21（2次否決）
        // 22（2次辞退）
        // 結果回答日時：登録日時
        // 受付完了FLG：完了
        // 決裁日 ：データ連携日
        // 審査結果理由：20（本人ベリNG)
        // 21（保証人ベリNG)
        // 契約日 ：ブランク
        // 契約番号 ：ブランク
        else if (("21".equals(msi_hunoukubun) || "22".equals(msi_hunoukubun)) && CommonUtil.chkNotNullEmpty(ukj_kekkakaitoudatetime) && "1".equals(ukj_uketukekanryouflg) && CommonUtil.chkNotNullEmpty(msi_hunouketteidate) && ("20".equals(msi_hunouriyuukubun) || "21".equals(msi_hunouriyuukubun))
                        && !CommonUtil.chkNotNullEmpty(mkk_kei_keiyakudate) && !CommonUtil.chkNotNullEmpty(mkk_kei_keiyakuno)) {
            status = "18";

        }

        // 17 契約成立
        // 審査結果 ：0（可決）
        // 1（所有権留保可決)
        // 結果回答日時：登録日時
        // 受付完了FLG：完了
        // 決裁日 ：データ連携日
        // 審査結果理由：10（ベリOK）
        // 契約日 ：契約成立日
        // 契約番号 ：あり
        else if (("0".equals(msi_hunoukubun) || "1".equals(msi_hunoukubun)) && CommonUtil.chkNotNullEmpty(ukj_kekkakaitoudatetime) && "1".equals(ukj_uketukekanryouflg) && CommonUtil.chkNotNullEmpty(msi_hunouketteidate) && "10".equals(msi_hunouriyuukubun)
                        && CommonUtil.chkNotNullEmpty(mkk_kei_keiyakudate) && CommonUtil.chkNotNullEmpty(mkk_kei_keiyakuno)) {
            status = "17";

        }

        // 16 本申込受付済
        // 審査結果 ：0（可決）
        // 1（所有権留保可決)
        // 結果回答日時：登録日時
        // 受付完了FLG：完了
        // 決裁日 ：データ連携日
        // 審査結果理由：ブランク や 30（ベリ保留)等
        // 契約日 ：ブランク
        // 契約番号 ：ブランク
        else if (("0".equals(msi_hunoukubun) || "1".equals(msi_hunoukubun)) && CommonUtil.chkNotNullEmpty(ukj_kekkakaitoudatetime) && "1".equals(ukj_uketukekanryouflg) && CommonUtil.chkNotNullEmpty(msi_hunouketteidate)
                        && (!CommonUtil.chkNotNullEmpty(msi_hunouriyuukubun) || "30".equals(msi_hunouriyuukubun)) && !CommonUtil.chkNotNullEmpty(mkk_kei_keiyakudate) && !CommonUtil.chkNotNullEmpty(mkk_kei_keiyakuno)) {
            status = "16";

        }

        // 15 本申込登録中
        // 審査結果 ：0（可決）
        // 1（所有権留保可決)
        // 結果回答日時：登録日時
        // 受付完了FLG：完了
        // 決裁日 ：ブランク
        else if (("0".equals(msi_hunoukubun) || "1".equals(msi_hunoukubun)) && CommonUtil.chkNotNullEmpty(ukj_kekkakaitoudatetime) && "1".equals(ukj_uketukekanryouflg) && !CommonUtil.chkNotNullEmpty(msi_hunouketteidate)) {
            status = "15";

        }

        // 14 本申込前／入力済

        // 13 期限切れ

        // 12 辞退
        else if ("93".equals(msi_hunoukubun)) {
            status = "12";
        }

        // 11 審査申込条件付可決
        // 審査結果 ：5〜8（条件付可決）
        // 結果回答日時：登録日時
        // 受付完了FLG：完了
        else if (("5".equals(msi_hunoukubun) || "6".equals(msi_hunoukubun) || "7".equals(msi_hunoukubun) || "8".equals(msi_hunoukubun)) && CommonUtil.chkNotNullEmpty(ukj_kekkakaitoudatetime) && "1".equals(ukj_uketukekanryouflg)) {
            status = "11";

        }

        // 10 審査申込可決
        // 審査結果 ：0（可決）
        // 1（所有権留保可決)
        // 結果回答日時：登録日時
        // 受付完了FLG：完了
        else if (("0".equals(msi_hunoukubun) || "1".equals(msi_hunoukubun)) && CommonUtil.chkNotNullEmpty(ukj_kekkakaitoudatetime) && "1".equals(ukj_uketukekanryouflg)) {
            status = "10";
        }

        // 9 否決
        // 審査結果 ：9（絶対否決）
        // 結果回答日時：登録日時
        // 受付完了FLG：完了
        else if ("9".equals(msi_hunoukubun) && CommonUtil.chkNotNullEmpty(ukj_kekkakaitoudatetime) && "1".equals(ukj_uketukekanryouflg)) {
            status = "9";
        }

        // 8 免許証番号不備
        // 審査結果 ：92（免許不備）
        // 結果回答日時：登録日時
        // 受付完了FLG：完了
        else if ("92".equals(msi_hunoukubun) && CommonUtil.chkNotNullEmpty(ukj_kekkakaitoudatetime) && "1".equals(ukj_uketukekanryouflg)) {
            status = "8";
        }

        // 7 不備返却
        // 審査結果 ：91（不備返却）
        // 備考 ：返却理由
        // 結果回答日時：登録日時
        // 受付完了FLG：完了
        else if ("91".equals(msi_hunoukubun) && CommonUtil.chkNotNullEmpty(ukj_kekkakaitoudatetime) && "1".equals(ukj_uketukekanryouflg)) {
            status = "7";
        }

        // 6 審査受付済
        // 審査結果 ：ブランク
        // 結果回答日時：ブランク
        // 受付完了FLG：未完了
        else if (!CommonUtil.chkNotNullEmpty(msi_hunoukubun) && !CommonUtil.chkNotNullEmpty(ukj_kekkakaitoudatetime) && !"1".equals(ukj_uketukekanryouflg)) {
            status = "6";
        }

        return status;
    }

    public HashMap<String, Object> getStatusText(String statusKbn, String roleType) {

        HashMap<String, Object> map = new HashMap<String, Object>();

        // 顧客の場合
        if ("3".equals(roleType)) {
            if ("1".equals(statusKbn) || "2".equals(statusKbn) || "3".equals(statusKbn) || "4".equals(statusKbn) || "5".equals(statusKbn) || "6".equals(statusKbn) || "7".equals(statusKbn) || "10".equals(statusKbn) || "11".equals(statusKbn) || "14".equals(statusKbn) || "15".equals(statusKbn)
                            || "16".equals(statusKbn)) {

                map.put("StatusText", "申込手続中");
                map.put("StatusColor", "lb");

            } else if ("8".equals(statusKbn)) {

                map.put("StatusText", "");
                map.put("StatusColor", "");

            } else if ("9".equals(statusKbn) || "12".equals(statusKbn) || "13".equals(statusKbn) || "18".equals(statusKbn)) {

                map.put("StatusText", "申込手続終了");
                map.put("StatusColor", "y");

            } else if ("17".equals(statusKbn)) {

                map.put("StatusText", "契約成立");
                map.put("StatusColor", "g");

            } else if ("19".equals(statusKbn)) {

                map.put("StatusText", "契約キャンセル");
                map.put("StatusColor", "r");

            }
        }
        // スタッフと加盟店
        else {
            if ("1".equals(statusKbn)) {

                map.put("StatusText", "審査申込前");

            } else if ("2".equals(statusKbn)) {

                map.put("StatusText", "審査申込前／未入力");

            } else if ("3".equals(statusKbn)) {

                map.put("StatusText", "審査申込前／入力済");

            } else if ("4".equals(statusKbn)) {

                map.put("StatusText", "審査申込登録中");

            } else if ("5".equals(statusKbn)) {

                map.put("StatusText", "申込エラー");

            } else if ("6".equals(statusKbn)) {

                map.put("StatusText", "審査受付済");

            } else if ("7".equals(statusKbn)) {

                map.put("StatusText", "不備返却");

            } else if ("8".equals(statusKbn)) {

                map.put("StatusText", "免許証番号不備");

            } else if ("9".equals(statusKbn)) {

                map.put("StatusText", "否決");

            } else if ("10".equals(statusKbn)) {

                map.put("StatusText", "審査申込可決");

            } else if ("11".equals(statusKbn)) {

                map.put("StatusText", "審査申込条件付可決");

            } else if ("12".equals(statusKbn)) {

                map.put("StatusText", "辞退");

            } else if ("13".equals(statusKbn)) {

                map.put("StatusText", "期限切れ");

            } else if ("14".equals(statusKbn)) {

                map.put("StatusText", "本申込前／入力済");

            } else if ("15".equals(statusKbn)) {

                map.put("StatusText", "本申込登録中");

            } else if ("16".equals(statusKbn)) {

                map.put("StatusText", "本申込受付済");

            } else if ("17".equals(statusKbn)) {
                map.put("StatusText", "契約成立");

            } else if ("18".equals(statusKbn)) {

                map.put("StatusText", "ベリファイNG");

            } else if ("19".equals(statusKbn)) {

                map.put("StatusText", "契約キャンセル");

            }

            // ステータスカラー
            if ("1".equals(statusKbn) || "2".equals(statusKbn) || "3".equals(statusKbn) || "4".equals(statusKbn) || "6".equals(statusKbn) || "14".equals(statusKbn) || "15".equals(statusKbn) || "16".equals(statusKbn)) {

                map.put("StatusColor", "lb");

            } else if ("".equals(statusKbn)) {
                map.put("StatusColor", "b");

            } else if ("10".equals(statusKbn) || "17".equals(statusKbn)) {
                map.put("StatusColor", "g");

            } else if ("7".equals(statusKbn) || "8".equals(statusKbn) || "11".equals(statusKbn) || "12".equals(statusKbn) || "19".equals(statusKbn)) {
                map.put("StatusColor", "y");

            } else if ("5".equals(statusKbn) || "9".equals(statusKbn) || "13".equals(statusKbn) || "18".equals(statusKbn)) {
                map.put("StatusColor", "r");

            }
        }

        return map;
    }

    /**
     * Jasper出力共通機能
     */
    public JasperPrint createJasper(String jasperReportPath, List<Map<String, Object>> pdfList, HashMap<String, Object> params, JasperPrint pdf) throws Exception {

        if (pdf.getPages().size() == 0) {
            pdf = JasperFillManager.fillReport(jasperReportPath, params, new JRBeanCollectionDataSource(pdfList));
        } else {
            JasperPrint addPdf = JasperFillManager.fillReport(jasperReportPath, params, new JRBeanCollectionDataSource(pdfList));
            for (JRPrintPage page : addPdf.getPages()) {
                pdf.addPage(page);
            }
        }

        return pdf;
    }

    public boolean printJasper(JasperPrint pdf, CommonModel model, String fileName) {
        boolean result = true;
        try {
            byte[] b = JasperExportManager.exportReportToPdf(pdf);
            model.setInputStream(new ByteArrayInputStream(b));

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            model.setFileName(URLEncoder.encode(fileName + "_" + sdf.format(c.getTime()), "UTF-8") + ".pdf");
            model.setContentLength(b.length);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;

    }

    /**
     * PDF出力共通機能
     */
    public boolean printPdf(String documentPath, CommonModel model, String fileName) {
        boolean result = true;

        try {

            File file = new File(documentPath);

            if (!file.exists()) {
                throw new FileNotFoundException("指定されたファイルが見つかりません: " + documentPath);
            }

            byte[] fileBytes;
            try (FileInputStream fis = new FileInputStream(file); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                fileBytes = baos.toByteArray();
            }

            model.setInputStream(new ByteArrayInputStream(fileBytes));

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            model.setFileName(fileName + sdf.format(calendar.getTime()) + ".pdf");

            model.setContentLength(fileBytes.length);

        } catch (FileNotFoundException e) {
            result = false;
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    /**
     * MCCS車両マスタからメーカーを取得する
     * 
     * @return
     */
    public ArrayList<String> getMccsMaker() {
        ArrayList<String> resultList = new ArrayList<String>();

        try {
            ArrayList<HashMap<String, Object>> resultArr = comdao.getMccsMaker();
            if (resultArr != null) {
                for (HashMap<String, Object> map : resultArr) {
                    resultList.add(map.get("Maker").toString());
                }
            }
        } catch (Exception e) {
            LogUtil.error(e);
        }

        return resultList;
    }

    public ArrayList<String> getMccsShamei(String maker) {
        ArrayList<String> resultList = new ArrayList<String>();

        try {
            ArrayList<HashMap<String, Object>> resultArr = comdao.getMccsShamei(maker);
            if (resultArr != null) {
                for (HashMap<String, Object> map : resultArr) {
                    resultList.add(map.get("Model").toString());
                }
            }
        } catch (Exception e) {
            LogUtil.error(e);
        }

        return resultList;
    }

    public ArrayList<String> getMccsKatashiki(String shamei) {
        ArrayList<String> resultList = new ArrayList<String>();

        try {
            ArrayList<HashMap<String, Object>> resultArr = comdao.getMccsKatashiki(shamei);
            if (resultArr != null) {
                for (HashMap<String, Object> map : resultArr) {
                    resultList.add(map.get("VehicleModel").toString());
                }
            }
        } catch (Exception e) {
            LogUtil.error(e);
        }

        return resultList;
    }

    /**
     * 
     * @param apiData
     * @return
     */
    public HashMap<String, Object> makeDataFromApi(HashMap<String, String> apiData) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        LogUtil.info(apiData.toString());
        try {
            // お申込年月日
            setSlashDate(map, "MoshikomiYear", "MoshikomiMonth", "MoshikomiDay", apiData.get("wun_mousikomidate"));
            // OBIC連携 受付開始日時
            setValue(map, "ObicWebAppDate", apiData.get("ukj_uketukekaisidatetime"));
            // 売買契約年月日
            setSlashDate(map, "BaibaikeiyakuDateYear", "BaibaikeiyakuDateMonth", "BaibaikeiyakuDateDay", apiData.get("wun_baibaikeiyakunengappi"));
            // 申込区分
            setValue(map, "MoshikomiKbn", apiData.get("wun_kojinhoujinkubun"));
            // 申込者名
            setName(map, "MoshikomiNameSei", "MoshikomiNameMei", apiData.get("wun_namekanji"));
            // 申込者名フリガナ
            setNameKana(map, "MoshikomiNameSeiKana", "MoshikomiNameMeiKana", apiData.get("wun_namekana"));
            // 申込者性別
            setValue(map, "MoshikomiSeibetsuKbn", apiData.get("wun_honnin_sex"));
            // 申込者年齢
            setValue(map, "MoshikomiAge", apiData.get("wun_nenrei"));
            // 申込者生年月日
            setBirthDate(map, "MoshikomiBirthDateYear", "MoshikomiBirthDateMonth", "MoshikomiBirthDateDay", apiData.get("wun_birthday"));
            // 携帯電話番号
            setValue(map, "MoshikomiMobile1", apiData.get("wun_home_telno1_1"));
            setValue(map, "MoshikomiMobile2", apiData.get("wun_home_telno1_2"));
            setValue(map, "MoshikomiMobile3", apiData.get("wun_home_telno1_3"));
            // OBIC連携 電話区分
            setValue(map, "MoshikomiMobileKbn", apiData.get("wun_home_telno1_kubun"));
            // 自宅電話暗号
            setValue(map, "MoshikomiTel1", apiData.get("wun_home_telno2_1"));
            setValue(map, "MoshikomiTel2", apiData.get("wun_home_telno2_2"));
            setValue(map, "MoshikomiTel3", apiData.get("wun_home_telno2_3"));
            // 申込者郵便番号
            setPostNumber(map, "MoshikomiPost", apiData.get("wun_home_postno"), apiData.get("wun_home_postedaban"));
            // 申込者住所１
            setValue(map, "MoshikomiAddress1", apiData.get("wun_home_addkanji1"));
            // 申込者住所２
            setValue(map, "MoshikomiAddress2", apiData.get("wun_home_addkanji2"));
            // 申込者住所フリガナ
            setValue(map, "MoshikomiAddressKana", apiData.get("wun_home_addkana"));
            // 申込者配偶者区分
            setValue(map, "MoshikomiHaigushaKbn", apiData.get("wun_haiguushaumu"));
            // 申込者住居区分
            setValue(map, "JukyoKbn", apiData.get("wun_jyuukyokubun"));
            // OBIC連携 住宅所有区分
            setValue(map, "JukyoShoyuKbn", apiData.get("wun_tyousa_jyuutakusyoyuukubun"));
            // 申込者ローン区分
            setValue(map, "MoshikomiLoanKbn", apiData.get("wun_tyousa_yatinumukubun"));
            // 申込者同居人数
            setValue(map, "MoshikomiDokyoNinzu", apiData.get("wun_tyousa_setaininzuu"));
            // 申込者居住年数
            setValue(map, "MoshikomiKyojuYear", apiData.get("wun_kyojyuunennsuu"));
            setValue(map, "MoshikomiKyojuMonth", apiData.get("wun_yobi2"));
            // 申込者税込年収
            setValue(map, "MoshikomiNenshu", apiData.get("wun_honnin_syuunyuu_nensyuu"));
            // 申込者ご職業
            setValue(map, "MoshikomiShokugyoKbn", apiData.get("wun_office_syokusyukubun"));
            // 申込者勤務先・学校名称
            setValue(map, "wun_office_namekanji", apiData.get("wun_office_namekanji"));
            // 勤務先電話番号
            setValue(map, "MoshikomiKinmusakiTel1", apiData.get("wun_office_telno1"));
            setValue(map, "MoshikomiKinmusakiTel2", apiData.get("wun_office_telno2"));
            setValue(map, "MoshikomiKinmusakiTel3", apiData.get("wun_office_telno3"));
            // OBIC連携 勤務先電話区分
            setValue(map, "MoshikomiKinmusakiTelKbn", apiData.get("wun_office_telno1_kubun"));
            // 所在地郵便番号
            setPostNumber(map, "MoshikomiKinmusakiPost", apiData.get("wun_office_postno"), apiData.get("wun_office_postedaban"));
            // 所在地住所１
            setValue(map, "MoshikomiKinmusakiAddress1", apiData.get("wun_office_addkanji1"));
            // 所在地住所２
            setValue(map, "MoshikomiKinmusakiAddress2", apiData.get("wun_office_addkanji2"));
            // 勤続年数
            setValue(map, "MoshikomiKinzokuYear", apiData.get("wun_office_kinzokunensuu"));
            setValue(map, "MoshikomiKinzokuMonth", apiData.get("wun_office_kinzokutukisuu"));
            // 役職
            setValue(map, "MoshikomiKinmusakiYakushokuKbn", apiData.get("wun_office_yakusyokukubun"));
            // 所属
            setValue(map, "MoshikomiKinmusakiShozoku", apiData.get("wun_office_busyonamekanji"));
            // 従業員数
            setValue(map, "MoshikomiKinmusakiJugyoin", apiData.get("wun_office_jyuugyouin"));
            // 業種区分
            setValue(map, "MoshikomiGyoshuKbn", apiData.get("wun_office_gyousyucode"));
            // 預金残高
            setValue(map, "MoshikomiYokinZandaka", apiData.get("wun_honnin_syuunyuu_yotyokin"));
            // 世帯主名前
            setValue(map, "MoshikomiSetainushiNameSei", apiData.get("wun_setainusisimei_sei"));
            setValue(map, "MoshikomiSetainushiNameMei", apiData.get("wun_setainusisimei_mei"));
            // 世帯主 お申込者との関係
            setValue(map, "MoshikomiSetainushiRelateKbn", apiData.get("wun_omousikomisyatonokankei"));
            // 世帯主居住区分
            setValue(map, "MoshikomiKyojuKbn", apiData.get("wun_tyousa_doukyokubun"));
            // 世帯主税込み年収
            setValue(map, "MoshikomiSetainushiNenshu", apiData.get("wun_haiguusya_syuunyuu_nensyuu"));
            // 世帯主月額クレジット債務
            setValue(map, "MoshikomiSetainushiCreditSaimu", apiData.get("wun_haiguusya_saimu_tukidukisiharaigaku"));
            // 申込者受給情報
            setValue(map, "MoshikomiNenkinJukyuKbn", apiData.get("wun_jyukyuusyurui_kubun"));
            // 申込者受給情報その他
            setValue(map, "MoshikomiNenkinJukyuKbnSonota", apiData.get("wun_jyukyuusyurui_sonota"));
            // 申込者年間受給額
            setValue(map, "MoshikomiNenkinJukyuPrice", apiData.get("wun_nenkanjyukyuugaku"));

            // 連帯保証人有無
            setValue(map, "HoshoninKbn", apiData.get("wun_hosyounin_umuflg"));
            // 保証人名前
            setName(map, "HoshoninNameSei", "HoshoninNameMei", apiData.get("wun_hosyounin_namekanji"));
            // 保証人フリガナ
            setNameKana(map, "HoshoninNameSeiKana", "HoshoninNameMeiKana", apiData.get("wun_hosyounin_namekana"));
            // 保証人性別
            setValue(map, "HoshoninSeibetsuKbn", apiData.get("wun_hosyounin_sex"));
            // 保証人年齢
            setValue(map, "HoshoninAge", apiData.get("wun_hosyounin_nenrei"));
            // 保証人生年月日
            setBirthDate(map, "HoshoninBirthDateYear", "HoshoninBirthDateMonth", "HoshoninBirthDateDay", apiData.get("wun_hosyounin_birthday"));
            // OBIC連携 保証人生年月日元号
            setValue(map, "HoshoninBirthDateGengo", apiData.get("wun_hosyounin_birthdaygengou"));
            // 保証人電話番号
            setValue(map, "HoshoninMobile1", apiData.get("wun_hosyounin_telno1_1"));
            setValue(map, "HoshoninMobile2", apiData.get("wun_hosyounin_telno1_2"));
            setValue(map, "HoshoninMobile3", apiData.get("wun_hosyounin_telno1_3"));
            // OBIC連携 勤務先電話区分
            setValue(map, "HoshoninMobileKbn", apiData.get("wun_hosyounin_telno1_kubun"));
            // 保証人自宅電話番号
            setValue(map, "HoshoninTel1", apiData.get("wun_hosyounin_telno2_1"));
            setValue(map, "HoshoninTel2", apiData.get("wun_hosyounin_telno2_2"));
            setValue(map, "HoshoninTel3", apiData.get("wun_hosyounin_telno2_3"));
            // OBIC連携 勤務先電話区分
            setValue(map, "HoshoninTelKbn", apiData.get("wun_hosyounin_telno2_kubun"));
            // 保証人郵便番号
            setPostNumber(map, "HoshoninPost", apiData.get("wun_hosyounin_postno"), apiData.get("wun_hosyounin_postedaban"));
            // 保証人住所１
            setValue(map, "HoshoninAddress1", apiData.get("wun_hosyounin_addkanji1"));
            // 保証人住所２
            setValue(map, "HoshoninAddress2", apiData.get("wun_hosyounin_addkanji2"));
            // 保証人住所フリガナ
            setValue(map, "HoshoninAddressKana", apiData.get("wun_hosyounin_addkana"));
            // 保証人税込み年収
            setValue(map, "HoshoninNenshu", apiData.get("wun_hosyounin_syuunyuu_nensyuu"));
            // 保証人居住年数
            setValue(map, "HoshoninKyojuYear", apiData.get("wun_hosyounin_kyojyuunennsuu"));
            setValue(map, "HoshoninKyojuMonth", apiData.get("wun_yobi3"));
            // 保証人住居区分
            setValue(map, "HoshoninJukyoKbn", apiData.get("wun_hosyounin_jyuukyokubun"));
            // 保証人ローン区分
            setValue(map, "HoshoninLoanKbn", apiData.get("wun_hosyounin_yatinjyutakuloanumu"));
            // 保証人配偶者区分
            setValue(map, "HoshoninHaigushaKbn", apiData.get("wun_hosyounin_haiguushaumu"));
            // 保証人同居人数
            setValue(map, "HoshoninDokyoNinzu", apiData.get("wun_hosyounin_setaininzuu"));
            // 保証人 お申込者との関係
            setValue(map, "HoshoninMoshikomiRelateKbn", apiData.get("wun_hosyounin_zokugar"));
            // 保証人 お申込者との関係その他
            setValue(map, "HoshoninMoshikomiRelateKbnSonota", apiData.get("wun_hosyounin_tudukigara_sonota"));
            // 保証人就業形態
            setValue(map, "HoshoninShokugyoKbn", apiData.get("wun_hosyounin_office_syokusyukubun"));
            // OBIC連携 職種区分
            setValue(map, "HoshoninKinmusakiKbn", apiData.get("wun_hosyounin_syokusyukubun"));
            // 保証人勤務先・学校
            setValue(map, "HoshoninKinmusaki", apiData.get("wun_hosyounin_office_namekanji"));
            // 保証人勤務先電話番号
            setValue(map, "HoshoninKinmusakiTel1", apiData.get("wun_hosyounin_office_telno1"));
            setValue(map, "HoshoninKinmusakiTel2", apiData.get("wun_hosyounin_office_telno2"));
            setValue(map, "HoshoninKinmusakiTel3", apiData.get("wun_hosyounin_office_telno3"));
            // OBIC連携 勤務先電話区分
            setValue(map, "HoshoninKinmusakiTelKbn", apiData.get("wun_hosyounin_office_telno1_kubun"));
            // 所在地郵便番号
            setPostNumber(map, "HoshoninKinmusakiPost", apiData.get("wun_hosyounin_office_postno"), apiData.get("wun_hosyounin_office_postedaban"));
            // 所在地住所１
            setValue(map, "HoshoninKinmusakiAddress1", apiData.get("wun_hosyounin_office_addkanji1"));
            // 所在地住所２
            setValue(map, "HoshoninKinmusakiAddress2", apiData.get("wun_hosyounin_office_addkanji2"));
            // 勤続年数
            setValue(map, "HoshoninKinzokuYear", apiData.get("wun_hosyounin_office_kinzokunensuu"));
            setValue(map, "HoshoninKinzokuMonth", apiData.get("wun_hosyounin_office_kinzokutukisuu"));
            // 役職
            setValue(map, "HoshoninKinmusakiYakushokuKbn", apiData.get("wun_hosyounin_office_yakusyokukubun"));
            // 所属
            setValue(map, "HoshoninKinmusakiShozoku", apiData.get("wun_hosyounin_office_busyonamekanji"));
            // 従業員数
            setValue(map, "HoshoninKinmusakiJugyoin", apiData.get("wun_hosyounin_office_jyuugyouin"));
            // 業種区分
            setValue(map, "HoshoninGyoshuKbn", apiData.get("wun_hosyounin_office_gyousyucode"));
            // 年金受給区分
            setValue(map, "HoshoninNenkinJukyuKbn", apiData.get("wun_hosyounin_jyukyuusyurui_kubun"));
            // 年金受給その他
            setValue(map, "HoshoninNenkinJukyuKbnSonota", apiData.get("wun_hosyounin_jyukyuusyurui_sonota"));

            // MCCS申込有無
            setValue(map, "MccsMoshikomiKbn", apiData.get("wun_mccstoritukeumukubun"));
            // シフト
            setValue(map, "ShiftKbn", apiData.get("wun_shiftkubun"));
            // リモートスターター
            setValue(map, "RemoteStarterKbn", apiData.get("wun_remotestarterkubun"));
            // 販売条件
            setValue(map, "HanbaiJokenKbn", apiData.get("wun_hanbaijyoukenumu"));
            // TODO DEBUG内容不明
            setValue(map, "HanbaiJokenKbnSentaku", apiData.get("wun_hanbaijyoukenari_sentaku"));
            // 使用目的
            setValue(map, "ShiyoMokutekiKbn", apiData.get("wun_omonasiyoumokutekikubun"));
            // 使用目的その他
            setValue(map, "ShiyoMokutekiKbnSonota", apiData.get("wun_omonasiyoumokuteki_sonota"));
            // 新車・中古車
            setValue(map, "UsedKbn", apiData.get("wun_kurumajyoutaikubun"));
            // エンジンスタート
            setValue(map, "EngineStartKbn", apiData.get("wun_starterhousikikubun"));
            // 車両情報 メーカー
            setValue(map, "Maker", apiData.get("wun_maker"));
            setValue(map, "MakerML", apiData.get("wun_maker"));
            // 車両情報 車名
            setValue(map, "Shamei", apiData.get("wun_syamei"));
            setValue(map, "ShameiML", apiData.get("wun_syamei"));
            // 車両情報 型式
            setValue(map, "Katashiki", apiData.get("wun_katasiki"));
            setValue(map, "KatashikiML", apiData.get("wun_katasiki"));
            // 車両情報 初年度
            set6ketaDate(map, "ShonendoYear", "ShonendoMonth", apiData.get("wun_syonenndotourokunengetu"));
            set6ketaDate(map, "ShonendoYearML", "ShonendoMonthML", apiData.get("wun_syonenndotourokunengetu"));
            // 車両情報マニュアル入力チェック
            setValue(map, "ManualInputCheck", apiData.get("wun_katasiki"));
            // グレード
            setValue(map, "Grade", apiData.get("wun_grade"));
            // 車台番号
            setValue(map, "ShadaiNum", apiData.get("wun_syadaibangou"));
            // 走行距離
            setValue(map, "SokoKyori", apiData.get("wun_soukoukyori"));
            // 登録番号
            setValue(map, "TorokuNum", apiData.get("wun_tourokubangou"));
            // 車体色
            setValue(map, "Color", apiData.get("wun_syataisyoku"));
            // 排気量
            setValue(map, "Haikiryo", apiData.get("wun_haikiryou"));
            // 所有者
            String owner = StringUtil.dataToString(apiData.get("wun_syoyuusya"));
            if (CommonUtil.chkNotNullEmpty(owner) && !owner.equals("本人")) {
                setValue(map, "Owner", "その他");
                setValue(map, "OwnerSonota", apiData.get("wun_syoyuusya"));
            } else {
                setValue(map, "Owner", apiData.get("wun_syoyuusya"));
            }
            // 本体価格
            setValue(map, "CarPrice", apiData.get("wun_kingaku_syaryouhontaikakaku"));
            // MCCS取付
            setValue(map, "MccsAttachPrice", apiData.get("wun_kingaku_mccstorituke"));
            // 付属品
            setValue(map, "FuzokuhinPrice", apiData.get("wun_kingaku_huzokuhin"));
            // 諸費用
            setValue(map, "OtherPrice", apiData.get("wun_kingaku_syohiyou"));
            // 車検・整備費用
            setValue(map, "ShakenPrice", apiData.get("wun_kingaku_syakenseibihiyou"));
            // 現金合計額
            setValue(map, "TotalPrice", apiData.get("wun_genkinkakakugoukei"));
            // 頭金 現金
            setValue(map, "AppPrice", apiData.get("wun_kingaku_atamakin"));
            // 頭金 下取り
            setValue(map, "ShitadoriPrice", apiData.get("wun_kingaku_sitadorijyuutougaku"));
            // OBIC連携 頭金
            setValue(map, "AtamaPrice", apiData.get("wun_atamakin"));
            // 残金
            setValue(map, "RemainPrice", apiData.get("wun_creditgankin"));
            // お支払期間開始
            set6ketaDate(map, "ShiharaiStartYear", "ShiharaiStartMonth", apiData.get("wun_henkikan_syokaihensainengetu"));
            // OBIC連携 初回返済年月
            setValue(map, "ShokaiBonusHensaiYYYYMM", apiData.get("wun_henkikan_syokaihensainengetu_zougaku"));
            // OBIC連携 ボーナス2回目返済年月
            setValue(map, "BonusHensaiYYYYMM", apiData.get("wun_henkikan_bonus2kaimeinengetu"));
            // お支払期間終了
            set6ketaDate(map, "ShiharaiEndYear", "ShiharaiEndMonth", apiData.get("wun_henkikan_saisyuuhensainengetu"));
            // OBIC連携 初回返済日
            setValue(map, "ShokaiBonusHensaiDD", apiData.get("wun_henkikan_syokaihensaidate"));
            // OBIC連携 初回返済日（ボーナス増額）
            setValue(map, "BonusHensaiDD", apiData.get("wun_henkikan_syokaihensaidate_zougaku"));
            // OBIC連携 最終返済日
            setValue(map, "SaishuHensai", apiData.get("wun_henkikan_saisyuuhensaidate"));
            // 支払い回数
            setValue(map, "ShiharaiKaisu", apiData.get("wun_henkikan_hensaiyoteikaisuu"));
            // ボーナス加算月区分
            setValue(map, "BonusKasanMonthKbn", apiData.get("wun_henkikan_hensaiyoteikaisuu"));
            // OBIC連携 ボーナス加算月
            setValue(map, "BonusKasanMonth1", apiData.get("wun_henkikan_bonusmonth1"));
            setValue(map, "BonusKasanMonth2", apiData.get("wun_henkikan_bonusmonth2"));
            // 加算支払金 回数
            setValue(map, "BonusKasanKaisu", apiData.get("wun_henkikan_hensaiyoteikaisuu_zougaku"));
            // 加算支払金 金額
            setValue(map, "BonusKasanPrice", apiData.get("wun_henkin_bonuszougaku"));
            // OBIC連携 料率パターン
            setValue(map, "RatePattern", apiData.get("wun_ryoritupattern"));
            // 第１回分割支払金 金額
            setValue(map, "BunkatsuShiharai1", apiData.get("wun_henkin_syokaihensaigaku"));
            // 第２回分割支払金 金額
            setValue(map, "BunkatsuShiharai2", apiData.get("wun_henkin_tukizukihensaigaku"));
            // 第２回分割支払金 回数
            setValue(map, "BunkatsuKaisu", apiData.get("wun_henkikan_hensaiyoteikaisuu"));
            // ④分割払手数料
            setValue(map, "BunkatsuTesuryo", apiData.get("wun_tesuurougoukei"));
            // ⑤分割支払金合計③＋④
            setValue(map, "BunkatsuShiharaiTotal", apiData.get("wun_kingaku_bunkatusiharaikingoukei"));
            // ⑥お支払総額②＋⑤
            setValue(map, "TotalShiharai", apiData.get("wun_kingaku_osiharaisougaku"));
            // OBIC連携 分割払元金
            setValue(map, "BunkatsuGankin", apiData.get("wun_bunkatugankin"));
            // OBIC連携 据置元金
            setValue(map, "SueokiGankin", apiData.get("wun_sueokigankin"));
            // OBIC連携 手数料_アドオン手数料総額
            setValue(map, "TesuryoAddon", apiData.get("wun_tes_adoontesuuryousougaku"));
            // OBIC連携 手数料_据置手数料
            setValue(map, "TesuryoSueokii", apiData.get("wun_tes_sueokitesuuryou"));
            // OBIC連携 手数料_スキップ手数料（顧客負担）
            setValue(map, "TesuryoSkip", apiData.get("wun_tes_skiptesuuryou_kokyaku"));
            // OBIC連携 最終返済額
            setValue(map, "SaishuHensaigaku", apiData.get("wun_henkin_saisyuuhensaigaku"));
            // OBIC連携 スキップ回数
            setValue(map, "SkipKaisu", apiData.get("wun_henkikan_skipkaisuu"));
            // OBIC連携 支払方法
            setValue(map, "ShiharaiHohoKbn", apiData.get("wun_hensaihouhoukubun"));
            // 所有権留保費用
            setValue(map, "ShoyukenRyuhoPrice", apiData.get("wun_kingaku_syoyuukenryuuhohiyou"));
            // 納車日区分
            setValue(map, "NoshaDateKbn", apiData.get("wun_nousyanengappi_kubun"));
            // 納車日
            setSlashDate(map, "NoshaDateYear", "NoshaDateMonth", "NoshaDateDay", apiData.get("wun_nousyanengappi_nyuuryoku"));
            // 加盟店連絡事項
            setValue(map, "StoreRenrakuJiko", apiData.get("wun_renrakujikou"));
            // 加盟店番号
            setValue(map, "StoreId", apiData.get("wun_kameitenkey1") + apiData.get("wun_kameitenkey2"));
            // 条件コード
            setValue(map, "JokenCode", apiData.get("wun_jyoukencode"));
            // 印刷項目 確定顧客No
            setValue(map, "KakuteiKokyakuNo", apiData.get("ukj_karihosyouninkokyakuno"));
            // 印刷項目 確定顧客枝番
            setValue(map, "KakuteiKokyakuEda", apiData.get("ukj_kakuteihosyouninkokyakuno"));
            // 販売担当者氏名
            setValue(map, "StoreTantoName", apiData.get("wun_hanbaitantousyamei"));
            // 販売担当者電話番号
            setValue(map, "StoreTantoTel1", apiData.get("wun_hanbaitantousya_telno1"));
            setValue(map, "StoreTantoTel2", apiData.get("wun_hanbaitantousya_telno2"));
            setValue(map, "StoreTantoTel3", apiData.get("wun_hanbaitantousya_telno3"));
            // 申込方法
            setValue(map, "DaikoMoshikomiKbn", apiData.get("wun_daikounyuuryokujyouhou"));
            // 送付先名称
            setValue(map, "MccsSofusakiName", apiData.get("wun_mccshassousaki_name"));
            // 送付先郵便番号
            setPostNumber(map, "MccsSofusakiPost", apiData.get("wun_mccshassousaki_postno"), apiData.get("wun_mccshassousaki_postno_edaban"));
            // 送付先住所
            setValue(map, "MccsSofusakiAddress1", apiData.get("wun_mccshassousaki_addkanji1"));
            setValue(map, "MccsSofusakiAddress2", apiData.get("wun_mccshassousaki_addkanji2"));
            setValue(map, "MccsSofusakiAddress3", apiData.get("wun_mccshassousaki_addkanji3"));
            // 送付先電話番号
            setValue(map, "MccsSofusakiTel1", apiData.get("wun_mccshassousaki_telno1"));
            setValue(map, "MccsSofusakiTel2", apiData.get("wun_mccshassousaki_telno2"));
            setValue(map, "MccsSofusakiTel3", apiData.get("wun_mccshassousaki_telno3"));
            // MCCS受付先名称
            setValue(map, "MccsUketsukeTantoName", apiData.get("wun_mccshassousaki_uketuketantousyamei"));
            // MCCS受付電話番号
            setValue(map, "MccsUketsukeTantoTel1", apiData.get("wun_mccshassousaki_uketuketantousyatelno1"));
            setValue(map, "MccsUketsukeTantoTel2", apiData.get("wun_mccshassousaki_uketuketantousyatelno2"));
            setValue(map, "MccsUketsukeTantoTel3", apiData.get("wun_mccshassousaki_uketuketantousyatelno3"));
            // MCCS取付先名称
            setValue(map, "MccsToritsukeTantoName", apiData.get("wun_mccshassousaki_torituketantousyamei"));
            // MCCS取付電話番号
            setValue(map, "MccsToritsukeTantoTel1", apiData.get("wun_mccshassousaki_torituketantousyatelno1"));
            setValue(map, "MccsToritsukeTantoTel2", apiData.get("wun_mccshassousaki_torituketantousyatelno2"));
            setValue(map, "MccsToritsukeTantoTel3", apiData.get("wun_mccshassousaki_torituketantousyatelno3"));

            // 法人番号
            setValue(map, "hojinNumber", apiData.get("wun_yobi4"));
            // 運転免許証番号
            setValue(map, "licenseNumber", apiData.get("wun_koutekisiryou_bangou"));
            // OBIC連携 公的資料区分
            setValue(map, "kotekiSiryoKbn", apiData.get("wun_koutekisiryou_siryoukubun"));
            // OBIC連携 公的資料確認日
            setValue(map, "kotekiSiryoKakuninbi", apiData.get("wun_koutekisiryou_kakunindate"));

            // 審査結果区分
            setValue(map, "shinsaKekkaKbn", apiData.get("msi_hunoukubun"));

        } catch (Exception e) {
            LogUtil.error(e);
        }

        return map;

    }

    private void setValue(HashMap<String, Object> map, String key, Object value) {
        try {
            map.put(key, CommonUtil.strNulltoBlank(value));
        } catch (Exception e) {

        }
    }

    private void setSlashDate(HashMap<String, Object> map, String yearKey, String monthKey, String dayKey, Object date) {
        try {
            String dateStr = CommonUtil.strNulltoBlank(date);
            if (!dateStr.equals("")) {
                String separate = "";
                if (dateStr.indexOf("　") > 0) {
                    separate = "　";
                } else if (dateStr.indexOf("/") > 0) {
                    separate = "/";
                } else if (dateStr.indexOf("-") > 0) {
                    separate = "-";
                }
                String[] dateArr = dateStr.split(separate);
                if (dateArr.length == 3) {
                    map.put(yearKey, dateArr[0]);
                    map.put(monthKey, dateArr[1].replaceFirst("^0+", ""));
                    if (dateArr[2].length() > 2) {
                        dateArr[2] = dateArr[2].substring(0, 2);
                    }
                    map.put(dayKey, dateArr[2].replaceFirst("^0+", ""));
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * YYMMDDのデータをYYYYMMDDに変換してMAPにPUTする
     * 
     * @param map
     * @param yearKey
     * @param monthKey
     * @param dayKey
     * @param date
     */
    private void setBirthDate(HashMap<String, Object> map, String yearKey, String monthKey, String dayKey, Object date) {
        try {

            if (date != null) {
                // YYMMDD→YYYYMMDD
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyMMdd");
                LocalDate datePre = LocalDate.parse(CommonUtil.strNulltoBlank(date), inputFormatter);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String datePost = datePre.format(outputFormatter);
                map.put(yearKey, datePost.substring(0, 4));
                map.put(monthKey, datePost.substring(4, 6).replaceFirst("^0+", ""));
                map.put(dayKey, datePost.substring(6, 8).replaceFirst("^0+", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void set6ketaDate(HashMap<String, Object> map, String yearKey, String monthKey, Object date) {
        try {
            if (date != null) {
                map.put(yearKey, String.valueOf(date).substring(0, 4));
                map.put(monthKey, String.valueOf(date).substring(4, 6).replaceFirst("^0+", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setName(HashMap<String, Object> map, String seiKey, String meiKey, Object name) {
        try {
            String nameStr = CommonUtil.strNulltoBlank(name);
            if (!nameStr.equals("")) {
                String[] nameArr = nameStr.split("　");
                if (nameArr.length == 2) {
                    map.put(seiKey, nameArr[0]);
                    map.put(meiKey, nameArr[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setNameKana(HashMap<String, Object> map, String seiKey, String meiKey, Object name) {
        try {
            String nameStr = CommonUtil.strNulltoBlank(name);
            if (!nameStr.equals("")) {
                String[] nameArr = nameStr.split(" ");
                if (nameArr.length == 2) {
                    map.put(seiKey, nameArr[0]);
                    map.put(meiKey, nameArr[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPostNumber(HashMap<String, Object> map, String postKey, Object post1, Object post2) {
        try {
            String post1Str = CommonUtil.strNulltoBlank(post1);
            String post2Str = CommonUtil.strNulltoBlank(post2);
            if (!CommonUtil.chkNotNullEmpty(post2Str)) {
                post2Str = "0000";
            } else {
                post2Str = String.format("%04d", Integer.parseInt(post2Str));
            }
            String postNo = post1Str + post2Str;
            map.put(postKey, postNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 顧客をDBに登録
     * 
     * @param moshikomiKbn
     *            個人(1)or法人(2)
     * @param number
     *            免許証番号or法人番号
     * @param con
     * @return
     * @throws Exception
     */
    public String registCustomer(String moshikomiKbn, String number, Connection con) throws Exception {

        String customerId = "";

        try {

            // 個人
            if (moshikomiKbn.equals("1")) {
                number = getLicenseNumberHash(number, as);
                customerId = comdao.getCustomerIdFromLicenseNumber(number);
                if (!CommonUtil.chkNotNullEmpty(customerId)) {
                    customerId = generateUniqueCustomerId();
                    comdao.registKojinCustomer(customerId, number, con);
                } else {
                    LogUtil.info(className, "会員登録：" + customerId + "は既に登録済み");
                }
            }
            // 法人
            else {
                customerId = comdao.getCustomerIdFromHojinNumber(number);
                if (!CommonUtil.chkNotNullEmpty(customerId)) {
                    customerId = generateUniqueCustomerId();
                    comdao.registHojinCustomer(customerId, number, con);
                } else {
                    LogUtil.info(className, "会員登録：" + customerId + "は既に登録済み");
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return customerId;
    }

    /**
     * 顧客にログインURLを送信。顧客にユーザーIDが振られていない場合は新たに振る
     * 
     * @param sendMethod
     *            sms or email
     * @param mail
     *            メールアドレス
     * @param tel
     *            電話番号
     * @param con
     * @throws Exception
     * @return 送信先顧客UserId
     */
    public String sendLoginGuide(String customerId, String sendMethod, String mail, String tel, Connection con) throws Exception {

        try {

            String to = "";
            if (sendMethod.equals("sms")) {
                LogUtil.info("送信先電話番号：" + tel);
                to = tel;
                if (!check_telNumber(tel)) {
                    throw new Exception("電話番号が正しくありません。");
                }
            } else {
                LogUtil.info("送信先メールアドレス：" + mail);
                to = mail;
                if (!check_mailAddress(mail)) {
                    throw new Exception("メールアドレスが正しくありません。");
                }
            }

            // MessageSendテーブルにレコード登録
            String onetimePass = generateOnetimePassword();
            comdao.updateUserOnetimepassword(onetimePass, customerId, con);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("onetimepassword", onetimePass);
            comdao.insertMessageSend(sendMethod, to, "【USS-SSオートローン】ログイン用パスワード設定通知", MailUtil.createMessage("passwordNotify", sendMethod, map), con);

        } catch (Exception e) {
            LogUtil.error(e);
            throw e;
        }

        return customerId;
    }

    /**
     * DBに未登録の顧客IDを生成して返す
     * 
     * @param con
     * @return
     * @throws Exception
     */
    public String generateUniqueCustomerId() throws Exception {
        String userId;
        boolean isDuplicate;
        do {
            userId = generateCustomerId();
            isDuplicate = comdao.isCustomerIdExist(userId);
        } while (isDuplicate);

        return userId;

    }

    /**
     * ランダムなアルファベット１文字＋数字６文字 合計７桁のIDを生成する
     * 
     * @return
     * @throws Exception
     */
    public static String generateCustomerId() throws Exception {
        String result = "";

        Random random = new Random();
        // ランダムなアルファベット（A-Z）
        char alphabet = (char)('A' + random.nextInt(26));
        // ランダムな6桁の数字
        String numbers = String.format("%06d", random.nextInt(1000000));
        result = alphabet + numbers;

        return result;
    }

    /**
     * ワンタイムパスワード生成
     * 
     * @return
     */
    public String generateOnetimePassword() throws Exception {

        // パスの長さ
        int length = 32;

        // 使用する文字セット
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // セキュアな乱数生成器
        SecureRandom random = new SecureRandom();

        // パスワードを構築
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }

        return password.toString();
    }

    /**
     * 新規WEB受付IDを取得する
     * 
     * @return
     * @throws Exception
     */
    public String getWebAppId() throws Exception {
        String webAppId = "";
        try {
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
            String webAppIdDate = today.format(formatter);// yymmdd
            String webAppIdSeq = comdao.getWebAppIdSeq();// 当日連番
            String webAppIdEda = "001";// 初回申込は001
            webAppId = webAppIdDate + webAppIdSeq + webAppIdEda;
            LogUtil.info(className, "WEB受付ID新規発行 : " + webAppId);
        } catch (Exception e) {
            LogUtil.info(e.getMessage());
            throw e;
        }
        return webAppId;
    }

    /**
     * 引数WEB受付IDの枝番を更新して返す
     * 
     * @return
     * @throws Exception
     */
    public String getUpdateWebAppId(String preWebAppId) throws Exception {
        String webAppId = "";
        try {
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
            String webAppIdDateSeq = preWebAppId.substring(0, 10);
            String preEda = preWebAppId.substring(10, 13);
            int newEdaNum = Integer.parseInt(preEda) + 1;
            String newEda = String.format("%03d", newEdaNum);
            webAppId = webAppIdDateSeq + newEda;
            LogUtil.info(className, "WEB受付ID更新 : " + webAppId);
        } catch (Exception e) {
            LogUtil.info(e.getMessage());
            throw e;
        }
        return webAppId;
    }

    /**
     * 
     * @param apiData
     * @return
     */
    public void setKbnToText(HashMap<String, Object> map) {

        String objectName = "";

        // お申込み区分
        objectName = "MoshikomiKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "個人";
            } else if ("2".equals(kbn)) {
                text = "法人";
            }
            map.put(objectName + "Text", text);
        }

        // 性別
        objectName = "MoshikomiSeibetsuKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "男性";
            } else if ("2".equals(kbn)) {
                text = "女性";
            }
            map.put(objectName + "Text", text);
        }

        objectName = "MoshikomiMobileKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("".equals(kbn)) {
                text = "";
            } else if ("".equals(kbn)) {
                text = "";
            }
            map.put(objectName + "Text", text);
        }

        // 配偶者
        objectName = "MoshikomiHaigushaKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("2".equals(kbn)) {
                text = "あり";
            } else if ("1".equals(kbn)) {
                text = "なし";
            }
            map.put(objectName + "Text", text);
        }

        // 住居区分
        objectName = "JukyoKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "自己所有";
            } else if ("2".equals(kbn)) {
                text = "家族所有";
            } else if ("3".equals(kbn)) {
                text = "公営団体";
            } else if ("4".equals(kbn)) {
                text = "社宅";
            } else if ("5".equals(kbn)) {
                text = "賃貸マンション";
            } else if ("6".equals(kbn)) {
                text = "借家";
            } else if ("7".equals(kbn)) {
                text = "アパート";
            } else if ("8".equals(kbn)) {
                text = "寮・間借り";
            }
            map.put(objectName + "Text", text);
        }

        objectName = "JukyoShoyuKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("".equals(kbn)) {
                text = "";
            } else if ("".equals(kbn)) {
                text = "";
            }
            map.put(objectName + "Text", text);
        }

        // 住宅ローン・家賃支払い（配偶者含む）
        objectName = "MoshikomiLoanKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("2".equals(kbn)) {
                text = "あり";
            } else if ("1".equals(kbn)) {
                text = "なし";
            }
            map.put(objectName + "Text", text);
        }

        // ご職業
        objectName = "MoshikomiShokugyoKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "正社員";
            } else if ("2".equals(kbn)) {
                text = "契約社員";
            } else if ("3".equals(kbn)) {
                text = "自営/役員";
            } else if ("4".equals(kbn)) {
                text = "派遣社員";
            } else if ("5".equals(kbn)) {
                text = "学生";
            } else if ("6".equals(kbn)) {
                text = "専業主婦";
            } else if ("8".equals(kbn)) {
                text = "パート/アルバイト";
            } else if ("9".equals(kbn)) {
                text = "年金受給者";
            } else if ("10".equals(kbn)) {
                text = "公務員";
            } else if ("11".equals(kbn)) {
                text = "その他";
            }
            map.put(objectName + "Text", text);
        }

        objectName = "MoshikomiKinmusakiTelKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("".equals(kbn)) {
                text = "";
            } else if ("".equals(kbn)) {
                text = "";
            }
            map.put(objectName + "Text", text);
        }

        // 役職
        objectName = "MoshikomiKinmusakiYakushokuKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "経営者・役員";
            } else if ("2".equals(kbn)) {
                text = "管理職";
            } else if ("3".equals(kbn)) {
                text = "一般社員";
            } else if ("4".equals(kbn)) {
                text = "契約社員";
            } else if ("5".equals(kbn)) {
                text = "パート・アルバイト";
            } else if ("6".equals(kbn)) {
                text = "その他";
            }
            map.put(objectName + "Text", text);
        }

        // 業種区分
        objectName = "MoshikomiGyoshuKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "農林漁業";
            } else if ("2".equals(kbn)) {
                text = "公務員";
            } else if ("3".equals(kbn)) {
                text = "金融・保険業";
            } else if ("4".equals(kbn)) {
                text = "製造業";
            } else if ("5".equals(kbn)) {
                text = "サービス業";
            } else if ("6".equals(kbn)) {
                text = "卸・小売業";
            } else if ("7".equals(kbn)) {
                text = "飲食業";
            } else if ("8".equals(kbn)) {
                text = "土木・建築業";
            } else if ("9".equals(kbn)) {
                text = "運輸・通信業";
            } else if ("10".equals(kbn)) {
                text = "不動産業";
            } else if ("11".equals(kbn)) {
                text = "電気・ガス・水道";
            } else if ("12".equals(kbn)) {
                text = "その他";
            }
            map.put(objectName + "Text", text);
        }

        // お申込者との関係
        objectName = "MoshikomiSetainushiRelateKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "配偶者";
            } else if ("2".equals(kbn)) {
                text = "子";
            } else if ("3".equals(kbn)) {
                text = "親";
            } else if ("4".equals(kbn)) {
                text = "兄弟姉妹";
            } else if ("5".equals(kbn)) {
                text = "祖父母";
            } else if ("9".equals(kbn)) {
                text = "その他";
            }
            map.put(objectName + "Text", text);
        }

        // 居住
        objectName = "MoshikomiKyojuKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "同居";
            } else if ("9".equals(kbn)) {
                text = "別居";
            }
            map.put(objectName + "Text", text);
        }

        // 受給情報
        objectName = "MoshikomiNenkinJukyuKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "国民年金";
            } else if ("2".equals(kbn)) {
                text = "厚生年金";
            } else if ("3".equals(kbn)) {
                text = "共済年金";
            } else if ("4".equals(kbn)) {
                text = "その他";
            }
            map.put(objectName + "Text", text);
        }

        // 連帯保証人
        objectName = "HoshoninKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "あり";
            } else if ("0".equals(kbn)) {
                text = "なし";
            }
            map.put(objectName + "Text", text);
        }

        // 性別
        objectName = "HoshoninSeibetsuKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "男";
            } else if ("2".equals(kbn)) {
                text = "女";
            }
            map.put(objectName + "Text", text);
        }

        objectName = "HoshoninMobileKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("".equals(kbn)) {
                text = "";
            } else if ("".equals(kbn)) {
                text = "";
            }
            map.put(objectName + "Text", text);
        }

        objectName = "HoshoninTelKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("".equals(kbn)) {
                text = "";
            } else if ("".equals(kbn)) {
                text = "";
            }
            map.put(objectName + "Text", text);
        }

        // 住居区分
        objectName = "HoshoninJukyoKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "自己所有";
            } else if ("2".equals(kbn)) {
                text = "家族所有";
            } else if ("3".equals(kbn)) {
                text = "公営団体";
            } else if ("4".equals(kbn)) {
                text = "社宅";
            } else if ("5".equals(kbn)) {
                text = "賃貸マンション";
            } else if ("6".equals(kbn)) {
                text = "借家";
            } else if ("7".equals(kbn)) {
                text = "アパート";
            } else if ("8".equals(kbn)) {
                text = "寮・間借り";
            }
            map.put(objectName + "Text", text);
        }

        // 住宅ローン・家賃支払い（配偶者含む）
        objectName = "HoshoninLoanKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("2".equals(kbn)) {
                text = "あり";
            } else if ("1".equals(kbn)) {
                text = "なし";
            }
            map.put(objectName + "Text", text);
        }

        // 配偶者
        objectName = "HoshoninHaigushaKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("2".equals(kbn)) {
                text = "あり";
            } else if ("1".equals(kbn)) {
                text = "なし";
            }
            map.put(objectName + "Text", text);
        }

        // お申込者とのご関係
        objectName = "HoshoninMoshikomiRelateKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "夫婦";
            } else if ("2".equals(kbn)) {
                text = "親子";
            } else if ("3".equals(kbn)) {
                text = "兄弟姉妹";
            } else if ("4".equals(kbn)) {
                text = "親戚";
            } else if ("5".equals(kbn)) {
                text = "代表";
            } else if ("9".equals(kbn)) {
                text = "友人";
            } else if ("10".equals(kbn)) {
                text = "知人";
            } else if ("11".equals(kbn)) {
                text = "その他";
            }
            map.put(objectName + "Text", text);
        }

        // 就業形態
        objectName = "HoshoninShokugyoKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "正社員";
            } else if ("2".equals(kbn)) {
                text = "契約社員";
            } else if ("3".equals(kbn)) {
                text = "自営/役員";
            } else if ("4".equals(kbn)) {
                text = "派遣社員";
            } else if ("5".equals(kbn)) {
                text = "学生";
            } else if ("6".equals(kbn)) {
                text = "専業主婦";
            } else if ("8".equals(kbn)) {
                text = "パート/アルバイト";
            } else if ("9".equals(kbn)) {
                text = "年金受給者";
            } else if ("10".equals(kbn)) {
                text = "公務員";
            } else if ("11".equals(kbn)) {
                text = "その他";
            } else if ("12".equals(kbn)) {
                text = "年金受給者＋その他";
            } else if ("13".equals(kbn)) {
                text = "無職";
            }
            map.put(objectName + "Text", text);
        }

        objectName = "HoshoninKinmusakiKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("".equals(kbn)) {
                text = "";
            } else if ("".equals(kbn)) {
                text = "";
            }
            map.put(objectName + "Text", text);
        }

        objectName = "HoshoninKinmusakiTelKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("".equals(kbn)) {
                text = "";
            } else if ("".equals(kbn)) {
                text = "";
            }
            map.put(objectName + "Text", text);
        }

        // 役職
        objectName = "HoshoninKinmusakiYakushokuKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "経営者・役員";
            } else if ("2".equals(kbn)) {
                text = "管理職";
            } else if ("3".equals(kbn)) {
                text = "一般社員";
            } else if ("4".equals(kbn)) {
                text = "契約社員";
            } else if ("5".equals(kbn)) {
                text = "パート・アルバイト";
            } else if ("6".equals(kbn)) {
                text = "その他";
            }
            map.put(objectName + "Text", text);
        }

        // 業種区分
        objectName = "HoshoninGyoshuKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "農林漁業";
            } else if ("2".equals(kbn)) {
                text = "公務員";
            } else if ("3".equals(kbn)) {
                text = "金融・保険業";
            } else if ("4".equals(kbn)) {
                text = "製造業";
            } else if ("5".equals(kbn)) {
                text = "サービス業";
            } else if ("6".equals(kbn)) {
                text = "卸・小売業";
            } else if ("7".equals(kbn)) {
                text = "飲食業";
            } else if ("8".equals(kbn)) {
                text = "土木・建築業";
            } else if ("9".equals(kbn)) {
                text = "運輸・通信業";
            } else if ("10".equals(kbn)) {
                text = "不動産業";
            } else if ("11".equals(kbn)) {
                text = "電気・ガス・水道";
            } else if ("12".equals(kbn)) {
                text = "その他";
            }
            map.put(objectName + "Text", text);
        }

        // 年金受給情報
        objectName = "HoshoninNenkinJukyuKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "国民年金";
            } else if ("2".equals(kbn)) {
                text = "厚生年金";
            } else if ("3".equals(kbn)) {
                text = "共済年金";
            } else if ("4".equals(kbn)) {
                text = "その他";
            }
            map.put(objectName + "Text", text);
        }

        // Connected(MCCS)申込
        objectName = "MccsMoshikomiKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "あり";
            } else if ("0".equals(kbn)) {
                text = "なし";
            }
            map.put(objectName + "Text", text);
        }

        // シフト
        objectName = "ShiftKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("0".equals(kbn)) {
                text = "AT";
            } else if ("1".equals(kbn)) {
                text = "MT";
            }
            map.put(objectName + "Text", text);
        }

        // リモートスターター
        objectName = "RemoteStarterKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "あり";
            } else if ("0".equals(kbn)) {
                text = "なし";
            }
            map.put(objectName + "Text", text);
        }

        // 販売の条件となっている商品・権利・役務の有無
        objectName = "HanbaiJokenKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "あり";
            } else if ("0".equals(kbn)) {
                text = "なし";
            }
            map.put(objectName + "Text", text);
        }

        objectName = "HanbaiJokenKbnSentaku";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("".equals(kbn)) {
                text = "";
            } else if ("".equals(kbn)) {
                text = "";
            }
            map.put(objectName + "Text", text);
        }

        // 主な使用目的
        objectName = "ShiyoMokutekiKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "日常";
            } else if ("2".equals(kbn)) {
                text = "商用";
            } else if ("3".equals(kbn)) {
                text = "その他";
            }
            map.put(objectName + "Text", text);
        }

        // 新車・中古車
        objectName = "UsedKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "新車";
            } else if ("0".equals(kbn)) {
                text = "中古車";
            }
            map.put(objectName + "Text", text);
        }

        // エンジンスタート
        objectName = "EngineStartKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("0".equals(kbn)) {
                text = "プッシュ";
            } else if ("1".equals(kbn)) {
                text = "回転キー";
            }
            map.put(objectName + "Text", text);
        }

        // ボーナス加算月
        objectName = "BonusKasanMonthKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("91".equals(kbn)) {
                text = "する（毎年8月・12月）";
            } else if ("90".equals(kbn)) {
                text = "しない";
            }
            map.put(objectName + "Text", text);
        }

        objectName = "ShiharaiHohoKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("".equals(kbn)) {
                text = "";
            } else if ("".equals(kbn)) {
                text = "";
            }
            map.put(objectName + "Text", text);
        }

        // 納車日
        objectName = "NoshaDateKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "契約日から20日以内";
            } else if ("2".equals(kbn)) {
                text = "日付指定";
            }
            map.put(objectName + "Text", text);
        }

        // 申込方法
        objectName = "DaikoMoshikomiKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("1".equals(kbn)) {
                text = "FAX";
            } else if ("0".equals(kbn)) {
                text = "メール";
            }
            map.put(objectName + "Text", text);
        }

        objectName = "kotekiSiryoKbn";
        if (CommonUtil.chkNotNullEmpty(StringUtil.dataToString(map.get(objectName)))) {
            String kbn = StringUtil.dataToString(map.get(objectName));
            String text = "";
            if ("".equals(kbn)) {
                text = "";
            } else if ("".equals(kbn)) {
                text = "";
            }
            map.put(objectName + "Text", text);
        }

    }

    /**
     * 契約照会APIのレスポンスと入力値を比較し結果を返す
     * 
     * @param model
     * @param apiRes
     * @return
     */
    public boolean checkApiResponse(HashMap<String, String> map, HashMap<String, Object> apiRes) {
        boolean result = true;

        try {
            // 生年月日
            String birthDate = StringUtil.dataToString(apiRes.get("kjn_birthday"));
            String birthYear = String.format("%02d", Integer.parseInt(birthDate.substring(0, 2)));
            String birthMonth = String.format("%02d", Integer.parseInt(birthDate.substring(2, 4)));
            String birthDay = String.format("%02d", Integer.parseInt(birthDate.substring(4, 6)));
            // 電話番号
            String telNo1 = StringUtil.dataToString(apiRes.get("kjn_home_telno1_1"));
            String telNo2 = StringUtil.dataToString(apiRes.get("kjn_home_telno1_2"));
            String telNo3 = StringUtil.dataToString(apiRes.get("kjn_home_telno1_3"));

            // 生年月日比較
            LogUtil.info(map.get("birthYear") + "," + birthYear);
            if (!map.get("birthYear").equals(birthYear))
                throw new Exception("生年月日：年不一致");

            LogUtil.info(map.get("birthMonth") + "," + birthMonth);
            if (!map.get("birthMonth").equals(birthMonth))
                throw new Exception("生年月日：月不一致");

            LogUtil.info(map.get("birthDay") + "," + birthDay);
            if (!map.get("birthDay").equals(birthDay))
                throw new Exception("生年月日：日不一致");
            // 電話番号比較
            LogUtil.info(map.get("telNo1") + "," + telNo1);
            if (!map.get("telNo1").equals(telNo1))
                throw new Exception("電話番号１不一致");

            LogUtil.info(map.get("telNo2") + "," + telNo2);
            if (!map.get("telNo2").equals(telNo2))
                throw new Exception("電話番号２不一致");

            LogUtil.info(map.get("telNo3") + "," + telNo3);
            if (!map.get("telNo3").equals(telNo3))
                throw new Exception("電話番号３不一致");

        } catch (Exception e) {
            result = false;
            LogUtil.error(className, e);
        }

        return result;
    }

    /**
     * 西暦を和暦に変換、返還後の元号と年をmapに入れて返す
     * 
     * @param seirekiYear
     * @return
     */
    public HashMap<String, String> convertSeirekiToWareki(String seirekiYear) {

        HashMap<String, String> wareki = new HashMap<String, String>();
        try {
            int year = Integer.parseInt(seirekiYear); // 西暦を整数に変換
            String gengo;
            int warekiYear;

            if (year >= 2019) { // 令和
                gengo = "5";
                warekiYear = year - 2018;
            } else if (year >= 1989) { // 平成
                gengo = "4";
                warekiYear = year - 1988;
            } else if (year >= 1926) { // 昭和
                gengo = "3";
                warekiYear = year - 1925;
            } else if (year >= 1912) { // 大正
                gengo = "2";
                warekiYear = year - 1911;
            } else if (year >= 1868) { // 明治
                gengo = "1";
                warekiYear = year - 1867;
            } else {
                throw new IllegalArgumentException("西暦が対応範囲外です: " + seirekiYear);
            }

            // 結果をMapに格納
            wareki.put("gengo", gengo);
            wareki.put("year", String.valueOf(warekiYear));
        } catch (NumberFormatException e) {
            LogUtil.error("無効な西暦が入力されました: " + seirekiYear, e);
        } catch (Exception e) {
            LogUtil.error("エラーが発生しました: ", e);
        }

        return wareki;
    }
}