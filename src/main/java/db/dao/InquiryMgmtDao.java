package db.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import model.InquiryMgmtModel;
import utils.CommonUtil;
import utils.StringUtil;

public class InquiryMgmtDao extends AccessEndPoint {

    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }

    // 一覧テーブル
    public ArrayList<HashMap<String, Object>> getSearchList(InquiryMgmtModel model) throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      Inquire.Seq     ");
        query.append("      ,Inquire.InquirerId    ");
        query.append("      ,InquirerInfo.UserName AS InquirerName    ");
        query.append("      ,InquirerInfo.RoleKbn AS InquirerRoleKbn    ");
        query.append("      ,Inquire.InquireBody  ");
        query.append("      ,Inquire.InquireAttachedFile1  ");
        query.append("      ,Inquire.InquireAttachedFile2  ");
        query.append("      ,CASE WHEN StaffIsUnread = TRUE THEN '1' ELSE '0' END AS StaffIsUnread   ");
        query.append("      ,CASE WHEN CustomerIsUnread = TRUE THEN '1' ELSE '0' END AS CustomerIsUnread ");
        query.append("      ,Inquire.AnswererId  ");
        query.append("      ,AnswererInfo.UserName AS AnswererName    ");
        query.append("      ,AnswererInfo.RoleKbn AS AnswererRoleKbn    ");
        query.append("      ,Inquire.AnswerBody  ");
        query.append("      ,Inquire.AnswerAttachedFile1  ");
        query.append("      ,Inquire.AnswerAttachedFile2  ");
        query.append("      ,DATE_FORMAT(Inquire.InquireDate, '%Y/%m/%d %H:%i') AS InquireDate  ");
        query.append("      ,DATE_FORMAT(Inquire.AnswerDate, '%Y/%m/%d %H:%i') AS AnswerDate  ");
        query.append("      ,CASE WHEN Inquire.AnswerDate IS NOT NULL THEN Inquire.AnswerDate ELSE Inquire.InquireDate END AS UpdateDate  ");
        query.append("      ,Inquire.AgreementBodyKbn  ");
        query.append("      ,CASE WHEN Inquire.AgreementBodyKbn = '1' THEN '添付された書類に同意します' WHEN Inquire.AgreementBodyKbn = '2' THEN 'メッセージ2' WHEN Inquire.AgreementBodyKbn = '3' THEN 'メッセージ3' ELSE '' END AS AgreementBody   ");
        query.append("      ,Inquire.AgreementDate  ");
        query.append("  FROM     ");
        query.append("      Inquire       ");
        query.append("  LEFT JOIN     ");
        query.append("      UserInfo InquirerInfo ON Inquire.InquirerId = InquirerInfo.UserId      ");
        query.append("  LEFT JOIN     ");
        query.append("      UserInfo AnswererInfo ON Inquire.AnswererId = AnswererInfo.UserId      ");

        query.append("  WHERE Inquire.IsDelete = 0     ");
        query.append("  AND (Inquire.InquirerId = ? OR Inquire.AnswererId = ?)  ");
        args.add(model.getUserId());
        args.add(model.getUserId());

        if (CommonUtil.chkNotNullEmpty(model.getSeq())) {
            query.append("  AND Inquire.Seq = ?   ");
            args.add(model.getSeq());
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionInquireDateFrom())) {
            query.append("  AND Inquire.InquireDate >= ?   ");
            args.add(model.getConditionInquireDateFrom() + " 00:00:00");
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionInquireDateTo())) {
            query.append("  AND Inquire.InquireDate <= ?   ");
            args.add(model.getConditionInquireDateTo() + " 23:59:59");
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionAnswerDateFrom())) {
            query.append("  AND Inquire.AnswerDate >= ?   ");
            args.add(model.getConditionAnswerDateFrom() + " 00:00:00");
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionAnswerDateTo())) {
            query.append("  AND Inquire.AnswerDate <= ?   ");
            args.add(model.getConditionAnswerDateTo() + " 23:59:59");
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionBodyKbn())) {
            if (model.getConditionBodyKbn().equals("0")) {
                query.append("  AND InquirerInfo.RoleKbn IN (2,3)   ");
            } else if (model.getConditionBodyKbn().equals("1")) {
                query.append("  AND InquirerInfo.RoleKbn = 1   ");
            }
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionInquirerKbn())) {
            query.append("  AND InquirerInfo.RoleKbn = ?   ");
            args.add(model.getConditionInquirerKbn());
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionInquirerName())) {
            query.append("  AND InquirerInfo.UserName like ?   ");
            args.add('%' + StringUtil.dataToString(model.getConditionInquirerName()) + '%');
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionInquirerId())) {
            query.append("  AND Inquire.InquirerId = ?   ");
            args.add(model.getConditionInquirerId());
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionAnswererKbn())) {
            query.append("  AND AnswererInfo.RoleKbn = ?   ");
            args.add(model.getConditionAnswererKbn());
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionAnswererName())) {
            query.append("  AND AnswererInfo.UserName like ?   ");
            args.add('%' + StringUtil.dataToString(model.getConditionAnswererName()) + '%');
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionAnswererId())) {
            query.append("  AND Inquire.AnswererId = ?   ");
            args.add(model.getConditionAnswererId());
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionAgreementBodyKbn())) {
            query.append("  AND Inquire.AgreementBodyKbn = ?   ");
            args.add(model.getConditionAgreementBodyKbn());
        }

        if (CommonUtil.chkNotNullEmpty(model.getConditionAgreementDateFlg())) {
            if (model.getConditionAgreementDateFlg().equals("0")) {
                query.append("  AND Inquire.AgreementDate IS NOT NULL   ");
            } else if (model.getConditionAgreementDateFlg().equals("1")) {
                query.append("  AND Inquire.AgreementDate IS NULL   ");
            }
        }

        if (CommonUtil.chkNotNullEmpty(model.getIsReplyed())) {
            query.append("  AND Inquire.AnswerDate IS NOT NULL   ");
        }

        if (CommonUtil.chkNotNullEmpty(model.getRoleType())) {
            if (model.getRoleType().equals("1")) {
                query.append(" ORDER BY Inquire.StaffIsUnread DESC, UpdateDate DESC  ");
            } else if (model.getRoleType().equals("2") || model.getRoleType().equals("3")) {
                query.append(" ORDER BY Inquire.CustomerIsUnread DESC, UpdateDate DESC  ");
            }
        }

        String sql = query.toString();

        return reader.selectProc(sql, args);
    }

    // ユーザ情報
    public ArrayList<HashMap<String, Object>> getUserIngoList(InquiryMgmtModel model) throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      UserId     ");
        query.append("  FROM     ");
        query.append("      UserInfo       ");

        query.append("  WHERE IsDelete = 0     ");
        query.append("  AND UserId = ?  ");
        args.add(model.getAnswererId());

        String sql = query.toString();

        return reader.selectProc(sql, args);
    }

    public int insertInquire(InquiryMgmtModel model, Connection con) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  INSERT INTO Inquire (   ");
        query.append("      InquirerId          ");
        query.append("      ,InquireBody        ");
        query.append("      ,InquireAttachedFile1    ");
        query.append("      ,InquireAttachedFile2    ");
        if (model.getRoleType().equals("1")) {
            query.append("      ,CustomerIsUnread           ");
        } else if (model.getRoleType().equals("2") || model.getRoleType().equals("3")) {
            query.append("      ,StaffIsUnread          ");
        }
        query.append("      ,InquireDate        ");
        query.append("      ,AnswererId          ");
        query.append("      ,AgreementBodyKbn          ");
        query.append("      ,CreateUser         ");
        query.append("      ,CreateTime         ");
        query.append("  ) ");

        query.append("  VALUES (        ");
        query.append("      ?           ");
        args.add(model.getUserId());
        query.append("      ,?          ");
        args.add(model.getInquireBody());
        query.append("      ,?          ");
        args.add(model.getInquireAttachedFile1());
        query.append("      ,?          ");
        args.add(model.getInquireAttachedFile2());
        query.append("      ,'1'        ");
        query.append("      ,now()      ");

        if (model.getRoleType().equals("1")) {
            query.append("      ,?        ");
            args.add(model.getAnswererId());
        } else if (model.getRoleType().equals("2") || model.getRoleType().equals("3")) {
            query.append("      ,'111'        ");
        } else {
            query.append("      ,null        ");
        }

        if (CommonUtil.chkNotNullEmpty(model.getAgreementBodyKbn())) {
            query.append("      ,?        ");
            args.add(model.getAgreementBodyKbn());
        } else {
            query.append("      ,null        ");
        }

        query.append("      ,?          ");
        args.add(model.getUserId());
        query.append("      ,now()      ");
        query.append("  ) ");

        String sql = query.toString();

        return writer.updateProc(sql, args);
    }

    public int updateIsUnread(InquiryMgmtModel model, Connection con) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  UPDATE   ");
        query.append("      Inquire          ");
        query.append("  SET      ");

        if (model.getRoleType().equals("1")) {
            query.append("      StaffIsUnread = '0'     ");
        } else if (model.getRoleType().equals("2") || model.getRoleType().equals("3")) {
            query.append("      CustomerIsUnread = '0'     ");
        }

        query.append("     ,UpdateTime = now()  ");
        query.append("     ,UpdateUser = ?  ");
        args.add(model.getUserId());
        query.append(" WHERE ");
        query.append("     Seq = ? ");
        args.add(model.getSeq());

        String sql = query.toString();

        return writer.updateProc(sql, args);
    }

    public int updateInquire(InquiryMgmtModel model, Connection con) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  UPDATE   ");
        query.append("      Inquire          ");
        query.append("  SET      ");
        query.append("      AnswerBody = ?  ");
        args.add(model.getAnswerBody());
        query.append("     ,AnswerAttachedFile1 = ?  ");
        args.add(model.getAnswerAttachedFile1());
        query.append("     ,AnswerAttachedFile2 = ?  ");
        args.add(model.getAnswerAttachedFile2());
        query.append("     ,AnswerDate = now()  ");

        if (CommonUtil.chkNotNullEmpty(model.getIsAgreement())) {
            if (model.getIsAgreement().equals("1")) {
                query.append("     ,AgreementDate = now()  ");
            }
        }
        if (model.getRoleType().equals("1")) {
            query.append("      ,CustomerIsUnread = '1'      ");
        } else if (model.getRoleType().equals("2") || model.getRoleType().equals("3")) {
            query.append("      ,StaffIsUnread = '1'     ");
        }
        query.append("     ,UpdateTime = now()  ");
        query.append("     ,UpdateUser = ?  ");
        args.add(model.getUserId());
        query.append(" WHERE ");
        query.append("     Seq = ? ");
        args.add(model.getSeq());

        String sql = query.toString();

        return writer.updateProc(sql, args);
    }

}