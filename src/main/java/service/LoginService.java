package service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.LoginDao;
import model.LoginModel;
import utils.StringUtil;

public class LoginService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    LoginDao dao = new LoginDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(LoginModel model) throws Exception {

        return true;

    }

    /**
     * ログイン処理
     * ログインチェックし、OKならユーザー情報をセッションへ格納
     */
    public boolean login(LoginModel model) throws Exception {

        boolean result = false;

        try {

            // ユーザー情報取得
            ArrayList<HashMap<String, Object>> userList = dao.getUserInfo(model.getLoginId());
            if (userList != null) {
                // ログインフォームとログインユーザーが一致しているか(スタッフログインは判定しない)
                String dbRoleKbn = userList.get(0).get("RoleKbn").toString();
                if (!dbRoleKbn.equals("1")) {
                    if (!model.getLoginRole().equals(dbRoleKbn)) {
                        switch (model.getLoginRole()) {
                            case "2":
                                throw new Exception("加盟店様専用のログインフォームとなります。");
                            case "3":
                                throw new Exception("ご契約者様専用のログインフォームとなります。");
                            default:
                                throw new Exception("ユーザーIDまたはパスワードが正しくありません。");
                        }
                    }
                }
                // 入力されたパスワードをHash化し、DBのパスワードと比較
                String inputPasswodHash = comSvc.getPasswordHash(model.getPassword(), as);
                String dbPasswordHash = StringUtil.dataToString(userList.get(0).get("Password"));
                if (inputPasswodHash.equals(dbPasswordHash)) {
                    // ログイン成功
                    model.setSuccessFlg("1");
                    // 最終ログイン時間をDBに登録
                    dao.updateLastLogintime(model.getLoginId());
                } else {
                    // ログイン失敗
                    throw new Exception("ユーザーIDまたはパスワードが正しくありません。");
                }
                
                
                // スタッフの拠点IPチェック
                if (dbRoleKbn.equals("1")) {
                    // 拠点IPリスト取得
                    ArrayList<HashMap<String, Object>> staffBaseSubnetList = dao.getStaffBaseSubnet();
                    if (staffBaseSubnetList != null) {
                        // アクセスIP
                        HttpServletRequest request = ServletActionContext.getRequest();
                        String xForwardedFor = ((HttpServletRequest) request).getHeader("X-Forwarded-For");
                        String ipAddress = "";
                        if (xForwardedFor != null) { 
                            ipAddress = xForwardedFor;
                        } else {
                            ipAddress = request.getRemoteAddr();
                        }
                        
                        boolean ipCheck = false;
                        for (HashMap<String, Object> map : staffBaseSubnetList) {
                            String subnet = StringUtil.dataToString(map.get("Subnet"));
                            SubnetUtils subnetUtils = new SubnetUtils(subnet);
                            SubnetInfo subnetInfo = subnetUtils.getInfo();
                            
                            if (subnetInfo.isInRange(ipAddress)) {
                                ipCheck = true;
                                break;
                            }
                        }
                        if (!ipCheck) {
                            // 許可IP範囲外
                            throw new Exception("ログインが許可されていません");
                        }
                    }
                }
                
            } else {
                throw new Exception("ユーザーIDまたはパスワードが正しくありません。");
            }
            
            
            
            

            // sessionにuser_id,role保存
            String user_name = userList.get(0).get("UserName").toString();
            String role = userList.get(0).get("RoleKbn").toString();
            HttpSession session = ServletActionContext.getRequest().getSession();
            Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                session.removeAttribute(attributeName);
            }
            
            session.setAttribute("user_id", model.getLoginId());
            session.setAttribute("user_name", user_name);
            session.setAttribute("role_type", role);

            result = true;

        } catch (Exception e) {
            model.setSuccessFlg("9");
            model.setErrormessage(e.getMessage());
            result = false;
        }

        return result;
    }

    /**
     * ログアウト処理
     * セッション破棄
     */
    public boolean logout(LoginModel model) throws Exception {

        HttpSession session = ServletActionContext.getRequest().getSession();
        // セッション破棄
        session.invalidate();

        return true;
    }
    
    
    /**
     * 代行ログアウト処理
     * セッション入替
     */
    public boolean proxyLogout(LoginModel model) throws Exception {

        HttpSession session = ServletActionContext.getRequest().getSession();
        
        String staff_id = StringUtil.dataToString(session.getAttribute("staff_id"));
        String staff_name = StringUtil.dataToString(session.getAttribute("staff_name"));
        
        // スタッフ情報を別IDで保持
        session.setAttribute("staff_id", "");
        session.setAttribute("staff_name", "");
        session.setAttribute("role_type", "1");
        session.setAttribute("proxy_flg", "0");
        
        session.setAttribute("user_id", staff_id);
        session.setAttribute("user_name", staff_name);
        

        return true;
    }

}