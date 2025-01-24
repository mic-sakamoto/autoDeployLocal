package service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.StaffMgmtDao;
import model.StaffMgmtModel;
import utils.StringUtil;
import utils.log4j.LogUtil;

public class StaffMgmtService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    StaffMgmtDao dao = new StaffMgmtDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(StaffMgmtModel model) throws Exception {

        return true;

    }

    public boolean newRegistration(StaffMgmtModel model) throws Exception {

        Connection con = dao.getCon();
        con.setAutoCommit(false);
        try {
            dao.insertNewRetration(model.getNewuserId(), comSvc.getPasswordHash(model.getNewpassword(), as), model.getNewisAdmin());

        } catch (Exception e) {
            con.rollback();
            LogUtil.info(e);
            e.printStackTrace();
            model.setSuccessFlg("9");
            model.setMessage("新規登録失敗");
            return false;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }

        return true;
    }

    /**
     * リスト生成
     */
    public boolean getSearchList(StaffMgmtModel model) throws Exception {

        ArrayList<HashMap<String, Object>> searchList = dao.getSearchList();
        
        if (searchList != null) {
            for (HashMap<String, Object> map : searchList) {
                String isAdmin = StringUtil.dataToString(map.get("IsAdmin"));
                if ("1".equals(isAdmin)) {
                    map.put("AdminType", "システム管理者");
                } else {
                    map.put("AdminType", "スタッフ");
                }
            }
        }
        
//        searchList.addAll(searchList);
//        searchList.addAll(searchList);
//        searchList.addAll(searchList);
//        searchList.addAll(searchList);
//        searchList.addAll(searchList);
        
        model.setSearchList(searchList);
        return true;
    }

    /**
     * パスワード変更
     */
    public boolean changePassword(StaffMgmtModel model) throws Exception {
        
        Connection con = dao.getCon();
        con.setAutoCommit(false);
        try {
        dao.changePassword(model.getListUserId(), comSvc.getPasswordHash(model.getUpdatePassword(), as));
    } catch (Exception e) {
        con.rollback();
        LogUtil.info(e);
        e.printStackTrace();
        model.setSuccessFlg("9");
        model.setMessage("パスワード変更失敗");
        return false;
    } finally {
        con.setAutoCommit(true);
        con.close();
    }

        return true;
    }

    /**
     * アカウント削除
     */
    public boolean deleteAccount(StaffMgmtModel model) throws Exception {
        
        Connection con = dao.getCon();
        con.setAutoCommit(false);
        try {
        dao.deleteAccount(model.getListUserId());
    } catch (Exception e) {
        con.rollback();
        LogUtil.info(e);
        e.printStackTrace();
        model.setSuccessFlg("9");
        model.setMessage("アカウント削除失敗");
        return false;
    } finally {
        con.setAutoCommit(true);
        con.close();
    }

        return true;
    }

}
