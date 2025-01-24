package db.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import model.VehicleMgmtModel;
import utils.CommonUtil;
import utils.StringUtil;


public class VehicleMgmtDao extends AccessEndPoint {

    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }


    // 一覧テーブル
    public ArrayList<HashMap<String, Object>> getSearchList(VehicleMgmtModel model) throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      seq     ");
        query.append("      ,id     ");
        query.append("      ,vehicle_no     ");
        query.append("      ,maker     ");
        query.append("      ,model     ");
        query.append("      ,katashiki     ");
        query.append("      ,year     ");
        query.append("      ,e_g_start_type     ");
        query.append("      ,verify_code     ");
        query.append("  FROM     ");
        query.append("      mccs_vehicle_master        ");

        query.append("  WHERE 1 = 1     ");

        if (CommonUtil.chkNotNullEmpty(model.getConditionMaker())) {
            query.append("  AND maker = ?     ");
            args.add(StringUtil.dataToString(model.getConditionMaker()));
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionCarName())) {
            query.append("  AND model like ?     ");
            args.add('%' + StringUtil.dataToString(model.getConditionCarName()) + '%');
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionCarType())) {
            query.append("  AND katashiki = ?     ");
            args.add(StringUtil.dataToString(model.getConditionCarType()));
        }
        
        
        if (CommonUtil.chkNotNullEmpty(model.getConditionDateFrom())) {
            query.append("  AND year => ?     ");
            args.add(StringUtil.dataToString(model.getConditionDateFrom()));
        }
        if (CommonUtil.chkNotNullEmpty(model.getConditionDateTo())) {
            query.append("  AND year =< ?     ");
            args.add(StringUtil.dataToString(model.getConditionDateTo()));
        }


        query.append("  ORDER BY      ");
        query.append("      UpdateTime DESC   ");

        String sql = query.toString();

        return reader.selectProc(sql, args);
    }
}