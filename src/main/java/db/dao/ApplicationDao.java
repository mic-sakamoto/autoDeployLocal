package db.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import model.ApplicationModel;
import utils.CommonUtil;
import utils.log4j.LogUtil;

public class ApplicationDao extends AccessEndPoint {

    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }

    /**
     * 申込入力画面の入力内容を一時保存
     * 
     * @param sql_INST_CLM
     *            INSERT文 カラム名
     * @param sql_INST_VAL
     *            INSERT文 更新内容
     * @param sql_UPDT
     *            UPDATE文
     * @param con
     * @return
     * @throws Exception
     */
    public boolean saveAppInput(String sql_INST_CLM, String sql_INST_VAL, String sql_UPDT, Connection con) throws Exception {
        boolean result = false;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "INSERT INTO ScreeningTemp (" + sql_INST_CLM + ") VALUES (" + sql_INST_VAL + ") ON DUPLICATE KEY UPDATE " + sql_UPDT;
        LogUtil.info(className, sql);

        int sqlRes = writer.updateProc(sql, args, con);
        if (sqlRes > 0) {
            result = true;
        }
        return result;
    }

    /**
     * 申込入力画面の一時保存した入力内容を取得
     * 
     * @param model
     * @return
     * @throws Exception
     */
    public HashMap<String, Object> getScreeningTemp(ApplicationModel model) throws Exception {

        HashMap<String, Object> result = new HashMap<String, Object>();

        try {
            // カラム名を取得
            ArrayList<Object> args = new ArrayList<Object>();
            String columnSql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'ScreeningTemp'";
            ArrayList<HashMap<String, Object>> columnMapArr = reader.selectProc(columnSql, args);

            // 上で取得したカラム名に対応する値を取得
            String valueSql = "SELECT * FROM ScreeningTemp WHERE WebAppId = '" + model.getWebAppId() + "'";
            ArrayList<HashMap<String, Object>> valueMapArr = reader.selectProc(valueSql, args);
            if (valueMapArr.size() < 1)
                throw new Exception("対象データ無し");

            // カラム名と値をMap型に
            HashMap<String, Object> valueMap = valueMapArr.get(0);
            model.setMoshikomiKbn(valueMap.get("MoshikomiKbn").toString());

            for (HashMap<String, Object> map : columnMapArr) {
                String columnName = map.get("COLUMN_NAME").toString();
                String value = "";
                if (valueMap.get(columnName) != null) {
                    value = valueMap.get(columnName).toString();

                }
                result.put(columnName, value);
            }

        } catch (Exception e) {
            LogUtil.info(e.getMessage());
        }
        return result;
    }

    /**
     * 下書き削除
     * 
     * @param webAppId
     * @return
     * @throws Exception
     */
    public boolean deleteScreeningTemp(String webAppId, Connection con) throws Exception {

        boolean result = false;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "DELETE FROM ScreeningTemp WHERE WebAppId = '" + webAppId + "'";
        LogUtil.info(className, sql);

        int sqlRes = writer.updateProc(sql, args, con);
        if (sqlRes > 0) {
            result = true;
        }
        return result;
    }

    /**
     * 申込入力下書き保存時or新規申し込み時に契約管理テーブルに新規登録
     * 
     * @param model
     * @param isTemp
     *            下書きフラグ
     * @return
     * @throws Exception
     */
    public boolean registContarct(ApplicationModel model, boolean isTemp, Connection con) throws Exception {
        boolean result = false;
        StringBuffer query = new StringBuffer();
        ArrayList<Object> args = new ArrayList<Object>();

        model.setCustomerId("333");
        model.setStoreId("222");

        query.append("  INSERT INTO         ");
        query.append("  Contract            ");
        query.append("  (                   ");
        query.append("      WebAppId,           ");
        query.append("      WebAppIdDate,       ");
        query.append("      WebAppIdSeq,        ");
        query.append("      WebAppIdEda,        ");
        query.append("      InputModeKbn,       ");
        query.append("      StatusKbn,          ");
        if (!isTemp) {// 非下書き
            if (model.getStatusKbn().equals("4")) {// 審査申込
                query.append("      ScreeningAppDate,   ");
                query.append("      FirstScreeningAppDate,   ");
            }
            if (model.getInputRoleKbn().equals("3"))// 顧客入力の場合は顧客入力状況を済みにする
                query.append("  IsCustomerInputDone, ");
        }
        query.append("      CustomerId,         ");
        query.append("      StoreId,            ");
        query.append("      CreateUser          ");
        query.append("  )                   ");
        query.append("  VALUES              ");
        query.append("  (                   ");
        query.append("      ? ,                 ");
        args.add(model.getWebAppId());
        query.append("      ? ,                 ");
        args.add(model.getWebAppId().substring(0, 6));
        query.append("      ? ,                 ");
        args.add(model.getWebAppId().substring(6, 10));
        query.append("      ? ,                 ");
        args.add(model.getWebAppId().substring(10, 13));
        query.append("      ? ,                 ");
        args.add(model.getInputModeKbn());
        query.append("      ? ,                 ");
        args.add(model.getStatusKbn());
        if (!isTemp) {// 非下書き
            if (model.getStatusKbn().equals("4")) {// 審査申込
                query.append("      NOW() ,         ");
                query.append("      NOW() ,         ");
            }
            if (model.getInputRoleKbn().equals("3")) // 顧客入力の場合は顧客入力状況を済みにする
                query.append("      TRUE,           ");
        }
        query.append("      ? ,                 ");
        args.add(model.getCustomerId());
        query.append("      ? ,                 ");
        args.add(model.getStoreId());
        query.append("      'SYSTEM'            ");
        query.append("  )                   ");

        String sql = query.toString();
        int sqlRes = writer.updateProc(sql, args, con);
        if (sqlRes > 0) {
            result = true;
        }
        return result;
    }

    /**
     * 申込時に契約レコードUPDATE & 更新前レコードを契約履歴テーブルにINSERT
     * 
     * @param model
     * @param con
     * @return
     * @throws Exception
     */
    public boolean updateContract(ApplicationModel model, Connection con) throws Exception {

        boolean result = true;

        try {
            // 更新対象レコードを取得
            String selectQuery = "SELECT * FROM Contract WHERE ContractId = ?";
            ArrayList<Object> selectArgs = new ArrayList<>();
            selectArgs.add(model.getContractId());

            ArrayList<HashMap<String, Object>> contractData = reader.selectProc(selectQuery, selectArgs);
            if (contractData.isEmpty()) {
                throw new Exception("契約ID : " + model.getContractId() + "が見つかりませんでした。");
            }

            // CoctractHistoryテーブルにINSERT
            HashMap<String, Object> contractRecord = contractData.get(0);
            ArrayList<String> columns = new ArrayList<>();
            ArrayList<Object> values = new ArrayList<>();
            for (Map.Entry<String, Object> entry : contractRecord.entrySet()) {
                // 'history_id' は自動生成されるため除外
                columns.add(entry.getKey());
                values.add(entry.getValue());
            }
            StringBuilder insertHistoryQuery = new StringBuilder("INSERT INTO ContractHistory (");
            insertHistoryQuery.append(String.join(", ", columns));
            insertHistoryQuery.append(") VALUES (");
            insertHistoryQuery.append(String.join(", ", Collections.nCopies(columns.size(), "?")));
            insertHistoryQuery.append(")");
            writer.updateProc(insertHistoryQuery.toString(), values, con);
        } catch (Exception e) {
            LogUtil.error(e);
            throw new Exception("契約履歴テーブルへのINSERTに失敗");
        }

        try {
            // Contractテーブルの更新
            StringBuffer query = new StringBuffer();
            ArrayList<Object> args = new ArrayList<Object>();

            query.append("  UPDATE         ");
            query.append("      Contract            ");
            query.append("  SET                   ");
            query.append("      WebAppId = ?,           ");
            args.add(model.getWebAppId());
            query.append("      WebAppIdDate = ?,       ");
            args.add(model.getWebAppId().substring(0, 6));
            query.append("      WebAppIdSeq = ?,        ");
            args.add(model.getWebAppId().substring(6, 10));
            query.append("      WebAppIdEda = ?,        ");
            args.add(model.getWebAppId().substring(10, 13));
            query.append("      StatusKbn = ?,          ");
            args.add(model.getStatusKbn());
            if (CommonUtil.chkNotNullEmpty(model.getDaikoStaffId())) {
                query.append("      StaffId = ?,          ");
                args.add(model.getDaikoStaffId());
            }
            query.append("      InputModeKbn = ?,       ");
            args.add(model.getInputModeKbn());
            // 新規申し込みかつ顧客入力の場合は顧客入力状況を済みにする
            if (model.getInputRoleKbn().equals("3")) {
                query.append("      IsCustomerInputDone = TRUE,       ");
            }
            switch (model.getStatusKbn()) {
                case "4":
                    query.append("      ScreeningAppDate = NOW(),   ");
                    query.append("      FirstScreeningAppDate = CASE WHEN FirstScreeningAppDate IS NULL THEN NOW() ELSE FirstScreeningAppDate END,   ");
                    break;
                case "15":
                    query.append("      MainAppDate = NOW(),          ");
                    break;
            }
            query.append("  UpdateUser = 'SYSTEM'     ");

            query.append(" WHERE ");
            query.append("     ContractId = ? ");
            args.add(model.getContractId());

            String sql = query.toString();
            int sqlRes = writer.updateProc(sql, args, con);
            if (sqlRes < 1) {
                result = false;
            }
        } catch (Exception e) {
            LogUtil.error(e);
            throw new Exception("契約テーブルのUPDATEに失敗");
        }

        return result;
    }

    /**
     * 対象WEB受付IDの契約情報を取得する
     * 
     * @param model
     * @return
     * @throws Exception
     */
    public HashMap<String, Object> getContractInfo(ApplicationModel model) throws Exception {
        HashMap<String, Object> result = new HashMap<String, Object>();
        ArrayList<Object> args = new ArrayList<Object>();

        try {
            String sql = "SELECT * FROM Contract WHERE ContractId = '" + model.getContractId() + "'";
            ArrayList<HashMap<String, Object>> mapArr = reader.selectProc(sql, args);
            if (mapArr.size() < 1) {
                throw new Exception("対象データ無し");
            } else {
                result = mapArr.get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}