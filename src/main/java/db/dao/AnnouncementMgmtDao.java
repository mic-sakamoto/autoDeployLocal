package db.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import model.AnnouncementMgmtModel;

public class AnnouncementMgmtDao extends AccessEndPoint {

    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }

    // 一覧テーブル

    public ArrayList<HashMap<String, Object>> getSearchList(AnnouncementMgmtModel model) throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      Seq     ");
        query.append("      ,IsTargetStore    ");
        query.append("      ,IsTargetCustomer     ");
        query.append("      ,CASE WHEN IsTargetStore = '1' AND IsTargetCustomer = '1' THEN '2' WHEN IsTargetStore = '1' THEN '0' WHEN IsTargetCustomer = '1' THEN '1' ELSE '' END AS IsTargetKbn  ");
        query.append("      ,DATE_FORMAT(OpenDate, '%Y-%m-%d') AS OpenDate     ");
        query.append("      ,DATE_FORMAT(OpenDate, '%H:%i') AS OpenTime     ");
        query.append("      ,DATE_FORMAT(CloseDate, '%Y-%m-%d') AS CloseDate     ");
        query.append("      ,DATE_FORMAT(CloseDate, '%H:%i') AS CloseTime     ");
        query.append("      ,CONCAT(DATE_FORMAT(OpenDate, '%Y/%m/%d/%H:%i'), ' ~ ', DATE_FORMAT(CloseDate, '%Y/%m/%d/%H:%i')) AS OpenCloseDate     ");
        query.append("      ,Body     ");
        query.append("  FROM     ");
        query.append("      Announcement       ");
        query.append("  WHERE     ");
        query.append("      IsDelete = 0   ");
        if(model.getSeq() != null) {
            query.append("  AND     ");
            query.append("      Seq = ?   "); args.add(model.getSeq());
        }

        String sql = query.toString();

        return reader.selectProc(sql, args);
    }

    
}
