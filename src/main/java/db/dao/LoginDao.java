package db.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class LoginDao extends AccessEndPoint {

    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }

    /**
     * 引数loginIdに紐づくUserInfoデータを取得する
     * @param loginId ログインID
     * @return
     * @throws Exception
     */
    public ArrayList<HashMap<String, Object>> getUserInfo(String loginId) throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT * ");
        query.append("  FROM     ");
        query.append("      UserInfo        ");
        query.append("  WHERE UserId = ?    ");
        query.append("  AND IsDelete = 0    ");
        args.add(loginId);

        String sql = query.toString();

        return reader.selectProc(sql, args);
    }

    /**
     * ログインユーザーの最終ログイン時間を更新する
     * @param userId ログインID
     * @return
     * @throws Exception
     */
    public int updateLastLogintime(String userId) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append(" UPDATE ");
        query.append("     UserInfo ");
        query.append(" SET ");
        query.append("     LastLoginTime = now() ");
        query.append(" WHERE ");
        query.append("     UserId = ? ");args.add(userId);

        String sql = query.toString();

        return writer.updateProc(sql, args);
    }

    
    /**
     * スタッフの拠点IP取得
     */
    public ArrayList<HashMap<String, Object>> getStaffBaseSubnet() throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT * ");
        query.append("  FROM     ");
        query.append("      StaffBaseSubnet        ");
        query.append("  WHERE IsDelete = 0    ");

        String sql = query.toString();

        return reader.selectProc(sql, args);
    }
}