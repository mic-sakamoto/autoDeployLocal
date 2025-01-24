package service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.PasswordSetDao;
import model.PasswordSetModel;
import utils.log4j.LogUtil;

public class PasswordSetService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    PasswordSetDao dao = new PasswordSetDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(PasswordSetModel model) throws Exception {

        return true;

    }

    /**
     * ユーザIDから以前に利用規約を同意したか（パスワード設定がされているか）確認
     */
    public boolean getUserInfo(PasswordSetModel model) throws Exception {
        
        if(model.getUserId().equals("")){
            model.setSuccessFlg("9");
            model.setMessage("ユーザ情報がありません");
            return false;
        }

        // UserInfoデータ取得
        ArrayList<HashMap<String, Object>> passwordSetList = dao.getPasswordSetHistory(model.getUserId());  
        if (passwordSetList != null) {
            if(passwordSetList.get(0).get("Password") != null) {
                model.setAgreedFlg("1");
            }else {
                model.setAgreedFlg("0");
            }
        } else {
            model.setSuccessFlg("9");
            model.setMessage("ユーザ情報がありません");
            return false;
        }
        return true;
    }
    
    /**
     * パスワード更新
     */
    public boolean updatePassword(PasswordSetModel model) throws Exception {
        
        Connection con = dao.getCon();
        con.setAutoCommit(false);
        
        try {
            // UserInfoデータ取得
            ArrayList<HashMap<String, Object>> passwordSetList = dao.getPasswordSetHistory(model.getUserId());  
            if (passwordSetList == null || passwordSetList.size() == 0) {
                model.setSuccessFlg("9");
                model.setMessage("ユーザ情報がありません");
                return false;
            }
            
            // 入力チェック
            if (!isValidPassword(model.getPassword())) {
                model.setSuccessFlg("9");
                model.setMessage("パスワードは半角数字・記号・英字小文字・英字大文字をそれぞれ1つ以上含んでください。");
                return false;
            }
            
            // パスワード更新
            dao.updatePassword(model, comSvc.getPasswordHash(model.getPassword(), as), con);
                       
            model.setSuccessFlg("1");
            model.setMessage("更新しました。");
            con.commit();

        } catch (Exception e) {
            con.rollback();
            LogUtil.info(e);
            e.printStackTrace();
            model.setSuccessFlg("9");
            model.setMessage("ログイン失敗");
            return false;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
        
        return true;
    }
    
    
    /**
     * パスワードの入力チェックを行う
     * 条件: 半角数字・記号・英字小文字・英字大文字をそれぞれ1つ以上含む
     */
    private boolean isValidPassword(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[!@#\\$%^&*])(?=.*[a-z])(?=.*[A-Z]).+$";
        return password != null && password.matches(pattern);
    }

}