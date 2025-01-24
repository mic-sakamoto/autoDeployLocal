package service;

import java.util.ArrayList;
import java.util.HashMap;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.AnnouncementMgmtDao;
import db.dao.CommonDao;
import model.AnnouncementMgmtModel;
import utils.log4j.LogUtil;

public class AnnouncementMgmtService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    AnnouncementMgmtDao dao = new AnnouncementMgmtDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(AnnouncementMgmtModel model) throws Exception {

        return true;

    }

    /**
     * お知らせ管理情報取得
     */
    public boolean getSearchList(AnnouncementMgmtModel model) throws Exception {
        
        try {     
            if(model.getUserId().equals("")){
                model.setSuccessFlg("9");
                model.setMessage("ユーザ情報がありません");
                return false;
            }   

            // データ取得
            ArrayList<HashMap<String, Object>> announcementInfoList = dao.getSearchList(model);    
            model.setResult(announcementInfoList);
            model.setSuccessFlg("1");

            return true;
            
        } catch (Exception e) {
            LogUtil.info(e);
            e.printStackTrace();
            model.setSuccessFlg("9");
            return false;
        }
        
    }

}