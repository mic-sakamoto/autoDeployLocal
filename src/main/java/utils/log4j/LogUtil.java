package utils.log4j;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ---------------------------------------------------------------------------*
 * Project Title: 04S.MIC.0189.0
 * File Name: LogUtil.java
 * Description: this class is used to log action from the system
 * ---------------------------------------------------------------------------*
 */

public class LogUtil extends ActionSupport {
  public static String APP_NAME = "uss-ss-al-web";
  /** Define default layout */

  /**
   * -------------------------------------------------------------------------
   * Description: preprocess before use
   * -------------------------------------------------------------------------
   */
  static {

    // VMオプションに設定したパスを取得
  }


  public static void info(String sGroup, Object oMsg) {
    LogManager.getLogger().info(oMsg);
  }

  public static void info(Object oMsg) {
    HttpSession session = ServletActionContext.getRequest().getSession(true);

    LogManager.getLogger(APP_NAME).info((String)session.getAttribute("userId") + ":" + (String)session.getAttribute("tempoCd") + ":" + oMsg);
  }

  // --------------------------------------------------------------------------
  public static void debug(String sGroup, Object oMsg) {
    LogManager.getLogger(sGroup).debug(oMsg);
  }

  public static void debug(Object oMsg) {
    LogManager.getLogger(APP_NAME).debug(oMsg);
  }

  // --------------------------------------------------------------------------
  public static void warn(String sGroup, Object oMsg) {
    LogManager.getLogger(sGroup).warn(oMsg);
  }

  public static void warn(Object oMsg) {
    LogManager.getLogger(APP_NAME).warn(oMsg);
  }

  /**
   * Object oMsgに「Exception」オブジェクトを設定すると、stacTraceがログに出力されます。
   *
   * @param sGroup
   * @param oMsg
   */
  public static void error(String sGroup, Object oMsg) {
    if (oMsg instanceof Exception) {
      LogManager.getLogger(sGroup).error(getStackTrace((Exception)oMsg));
    } else {
      LogManager.getLogger(sGroup).error(oMsg);
    }
  }

  /**
   * Object oMsgに「Exception」オブジェクトを設定すると、stacTraceがログに出力されます。
   *
   * @param oMsg
   */
  public static void error(Object oMsg) {
    if (oMsg instanceof Exception) {
      LogManager.getLogger(APP_NAME).error(getStackTrace((Exception)oMsg));
    } else {
      LogManager.getLogger(APP_NAME).error(oMsg);
    }
  }

  // --------------------------------------------------------------------------
  /**
   * Object oMsgに「Exception」オブジェクトを設定すると、stacTraceがログに出力されます。
   *
   * @param sGroup
   * @param oMsg
   */
  public static void fatal(String sGroup, Object oMsg) {
    LogManager.getLogger(sGroup).fatal(oMsg);
  }

  /**
   * Object oMsgに「Exception」オブジェクトを設定すると、stacTraceがログに出力されます。
   *
   * @param oMsg
   */
  public static void fatal(Object oMsg) {
    LogManager.getLogger(APP_NAME).fatal(oMsg);
  }

  public static String getStackTrace(Exception e) {
    ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
    PrintStream print = new PrintStream(byteArr);
    e.printStackTrace(print);
    return byteArr.toString();
  }

}
