package db.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import db.DbConnection;
import utils.log4j.LogUtil;

/**
 * アクセス用ＤＡＯ
 *
 * @since 2007/06/02
 * @author mic
 *
 */
public class AccessReaderEndPoint extends DbConnection {

    private String className;

    /** DEBUG出力フラグ */
    private final boolean _DEBUG = true;
    private final String ResourceNm = "java:comp/env/jdbc/ReaderEndPoint";

    protected Connection getCon() throws Exception {
        return super.getCon(this.ResourceNm);
    }

    /**
     * コンストラクター
     */
    public AccessReaderEndPoint() {
        className = this.getClass().getName();
        // curCon = super.getCon("java:comp/env/jdbc/aucDB");
    }

    /**
     * プリペアドステートメントのオブジェクト取得
     *
     * @param query
     *            SQLクエリ
     * @return PreparedStatementオブジェクト
     */
    public PreparedStatement getPreparedStatementObject(String query) throws Exception {
        try {
            curCon = super.getCon(this.ResourceNm);
            PreparedStatement pstmt = curCon.prepareStatement(query);
            // PreparedStatement pstmt = curCon.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return pstmt;
        } catch (Exception e) {
            LogUtil.error(className, "connection error" + e);
            throw e;
        }
    }

    /**
     * プリペアドステートメントのオブジェクト取得<<getPreparedStatementObject(String query)のコネクションを取得しないバージョン>>
     *
     * @param query
     *            SQLクエリ
     * @return PreparedStatementオブジェクト
     */
    public PreparedStatement getPreparedStatementObject(String query, Connection curCon) throws Exception {
        try {
            // curCon = super.getCon( this.ResourceNm);
            PreparedStatement pstmt = curCon.prepareStatement(query);
            // PreparedStatement pstmt = curCon.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return pstmt;
        } catch (Exception e) {
            LogUtil.error(className, "connection error" + e);
            throw e;
        }
    }




    /**
     * SELECT処理 プリペアドステートオブジェクト一時作成
     *
     * @param query
     *            プリペアドステートメントクエリ
     * @param args
     *            実行引数
     * @return 結果リスト
     */
    public ArrayList<HashMap<String, Object>> selectProc(String query) throws Exception {
        return selectProc(query, null);
    }

    public ArrayList<HashMap<String, Object>> selectProc(String query, ArrayList<Object> args) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            if (args != null) {

                Iterator ite = args.iterator();
                while (ite.hasNext()) {
                    Object ipBean = ite.next();
                    LogUtil.info(className, "プレースホルダ―：" + ipBean);
                }
            }

            pstmt = getPreparedStatementObject(query);
            return selectPreparedStatement(pstmt, args);
        } catch (Exception e) {
            LogUtil.error(this.className, e);
            throw e;
        } finally {
            this.closeCurrentConnection();
            this.closePrepareStatement(pstmt);
        }
    }

    public ArrayList<HashMap<String, Object>> selectProc(String query, ArrayList<Object> args, Connection curCon) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            LogUtil.info(className, "exe query: " + query);
            if (args != null) {

                Iterator ite = args.iterator();
                while (ite.hasNext()) {
                    Object ipBean = ite.next();
                    LogUtil.info(className, "プレースホルダ―：" + ipBean);
                }
            }

            pstmt = getPreparedStatementObject(query, curCon);
            return selectPreparedStatement(pstmt, args);
        } catch (Exception e) {
            LogUtil.error(this.className, e);
            throw e;
        } finally {
            // this.closeCurrentConnection();
            // this.closePrepareStatement(pstmt);
        }
    }

    /**
     * SELECT処理
     *
     * @param pstmt
     *            プリペアドステートメントオブジェクト
     * @param args
     *            実行引数
     * @return 結果リスト
     */
    public ArrayList<HashMap<String, Object>> selectPreparedStatement(PreparedStatement pstmt, ArrayList<Object> args) throws Exception {
        ResultSet rs = null;
        try {
            if (args != null)
                for (int i = 0; i < args.size(); i++) {
                    setPreparedParam(pstmt, i + 1, args.get(i));
                }
            long time1, time2, time3;
            time1 = System.currentTimeMillis();
            LogUtil.info(className, "exe query: " + pstmt.toString());
            rs = pstmt.executeQuery();
            if (_DEBUG) {
                time2 = System.currentTimeMillis();
                LogUtil.debug(className, "pstmt execute: " + Long.toString((time2 - time1)));
            }
            ArrayList<HashMap<String, Object>> recordSet = super.getRecordSet(rs);
            if (_DEBUG) {
                time3 = System.currentTimeMillis();
                LogUtil.debug(className, "create List  : " + Long.toString((time3 - time2)));
            }
            if (recordSet.size() > 0) {
                return recordSet;
            } else {
                return null;
            }

        } catch (SQLException sqle) {
            LogUtil.error(className, "SQL error：" + sqle);
            throw sqle;
        } finally {
            // this.closeCurrentConnection();
            this.closeResultSet(rs);
        }
    }

    // プリペアドステートメントのパラメータセット
    private void setPreparedParam(PreparedStatement pstmt, int index, Object arg) throws Exception {
        if (arg instanceof Boolean)
            pstmt.setBoolean(index, (Boolean)arg);
        else if (arg instanceof Byte)
            pstmt.setByte(index, (Byte)arg);
        else if (arg instanceof Date)
            pstmt.setDate(index, (Date)arg);
        else if (arg instanceof Short)
            pstmt.setShort(index, (Short)arg);
        else if (arg instanceof Integer)
            pstmt.setInt(index, (Integer)arg);
        else if (arg instanceof Long)
            pstmt.setLong(index, (Long)arg);
        else if (arg instanceof Float)
            pstmt.setFloat(index, (Float)arg);
        else if (arg instanceof Double)
            pstmt.setDouble(index, (Double)arg);
        else if (arg instanceof String)
            pstmt.setString(index, (String)arg);
        // else if(arg instanceof Timestamp) pstmt.setTimestamp(index, (Timestamp)arg);
        else if (arg instanceof Timestamp)
            pstmt.setString(index, timestampToStr((Timestamp)arg));
        else if (arg instanceof Time)
            pstmt.setTime(index, (Time)arg);
        else
            pstmt.setObject(index, arg);
    }

    /**
     * ストアドプロシージャ実行
     *
     * @param stored
     *            実行するプロシージャ名
     * @param [args
     *            実行引数] 任意
     * @return ストアドプロシージャの戻り値(int)プロシージャ内でreturnがない場合は0が戻る
     */
    public int storedExec(String stored) throws Exception {
        return _storedExec(stored, null, false);
    }

    public int storedExec(String stored, ArrayList<Object> args) throws Exception {
        return _storedExec(stored, args, false);
    }

    public int storedExecE(String stored) throws Exception {
        return _storedExec(stored, null, true);
    }

    public int storedExecE(String stored, ArrayList<Object> args) throws Exception {
        return _storedExec(stored, args, true);
    }

    public int _storedExec(String _stored, ArrayList<Object> args, boolean exceptionFlg) throws Exception {
        StringBuffer buf = new StringBuffer("{ ? = call " + _stored + "(");
        if (args != null)
            for (int i = 0; i < args.size(); i++) {
                buf.append("?");
                if ((i + 1) != args.size())
                    buf.append(",");
                if ((i + 1) == args.size() && exceptionFlg)
                    buf.append(",");
            }
        if (exceptionFlg)
            buf.append("?");
        buf.append(")}");
        String stored = buf.toString();
        CallableStatement cstmt = null;
        try {
            LogUtil.debug(className, "exe stored: " + stored);
            curCon = super.getCon(this.ResourceNm);
            cstmt = curCon.prepareCall(stored);
            cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
            int result_num = 2;
            if (args != null)
                for (int i = 0; i < args.size(); i++) {
                    LogUtil.debug(className, "引数" + i + "：" + args.get(i));
                    setStoredParam(cstmt, i + 2, args.get(i));
                    result_num++;
                }
            long time1, time2;
            time1 = System.currentTimeMillis();
            if (exceptionFlg)
                cstmt.registerOutParameter(result_num, java.sql.Types.VARCHAR);
            cstmt.execute();
            if (_DEBUG) {
                time2 = System.currentTimeMillis();
                LogUtil.debug(className, "pstmt execute: " + Long.toString((time2 - time1)));
            }

            if (exceptionFlg) {
                Object errMessage = cstmt.getObject(result_num);
                if (errMessage != null) {
                    LogUtil.error("stored error[" + _stored + "] " + errMessage.toString());
                    throw new Exception(errMessage.toString());
                }
            }

            return cstmt.getInt(1);

        } catch (SQLException sqle) {
            LogUtil.error(className, "SQL error：" + sqle);
            throw sqle;
        } finally {
            this.closeCurrentConnection();
            this.closeCallableStatement(cstmt);
        }
    }

    /**
     * ストアドプロシージャ実行
     *
     * @param stored
     *            実行するプロシージャ名
     * @param [args
     *            実行引数] 任意
     * @param [resultNum
     *            取得する結果セットの指定(何番目に実行したselectか)] 任意 default：1
     * @return 結果リスト(プロシージャ内で最後に実行したselect結果)
     */
    public ArrayList<HashMap<String, Object>> storedExecRs(String stored) throws Exception {
        return _storedExecRs(stored, null, 1, false);
    }

    public ArrayList<HashMap<String, Object>> storedExecRs(String stored, ArrayList<Object> args) throws Exception {
        return _storedExecRs(stored, args, 1, false);
    }

    public ArrayList<HashMap<String, Object>> storedExecRs(String stored, int resultNum) throws Exception {
        return _storedExecRs(stored, null, resultNum, false);
    }

    public ArrayList<HashMap<String, Object>> storedExecRsE(String stored) throws Exception {
        return _storedExecRs(stored, null, 1, true);
    }

    public ArrayList<HashMap<String, Object>> storedExecRsE(String stored, ArrayList<Object> args) throws Exception {
        return _storedExecRs(stored, args, 1, true);
    }

    public ArrayList<HashMap<String, Object>> storedExecRsE(String stored, int resultNum) throws Exception {
        return _storedExecRs(stored, null, resultNum, true);
    }

    public ArrayList<HashMap<String, Object>> _storedExecRs(String _stored, ArrayList<Object> args, int resultNum, boolean exceptionFlg)
                    throws Exception {
        StringBuffer buf = new StringBuffer("{ call " + _stored + "(");
        if (args != null)
            for (int i = 0; i < args.size(); i++) {
                buf.append("?");
                if ((i + 1) != args.size())
                    buf.append(",");
                if ((i + 1) == args.size() && exceptionFlg)
                    buf.append(",");
            }
        if (exceptionFlg)
            buf.append("?");
        buf.append(")}");
        String stored = buf.toString();
        CallableStatement cstmt = null;
        try {
            LogUtil.debug(className, "exe stored: " + stored);
            curCon = super.getCon(this.ResourceNm);
            cstmt = curCon.prepareCall(stored);
            int result_num = 1;
            if (args != null)
                for (int i = 0; i < args.size(); i++) {
                    setStoredParam(cstmt, i + 1, args.get(i));
                    result_num++;
                }
            if (exceptionFlg)
                cstmt.registerOutParameter(result_num, java.sql.Types.VARCHAR);
            long time1, time2, time3;
            time1 = System.currentTimeMillis();
            cstmt.execute();
            if (_DEBUG) {
                time2 = System.currentTimeMillis();
                LogUtil.debug(className, "pstmt execute: " + Long.toString((time2 - time1)));
            }
            for (int i = 1; i < resultNum; i++)
                cstmt.getMoreResults();
            ResultSet rs = cstmt.getResultSet();
            if (rs == null)
                return null;
            ArrayList<HashMap<String, Object>> recordSet = super.getRecordSet(rs);
            if (recordSet == null)
                return null;
            if (_DEBUG) {
                time3 = System.currentTimeMillis();
                LogUtil.debug(className, "create List  : " + Long.toString((time3 - time2)));
            }

            if (exceptionFlg) {
                Object errMessage = cstmt.getObject(result_num);
                if (errMessage != null) {
                    LogUtil.error("stored error[" + _stored + "] " + errMessage.toString());
                    throw new Exception(errMessage.toString());
                }
            }

            if (recordSet.size() > 0) {
                return recordSet;
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            LogUtil.error(className, "SQL error：" + sqle);
            throw sqle;
        } finally {
            this.closeCurrentConnection();
            this.closeCallableStatement(cstmt);
        }
    }

    private void setStoredParam(CallableStatement cstmt, int index, Object arg) throws Exception {
        if (arg instanceof Boolean)
            cstmt.setBoolean(index, (Boolean)arg);
        else if (arg instanceof Byte)
            cstmt.setByte(index, (Byte)arg);
        else if (arg instanceof Date)
            cstmt.setDate(index, (Date)arg);
        else if (arg instanceof Short)
            cstmt.setShort(index, (Short)arg);
        else if (arg instanceof Integer)
            cstmt.setInt(index, (Integer)arg);
        else if (arg instanceof Long)
            cstmt.setLong(index, (Long)arg);
        else if (arg instanceof Float)
            cstmt.setFloat(index, (Float)arg);
        else if (arg instanceof Double)
            cstmt.setDouble(index, (Double)arg);
        else if (arg instanceof String)
            cstmt.setString(index, (String)arg);
        else if (arg instanceof Timestamp)
            cstmt.setTimestamp(index, (Timestamp)arg);
        else
            cstmt.setObject(index, arg);
    }

    /**
     * OUTPUTが設定されたストアドプロシージャ用
     *
     * @param stored
     * @param args
     * @param regArgs
     * @return
     * @throws Exception
     */
    public ArrayList<Object> storedExecRsOutput(String stored, ArrayList<Object> args, ArrayList<Object> regArgs) throws Exception {
        return _storedExecRsOutput(stored, args, 1, false, regArgs);
    }

    /**
     * OUTPUTが設定されたストアドプロシージャ用
     * 基本は_storedExecRsと同じ
     *
     * @param _stored
     * @param args
     *            パラメータ用引数
     * @param resultNum
     * @param exceptionFlg
     *            未使用
     * @param regArgs
     *            OUTPUTパラメータ登録用引数
     * @return
     * @throws Exception
     */
    public ArrayList<Object> _storedExecRsOutput(String _stored, ArrayList<Object> args, int resultNum, boolean exceptionFlg,
                    ArrayList<Object> regArgs) throws Exception {
        StringBuffer buf = new StringBuffer("{ call " + _stored + "(");
        if (args != null)
            for (int i = 0; i < args.size(); i++) {
                buf.append("?");
                if ((i + 1) != args.size())
                    buf.append(",");
                if ((i + 1) == args.size() && exceptionFlg)
                    buf.append(",");
            }

        //
        for (int i = 0; i < regArgs.size(); i++) {
            if (i == 0)
                buf.append(",");
            buf.append("?");
            if ((i + 1) != regArgs.size())
                buf.append(",");
            if ((i + 1) == regArgs.size() && exceptionFlg)
                buf.append(",");
        }
        //

        if (exceptionFlg)
            buf.append("?");
        buf.append(")}");
        String stored = buf.toString();
        CallableStatement cstmt = null;
        try {
            LogUtil.debug(className, "exe storedOutput: " + stored);
            curCon = super.getCon(this.ResourceNm);
            cstmt = curCon.prepareCall(stored);
            int result_num = 1;
            if (args != null)
                for (int i = 0; i < args.size(); i++) {
                    setStoredParam(cstmt, i + 1, args.get(i));
                    result_num++;
                }

            //
            int start = args.size() + 1;
            for (int i = 0; i < regArgs.size(); i++) {
                registerOutParam(cstmt, start, regArgs.get(i));
                start++;
                result_num++;
            }
            //

            if (exceptionFlg)
                cstmt.registerOutParameter(result_num, java.sql.Types.VARCHAR);
            long time1, time2, time3;
            time1 = System.currentTimeMillis();
            cstmt.execute();
            if (_DEBUG) {
                time2 = System.currentTimeMillis();
                LogUtil.debug(className, "pstmt execute: " + Long.toString((time2 - time1)));
            }

            ArrayList<Object> record = new ArrayList<Object>();
            for (int i = 0; i < regArgs.size(); i++) {

                if (regArgs.get(i) instanceof Character)
                    record.add(cstmt.getString(args.size() + 1 + i));
                else if (regArgs.get(i) instanceof Character)
                    record.add(cstmt.getString(args.size() + 1 + i));
                else if (regArgs.get(i) instanceof String)
                    record.add(cstmt.getString(args.size() + 1 + i));
                else if (regArgs.get(i) instanceof BigDecimal)
                    record.add(cstmt.getBigDecimal(args.size() + 1 + i));
                else if (regArgs.get(i) instanceof Integer)
                    record.add(cstmt.getInt(args.size() + 1 + i));
            }

            // record.add(cstmt.getInt(26));
            // record.add(cstmt.getBigDecimal(27));
            // record.add(cstmt.getString(28));
            // record.add(cstmt.getInt(29));
            // record.add(cstmt.getString(30));

            /*
             * for(int i=1; i<resultNum; i++) cstmt.getMoreResults();
             * ResultSet rs = cstmt.getResultSet();
             * if(rs == null) return null;
             * ArrayList<HashMap<String , Object>> recordSet = super.getRecordSet(rs);
             * if(recordSet == null) return null;
             * if(_DEBUG){
             * time3 = System.currentTimeMillis();
             * LogUtil.debug(className, "create List  : "+Long.toString((time3 - time2)));
             * }
             *
             * if(exceptionFlg){
             * Object errMessage = cstmt.getObject(result_num);
             * if(errMessage != null){
             * LogUtil.error("stored error["+_stored+"] " + errMessage.toString());
             * throw new Exception(errMessage.toString());
             * }
             * }
             *
             * if(recordSet.size() > 0){
             * return recordSet;
             * }else{
             * return null;
             * }
             */

            return record;

        } catch (SQLException sqle) {
            LogUtil.error(className, "SQL error：" + sqle);
            throw sqle;
        } finally {
            this.closeCurrentConnection();
            this.closeCallableStatement(cstmt);
        }
    }

    /**
     * OUTPUT取得用
     *
     * @param cstmt
     * @param index
     * @param arg
     * @throws Exception
     */
    private void registerOutParam(CallableStatement cstmt, int index, Object arg) throws Exception {

        if (arg instanceof String)
            cstmt.registerOutParameter(index, Types.VARCHAR);
        else if (arg instanceof BigDecimal)
            cstmt.registerOutParameter(index, Types.DECIMAL);
        else if (arg instanceof Integer)
            cstmt.registerOutParameter(index, Types.INTEGER);
        else if (arg instanceof Character)
            cstmt.registerOutParameter(index, Types.CHAR);
    }

    /**
     * resultset クローズ
     *
     * @param 閉じるresultSet
     */
    public void closeResultSet(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            LogUtil.error(className, "resultSet close error：" + e);
        }
    }

    /**
     * Statement クローズ
     *
     * @param 閉じるStatement
     */
    public void closeStatement(Statement stmt) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            LogUtil.error(className, "Statement close error：" + e);
        }
    }

    /**
     * PreparedStatement クローズ
     *
     * @param 閉じるPreparedStatement
     */
    public void closePrepareStatement(PreparedStatement pstmt) {
        try {
            if (pstmt != null)
                pstmt.close();
        } catch (SQLException e) {
            LogUtil.error(className, "PrepareStatement close error：" + e);
        }
    }

    /**
     * CallableStatement クローズ
     *
     * @param 閉じるCallableStatement
     */
    public void closeCallableStatement(CallableStatement cstmt) {
        try {
            if (cstmt != null)
                cstmt.close();
        } catch (SQLException e) {
            LogUtil.error(className, "CallableStatement close error：" + e);
        }
    }

    /**
     * connection クローズ
     */
    public void closeCurrentConnection() {
        try {
            super.closeCon(curCon);
        } catch (Exception e) {
            LogUtil.error(className, "connection close error：" + e);
        }
    }

    /**
     * connection クローズ
     */
    public void closeCurrentConnection(Connection curCon) {
        try {
            super.closeCon(curCon);
        } catch (Exception e) {
            LogUtil.error(className, "connection close error：" + e);
        }
    }

    // 日時型 → 文字列
    private String timestampToStr(Timestamp timestamp) {
        String format = "yyyy-MM-dd HH:mm:ss.SSS";
        return new SimpleDateFormat(format).format(timestamp);
    }

    private String dateToStr(Date date) {
        String format = "yyyy-MM-dd";
        return new SimpleDateFormat(format).format(date);
    }
}
