package db.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import model.MccsMgmtModel;
import utils.CommonUtil;
import utils.StringUtil;


public class MccsMgmtDao extends AccessEndPoint {

    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }
    
    // 一覧テーブル
    public ArrayList<HashMap<String, Object>> getSearchList(MccsMgmtModel model) throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      Contract.CustomerId     ");
        query.append("      ,UserInfo.UserName    ");
        query.append("      ,Contract.MccsStatusType  ");
        query.append("      ,Contract.WebAppId  ");
        query.append("      ,DATE_FORMAT(Contract.MccsAttachtDate, '%Y/%m/%d') AS MccsAttachtDate  ");
        query.append("      ,DATE_FORMAT(Contract.MainAppDate, '%Y/%m/%d') AS MainAppDate  ");
        query.append("      ,DATE_FORMAT(Contract.VerifiedDate, '%Y/%m/%d') AS VerifiedDate  ");
        query.append("      ,DATE_FORMAT(Contract.UpdateTime, '%Y/%m/%d') AS UpdateDate  ");
        query.append("  FROM     ");
        query.append("      Contract       ");
        query.append("  LEFT JOIN     ");
        query.append("      UserInfo ON Contract.CustomerId = UserInfo.UserId      ");
        
        query.append("  WHERE 1 = 1     ");
        
        if(CommonUtil.chkNotNullEmpty(model.getConditionCustomerId())) {
            query.append("  AND Contract.CustomerId = ?   "); args.add(model.getConditionCustomerId());
        }
        
        if(CommonUtil.chkNotNullEmpty(model.getConditionUserName())) {
            query.append("  AND UserInfo.UserName like ?   ");
            args.add('%' + StringUtil.dataToString(model.getConditionUserName()) + '%');
        }
        
        if(CommonUtil.chkNotNullEmpty(model.getConditionMccsStatusType())) {
            query.append("  AND Contract.MccsStatusType = ?   "); args.add(model.getConditionMccsStatusType());
        }
        
        if(CommonUtil.chkNotNullEmpty(model.getConditionWebAppId())) {
            query.append("  AND Contract.WebAppId = ?   "); args.add(model.getConditionWebAppId());
        }
        
        if(CommonUtil.chkNotNullEmpty(model.getConditionMccsAttachtDateFrom())) {
            query.append("  AND Contract.MccsAttachtDate >= ?   "); args.add(model.getConditionMccsAttachtDateFrom()+" 00:00:00");
        }
        
        if(CommonUtil.chkNotNullEmpty(model.getConditionMccsAttachtDateTo())) {
            query.append("  AND Contract.MccsAttachtDate <= ?   "); args.add(model.getConditionMccsAttachtDateTo()+" 23:59:59");
        }
        
        if(CommonUtil.chkNotNullEmpty(model.getConditionMainAppDateFrom())) {
            query.append("  AND Contract.MainAppDate >= ?   "); args.add(model.getConditionMainAppDateFrom()+" 00:00:00");
        }
        
        if(CommonUtil.chkNotNullEmpty(model.getConditionMainAppDateTo())) {
            query.append("  AND Contract.MainAppDate <= ?   "); args.add(model.getConditionMainAppDateTo()+" 23:59:59");
        }
        
        if(CommonUtil.chkNotNullEmpty(model.getConditionVerifiedDateFrom())) {
            query.append("  AND Contract.VerifiedDate >= ?   "); args.add(model.getConditionVerifiedDateFrom()+" 00:00:00");
        }
        
        if(CommonUtil.chkNotNullEmpty(model.getConditionVerifiedDateTo())) {
            query.append("  AND Contract.VerifiedDate <= ?   "); args.add(model.getConditionVerifiedDateTo()+" 23:59:59");
        }
        
        if(CommonUtil.chkNotNullEmpty(model.getConditionUpdateDateFrom())) {
            query.append("  AND Contract.UpdateTime >= ?   "); args.add(model.getConditionUpdateDateFrom()+" 00:00:00");
        }
        
        if(CommonUtil.chkNotNullEmpty(model.getConditionUpdateDateTo())) {
            query.append("  AND Contract.UpdateTime <= ?   "); args.add(model.getConditionUpdateDateTo()+" 23:59:59");
        }
        
        query.append(" ORDER BY Contract.UpdateTime DESC  ");

        String sql = query.toString();

        return reader.selectProc(sql, args);
    }


}