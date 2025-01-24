package db.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;


public class StatusDao extends AccessEndPoint {

    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }


 // 一覧テーブル
    public ArrayList<HashMap<String, Object>> getContract(String webAppId) throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      C.WebAppId     ");
        query.append("      ,C.CustomerId     ");
        query.append("      ,UICustomer.UserName AS CustomerName     ");
        query.append("      ,C.StoreId     ");
        query.append("      ,UIStore.UserName AS StoreName     ");
        query.append("      ,C.StaffId     ");
        query.append("      ,C.StatusKbn     ");
        query.append("      ,CASE WHEN C.StaffId IS NOT NULL THEN '1' ELSE '0' END AS IsProxyInput     ");
        query.append("      ,UIStaff.UserName AS StaffName     ");
        query.append("      ,DATE_FORMAT(C.ScreeningAppDate, '%Y/%m/%d') AS ScreeningAppDate    ");
        query.append("      ,DATE_FORMAT(C.FirstScreeningAppDate, '%Y/%m/%d') AS FirstScreeningAppDate    ");
        query.append("      ,DATE_FORMAT(C.MainAppDate, '%Y/%m/%d') AS MainAppDate    ");
        query.append("      ,DATE_FORMAT(C.VerifiedDate, '%Y/%m/%d') AS VerifiedDate     ");
        query.append("      ,DATE_FORMAT(C.LoanDate, '%Y/%m/%d') AS LoanDate     ");
        
        query.append("      ,C.InputRoleKbn     ");
        query.append("      ,C.IsCustomerInputDone     ");
        
        query.append("      ,C.UpdateTime     ");
        query.append("  FROM     ");
        query.append("      Contract C       ");
        query.append("  LEFT JOIN     ");
        query.append("      UserInfo UICustomer       ");
        query.append("   ON UICustomer.UserId = C.CustomerId       ");
        query.append("  AND UICustomer.RoleKbn = 3       ");

        query.append("  LEFT JOIN     ");
        query.append("      UserInfo UIStore       ");
        query.append("   ON UIStore.UserId = C.StoreId       ");
        query.append("  AND UIStore.RoleKbn = 2       ");

        query.append("  LEFT JOIN     ");
        query.append("      UserInfo UIStaff       ");
        query.append("   ON UIStaff.UserId = C.StaffId       ");
        query.append("  AND UIStaff.RoleKbn = 1       ");

        query.append("  WHERE 1 = 1     ");

        query.append("  AND C.WebAppId = ?     ");
        args.add(webAppId);

        String sql = query.toString();

        return reader.selectProc(sql, args);
    }
}