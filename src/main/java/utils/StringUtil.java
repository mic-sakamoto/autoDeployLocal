package utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;

import utils.log4j.LogUtil;

public class StringUtil {

  private String CLASSNAME = this.getClass().getName();

  private static Map<String, String> convertCodeMap_SEX = new HashMap<>();
  static {
    convertCodeMap_SEX.put("1", "男");
    convertCodeMap_SEX.put("2", "女");
  }

  /**
   * コンストラクタ
   */
  public StringUtil() {

  }

  /**
   * null -> 空文字に変換
   *
   * @param str
   *          対象文字列
   * @return nullの場合空文字、その他変換せず戻し
   */
  public static String nullToEmptyChar(Object str) {
    if (str == null) {
      return "";
    } else {
      return str.toString();
    }
  }

  /**
   * 空文字 -> nullに変換
   *
   * @param str
   *          対象文字列
   * @return 空文字の場合null、その他変換せず戻し
   */
  public static String emptyCharToNull(String str) {
    if ("".equals(str)) {
      return null;
    } else if ("null".equals(str)) {
      return null;
    } else {
      return str;
    }
  }

  /**
   * 空文字 ->0に変換
   *
   * @param str
   *          対象文字列
   * @return 空文字orNULL の場合'0'に変換
   */
  public static String emptyCharToZero(String str) {
    if (str == null || "".equals(str)) {
      return "0";
    } else {
      return str;
    }
  }

  /**
   * float -> Stringに変換
   *
   * @param arg
   *          対象オブジェクト
   * @return nullの場合空文字、その他文字列に変換
   */
  public static Float strToFloat(String arg) {
    if (arg.equals("")) {
      return null;
    }
    return Float.parseFloat(arg);
  }

  /**
   * DB取得時の型 -> Stringに変換
   *
   * @param arg
   *          対象オブジェクト
   * @return nullの場合空文字、その他文字列に変換
   */
  public static String dataToString(Object arg) {
    if (arg == null) {
      return "";
    }
    String type = arg.getClass().getCanonicalName();
    if (type.equals("java.lang.String")) {
      return (String)arg;
    } else if (type.equals("java.lang.Short")) {
      return Short.toString((Short)arg);
    } else if (type.equals("java.lang.Integer")) {
      return Integer.toString((Integer)arg);
    } else if (type.equals("java.lang.Long")) {
      return Long.toString((Long)arg);
    } else if (type.equals("java.lang.Float")) {
      return Float.toString((Float)arg);
    } else if (type.equals("java.lang.Double")) {
      return Double.toString((Double)arg);
    } else if (type.equals("java.math.BigDecimal")) {
      return ((BigDecimal)arg).setScale(0, BigDecimal.ROUND_DOWN).toString();
    } else if (type.equals("java.sql.Timestamp")) {
      String timeformat = "yyyy/MM/dd";
      return new SimpleDateFormat(timeformat).format((Timestamp)arg);
    } else {
      return arg.toString();
    }
  }

  /**
   * argがbigdecimalの場合は、指定のscaleのstringを返す。
   *
   * @param arg
   * @param scale
   * @return
   */
  public static String dataToString(Object arg, int scale) {
    if (arg == null) {
      return "";
    }
    String type = arg.getClass().getCanonicalName();
    if (type.equals("java.math.BigDecimal")) {
      return ((BigDecimal)arg).setScale(scale, BigDecimal.ROUND_DOWN).toString();
    }
    return "";
  }

  /**
   * NULL -> 0 を返す
   *
   * @param arg
   * @return args=null の場合 return 0
   */
  public static Integer dataToInteger(Object arg) {

    if (arg == null) {
      return 0;
    }
    if (arg.equals("")) {
      return 0;
    }
    if (arg instanceof BigDecimal) {
      return ((BigDecimal)arg).intValue();
    }
    if (arg instanceof Short) {
      return ((Short)arg).intValue();
    }
    try {
      if (arg instanceof String) {
        return Integer.valueOf(arg.toString());
      }
    } catch (Exception e) {
      LogUtil.error(e);
      LogUtil.error("argからintegerへの変換でエラーが発生しました。");
      return 0;

    }
    return (Integer)arg;
    /*
     * if(arg instanceof Short) return (Integer) arg;
     * if(arg instanceof Integer) return (Integer) arg;
     * return Integer.parseInt(String.valueOf(arg));
     */
  }

  /**
   * NULL -> 0 を返す
   *
   * @param arg
   * @return args=null の場合 return 0
   */
  public static long dataToLong(Object arg) {

    if (arg == null) {
      return 0;
    }
    if (arg.equals("")) {
      return 0;
    }
    if (arg instanceof BigDecimal) {
      return ((BigDecimal)arg).longValue();
    }
    if (arg instanceof Short) {
      return ((Short)arg).longValue();
    }
    try {
      if (arg instanceof String) {
        return Long.parseLong(arg.toString());
      }
    } catch (Exception e) {
      LogUtil.error(e);
      LogUtil.error("argからintegerへの変換でエラーが発生しました。");
      return 0;

    }
    return (Long)arg;
    /*
     * if(arg instanceof Short) return (Integer) arg;
     * if(arg instanceof Integer) return (Integer) arg;
     * return Integer.parseInt(String.valueOf(arg));
     */
  }

  /**
   * DateをyyyyMMddの文字列に変換
   * 20120206 Adachi
   *
   * @param arg(Date)
   * @return
   */
  public static String dataToString_yyyyMMdd(Object arg) {

    String timeformat = "yyyyMMdd";
    return new SimpleDateFormat(timeformat).format((Date)arg);

  }

  public static String IntegerToString(Integer arg) {
    if (arg == null) {
      return "";
    }
    return Integer.toString(arg);
  }

  /**
   * 数値文字列か確認
   *
   * @param arg
   *          確認したい文字列
   * @param nullFlg
   *          nullフラグ true:可
   * @param voidFlg
   *          空文字可フラグ true:可
   * @return 数値文字列：true、数値以外が含まれている：false
   */
  public static boolean isNumStr(String arg, boolean nullFlg, boolean voidFlg) {
    if (arg == null) {
      return nullFlg;
    }

    if ("".equals(arg)) {
      return voidFlg;
    }

    String reg = "^[0-9]+\\.?[0-9]*$";
    Pattern pattern = Pattern.compile(reg);
    Matcher matcher = pattern.matcher(arg);
    return matcher.matches();
  }

  /**
   * 数値文字列か確認<br>
   * 正の整数のみ
   *
   * @param arg
   *          確認したい文字列
   * @param nullFlg
   *          nullフラグ true:可
   * @param voidFlg
   *          空文字可フラグ true:可
   * @return 数値文字列：true、数値以外が含まれている：false
   */
  public static boolean isNumStr2(String arg, boolean nullFlg, boolean voidFlg) {

    if (arg == null) {
      return nullFlg;
    }

    if ("".equals(arg)) {
      return voidFlg;
    }

    String reg = "^[0-9]+$";
    Pattern pattern = Pattern.compile(reg);
    Matcher matcher = pattern.matcher(arg);
    return matcher.matches();
  }

  /**
   * 数値文字列か確認<br>
   * 負の整数も含む
   *
   * @param arg
   *          確認したい文字列
   * @param nullFlg
   *          nullフラグ true:可
   * @param voidFlg
   *          空文字可フラグ true:可
   * @return 数値文字列：true、数値以外が含まれている：false
   */
  public static boolean isNumStr3(String arg, boolean nullFlg, boolean voidFlg) {

    if (arg == null) {
      return nullFlg;
    }

    if ("".equals(arg)) {
      return voidFlg;
    }

    String reg = "^-?[0-9]+$";
    Pattern pattern = Pattern.compile(reg);
    Matcher matcher = pattern.matcher(arg);
    return matcher.matches();
  }

  /**
   * 前ゼロ埋めの数値を取得する
   *
   * @param size
   *          桁数
   * @param arg
   *          対象文字列
   * @return
   */
  public static String numericFomat(int size, int arg) throws Exception {

    return String.format("%0" + size + "d", arg);
  }

  /**
   * 半角英数文字列か確認<br>
   * 正の整数のみ
   *
   * @param arg
   *          確認したい文字列
   * @param nullFlg
   *          nullフラグ true:可
   * @param voidFlg
   *          空文字可フラグ true:可
   * @return 半角英数文字列：true、半角英数以外が含まれている：false
   */
  public static boolean isAlphaNumStr(String arg, boolean nullFlg, boolean voidFlg) {

    if (arg == null) {
      return nullFlg;
    }

    if ("".equals(arg)) {
      return voidFlg;
    }

    String reg = "^[a-zA-Z0-9]+$";
    Pattern pattern = Pattern.compile(reg);
    Matcher matcher = pattern.matcher(arg);
    return matcher.matches();
  }

  /**
   * 前後の全角スペースを削除します
   *
   * @param str
   * @return
   * @throws Exception
   */
  public static String zenkakuTrim(String str) throws Exception {

    if (str == null) {
      return "";
    }

    return str.replaceAll("^[\\s　]*", "").replaceAll("[\\s　]*$", "");
  }

  /**
   * ダブルクォート
   *
   * @param obj
   * @return
   * @throws Exception
   */
  public static String wCoat(Object obj) throws Exception {
    String str = "";

    if (obj == null) {
      str = "";
    } else if (obj.toString().equals("")) {
      str = "";
    } else {
      String retStr = obj.toString().replaceAll("\"", "\"\"");
      retStr = retStr.replaceAll("\r", "");
      // retStr = retStr.replaceAll("\n", "\\\\n");
      retStr = retStr.replaceAll("\n", "§");
      str = ("\"") + retStr + ("\"");
    }
    return str;
  }

    /**
     * カンマ区切りにします。また
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static String comma(Object obj) throws Exception {
        String str = "";

        if (obj == null) {
            str = "";
        } else if (obj.toString().equals("")) {
            str = "";
        } else {
            try {
            NumberFormat nfNum = NumberFormat.getNumberInstance();
            str = nfNum.format(StringUtil.dataToLong(obj));
            } catch (Exception e) {
                str = obj.toString();
            }
        }
        return str;
    }

  /**
   * 数字のみ全角から半角に変換
   *
   * @param str
   *          変換したい文字列
   * @return 変換後の値
   */
  public static String numberZenToHan(String str) throws Exception {
    return str.replaceAll("０", "0").replaceAll("１", "1").replaceAll("２", "2").replaceAll("３", "3").replaceAll("４", "4").replaceAll("５", "5")
            .replaceAll("６", "6").replaceAll("７", "7").replaceAll("８", "8").replaceAll("９", "9");
  }

  /**
   * @param str
   *          変換したい文字列
   * @param delimiter
   *          区切り文字
   * @return List
   */
  public static List<String> stringToList(String str, String delimiter) throws Exception {
    str = emptyCharToNull(str);
    delimiter = emptyCharToNull(delimiter);
    if (str == null || delimiter == null) {
      return null;
    }

    return Arrays.asList(str.split(delimiter));
  }

  /**
   * String⇒int変換
   * nullの場合は、0でretrn
   *
   * @param str
   * @return
   */
  public static int stringToInt(String str) {
    try {
      if (str == null) {
        return 0;
      }
      return Integer.valueOf(str);
    } catch (Exception e) {
      return 0;
    }
  }

  public static String trim(String str) {
    try {
      str = str.trim();
    } catch (Exception e) {
      str = null;
    }
    return str;
  }

  /**
   * ランダム文字列生成
   *
   * @param length
   *          桁数
   * @param baseStr
   *          ベース文字列
   * @return
   */
  public static String make_random_str(int length, String baseStr) {
    // 指定がない場合に使用
    String def = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    if (baseStr == null || "".equals(baseStr)) {
      baseStr = def;
    }
    return RandomStringUtils.random(length, baseStr);
  }

  /**
   * パスワード生成
   *
   * @param length
   *          桁数
   * @param baseStr
   *          ベース文字列
   * @return
   */
  public static String make_password(int length) {
    // 英数（iolIO01除外）、一部記号
    String base = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    return make_random_str(length, base);
  }

  public static String convertCode_SEX(String sexCode) {
    return StringUtil.convertCodeMap_SEX.get(sexCode);
  }

  /**
   * メールのタイトル・本文にシングルコートがあった場合は、シングルコートでエスケープする。
   * 例 ) don't ⇒ don''t
   *
   * @param str
   * @return
   */
  public static String escapeSingleQuotation(String str) {
    if (str != null) {
      str = str.replaceAll("\'", "\'\'");
    }
    return str;
  }

  public static String convNumberFormat(BigDecimal value) {
    if (value != null) {
      return NumberFormat.getNumberInstance().format(value.doubleValue());
    } else {
      return "";
    }
  }

  /**
   * LPADを行います。
   * 文字列[str]の左に指定した文字列[addStr]を[len]に
   * 満たすまで挿入します。
   *
   * @param str
   *          対象文字列
   * @param len
   *          補充するまでの桁数（LPADを行った後のサイズを指定します。）
   * @param addStr
   *          挿入する文字列
   * @return 変換後の文字列。
   */
  public static String lpad(String str, int len, String addStr) {
    return fillString(str, "L", len, addStr);
  }

  /**
   * RPADを行います。
   * 文字列[str]の右に指定した文字列[addStr]を[len]に
   * 満たすまで挿入します。
   *
   * @param str
   *          対象文字列
   * @param len
   *          補充するまでの桁数（RPADを行った後のサイズを指定します。）
   * @param addStr
   *          挿入する文字列
   * @return 変換後の文字列。
   */
  public static String rpad(String str, int len, String addStr) {
    return fillString(str, "R", len, addStr);
  }

  /**
   * 文字列[str]に対して、補充する文字列[addStr]を
   * [position]の位置に[len]に満たすまで挿入します。
   *
   * ※[str]がnullや空リテラルの場合でも[addStr]を
   * [len]に満たすまで挿入した結果を返します。
   *
   * @param str
   *          対象文字列
   * @param position
   *          前に挿入 ⇒ L or l 後に挿入 ⇒ R or r
   * @param len
   *          補充するまでの桁数
   * @param addStr
   *          挿入する文字列
   * @return 変換後の文字列。
   */
  public static String fillString(String str, String position, int len, String addStr) {
    if (addStr == null || addStr.length() == 0) {
      throw new IllegalArgumentException("挿入する文字列の値が不正です。addStr=" + addStr);
    }
    if (str == null) {
      str = "";
    }
    StringBuffer buffer = new StringBuffer(str);
    while (len > buffer.length()) {
      if (position.equalsIgnoreCase("l")) {
        int sum = buffer.length() + addStr.length();
        if (sum > len) {
          addStr = addStr.substring(0, addStr.length() - (sum - len));
          buffer.insert(0, addStr);
        } else {
          buffer.insert(0, addStr);
        }
      } else {
        buffer.append(addStr);
      }
    }
    if (buffer.length() == len) {
      return buffer.toString();
    }
    return buffer.toString().substring(0, len);
  }


  public static long dateToLong(String str) {
      long longDate = 0;
      if (CommonUtil.chkNotNullEmpty(str)) {
          longDate = Long.parseLong(str.replaceAll(" ", "").replaceAll("/", "").replaceAll(":", "").replaceAll("-", ""));
      }
      return longDate;
  }
  
  
  public static String makeCsvRow(ArrayList<String> list, boolean isEnclosing) {

      StringBuffer sb = new StringBuffer();

      if (list != null) {

          for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {

              if (isEnclosing) {
                  sb.append("\"" + iterator.next() + "\"");
              } else {
                  sb.append(iterator.next());
              }

              if (iterator.hasNext()) {
                  sb.append(",");
              }
          }
      }

      return sb.toString() + "\n";
  }
  public static String makeCsvRowNoQuot(ArrayList<String> list, boolean isEnclosing) {

      StringBuffer sb = new StringBuffer();

      if (list != null) {

          for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {

              if (isEnclosing) {
                  sb.append(iterator.next());
              } else {
                  sb.append(iterator.next());
              }

              if (iterator.hasNext()) {
                  sb.append(",");
              }
          }
      }

      return sb.toString() + "\n";
  }
  
  
  /**
   * String⇒boolean変換
   * nullの場合は、0でretrn
   *
   * @param str
   * @return
   */
  public static boolean stringToBool(String str) {
    try {
      if (str == null) {
        return false;
      } else if ("1".equals(str)) {
        return true;
      } 
      return false;
    } catch (Exception e) {
      return false;
    }
  }
  
  /**
   * 全角文字
   * 
   *
   * @param arg
   *          確認したい文字列
   * @param nullFlg
   *          nullフラグ true:可
   * @param voidFlg
   *          空文字可フラグ true:可
   * @return 全角文字のみ（数字を含まない）：true、半角文字が含まれている：false
   */
  public static boolean isZenStr(String arg, boolean nullFlg, boolean voidFlg) {

      if (arg == null) {
          return nullFlg;
      }

      if ("".equals(arg)) {
          return voidFlg;
      }

      // 全角文字のみを許可する正規表現（全角文字全般を許可）
      String reg = "^[\\uFF00-\\uFFFF]+$";
      Pattern pattern = Pattern.compile(reg);
      Matcher matcher = pattern.matcher(arg);
      return matcher.matches();
  }
  
  /**
   * 全角文字（英字、記号、ひらがな、カタカナなど）を許可し、数字（全角、半角）は拒否
   * 
   *
   * @param arg
   *          確認したい文字列
   * @param nullFlg
   *          nullフラグ true:可
   * @param voidFlg
   *          空文字可フラグ true:可
   * @return 全角文字のみ（数字を含まない）：true、数字が含まれている：false
   */
  public static boolean isNoNumZenStr(String arg, boolean nullFlg, boolean voidFlg) {

      if (arg == null) {
          return nullFlg;
      }

      if ("".equals(arg)) {
          return voidFlg;
      }

      // 全角文字で数字を除外した正規表現
      String reg = "^[^０-９]+$";
      Pattern pattern = Pattern.compile(reg);
      Matcher matcher = pattern.matcher(arg);
      return matcher.matches();
  }
  
  /**
   * 半角文字のみか確認
   * 半角の英字、数字、記号などを許可し、全角文字は拒否
   *
   * @param arg
   *          確認したい文字列
   * @param nullFlg
   *          nullフラグ true:可
   * @param voidFlg
   *          空文字可フラグ true:可
   * @return 半角文字のみ（全角文字を含まない）：true、全角文字が含まれている：false
   */
  public static boolean isHanStr(String arg, boolean nullFlg, boolean voidFlg) {

      if (arg == null) {
          return nullFlg;
      }

      if ("".equals(arg)) {
          return voidFlg;
      }

      // 半角文字のみを許可する正規表現
      String reg = "^[\\x20-\\x7E]+$"; 
      Pattern pattern = Pattern.compile(reg);
      Matcher matcher = pattern.matcher(arg);
      return matcher.matches();
  }
  
  /**
   * 半角カナ、記号文字列か確認<br>
   * 
   *
   * @param arg
   *          確認したい文字列
   * @param nullFlg
   *          nullフラグ true:可
   * @param voidFlg
   *          空文字可フラグ true:可
   * @return 半角カナ、記号文字列：true、半角カナ、記号以外が含まれている：false
   */
  public static boolean isKanaKigoHanStr(String arg, boolean nullFlg, boolean voidFlg) {

      if (arg == null) {
        return nullFlg;
      }

      if ("".equals(arg)) {
        return voidFlg;
      }

      // 半角カナと記号に対応する正規表現
      String reg = "^[\uFF65-\uFF9F!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~]+$";
      Pattern pattern = Pattern.compile(reg);
      Matcher matcher = pattern.matcher(arg);
      return matcher.matches();
  }
  
  /**
   * 半角カナ文字列か確認
   * 半角カタカナのみを許可する
   *
   * @param arg
   *          確認したい文字列
   * @param nullFlg
   *          nullフラグ true:可
   * @param voidFlg
   *          空文字可フラグ true:可
   * @return 半角カナ文字列のみ：true、半角カナ以外が含まれている：false
   */
  public static boolean isKanaHanStr(String arg, boolean nullFlg, boolean voidFlg) {

      if (arg == null) {
          return nullFlg;
      }

      if ("".equals(arg)) {
          return voidFlg;
      }

      // 半角カナ文字のみを許可する正規表現
      String reg = "^[\uFF65-\uFF9F]+$";  // 半角カタカナ範囲のみ
      Pattern pattern = Pattern.compile(reg);
      Matcher matcher = pattern.matcher(arg);
      return matcher.matches();
  }

}
