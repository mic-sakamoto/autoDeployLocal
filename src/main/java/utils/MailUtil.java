package utils;

import java.util.HashMap;

public class MailUtil {

    private String CLASSNAME = this.getClass().getName();

    public static String createMessage(String messageType, String sendType, HashMap<String, String> args) {
        String body = "";

        switch (messageType) {
            case "passwordNotify":// パスワード通知設定
                String onetimePassword = args.get("onetimepassword");
                if (sendType.equals("sms")) {
                    body += "<html>";
                    body += "USS-SSオートローンのお取引を賜り、ありがとうございます。<br><br>";
                    body += "<br>";
                    body += "<br>";
                    body += "以下のURLにアクセスし、専用WEBサイトご利用のためのパスワード設定をお願いします。";
                    body += "<br>";
                    body += "https://uss-ss-al.net/password-set?AuthCode=" + onetimePassword;
                    body += "<br>";
                    body += "※この通知にお心当たりのない場合は、破棄をお願いいたします。";
                    body += "</html>";
                } else {
                    body = "以下のURLにてUSS-SSオートローン専用WEBサイトのログインパスワード設定をお願いします。";
                    body += "https://uss-ss-al.net/password-set?AuthCode=" + onetimePassword;
                }
                break;
        }

        return body;
    }

}
