package service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.TopDao;
import model.TopModel;
import utils.StringUtil;
import utils.log4j.LogUtil;

public class TopService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    TopDao dao = new TopDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(TopModel model) throws Exception {
        return true;
    }
    
    
    /**
     * 表示権限セット
     */
    public boolean getDisplayAuthority(TopModel model) throws Exception {

        // Announcementデータ取得
        try {
            if(model.getRoleType().equals("2") || model.getRoleType().equals("3")) {
                //加盟店または契約者のみ表示
                model.setDisplayAuthority("1");
            }else {
                model.setDisplayAuthority("0");
            }
            return true;
        } catch(Exception e) {
            model.setSuccessFlg("9");
            model.setMessage("エラー発生");
            return false;
        } finally {
            
        }
                 
    }
    
    
    /**
     * お知らせ情報取得
     */
    public boolean getAnnouncementInfo(TopModel model) throws Exception {

        // Announcementデータ取得
        try {
            ArrayList<HashMap<String, Object>> announcementInfoList = dao.getAnnouncementInfo(model);  
            if (announcementInfoList != null) {
                model.setResult(announcementInfoList);
                model.setSuccessFlg("1");
            }
            
            return true;
        } catch(Exception e) {
            model.setSuccessFlg("9");
            model.setMessage("エラー発生");
            return false;
        } finally {
            
        }
                 
    }
    
    
    /**
     * 通知設定情報取得
     */
    public boolean getUserInfo(TopModel model) throws Exception {
        
        if(model.getUserId().equals("")){
            model.setSuccessFlg("9");
            model.setMessage("ユーザ情報がありません");
            return false;
        }

        // UserInfoデータ取得
        ArrayList<HashMap<String, Object>> settingList = dao.getSettingInfo(model.getUserId());  
        if (settingList != null) {
            model.setNotifyScreeningResult(StringUtil.dataToString(settingList.get(0).get("IsNotifyScreeningResult")));
            model.setNotifyMainResult(StringUtil.dataToString(settingList.get(0).get("IsNotifyMainResult")));
            model.setNotifyKbn(StringUtil.dataToString(settingList.get(0).get("NotifyKbn")));
            model.setTelNumber(StringUtil.dataToString(settingList.get(0).get("TelNumber")));
            model.setMailAddress(StringUtil.dataToString(settingList.get(0).get("MailAddress")));
        } else {
            model.setSuccessFlg("9");
            model.setMessage("ユーザ情報がありません");
            return false;
        }
        return true;
    }
    
    
    /**
     * 通知設定更新
     */
    public boolean updateSetting(TopModel model) throws Exception {
        
        Connection con = dao.getCon();
        con.setAutoCommit(false);
        
        try {
            // UserInfoデータ取得
            ArrayList<HashMap<String, Object>> settingList = dao.getSettingInfo(model.getUserId());  
            if (settingList == null || settingList.size() == 0) {
                model.setSuccessFlg("9");
                model.setMessage("ユーザ情報がありません");
                return false;
            }
            
            // 入力チェック
            String errorMsg = "";
            
            if(model.getNotifyKbn().equals("1")) {
                //通知方法：SMS送信
                if(model.getTelNumber() == null || model.getTelNumber().equals("")) {
                    errorMsg = "携帯電話番号を入れてください。";
                }
                else if (!comSvc.check_telNumber(model.getTelNumber())) {
                    errorMsg = "携帯電話番号は半角数字のみで10桁または11桁を指定してください。";
                }
            }
            else {
                //通知方法：メール送信
                if(model.getMailAddress() == null || model.getMailAddress().equals("")) {
                    errorMsg = "メールアドレスを入れてください。";
                }
                else if (!comSvc.check_mailAddress(model.getMailAddress())) {
                    errorMsg = "メールアドレスは'＠'を含む半角英数字記号を指定してください。";
                }
            }

            
            if(!errorMsg.equals("")) {
                model.setSuccessFlg("9");
                model.setMessage(errorMsg);
                return false;
            }
            
            // パスワード更新 デモ版は変更処理不要
//            dao.updateSetting(model, con);
                       
            model.setSuccessFlg("1");
            model.setMessage("保存しました。");
            con.commit();

        } catch (Exception e) {
            con.rollback();
            LogUtil.info(e);
            e.printStackTrace();
            model.setSuccessFlg("9");
            model.setMessage("更新処理失敗");
            return false;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
        
        return true;
    }
    
}