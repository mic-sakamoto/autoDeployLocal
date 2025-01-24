package utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;

import com.opensymphony.xwork2.ActionSupport;

import utils.log4j.LogUtil;

public class CommonUtil extends ActionSupport {

    public static String strOrNull(Object obj) {

        if (obj == null) {
            return null;
        } else {
            return obj.toString();
        }

    }

    public static String strNulltoBlank(Object obj) {

        if (obj == null) {
            return "";
        } else {
            return obj.toString();
        }

    }

 // 文字列が数字がどうか
    public static boolean isNumber(String num) {
        return NumberUtils.isNumber(num);
    }
//    public static boolean isNumber(String str) {
//        try {
//            char[] chars = str.toCharArray();
//
//            for (int i = 0; i < chars.length; i++) {
//                if (String.valueOf(chars[i]).getBytes().length < 2) {
//                } else {
//                    throw new Exception();
//                }
//            }
//
//            Integer.parseInt(str);
//            return true;
//        } catch (Exception nfex) {
//            return false;
//        }
//    }

    public static boolean urlCheck(URL url) {

        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            int status = conn.getResponseCode();
            conn.disconnect();

            System.out.println("ファイル取得:" + url);
            // ファイルが見つからない時
            if (status != 200) {
                System.out.println("ファイルが見つからない!");
                return false;
            } else {
                return true;
            }

        } catch (IOException e) {
            return false;
        }
    }

    // URL妥当チェック
    public static boolean isExistURL(String urlStr) {
        URL url;
        int status = 0;
        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            status = conn.getResponseCode();
            conn.disconnect();
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        if (status == HttpURLConnection.HTTP_OK) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean chkNotNullEmpty(String str) {

        if (str != null && !"".equals(str)) {
            return true;
        }
        return false;
    }

    public static boolean chkNotNullEmpty(int val) {

        if (val != 0) {
            return true;
        }
        return false;
    }

    public static boolean chkNotNullEmpty(BigDecimal val) {

        if (val != null && !"".equals(val) && val.longValue() != 0) {
            return true;
        }
        return false;
    }

    // TEL,FAX妥当チェック
    public static boolean chkTel(String address) {

        boolean errFlg = true;

        // メールアドレス1の半角チェック
        if (!address.matches("[0-9\\-+]+")) {
            errFlg = false;
        }

        return errFlg;

    }

    // メールアドレス妥当チェック
    public static boolean chkMailAddress(String address) {

        boolean errFlg = true;

        // メールアドレス1の半角チェック
        if (!address.matches("[0-9a-zA-Z@\\-_.*!#$%&'*+/=?^`{|}~]+")) {
            errFlg = false;
        } else {
            // @なし
            if (address.indexOf("@") == -1) {
                errFlg = false;
            } else {
                // 先頭がドット
                if (address.startsWith(".")) {
                    errFlg = false;
                } else {
                    // ドット連続
                    if (address.indexOf("..") != -1) {
                        errFlg = false;
                    } else {
                        // @前ドット
                        if (address.indexOf(".@") != -1) {
                            errFlg = false;
                        }
                    }
                }
            }
        }

        return errFlg;

    }

    /**
     * booleanチェック
     *
     * @throws Exception
     */
    public static String booleanToString(String value) throws Exception {

        if (value == null) {
            return "0";
        } else if ("true".equals(value)) {
            return "1";
        } else if ("false".equals(value)) {
            return "0";
        } else if ("0".equals(value)) {
            return "0";
        } else if ("1".equals(value)) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * 画像拡張子チェック
     *
     * @param is
     * @return
     */
    public static boolean chkUrlImg(InputStream is) {

        byte[] buf = new byte[8];
        int len = 0;
        try {
            len = is.read(buf);
            if (len == -1) {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
            }
        }

        final byte[] BMP_SIG = new byte[] { 0x42, 0x4D };
        final byte[] GIF_SIG_1 = new byte[] { 0x47, 0x49, 0x46, 0x38, 0x37, 0x61 };
        final byte[] GIF_SIG_2 = new byte[] { 0x47, 0x49, 0x46, 0x38, 0x39, 0x61 };
        final byte[] JPG_SIG = new byte[] { (byte)0xFF, (byte)0xD8, (byte)0xFF };
        final byte[] PNG_SIG = new byte[] { (byte)0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A };
        final byte[] PSD_SIG = new byte[] { 0x38, 0x42, 0x50, 0x53 };
        final byte[] TIF_SIG_1 = new byte[] { 0x49, 0x49, 0x2A, 0x00 };
        final byte[] TIF_SIG_2 = new byte[] { 0x4D, 0x4D, 0x00, 0x2A };

        if (len >= BMP_SIG.length && compareByteArray(buf, BMP_SIG, BMP_SIG.length) == 0) {
            return true;
        } else if (len >= GIF_SIG_1.length && compareByteArray(buf, GIF_SIG_1, GIF_SIG_1.length) == 0) {
            return true;
        } else if (len >= GIF_SIG_2.length && compareByteArray(buf, GIF_SIG_2, GIF_SIG_2.length) == 0) {
            return true;
        } else if (len >= JPG_SIG.length && compareByteArray(buf, JPG_SIG, JPG_SIG.length) == 0) {
            return true;
        } else if (len >= PNG_SIG.length && compareByteArray(buf, PNG_SIG, PNG_SIG.length) == 0) {
            return true;
        } else if (len >= PSD_SIG.length && compareByteArray(buf, PSD_SIG, PSD_SIG.length) == 0) {
            return true;
        } else if (len >= TIF_SIG_1.length && compareByteArray(buf, TIF_SIG_1, TIF_SIG_1.length) == 0) {
            return true;
        } else if (len >= TIF_SIG_2.length && compareByteArray(buf, TIF_SIG_2, TIF_SIG_2.length) == 0) {
            return true;
        }

        return false;
    }

    /**
     * byte配列を指定長さ分比較して結果を返す
     *
     * @param a1
     *            比較対象の配列(1つ目)
     * @param a2
     *            比較対象の配列(2つ目)
     * @param compareLength
     *            比較する長さ
     * @return 指定長さの内容が等しければ{@code 0}を等しくなければ{@code -1}
     *         いずれかの配列の長さが比較用の指定長さに満たない場合は{@code -1}
     */
    private static int compareByteArray(byte[] a1, byte[] a2, int compareLength) {
        if (a1.length < compareLength || a2.length < compareLength) {
            return -1;
        }
        for (int i = 0; i < compareLength; i++) {
            if (a1[i] != a2[i]) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * メインメソッド
     *
     * @param args
     *            0:入力パス 1:出力パス 2:指定幅 3:指定高さ
     * @throws IOException
     */
    public static boolean imageResizeMove(String inPath, String outPath, int width, int height) throws IOException {
        resizeByScaledInstance(inPath, outPath, width, height);
        return true;
    }

    /**
     * 画像リサイズするメソッド
     * @param inputPath
     * 入力パス
     * @param outputPath
     * 出力パス
     * @param maxWidth
     * 指定幅
     * @param maxHeight
     *            指定高さ
     * @throws IOException
     */
    public static void resizeByScaledInstance(String inputPath, String outputPath, int maxWidth, int maxHeight) throws IOException {

        // 元画像の読み込み
        BufferedImage sourceImage = ImageIO.read(new File(inputPath));

        // 縮小比率の計算
        BigDecimal bdW = new BigDecimal(maxWidth);
        bdW = bdW.divide(new BigDecimal(sourceImage.getWidth()), 8, BigDecimal.ROUND_HALF_UP);
        BigDecimal bdH = new BigDecimal(maxHeight);
        bdH = bdH.divide(new BigDecimal(sourceImage.getHeight()), 8, BigDecimal.ROUND_HALF_UP);
        // 縦横比は変えずに最大幅、最大高さを超えないように比率を指定する
        if (bdH.compareTo(bdW) < 0) {
            maxWidth = -1;
        } else {
            maxHeight = -1;
        }

        // スケールは以下から選択
        // Image.SCALE_AREA_AVERAGING 遅いがなめらか
        // Image.SCALE_DEFAULT 普通 速度はFASTと変わらない
        // Image.SCALE_FAST 早くて汚い
        // Image.SCALE_REPLICATE わからん そこそこ汚い
        // Image.SCALE_SMOOTH 遅くてなめらか
        Image targetImage = sourceImage.getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);

        // Image -> BufferedImageの変換
        BufferedImage targetBufferedImage = new BufferedImage(targetImage.getWidth(null), targetImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = targetBufferedImage.createGraphics();
        g.drawImage(targetImage, 0, 0, null);

        // 拡張子取得
        String ext = inputPath.substring(inputPath.lastIndexOf(".") + 1);
        // 変換画像の出力
        ImageIO.write(targetBufferedImage, ext, new File(outputPath));
    }

    /**
     * PDF表示用画像
     * @param inputPath
     * @param maxWidth
     * @param maxHeight
     * @return
     * @throws IOException
     */
    public static InputStream resizeAndReturnStream(BufferedImage sourceImage, int maxWidth, int maxHeight) throws IOException {

        // 元画像の読み込み

        // 縮小比率の計算
        BigDecimal bdW = new BigDecimal(maxWidth);
        bdW = bdW.divide(new BigDecimal(sourceImage.getWidth()), 8, BigDecimal.ROUND_HALF_UP);
        BigDecimal bdH = new BigDecimal(maxHeight);
        bdH = bdH.divide(new BigDecimal(sourceImage.getHeight()), 8, BigDecimal.ROUND_HALF_UP);
        // 縦横比は変えずに最大幅、最大高さを超えないように比率を指定する
        if (bdH.compareTo(bdW) < 0) {
            maxWidth = -1;
        } else {
            maxHeight = -1;
        }

        // スケールは以下から選択
        // Image.SCALE_AREA_AVERAGING 遅いがなめらか
        // Image.SCALE_DEFAULT 普通 速度はFASTと変わらない
        // Image.SCALE_FAST 早くて汚い
        // Image.SCALE_REPLICATE わからん そこそこ汚い
        // Image.SCALE_SMOOTH 遅くてなめらか
        Image targetImage = sourceImage.getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);

        // Image -> BufferedImageの変換
        BufferedImage targetBufferedImage = new BufferedImage(targetImage.getWidth(null), targetImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = targetBufferedImage.createGraphics();
        g.drawImage(targetImage, 0, 0, null);

        // 拡張子取得
        String ext = "jpg";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(targetBufferedImage, ext, os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());

        // ImageInputStream bigInputStream = ImageIO.createImageInputStream(targetBufferedImage);

        return is;
    }

    /**
     * 指定した２つの時間間隔を返します。
     * @param formatD
     * 引数date1、date2の書式を指定します。yyyymmddまたはyyyy/mm/ddです。
     * @param date1
     *  算出対象日を指定します。
     * @param date2
     * 基準日を指定します。
     * @param formatT
     * 引数Time1、Time2の書式を指定します。hhmmssまたはhh:mm:ssです。
     * @param Time1
     * 算出対象日を指定します。
     * @param Time2
     * 基準日を指定します。
     * @return date1とdate2の間隔を時間(分)で返します。
     */
    public static long getTimeDiff(String formatD, String date1, String date2, String formatT, String Time1, String Time2) {
        String strDate1 = "";
        String strDate2 = "";
        String strTime1 = "";
        String strTime2 = "";

        Date dtday1 = null;
        Date dtday2 = null;

        long days = 0;
        long lngtime1 = 0;
        long lngtime2 = 0;

        // ---日付---
        if (formatD.equals("yyyy/mm/dd")) {
            strDate1 = date1;
            strDate2 = date2;
        } else {
            strDate1 = date1.substring(0, 4) + "/" + date1.substring(4, 6) + "/" + date1.substring(6, 8);
            strDate2 = date2.substring(0, 4) + "/" + date2.substring(4, 6) + "/" + date2.substring(6, 8);
        }
        // ---時刻---
        if (formatT.equals("hh:mm:ss")) {
            strTime1 = Time1;
            strTime2 = Time2;
        } else {
            strTime1 = Time1.substring(0, 2) + ":" + Time1.substring(2, 4) + ":" + Time1.substring(4, 6);
            strTime2 = Time2.substring(0, 2) + ":" + Time2.substring(2, 4) + ":" + Time2.substring(4, 6);
        }
        strDate1 = strDate1 + " " + strTime1;
        strDate2 = strDate2 + " " + strTime2;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try {
            dtday1 = formatter.parse(strDate1); // 文字列 → 日付
            dtday2 = formatter.parse(strDate2); // 文字列 → 日付
        } catch (ParseException e) { // 書式エラー
            return days;
        } catch (Exception e) {
            return days;
        }

        lngtime1 = dtday1.getTime(); // 1970 年 1 月 1 日 00:00:00 GMT からのミリ秒数を取得。
        lngtime2 = dtday2.getTime(); // 1970 年 1 月 1 日 00:00:00 GMT からのミリ秒数を取得。

        // 差分計算 注：差の分数 = 1000 * 60 = 60000)
        days = (lngtime2 - lngtime1) / 60000;

        return days;
    }

    public static int getLimitStart(String page, int numDisp) {

        int _intPage = 0;
        int _limitStart = 0;

        if (CommonUtil.chkNotNullEmpty(page) && !"1".equals(page)) {
            _intPage = Integer.parseInt(page);
            _limitStart = ((_intPage - 1) * numDisp);
        }

        return _limitStart;
    }

    /**
     * 現在の曜日を返します。
     * ※曜日は省略します。
     *
     * @return 現在の曜日
     */
    public static String getDayOfTheWeekShort(int i) {
        switch (i) {
            case Calendar.SUNDAY:
                return "日";
            case Calendar.MONDAY:
                return "月";
            case Calendar.TUESDAY:
                return "火";
            case Calendar.WEDNESDAY:
                return "水";
            case Calendar.THURSDAY:
                return "木";
            case Calendar.FRIDAY:
                return "金";
            case Calendar.SATURDAY:
                return "土";
            default:
                return "";
        }
    }

    /**
     * md5でハッシュ化
     *
     * @param code
     * @return
     * @throws Exception
     */
    public static String md5Hash(String str) throws Exception {
        String code = "QBRICK" + ":" + str;
        String md5 = "";
        try {
            byte[] str_bytes = code.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5_bytes = md.digest(str_bytes);

            StringBuilder hexStrBuilder = new StringBuilder();
            for (int i = 0; i < md5_bytes.length; i++) {

                // 16進数表記で1桁数値だった場合、2桁目を0で埋める
                if ((md5_bytes[i] & 0xff) < 0x10) {
                    hexStrBuilder.append("0");
                }
                hexStrBuilder.append(Integer.toHexString(0xff & md5_bytes[i]));
            }
            md5 = hexStrBuilder.toString();

        } catch (Exception e) {
            LogUtil.error(e);
        }
        return md5;
    }

    public static ArrayList<HashMap<String, String>> getCategoryList(List<HashMap<String, Object>> categoryMasterList) {

        ArrayList<HashMap<String, String>> compList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> compMap = new HashMap<String, String>();
        compMap.put("key", "");
        compMap.put("name", "");
        compList.add(compMap);

        if (categoryMasterList != null) {

            HashMap<String, String> tmpMap = new HashMap<String, String>();

            for (HashMap<String, Object> map : categoryMasterList) {

                String layerCd = StringUtil.dataToString(map.get("LayerCd"));
                String categoryName = StringUtil.dataToString(map.get("CategoryName"));
                String categoryId = StringUtil.dataToString(map.get("CategoryId"));

                compMap = new HashMap<String, String>();

                tmpMap.put(layerCd, categoryName);

                StringBuffer strb = new StringBuffer();
                for (int i = 0; i < layerCd.length() / 3; i++) {
                    if (i != 0) {
                        strb.append(" / ");
                    }
                    strb.append(tmpMap.get(layerCd.substring(0, (i + 1) * 3)));
                }

                compMap.put("key", categoryId);
                compMap.put("name", strb.toString());
                compList.add(compMap);
            }
        }

        return compList;
    }



    /**
     * MySQL datetime 型に格納する、
     * 共通の時間の書式。
     */
    private static final SimpleDateFormat COMMON_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 現在の時間を、共通の時間の書式で返答する。
     *
     * @return
     */
    public static String getCurrentDatetime() {
        return COMMON_DATETIME_FORMAT.format(new Date());
    }

    /**
     * 現在の時間に引数の分を加算し、共通の時間の書式で返答する。
     *
     * @return
     */
    public static String getCurrentDatetimeAddMinute(int minute) {
        return COMMON_DATETIME_FORMAT.format(new Date(System.currentTimeMillis() + 1000 * 60 * minute));
    }

    /**
     * MySQL の datetime 型の書式（おそらくこれがデフォルト）
     * show variables like "%datetime%"
     * で確認・変更可能。
     */
    private static String MYSQL_DATETIME_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
    //private static SimpleDateFormat MYSQL_DATETIME_FORMAT = new SimpleDateFormat(MYSQL_DATETIME_FORMAT_STR);
    private static DateTimeFormatter MYSQL_DATETIME_FORMAT = DateTimeFormatter.ofPattern(MYSQL_DATETIME_FORMAT_STR);

    /**
     * 残り時間を計算して返答する。
     *
     * 戻り値について。
     * 過去なら:
     * "終了"
     * 1日以内なら:
     * "0時間0分", "0時間1分", "0時間12分",
     * "1時間0分", "1時間1分", "1時間12分",
     * "12時間0分", "12時間1分", "12時間12分"
     * 1日以上なら:
     * "1日", "3日", "366日", "3651日"
     * といった文字列を返答。
     *
     * @param endDatetimeStr
     *            InterAuctUtil.MYSQL_DATE_TIME_FORMAT_STR の書式に従った文字列
     * @return
     */
    public static String getRemainingTime(String endDatetimeStr) {

        if (endDatetimeStr == null) {
            return "";
        }

        try {

            Date currentDate = new Date();
            Date endDate = null;
            LocalDateTime endDate2 = LocalDateTime.parse(endDatetimeStr, MYSQL_DATETIME_FORMAT);
            endDate = toDate(endDate2);
            // endDate = MYSQL_DATETIME_FORMAT.parse();
            long diff = endDate.getTime() - currentDate.getTime();

            // 過去なら
            if (diff < 0) {
                return "終了";
            }

            // 違いをそれぞれの単位で表現する
            long diffDay = diff / DateUtils.MILLIS_PER_DAY;
            long diffHour = (diff % DateUtils.MILLIS_PER_DAY) / DateUtils.MILLIS_PER_HOUR;
            long diffMinute = (diff % DateUtils.MILLIS_PER_HOUR) / DateUtils.MILLIS_PER_MINUTE;

            if (1 <= diffDay) {
                // 1日以上の違いなら
                return String.format("%d日", diffDay);
            } else {
                // 1日以内の違いなら
                return String.format("%d時間%d分", diffHour, diffMinute);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return endDatetimeStr;
    }

        public static Date toDate(LocalDateTime localDateTime) {
            ZoneId zone = ZoneId.systemDefault();
            ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zone);
            Instant instant = zonedDateTime.toInstant();
            return Date.from(instant);
        }

        public static String toStr(LocalDateTime localDateTime, String format) {

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            return localDateTime.format(dateTimeFormatter);

        }

    /**
     * 指定された日付と、現在の時間の、
     * 二つの日付の差が第2引数の分を超えるか
     *
     * @param s1
     * @return 以内ならTrue、外ならfalse
     */
    public static boolean isDiffWithCurrentByMinutes(String s1, int minutes) {
        long diff_to_millis = getDiffWithCurrentByMillis(s1);
        long minutes_to_millis = new BigDecimal(minutes).multiply(new BigDecimal(60000)).longValue();

        return !(minutes_to_millis <= diff_to_millis);
    }

    /**
     * 指定された日付と、現在の時間の、
     * 二つの日付の差をミリ秒単位で返答。
     *
     * @param s1
     * @return 指定された日付が、現在より過去なら負。現在より未来なら正の値が返る。
     */
    public static long getDiffWithCurrentByMillis(String s1) {
        try {

            // Date cd = new Date();
            // String s2 = MYSQL_DATETIME_FORMAT.format(cd);
            // SimpleDateFormat sdf = new SimpleDateFormat(MYSQL_DATETIME_FORMAT_STR);
            // d1 = sdf.parse(s1);
            // d2 = sdf.parse(s2);

            String s2 = toStr(LocalDateTime.now(), MYSQL_DATETIME_FORMAT_STR);

            Date d1 = null, d2 = null;

            LocalDateTime ls1 = LocalDateTime.parse(s1, MYSQL_DATETIME_FORMAT);
            d1 = toDate(ls1);

            LocalDateTime ls2 = LocalDateTime.parse(s2, MYSQL_DATETIME_FORMAT);
            d2 = toDate(ls2);

            return d1.getTime() - d2.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void mapToBean(HashMap<String, Object> rsMap, Object bean) throws Exception {

        Method[] methods = bean.getClass().getMethods();

        for (Method method : methods) {
            String name = method.getName();
            if (name.length() > 3 && "set".equals(name.substring(0, 3))) {
                String colName = name.substring(3, name.length());

                try {

                    if (rsMap.containsKey(colName)) {

                        String typeName = "";
                        try {
                            typeName = bean.getClass().getDeclaredField(colName).getType().getName();
                        } catch (Exception e) {
                            try {
                                typeName = bean.getClass().getField(colName).getType().getName();
                            } catch (Exception e2) {
                                try {
                                    typeName = bean.getClass().getDeclaredField(colName.toLowerCase()).getType().getName();
                                } catch (Exception e3) {
                                    try {
                                        typeName = bean.getClass().getField(colName.toLowerCase()).getType().getName();
                                    } catch (Exception e4) {
                                        try {
                                            typeName = bean.getClass().getDeclaredField(colName.substring(0, 1).toLowerCase().concat(colName.substring(1))).getType().getName();
                                        } catch (Exception e5) {
                                            typeName = bean.getClass().getField(colName.substring(0, 1).toLowerCase().concat(colName.substring(1))).getType().getName();
                                        }
                                    }
                                }
                            }
                        }

                        Object obj = rsMap.get(colName);

                        if (obj != null && CommonUtil.chkNotNullEmpty(obj.toString())) {

                            setInvoke(typeName, method, bean, obj);

                        }

                    } else if (rsMap.containsKey(colName.toLowerCase())) {

                        String typeName = "";
                        try {
                            typeName = bean.getClass().getDeclaredField(colName.toLowerCase()).getType().getName();
                        } catch (Exception e) {
                            typeName = bean.getClass().getField(colName.toLowerCase()).getType().getName();
                        }

                        Object obj = rsMap.get(colName.toLowerCase());

                        if (obj != null && CommonUtil.chkNotNullEmpty(obj.toString())) {

                            setInvoke(typeName, method, bean, obj);
                        }
                    }

                } catch (Exception e) {
                }
            }
        }
    }

    private static void setInvoke(String typeName, Method method, Object bean, Object obj) throws Exception {
        if (typeName.indexOf("Boolean") >= 0)
            method.invoke(bean, Boolean.parseBoolean(obj.toString()));
        else if (typeName.indexOf("Byte") >= 0)
            method.invoke(bean, Byte.parseByte(obj.toString()));
        else if (typeName.indexOf("Date") >= 0)
            method.invoke(bean, Date.parse(obj.toString()));
        else if (typeName.indexOf("Short") >= 0)
            method.invoke(bean, Short.parseShort(obj.toString()));
        else if (typeName.indexOf("Int") >= 0)
            method.invoke(bean, Integer.parseInt(obj.toString()));
        else if (typeName.indexOf("int") >= 0)
            method.invoke(bean, Integer.parseInt(obj.toString()));
        else if (typeName.indexOf("Long") >= 0)
            method.invoke(bean, Long.parseLong(obj.toString()));
        else if (typeName.indexOf("Float") >= 0)
            method.invoke(bean, Float.parseFloat(obj.toString()));
        else if (typeName.indexOf("Double") >= 0)
            method.invoke(bean, Double.parseDouble(obj.toString()));
        else if (typeName.indexOf("BigDecimal") >= 0)
            method.invoke(bean, new BigDecimal(obj.toString()));
        else if (typeName.indexOf("String") >= 0)
            method.invoke(bean, obj.toString());
        else if (typeName.indexOf("Timestamp") >= 0)
            method.invoke(bean, Date.parse(obj.toString()));
        else
            method.invoke(bean, obj);
    }
        
    public static ArrayList<HashMap<String, String>> getPrefectureList() {
        
        ArrayList<HashMap<String, String>> prefectureList = new ArrayList<HashMap<String, String>>();
        
        prefectureList.add(setPrefectureMap("1", "北海道"));
        prefectureList.add(setPrefectureMap("2", "青森県"));
        prefectureList.add(setPrefectureMap("3", "岩手県"));
        prefectureList.add(setPrefectureMap("4", "宮城県"));
        prefectureList.add(setPrefectureMap("5", "秋田県"));
        prefectureList.add(setPrefectureMap("6", "山形県"));
        prefectureList.add(setPrefectureMap("7", "福島県"));
        prefectureList.add(setPrefectureMap("8", "茨城県"));
        prefectureList.add(setPrefectureMap("9", "栃木県"));
        prefectureList.add(setPrefectureMap("10", "群馬県"));
        prefectureList.add(setPrefectureMap("11", "埼玉県"));
        prefectureList.add(setPrefectureMap("12", "千葉県"));
        prefectureList.add(setPrefectureMap("13", "東京都"));
        prefectureList.add(setPrefectureMap("14", "神奈川県"));
        prefectureList.add(setPrefectureMap("15", "新潟県"));
        prefectureList.add(setPrefectureMap("16", "富山県"));
        prefectureList.add(setPrefectureMap("17", "石川県"));
        prefectureList.add(setPrefectureMap("18", "福井県"));
        prefectureList.add(setPrefectureMap("19", "山梨県"));
        prefectureList.add(setPrefectureMap("20", "長野県"));
        prefectureList.add(setPrefectureMap("21", "岐阜県"));
        prefectureList.add(setPrefectureMap("22", "静岡県"));
        prefectureList.add(setPrefectureMap("23", "愛知県"));
        prefectureList.add(setPrefectureMap("24", "三重県"));
        prefectureList.add(setPrefectureMap("25", "滋賀県"));
        prefectureList.add(setPrefectureMap("26", "京都府"));
        prefectureList.add(setPrefectureMap("27", "大阪府"));
        prefectureList.add(setPrefectureMap("28", "兵庫県"));
        prefectureList.add(setPrefectureMap("29", "奈良県"));
        prefectureList.add(setPrefectureMap("30", "和歌山県"));
        prefectureList.add(setPrefectureMap("31", "鳥取県"));
        prefectureList.add(setPrefectureMap("32", "島根県"));
        prefectureList.add(setPrefectureMap("33", "岡山県"));
        prefectureList.add(setPrefectureMap("34", "広島県"));
        prefectureList.add(setPrefectureMap("35", "山口県"));
        prefectureList.add(setPrefectureMap("36", "徳島県"));
        prefectureList.add(setPrefectureMap("37", "香川県"));
        prefectureList.add(setPrefectureMap("38", "愛媛県"));
        prefectureList.add(setPrefectureMap("39", "高知県"));
        prefectureList.add(setPrefectureMap("40", "福岡県"));
        prefectureList.add(setPrefectureMap("41", "佐賀県"));
        prefectureList.add(setPrefectureMap("42", "長崎県"));
        prefectureList.add(setPrefectureMap("43", "熊本県"));
        prefectureList.add(setPrefectureMap("44", "大分県"));
        prefectureList.add(setPrefectureMap("45", "宮崎県"));
        prefectureList.add(setPrefectureMap("46", "鹿児島県"));
        prefectureList.add(setPrefectureMap("47", "沖縄県"));
        
        return prefectureList;
    }
    
    private static HashMap<String, String> setPrefectureMap(String code, String name) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("code", code);
        map.put("name", name);
        return map;
    }
    
    public static String toHankaku(String input) {
        if (input == null) {
            return null;
        }
        return Normalizer.normalize(input, Normalizer.Form.NFKC);
    }

}
