package db.dao;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import utils.StringUtil;
import utils.log4j.LogUtil;

public class CommonDao extends AccessEndPoint {

    // HttpSession session = ServletActionContext.getRequest().getSession();
    private String className = this.getClass().getSimpleName();

    public ArrayList<HashMap<String, Object>> getAwsConfig() throws Exception {

        StringBuffer query = new StringBuffer();
        query.append(" SELECT * FROM AwsConfig ");

        return reader.selectProc(query.toString());
    }

    /**
     * Web受付IDの通番を新規取得する
     * 
     * @return WebAppIdの通番4桁
     * @throws Exception
     */
    public String getWebAppIdSeq() throws Exception {
        Connection con = reader.getCon();
        con.setAutoCommit(false);

        String seq = "0001";

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String webAppIdDate = today.format(formatter);

        try {
            StringBuffer query = new StringBuffer();
            ArrayList<Object> args = new ArrayList<Object>();
            query.append("  SELECT COALESCE(MAX(CAST(WebAppIdSeq AS UNSIGNED)), 0) + 1 AS MaxSeq ");
            query.append("  FROM                ");
            query.append("      Contract        ");
            query.append("  WHERE               ");
            query.append("      WebAppIdDate = ?");
            query.append("      FOR UPDATE ");// 排他制御
            args.add(webAppIdDate);

            String sql = query.toString();
            ArrayList<HashMap<String, Object>> sqlRes = reader.selectProc(sql, args, con);
            Object value = sqlRes.get(0).get("MaxSeq");

            LogUtil.info("value : " + String.valueOf(value));
            if (value != null) {
                seq = String.format("%04d", Integer.parseInt(String.valueOf(value)));
                LogUtil.info("seq : " + seq);
            } else {
                LogUtil.info("DBにWebAppIdDate = " + webAppIdDate + "のデータなし");
            }
        } catch (Exception e) {
            con.rollback();
            LogUtil.info(e.getMessage());
            throw e;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }

        return seq;
    }

    /**
     * 免許証番号から顧客を特定
     * 
     * @param licenseNumber
     * @return
     * @throws Exception
     */
    public String getCustomerIdFromLicenseNumber(String licenseNumber) throws Exception {
        String result = null;

        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "SELECT UserId FROM UserInfo WHERE roleKbn = 3 AND LicenseNumber = '" + licenseNumber + "'";
        ArrayList<HashMap<String, Object>> mapArr = reader.selectProc(sql, args);
        if (mapArr.size() > 0) {
            result = String.valueOf(mapArr.get(0).get("UserId"));
        }

        return result;
    }

    /**
     * 法人番号から顧客を特定
     * 
     * @param hojinNumber
     * @return
     * @throws Exception
     */
    public String getCustomerIdFromHojinNumber(String hojinNumber) throws Exception {
        String result = "";

        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "SELECT UserId FROM UserInfo WHERE roleKbn = 3 AND HojinNumber = '" + hojinNumber + "'";
        ArrayList<HashMap<String, Object>> mapArr = reader.selectProc(sql, args);
        if (mapArr.size() > 0) {
            result = String.valueOf(mapArr.get(0).get("UserId"));
        }

        return result;
    }

    /**
     * 契約番号から顧客を特定
     * 
     * @param contractNo
     * @return
     * @throws Exception
     */
    public String getCustomerIdFromContactNo(String contractNo) throws Exception {
        String result = "";

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  SELECT  ");
        query.append("      u.UserId     ");
        query.append("  FROM     ");
        query.append("      UserInfo u       ");
        query.append("  LEFT JOIN Contract c ");
        query.append("  ON u.UserId = c.CustomerId ");
        query.append("  WHERE     ");
        query.append("      c.ContractNumber = ?            ");
        args.add(contractNo);
        query.append("      AND u.IsDelete = 0            ");
        query.append("      AND u.RoleKbn = 3             ");

        ArrayList<HashMap<String, Object>> mapArr = reader.selectProc(query.toString(), args);
        if (mapArr.size() < 1) {
            throw new Exception("");
        } else {
            result = StringUtil.dataToString(mapArr.get(0).get("UserId"));
        }

        return result;
    }

    /**
     * MCCS車両マスタからメーカー一覧を取得
     * 
     * @return ArrayList<HashMap<String, Object>>
     * @throws Exception
     */
    public ArrayList<HashMap<String, Object>> getMccsMaker() throws Exception {

        StringBuffer query = new StringBuffer();
        query.append(" SELECT Maker FROM MccsVehicleMaster GROUP BY Maker ");

        return reader.selectProc(query.toString());
    }

    /**
     * MCCS車両マスタからメーカーにひもづく車名一覧を取得
     * 
     * @param maker
     * @return
     * @throws Exception
     */
    public ArrayList<HashMap<String, Object>> getMccsShamei(String maker) throws Exception {

        StringBuffer query = new StringBuffer();
        query.append(" SELECT Model FROM MccsVehicleMaster WHERE Maker = '" + maker + "' GROUP BY Model ");

        return reader.selectProc(query.toString());
    }

    /**
     * MCCS車両マスタから車名にひもづく型式一覧を取得
     * 
     * @param shamei
     * @return
     * @throws Exception
     */
    public ArrayList<HashMap<String, Object>> getMccsKatashiki(String shamei) throws Exception {

        StringBuffer query = new StringBuffer();
        query.append(" SELECT VehicleModel FROM MccsVehicleMaster WHERE Model = '" + shamei + "' GROUP BY VehicleModel ");

        return reader.selectProc(query.toString());
    }

    /**
     * 個人の顧客をUserInfoテーブルに登録する
     * 
     * @param customerId
     * @param licenseNumber
     * @param mail
     * @param tel
     * @param con
     * @return
     * @throws Exception
     */
    public int registKojinCustomer(String customerId, String licenseNumber, Connection con) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  INSERT INTO         ");
        query.append("  UserInfo            ");
        query.append("  (                   ");
        query.append("      UserId,         ");
        query.append("      RoleKbn,        ");
        query.append("      LisenceNumber,  ");
        query.append("      CreateUser     ");
        query.append("  )               ");
        query.append("  VALUES          ");
        query.append("  (               ");
        query.append("      ?,          ");
        args.add(customerId);
        query.append("      ?,          ");
        args.add("3");
        query.append("      ?,          ");
        args.add(licenseNumber);
        query.append("      'SYSTEM'    ");
        query.append("  );              ");

        String sql = query.toString();

        return writer.updateProc(sql, args, con);
    }

    /**
     * 法人の顧客をUserInfoテーブルに登録する
     * 
     * @param customerId
     * @param hojinNumber
     * @param mail
     * @param tel
     * @param con
     * @return
     * @throws Exception
     */
    public int registHojinCustomer(String customerId, String hojinNumber, Connection con) throws Exception {

        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append("  INSERT INTO         ");
        query.append("  UserInfo            ");
        query.append("  (                   ");
        query.append("      UserId,         ");
        query.append("      RoleKbn,        ");
        query.append("      HojinNumber,    ");
        query.append("      CreateUser     ");
        query.append("  )               ");
        query.append("  VALUES          ");
        query.append("  (               ");
        query.append("      ?,          ");
        args.add(customerId);
        query.append("      ?,          ");
        args.add("3");
        query.append("      ?,          ");
        args.add(hojinNumber);
        query.append("      'SYSTEM'    ");
        query.append("  );              ");

        String sql = query.toString();

        return writer.updateProc(sql, args, con);
    }

    /**
     * 引数顧客IDが既に存在しているかチェック
     * 
     * @param customerId
     * @return
     * @throws Exception
     */
    public boolean isCustomerIdExist(String customerId) {

        boolean result = false;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM UserInfo WHERE UserId = '" + customerId + "'");
            ArrayList<HashMap<String, Object>> resArr = reader.selectProc(query.toString());
            if (resArr.size() > 0) {
                result = true;
            }
        } catch (Exception e) {
            LogUtil.error(className, e);
        }

        return result;
    }

    /**
     * MessageSendにレコードをINSERTする
     * 
     * @param type
     * @param to
     * @param subject
     * @param body
     * @param con
     * @return
     */
    public boolean insertMessageSend(String messageType, String to, String subject, String body, Connection con) {
        boolean result = true;

        try {
            StringBuffer query = new StringBuffer();
            ArrayList<Object> args = new ArrayList<Object>();
            int notifyKbn = messageType.equals("sms") ? 1 : 2;

            query.append("  INSERT INTO         ");
            query.append("  MessageSend            ");
            query.append("  (                   ");
            query.append("      NotifyKbn,         ");
            if (messageType.equals("sms")) {
                query.append("      PhoneNumber,        ");
                query.append("      SenderID,    ");
            } else {
                query.append("      MailAddress,      ");
            }
            query.append("      Subject,    ");
            query.append("      Body, ");
            query.append("      IsSendTarget, ");
            query.append("      CreateUser    ");
            query.append("  )               ");
            query.append("  VALUES          ");
            query.append("  (               ");
            query.append("      ?,          ");
            args.add(notifyKbn);
            if (messageType.equals("sms")) {
                query.append("      ?,          ");
                args.add(to);
                query.append("      ?,          ");
                args.add("AL-WEB");
            } else {
                query.append("      ?,          ");
                args.add(to);

            }
            query.append("      ?,          ");
            args.add(subject);
            query.append("      ?,          ");
            args.add(body);
            query.append("      TRUE,    ");
            query.append("      'SYSTEM'    ");
            query.append("  );              ");

            String sql = query.toString();
            LogUtil.info(className, sql);
            int recCount = writer.updateProc(sql, args, con);
            if (recCount < 1)
                result = false;
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    /**
     * 引数顧客のワンタイムパスワードと期限を更新する
     * 
     * @param onetimePassword
     * @param userId
     * @param con
     * @return
     * @throws Exception
     */
    public int updateUserOnetimepassword(String onetimePassword, String userId, Connection con) throws Exception {
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        query.append(" UPDATE ");
        query.append("     UserInfo ");
        query.append(" SET ");
        query.append("     OnetimePassword = ?, ");
        args.add(onetimePassword);
        query.append("     OnetimePasswordLimit = (DATE_ADD(NOW(), INTERVAL 1 HOUR)) ");
        query.append(" WHERE ");
        query.append("     UserId = ? ");
        query.append("     AND IsDelete = 0 ");
        args.add(userId);

        String sql = query.toString();
        return writer.updateProc(sql, args, con);
    }
}
