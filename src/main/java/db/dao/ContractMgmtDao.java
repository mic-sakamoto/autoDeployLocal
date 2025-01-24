package db.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import model.ContractMgmtModel;
import utils.CommonUtil;
import utils.StringUtil;

public class ContractMgmtDao extends AccessEndPoint {

    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }

    // 一覧テーブル
    public ArrayList<HashMap<String, Object>> getSearchList(ContractMgmtModel model) throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      C.ContractId     ");
        query.append("      ,C.WebAppId     ");
        query.append("      ,C.CustomerId     ");
        query.append("      ,UICustomer.UserName AS CustomerName     ");
        query.append("      ,C.StoreId     ");
        query.append("      ,UIStore.UserName AS StoreName     ");
        query.append("      ,C.StaffId     ");
        query.append("      ,C.StatusKbn     ");
        query.append("      ,C.InputRoleKbn     ");
        query.append("      ,C.InputModeKbn     ");
        query.append("      ,CASE WHEN C.StaffId IS NOT NULL THEN '1' ELSE '0' END AS IsProxyInput     ");
        query.append("      ,UIStaff.UserName AS StaffName     ");
        query.append("      ,DATE_FORMAT(C.ScreeningAppDate, '%Y/%m/%d') AS ScreeningAppDate    ");
        query.append("      ,DATE_FORMAT(C.MainAppDate, '%Y/%m/%d') AS MainAppDate    ");
        query.append("      ,DATE_FORMAT(C.VerifiedDate, '%Y/%m/%d') AS VerifiedDate     ");
        query.append("      ,DATE_FORMAT(C.LoanDate, '%Y/%m/%d') AS LoanDate     ");
        query.append("      ,DATE_FORMAT(C.UpdateTime, '%Y/%m/%d') AS UpdateTime     ");
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
        
        // 加盟店の場合は自分ののみ
        if ("2".equals(model.getRoleType())) {
            query.append("  AND C.StoreId = ?     ");
            String userId = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "user_id" ));
            args.add(StringUtil.dataToString(userId));
        }
        // 顧客の場合は自分ののみ
        if ("3".equals(model.getRoleType())) {
            query.append("  AND C.CustomerId = ?     ");
            String userId = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "user_id" ));
            args.add(StringUtil.dataToString(userId));
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionWebAppId())) {
            query.append("  AND C.WebAppId = ?     ");
            args.add(StringUtil.dataToString(model.getConditionWebAppId()));
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionStatus())) {
            query.append("  AND C.StatusKbn = ?     ");
            args.add(StringUtil.dataToString(model.getConditionStatus()));
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionScreeningAppDateFrom())) {
            query.append("  AND C.ScreeningAppDate >= ?     ");
            args.add(StringUtil.dataToString(model.getConditionScreeningAppDateFrom()));
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionScreeningAppDateTo())) {
            query.append("  AND C.ScreeningAppDate <= ?     ");
            args.add(StringUtil.dataToString(model.getConditionScreeningAppDateTo()));
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionVerifiedDateFrom())) {
            query.append("  AND C.VerifiedDate >= ?     ");
            args.add(StringUtil.dataToString(model.getConditionVerifiedDateFrom()));
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionVerifiedDateTo())) {
            query.append("  AND C.VerifiedDate <= ?     ");
            args.add(StringUtil.dataToString(model.getConditionVerifiedDateTo()));
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionStoreChargeName())) {
            query.append("  AND UIStaff.UserName like ?     ");
            args.add('%' + StringUtil.dataToString(model.getConditionStoreChargeName()) + '%');
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionMainAppDateFrom())) {
            query.append("  AND C.MainAppDate >= ?     ");
            args.add(StringUtil.dataToString(model.getConditionMainAppDateFrom()));
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionMainAppDateTo())) {
            query.append("  AND C.MainAppDate <= ?     ");
            args.add(StringUtil.dataToString(model.getConditionMainAppDateTo()));
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionLoanDateFrom())) {
            query.append("  AND C.LoanDate >= ?     ");
            args.add(StringUtil.dataToString(model.getConditionLoanDateFrom()));
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionLoanDateTo())) {
            query.append("  AND C.LoanDate <= ?     ");
            args.add(StringUtil.dataToString(model.getConditionLoanDateTo()));
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionInputKbn())) {
            query.append("  AND C.InputRoleKbn = ?     ");
            args.add(StringUtil.dataToString(model.getConditionInputKbn()));
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionUserId())) {
            query.append("  AND C.CustomerId = ?     ");
            args.add(StringUtil.dataToString(model.getConditionUserId()));
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionUserName())) {
            query.append("  AND UICustomer.UserName like ?     ");
            args.add('%' + StringUtil.dataToString(model.getConditionUserName()) + '%');
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionProxyInput())) {
            query.append("  AND C.StaffId IS NOT NULL     ");
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionStoreId())) {
            query.append("  AND C.StoreId = ?     ");
            args.add(StringUtil.dataToString(model.getConditionStoreId()));
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionStoreName())) {
            query.append("  AND UIStore.UserName like ?     ");
            args.add('%' + StringUtil.dataToString(model.getConditionStoreName()) + '%');
        }

        query.append("  ORDER BY      ");
        query.append("      C.UpdateTime DESC   ");

        String sql = query.toString();

        return reader.selectProc(sql, args);
    }
    
    
 // 一覧テーブル
    public ArrayList<HashMap<String, Object>> getContract(ContractMgmtModel model) throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      StatusKbn     ");
        query.append("      ,VerifiedDate     ");
        query.append("      ,ScreeningAppDate     ");
        query.append("      ,ScreeningAppResDate     ");
        query.append("  FROM     ");
        query.append("      Contract        ");

        query.append("  WHERE ContractId = ?     ");
        args.add(StringUtil.dataToString(model.getContractId()));


        String sql = query.toString();

        return reader.selectProc(sql, args);
    }

}