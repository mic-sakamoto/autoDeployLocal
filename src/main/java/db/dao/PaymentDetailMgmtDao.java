package db.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import model.PaymentDetailMgmtModel;
import utils.CommonUtil;


public class PaymentDetailMgmtDao extends AccessEndPoint {

    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }
    
    // 一覧テーブル
    public ArrayList<HashMap<String, Object>> getSearchList(PaymentDetailMgmtModel model) throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      Seq     ");
        query.append("      ,UserId    ");
        query.append("      ,DATE_FORMAT(PaymentDate, '%Y/%m/%d') AS PaymentDate     ");
        query.append("      ,PdfUrl  ");
        query.append("  FROM     ");
        query.append("      PaymentDetailInfo       ");
        query.append("  WHERE IsDelete = 0     ");
        query.append("  AND UserId = ?   "); args.add(model.getUserId());
        
        if(CommonUtil.chkNotNullEmpty(model.getConditionPaymentDateFrom())) {
            query.append("  AND PaymentDate >= ?   "); args.add(model.getConditionPaymentDateFrom());
        }
        
        if(CommonUtil.chkNotNullEmpty(model.getConditionPaymentDateTo())) {
            query.append("  AND PaymentDate <= ?   "); args.add(model.getConditionPaymentDateTo());
        }
        
        query.append(" ORDER BY PaymentDate DESC   ");

        String sql = query.toString();

        return reader.selectProc(sql, args);
    }


}