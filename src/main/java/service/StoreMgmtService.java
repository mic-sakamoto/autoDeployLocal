package service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.StoreMgmtDao;
import model.StoreMgmtModel;
import utils.StringUtil;

public class StoreMgmtService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    StoreMgmtDao dao = new StoreMgmtDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(StoreMgmtModel model) throws Exception {
        return true;
    }
    
    /**
     * リスト生成
     */
    public boolean getSearchList(StoreMgmtModel model) throws Exception {

        // 契約データ取得
        ArrayList<HashMap<String, Object>> searchList = dao.getSearchList(model);
        
//        ArrayList<HashMap<String, Object>> searchList = new ArrayList<HashMap<String, Object>>();
//        
//        // 開発中
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("UserId", "1234567890");
//        map.put("UserName", "株式会社ユーエスオート西葛西支店");
//        map.put("MemberCode", "T4100");
//        map.put("TelNumber", "090-1111-2222");
//        map.put("MailAddress", "test123456789@test.co.jp");
//        map.put("CreateTime", "2024/12/01");
//        map.put("LastLoginTime", "2024/12/12");
//        searchList.add(map);
//        searchList.add(map);
//        searchList.add(map);
//        searchList.add(map);
//        searchList.add(map);
//        searchList.add(map);
//        searchList.add(map);
//        searchList.add(map);
//        searchList.add(map);
//        searchList.add(map);
        
        if (searchList != null) {

//            for (HashMap<String, Object> map : searchList) {
//            }
            
        } else {
            model.setSuccessFlg("9");
            model.setMessage("加盟店情報がありません");
            return false;
        }
        
        model.setSearchList(searchList);
        
        return true;
    }
    
    
    /**
     * 代行ログイン
     */
    public boolean proxyLogin(StoreMgmtModel model) throws Exception {

        
        HttpSession session = ServletActionContext.getRequest().getSession();
        
        String staff_id = StringUtil.dataToString(session.getAttribute("user_id"));
        String staff_name = StringUtil.dataToString(session.getAttribute("user_name"));
        
        // スタッフ情報を別IDで保持
        session.setAttribute("staff_id", staff_id);
        session.setAttribute("staff_name", staff_name);
        session.setAttribute("role_type", "2");
        session.setAttribute("proxy_flg", "1");
        
        ArrayList<HashMap<String, Object>> userList = dao.getUserInfo(model.getProxyLoginUserId());
        if (userList != null) {
            session.setAttribute("user_id", model.getProxyLoginUserId());
            session.setAttribute("user_name", StringUtil.dataToString(userList.get(0).get("UserName")));
        } else {
            model.setSuccessFlg("9");
            model.setMessage("代行ログインに失敗しました");
            return false;
        }
        
        
        return true;
    }
}