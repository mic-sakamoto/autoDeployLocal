package db.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import model.PasswordSetModel;

public class PasswordSetDao extends AccessEndPoint {
    
    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }

    /**
     * セッションユーザが新規パスワード設定か確認
     * 
     */
    public ArrayList<HashMap<String, Object>> getPasswordSetHistory(String userId) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      UserId     ");
        query.append("      ,Password     ");
        query.append("  FROM     ");
        query.append("      UserInfo     ");
        query.append("  WHERE UserId = ?     ");
        query.append("  AND ");
        query.append("     IsDelete = 0 ");

        args.add(userId);

        String sql = query.toString();

        return reader.selectProc(sql, args);
    }
    
    
    /**
     * パスワード更新
     */
    public int updatePassword(PasswordSetModel model, String HashedPassword, Connection con) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append(" UPDATE ");
        query.append("     UserInfo ");
        query.append(" SET ");
        query.append("     Password = ? ");         args.add(HashedPassword);
        query.append("     ,UpdateTime = now()  ");
        query.append("     ,UpdateUser = ?  "); args.add(model.getUserId());
        query.append(" WHERE ");
        query.append("     UserId = ? ");  args.add(model.getUserId());
        query.append(" AND ");
        query.append("     IsDelete = 0 ");
        if (model.getUserId().length() == 7) {
            query.append(" AND ");
            query.append("     RoleKbn = 2 ");
        } else if (model.getUserId().length() == 9) {
            query.append(" AND ");
            query.append("     RoleKbn = 3 ");
        }

        String sql = query.toString();

        return writer.updateProc(sql, args, con);
    }

}