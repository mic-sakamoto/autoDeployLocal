package db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.opensymphony.xwork2.ActionSupport;

import utils.log4j.LogUtil;


//import java.sql.ResultSet;

public class DbConnection extends ActionSupport  {

	//protected static Connection curCon = null;
	protected Connection curCon = null;

	private String className = this.getClass().getName();

	public String timestampFormat = "yyyy/MM/dd HH:mm:ss";
	public String timeFormat = "HH:mm:ss";

	/**
	 * DBコネクションを取得します。
	 * @return DBへの接続
	 * @throws SQLException
	 */
	public Connection getCon( String name ) throws Exception {

		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup( name );
			curCon = ds.getConnection();
		} catch (Exception e) {
			LogUtil.error( "コネクションの取得でエラー発生！！" );
			e.printStackTrace();
			throw e;
		}
		return curCon;
	}

	/**
	 * コネクションを切断する
	 * @return
	 */
	protected void closeCon(Connection _curCon) throws Exception {

		if ( _curCon != null && !_curCon.isClosed() ) {
			LogUtil.debug( className , ">>>> コネクションを切断します。>>>>" );
			_curCon.close();
		}
	}


	/**
	 * DBより取得したresultsetをListにして返します。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	protected ArrayList<HashMap<String , Object>> getRecordSet( ResultSet rs ) throws Exception {

		ResultSetMetaData rsmd = rs.getMetaData();

		//結果のカラム数を取得
		int iNumCol = rsmd.getColumnCount();

		HashMap<String , Object> record = null;

		ArrayList<HashMap<String , Object>> recordSet = new ArrayList<HashMap<String , Object>>();

		while (rs.next()) {

			record = new HashMap<String , Object>();

			//結果をHASHMAPに格納します。キーがカラム名になります。
			for (int i = 1; i <= iNumCol; i++){
				//カラム名取得
				//String ColumnName = rsmd.getColumnName(i);
				String ColumnName = rsmd.getColumnLabel(i);
				//カラムの型を取得
				String sColumnType = rsmd.getColumnTypeName(i);
				//
				int iColumnType = rsmd.getColumnType(i);
				//LogUtil.info(className,"カラム名："+ ColumnName + "　　データ型名：" + sColumnType + "　　データ型コード：" + iColumnType);

				switch (iColumnType){
				case java.sql.Types.NULL:
					record.put(ColumnName, null);
					break;
				case java.sql.Types.CHAR:
				case java.sql.Types.VARCHAR:
					record.put(ColumnName, rs.getString(ColumnName));
					break;
				case java.sql.Types.INTEGER:
				case java.sql.Types.SMALLINT:
					int vali = rs.getInt(ColumnName);
					if(rs.wasNull()){
						record.put(ColumnName, null);
					}else{
						record.put(ColumnName, vali);
					}
					break;
				/*
				case java.sql.Types.SMALLINT:
					short valsh = rs.getShort(ColumnName);
					if(rs.wasNull()){
						record.put(ColumnName, null);
					}else{
						record.put(ColumnName, valsh);
					}
					break;
				*/
				case java.sql.Types.DOUBLE:
					if(sColumnType.equals("float")){
						float valf = rs.getFloat(ColumnName);
						if(rs.wasNull()){
							record.put(ColumnName, null);
						}else{
							record.put(ColumnName, valf);
						}
					}else{
						record.put(ColumnName, rs.getDouble(ColumnName));
						double vald = rs.getDouble(ColumnName);
						if(rs.wasNull()){
							record.put(ColumnName, null);
						}else{
							record.put(ColumnName, vald);
						}
					}
					break;
				case java.sql.Types.TIMESTAMP:
					SimpleDateFormat sdf_tsf =new SimpleDateFormat( timestampFormat );
					if ( rs.getTimestamp(ColumnName) != null ) {
						record.put(ColumnName, sdf_tsf.format( rs.getTimestamp(ColumnName) ) );
					} else {
						record.put(ColumnName ,  rs.getTimestamp(ColumnName) );
					}
					break;
				case java.sql.Types.TIME:
					SimpleDateFormat sdf_tf =new SimpleDateFormat( timeFormat );
					if ( rs.getTimestamp(ColumnName) != null ) {
						record.put(ColumnName, sdf_tf.format( rs.getTimestamp(ColumnName) ) );
					} else {
						record.put(ColumnName ,  rs.getTimestamp(ColumnName) );
					}
					break;
				case java.sql.Types.DECIMAL:
					BigDecimal valBD = rs.getBigDecimal(ColumnName);
					if(rs.wasNull()){
						record.put(ColumnName, null);
					}else{
						record.put(ColumnName, valBD);
					}
					break;
				case java.sql.Types.BIGINT:
					//ほんとはlongが正しい。
					BigDecimal valBD_ = rs.getBigDecimal(ColumnName);
					if(rs.wasNull()){
						record.put(ColumnName, null);
					}else{
						record.put(ColumnName, valBD_);
					}
					break;
				default:
					record.put(ColumnName, rs.getObject(ColumnName));
				}

			}
			if ( record.size() > 0 ){
				recordSet.add( record );
			}
		}
		return recordSet;
	}


}
