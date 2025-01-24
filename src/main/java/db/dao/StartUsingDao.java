package db.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import model.StartUsingModel;

public class StartUsingDao extends AccessEndPoint {

    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }

    /**
     * 契約番号とユーザー情報からユーザーがDB上に存在するかチェック
     * @param model
     * @return
     * @throws Exception
     */
    public boolean getUserExistCheckFromContractNo(StartUsingModel model) throws Exception {
        
        boolean result = false;
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      u.UserId     ");
        query.append("  FROM     ");
        query.append("      UserInfo u       ");
        query.append("  LEFT JOIN Contract c ");
        query.append("  ON u.UserId = c.CustomerId ");
        query.append("  WHERE     ");
        query.append("      c.ContractNo = ?            ");args.add(model.getContract_number());
        query.append("      AND u.LastNameKana = ?      ");args.add(model.getLast_name_kana());
        query.append("      AND u.FirstNameKana = ?     ");args.add(model.getFirst_name_kana());
        query.append("      AND DATE_FORMAT(u.BirthDate,'%Y') = ?    ");args.add(model.getBirth_year());
        query.append("      AND DATE_FORMAT(u.BirthDate,'%c') = ?    ");args.add(model.getBirth_month());
        query.append("      AND DATE_FORMAT(u.BirthDate,'%e') = ?    ");args.add(model.getBirth_date());
        query.append("      AND u.TelNumber = ?         ");args.add(model.getTel_number1()+model.getTel_number2()+model.getTel_number3());
        query.append("      AND u.IsDelete = 0            ");
        query.append("      AND u.RoleKbn = 3             ");

        String sql = query.toString();
        ArrayList<HashMap<String, Object>> arr = reader.selectProc(sql, args);
        if(arr.size() > 0) {
            result = true;
        }

        return result;
    }

    public int insertNewRetration(String userId, String password, Integer isAdmin) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  INSERT INTO ");
        query.append("  UserInfo    ");
        query.append("  (UserId,    ");
        query.append("  Password,   ");
        query.append("  IsAdmin,    ");
        query.append("  CreateUser) ");
        query.append("  VALUES      ");
        query.append("  ( ? ,       ");
        query.append("  ? ,         ");
        query.append("     ?,       ");
        query.append("  'HANAZAWA');   ");
        args.add(userId);
        args.add(password);
        args.add(isAdmin);

        String sql = query.toString();

        return writer.updateProc(sql, args);
    }

    public int changePassword(String listUserId, String updatePassword) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  UPDATE ");
        query.append("  UserInfo    ");
        query.append("  SET    ");
        query.append("  Password = ?,   ");
        query.append("  UpdateTime = now()     ");
        query.append("  Where ");
        query.append("  UserId = ?      ");

        args.add(updatePassword);
        args.add(listUserId);

        String sql = query.toString();

        return writer.updateProc(sql, args);
    }
}