package db.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class StaffMgmtDao extends AccessEndPoint {

    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }

    // 一覧テーブル

    public ArrayList<HashMap<String, Object>> getSearchList() throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      UserId     ");
        query.append("      ,UserName    ");
        query.append("      ,IsAdmin     ");
        query.append("      ,LastLoginTime     ");
        query.append("  FROM     ");
        query.append("      UserInfo       ");
        query.append("  WHERE     ");
        query.append("      IsDelete = 0   ");
        query.append("  AND RoleKbn = 1   ");
        // if(model.getSearchUserID != null) {
        // query.append(" AND UserId in (?) ");args.add(model.getSearchUserID);
        // }

        String sql = query.toString();

        return reader.selectProc(sql, args);
    }

    public int insertNewRetration(String userId, String password, Integer isAdmin) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  INSERT INTO ");
        query.append("  UserInfo    ");
        query.append("  (UserId,    ");
        query.append("  Password,   ");
        query.append("  IsAdmin,    ");
        query.append("  CreateUser) ");
        query.append("  VALUES      ");
        query.append("  ( ? ,       ");
        query.append("  ? ,         ");
        query.append("     ?,       ");
        query.append("  'HANAZAWA');   ");
        args.add(userId);
        args.add(password);
        args.add(isAdmin);

        String sql = query.toString();

        return writer.updateProc(sql, args);
    }

    public int changePassword(String listUserId, String updatePassword) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  UPDATE ");
        query.append("  UserInfo    ");
        query.append("  SET    ");
        query.append("  Password = ?,   ");
        query.append("  UpdateTime = now()     ");
        query.append("  Where ");
        query.append("  UserId = ?      ");

        args.add(updatePassword);
        args.add(listUserId);

        String sql = query.toString();

        return writer.updateProc(sql, args);
    }

    public int deleteAccount(String listUserId) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  UPDATE ");
        query.append("  UserInfo    ");
        query.append("  SET    ");
        query.append("  IsDelete = 1,   ");
        query.append("  UpdateTime = now()     ");
        query.append("  Where ");
        query.append("  UserId = ?      ");

        args.add(listUserId);

        String sql = query.toString();

        return writer.updateProc(sql, args);
    }

}
