package utils;

import java.io.InputStream;
import java.util.Properties;

import utils.log4j.LogUtil;

/**
 * マイページ 問合せ
 *
 * @author mic-kurokawa
 *
 */
public class MailContentsProperties {

    private String CLASSNAME = "";

    private static MailContentsProperties instance_ = new MailContentsProperties();

    protected MailContentsProperties() {
        CLASSNAME = this.getClass().getName();
        this.reload();
    }

    private static Properties rb;

    public void reload() {
        rb = new Properties();
        try {
            ClassLoader cloader = Thread.currentThread().getContextClassLoader(); // スレッドからクラスローダー取得

            InputStream stream = cloader.getResourceAsStream("mailContents.xml");
            rb.loadFromXML(stream);
            stream.close();
        } catch (Exception e) {
            LogUtil.error(e);
            LogUtil.fatal(CLASSNAME, "プロパティファイル読み込みエラー");

        }
    }

    // 会員登録時のメール
    public static final String TITLE() {
        return StringUtil.escapeSingleQuotation(rb.getProperty("TITLE"));
    }

    public static final String BODY() {
        return StringUtil.escapeSingleQuotation(rb.getProperty("BODY"));
    }

}
