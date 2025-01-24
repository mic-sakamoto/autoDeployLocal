package db.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import model.TopModel;

public class TopDao extends AccessEndPoint {

    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }

    /**
     * お知らせ情報取得
     * 
     */
    public ArrayList<HashMap<String, Object>> getAnnouncementInfo(TopModel model) throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      DATE_FORMAT(OpenDate, '%Y/%m/%d')  as OpenDate,  ");
        query.append("      Body  ");
        query.append("  FROM     ");
        query.append("      Announcement     ");
        query.append("  WHERE IsStop = 0  "); // 公開中止フラグ
        query.append("  AND IsDelete = 0  "); // 削除フラグ
        
        if(model.getRoleType().equals("2")) {
            query.append("  AND IsTargetStore = 1    "); // 加盟店でログインした場合
        }else if(model.getRoleType().equals("3")){
            query.append("  AND IsTargetCustomer = 1    "); // 契約者でログインした場合
        }
        
        query.append("  AND now() >= OpenDate  "); // 公開開始日
        query.append("  AND now() <= CloseDate  "); // 公開停止日
        query.append("  ORDER BY OpenDate  ");
                        
        String sql = query.toString();

        return reader.selectProc(sql, args);
    }
    
    
    /**
     * 通信設定情報取得
     * 
     */
    public ArrayList<HashMap<String, Object>> getSettingInfo(String userId) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      IsNotifyScreeningResult     ");
        query.append("      ,IsNotifyMainResult     ");
        query.append("      ,NotifyKbn     ");
        query.append("      ,TelNumber     ");
        query.append("      ,MailAddress     ");
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
     * 通信設定更新
     */
    public int updateSetting(TopModel model, Connection con) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append(" UPDATE ");
        query.append("     UserInfo ");
        query.append(" SET ");
        query.append("     IsNotifyScreeningResult = ? ");  args.add(model.getNotifyScreeningResult());
        query.append("     ,IsNotifyMainResult = ? ");      args.add(model.getNotifyMainResult());
        query.append("     ,NotifyKbn = ? ");               args.add(model.getNotifyKbn());
        if(model.getNotifyKbn().equals("1")) {
            query.append("     ,TelNumber = ? ");               args.add(model.getTelNumber());
        }else {
            query.append("     ,MailAddress = ? ");             args.add(model.getMailAddress());
        }     
        query.append("     ,UpdateTime = now()  ");
        query.append("     ,UpdateUser = ?  "); args.add(model.getUserId());
        query.append(" WHERE ");
        query.append("     UserId = ? ");  args.add(model.getUserId());
        query.append(" AND ");
        query.append("     IsDelete = 0 ");

        String sql = query.toString();

        return writer.updateProc(sql, args, con);
    }

}