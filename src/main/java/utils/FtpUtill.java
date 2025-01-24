package utils;

import java.io.FileInputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.opensymphony.xwork2.ActionSupport;

import utils.log4j.LogUtil;

public class FtpUtill {



	private static String ClassName_="FtpClient";

	private static String FTP_URL;
	private static String FTP_LOGIN_ID;
	private static String FTP_LOGIN_PASS;
	private final static int Port = 21;
	private static FTPClient ftpclient = new FTPClient();
	private static final int RetryCount = 3;
	private static final int Sleeptime = 500 * 3;          //1.5秒

	/** エンコード */
	private final static String ENCODING = "utf-8";


//	public FtpUtill(ActionSupport as)throws Exception{
//		this.FTP_URL = as.getText("IMAGE-ORG-FTP-URL");
//		this.FTP_LOGIN_ID = as.getText("IMAGE-ORG-FTP-LOGIN-ID");
//		this.FTP_LOGIN_PASS = as.getText("IMAGE-ORG-FTP-LOGIN-PASS");
//	}

	public static void setProp(ActionSupport as)throws Exception{
		FTP_URL = as.getText("IMAGE-ORG-FTP-URL");
		FTP_LOGIN_ID = as.getText("IMAGE-ORG-FTP-LOGIN-ID");
		FTP_LOGIN_PASS = as.getText("IMAGE-ORG-FTP-LOGIN-PASS");
	}

	public static void connect() throws Exception {

		try {

			ftpclient.setDefaultTimeout(5 * 100);
			ftpclient.connect(FTP_URL, Port);

			/*接続完了後のタイムアウト*/
			ftpclient.setSoTimeout(5 * 100);
			ftpclient.setDataTimeout(5 * 100);

		} catch (Exception e) {
			throw new Exception("FTPサーバ接続失敗 " + ftpclient.getReplyCode());
		}

		if (!FTPReply.isPositiveCompletion(ftpclient.getReplyCode())) {
			//接続エラー処理
			throw new Exception("FTPサーバ接続失敗 "  + ftpclient.getReplyCode());

		}

		//ログイン
		if (ftpclient.login(FTP_LOGIN_ID, FTP_LOGIN_PASS)==false) {
			//ログイン処理失敗
			throw new Exception("FTPサーバログイン失敗"  + ftpclient.getReplyCode());
		}
	}

	/**
	 * FTP切断処理
	 * @throws Exception
	 */
	public static void disconnect() throws Exception {
		if (ftpclient != null && ftpclient.isConnected()) {
			try{
				ftpclient.logout();
			}catch(Exception e){
				LogUtil.warn(e);
			}finally{
				ftpclient.disconnect();
			}
		}
	}


	/**
	 * FTPサーバへ画像送信処理
	 * @param remoteFilepath 転送先ファイルパス
	 * @param is             ローカル画像ファイルへのストリーム
	 * @return               処理結果 (0:成功 -1:失敗)
	 * @throws Exception
	 */
	public static int storeFile(String basedir, String remotedir, String remoteFilePath, FileInputStream is) throws Exception {

		boolean isExist = false;
		String[] names = null;

		if (ftpclient==null || !ftpclient.isConnected()) {
			LogUtil.debug(ClassName_, "FTP接続してないのでします[" + remoteFilePath + "]");

			try {
				connect();                                                                         //接続処理
				LogUtil.debug(ClassName_, "FTP接続完了");
			} catch (Exception e) {                                                                //以下リトライ処理

				for (int i=0;i<RetryCount;i++) {

					Thread.sleep(Sleeptime);                                                       //待機する
					LogUtil.warn(ClassName_, "FTP接続リトライ[" + (i+1) + "]回目");
					                                                                 //念のため接続を切る処理を入れる
					try {
						connect();                                                                 //接続処理
						LogUtil.warn(ClassName_, "FTP接続リトライ[" + (i+1) + "]回目で接続成功");
						break;
					} catch (Exception re) {
						LogUtil.warn(ClassName_, e.getMessage());
						if (ftpclient!=null || ftpclient.isConnected()) {
							ftpclient.disconnect();
						}
						if (i==(RetryCount-1)) {
							is.close();
							return -1;
						}

					}

				}
			}
		}

		try{

			//ACTIVEモードに設定
			ftpclient.enterLocalActiveMode();

			//バイナリモード
			ftpclient.setFileType(FTP.BINARY_FILE_TYPE);

			//ディレクトリが存在するか確認
			isExist = false;
			names = ftpclient.listNames();
			if(names!=null){
				for(String name : names) {
				    if(name.equals(basedir)) {
				        isExist = true;
				    }
				}
			}

			//ディレクトリがなければ作成する
			if(isExist==false){
				if(ftpclient.makeDirectory(basedir)==false){
					return -1;
				}
			}

			ftpclient.changeWorkingDirectory(basedir);

			//ディレクトリが存在するか確認
			isExist = false;
			names = ftpclient.listNames();
			if(names!=null){
				for(String name : names) {
				    if(name.equals(remotedir)) {
				        isExist = true;
				    }
				}
			}

			//ディレクトリがなければ作成する
			if(isExist==false){
				if(ftpclient.makeDirectory(remotedir)==false){
					return -1;
				}
			}


			//FTP送信
			ftpclient.setBufferSize(1024 * 1024);
			if(ftpclient.storeFile(remoteFilePath, is)){
				return 0;//成功
			}else{
				return -1;//失敗
			}

		} catch (Exception e) {
			LogUtil.warn(ClassName_, "ファイル送信失敗 リトライを行ないます FTP ReplyCode:" + ftpclient.getReplyCode());
			disconnect();//FTP接続が残っていたら切る
			if (ftpclient==null || !ftpclient.isConnected()) {
				LogUtil.debug(ClassName_, "FTP接続してないのでします[" + remoteFilePath + "]");
				try {
					connect();                                                                         //接続処理
					LogUtil.debug(ClassName_, "FTP接続完了");
				} catch (Exception e2) {                                                                //以下リトライ処理
					for (int i=0;i<RetryCount;i++) {
						Thread.sleep(Sleeptime);                                                       //ちょっと待機する
						LogUtil.warn(ClassName_, "FTP接続リトライ[" + (i+1) + "]回目");
						//念のため接続を切る処理を入れる
						try {
							connect();                                                                 //接続処理
							LogUtil.warn(ClassName_, "FTP接続リトライ[" + (i+1) + "]回目で接続成功");
							break;
						} catch (Exception re) {
							LogUtil.error(ClassName_, re.getMessage());
							if (ftpclient!=null || ftpclient.isConnected()) {
								ftpclient.disconnect();
							}
							if (i==(RetryCount-1)) {
								is.close();
								return -1;
							}
						}
					}
				}
			}

			try{

				//ACTIVEモードに設定
				ftpclient.enterLocalActiveMode();

				//バイナリモード
				ftpclient.setFileType(FTP.BINARY_FILE_TYPE);

				//ディレクトリが存在するか確認
				isExist = false;
				names = ftpclient.listNames();
				if(names!=null){
					for(String name : names) {
					    if(name.equals(basedir)) {
					        isExist = true;
					    }
					}
				}
				//ディレクトリがなければ作成する
				if(isExist==false){
					if(ftpclient.makeDirectory(basedir)==false){
						return -1;
					}
				}

				ftpclient.changeWorkingDirectory(basedir);

				//ディレクトリが存在するか確認
				isExist = false;
				names = ftpclient.listNames();
				if(names!=null){
					for(String name : names) {
					    if(name.equals(remotedir)) {
					        isExist = true;
					    }
					}
				}

				//ディレクトリがなければ作成する
				if(isExist==false){
					if(ftpclient.makeDirectory(remotedir)==false){
						return -1;
					}
				}

				//FTP送信
				ftpclient.setBufferSize(1024 * 1024);
				if(ftpclient.storeFile(remoteFilePath, is)){
					return 0;//成功
				}else{
					return -1;//失敗
				}

			} catch (Exception e3) {
				LogUtil.warn(ClassName_, "FTP接続リトライを行なってもFTP送信失敗" + ftpclient.getReplyCode());
				return -1;
			}
		} finally {
			ftpclient.changeWorkingDirectory("/");
			is.close();
		}

	}



	/**
	 * FTPサーバからの画像取得処理
	 * @param remoteFilename 画像転送元
	 * @param os             画像転送先
	 * @return               転送元ファイルのサイズ
	 * @throws Exception
	 */
	public static long retrieveFile (String remoteFilename, OutputStream os) throws Exception {

		if (ftpclient==null || !ftpclient.isConnected()) {
			LogUtil.debug(ClassName_, "FTP接続してないのでします[" + remoteFilename + "]");

			try {
				connect();                                                                         //接続処理
				LogUtil.debug(ClassName_, "FTP接続完了");
			} catch (Exception e) {                                                                //以下リトライ処理

				for (int i=0;i<RetryCount;i++) {

					Thread.sleep(Sleeptime);                                                       //ちょっと待機する
					LogUtil.warn(ClassName_, "FTP接続リトライ[" + (i+1) + "]回目");
					                                                                 //念のため接続を切る処理を入れる
					try {
						connect();                                                                 //接続処理
						LogUtil.warn(ClassName_, "FTP接続リトライ[" + (i+1) + "]回目で接続成功");
						break;
					} catch (Exception re) {
						LogUtil.error(ClassName_, e.getMessage());
						if (ftpclient!=null || ftpclient.isConnected()) {
							ftpclient.disconnect();
						}
						if (i==(RetryCount-1)) {
							os.close();
							return -1;
						}

					}

				}
			}
		}

		try{
			//バイナリモード
			ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
//			ftpclient.pasv();

//			FTPFile[] f = ftpclient.listFiles(remoteFilename);

			ftpclient.setBufferSize(1024 * 1024);
			ftpclient.retrieveFile(remoteFilename, os);

			return 1;
		} catch (Exception e) {
			LogUtil.warn(ClassName_, "ファイル取得失敗 リトライを行ないます FTP ReplyCode:" + ftpclient.getReplyCode());
			disconnect();//FTP接続が残っていたら切る
			if (ftpclient==null || !ftpclient.isConnected()) {
				LogUtil.debug(ClassName_, "FTP接続してないのでします[" + remoteFilename + "]");
				try {
					connect();                                                                         //接続処理
					LogUtil.debug(ClassName_, "FTP接続完了");
				} catch (Exception e2) {                                                                //以下リトライ処理
					for (int i=0;i<RetryCount;i++) {
						Thread.sleep(Sleeptime);                                                       //ちょっと待機する
						LogUtil.warn(ClassName_, "FTP接続リトライ[" + (i+1) + "]回目");
						//念のため接続を切る処理を入れる
						try {
							connect();                                                                 //接続処理
							LogUtil.warn(ClassName_, "FTP接続リトライ[" + (i+1) + "]回目で接続成功");
							break;
						} catch (Exception re) {
							LogUtil.error(ClassName_, re.getMessage());
							if (ftpclient!=null || ftpclient.isConnected()) {
								ftpclient.disconnect();
							}
							if (i==(RetryCount-1)) {
								os.close();
								return -1;
							}
						}
					}
				}
			}

			try{
				//バイナリモード
				ftpclient.setFileType(FTP.BINARY_FILE_TYPE);

//				FTPFile[] f = ftpclient.listFiles(remoteFilename);

				ftpclient.setBufferSize(1024 * 1024);
				ftpclient.retrieveFile(remoteFilename, os);

				return 1;//再接続後にFTP取得成功した場合
			} catch (Exception e3) {
				LogUtil.warn(ClassName_, "FTP取得リトライを行なってもFTP取得失敗 FTP ReplyCode:" + ftpclient.getReplyCode());
				return -1;//再接続をしてもFTP取得失敗した場合は処理中の画像を飛ばし、次の画像の取得処理に移る
			}
		} finally {
			os.close();
		}

	}



}

