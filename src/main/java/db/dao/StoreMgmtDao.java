package db.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import model.StoreMgmtModel;
import utils.CommonUtil;
import utils.StringUtil;


public class StoreMgmtDao extends AccessEndPoint {

    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }

    // 一覧テーブル
    public ArrayList<HashMap<String, Object>> getSearchList(StoreMgmtModel model) throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      UserId     ");
        query.append("      ,RoleKbn     ");
        query.append("      ,UserName     ");
        query.append("      ,TelNumber     ");
        query.append("      ,MailAddress     ");
        query.append("      ,DATE_FORMAT(CreateTime, '%Y/%m/%d') AS CreateTime     ");
        query.append("      ,DATE_FORMAT(LastLoginTime, '%Y/%m/%d') AS LastLoginTime     ");
        query.append("  FROM     ");
        query.append("      UserInfo        ");

        query.append("  WHERE RoleKbn = 2     ");

        if (CommonUtil.chkNotNullEmpty(model.getConditionStoreId())) {
            query.append("  AND UserId = ?     ");
            args.add(StringUtil.dataToString(model.getConditionStoreId()));
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionStoreName())) {
            query.append("  AND UserName like ?     ");
            args.add('%' + StringUtil.dataToString(model.getConditionStoreName()) + '%');
        }


        query.append("  ORDER BY      ");
        query.append("      UpdateTime DESC   ");

        String sql = query.toString();

        return reader.selectProc(sql, args);
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
}