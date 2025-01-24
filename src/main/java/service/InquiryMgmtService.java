package service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.ContractMgmtDao;
import db.dao.InquiryMgmtDao;
import model.ContractMgmtModel;
import model.InquiryMgmtModel;
import utils.StringUtil;
import utils.log4j.LogUtil;

public class InquiryMgmtService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    InquiryMgmtDao dao = new InquiryMgmtDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();
    ContractMgmtModel conModel = new ContractMgmtModel();
    ContractMgmtDao conDao = new ContractMgmtDao();

    public boolean init(InquiryMgmtModel model) throws Exception {
        return true;
    }

    /**
     * お問い合わせ管理情報取得
     */
    public boolean getSearchList(InquiryMgmtModel model) throws Exception {

        try {

            if (model.getUserId().equals("")) {
                model.setSuccessFlg("9");
                model.setMessage("ユーザ情報がありません");
                return false;
            }

            // データ取得
            ArrayList<HashMap<String, Object>> inquiryInfoList = dao.getSearchList(model);
            if (inquiryInfoList != null) {

                model.setResult(inquiryInfoList);
                model.setSuccessFlg("1");

            } else {
                model.setSuccessFlg("9");
                model.setMessage("お問い合わせ管理情報がありません");
                return false;
            }

            return true;

        } catch (Exception e) {
            LogUtil.info(e);
            e.printStackTrace();
            model.setSuccessFlg("9");
            return false;
        }
    }

    /**
     * お問い合わせ管理情報取得
     */
    public boolean getSearchDetail(InquiryMgmtModel model) throws Exception {

        Connection con = dao.getCon();
        con.setAutoCommit(false);

        try {

            if (model.getUserId().equals("")) {
                model.setSuccessFlg("9");
                model.setMessage("ユーザ情報がありません");
                return false;
            }

            // データ取得
            ArrayList<HashMap<String, Object>> inquiryInfoList = dao.getSearchList(model);
            if (inquiryInfoList != null) {

                // 未読フラグ更新
                if (model.getRoleType().equals("1")) {
                    if (StringUtil.dataToString(inquiryInfoList.get(0).get("StaffIsUnread")).equals("1")) {
                        dao.updateIsUnread(model, con);
                    }
                } else if (model.getRoleType().equals("2") || model.getRoleType().equals("3")) {
                    if (StringUtil.dataToString(inquiryInfoList.get(0).get("CustomerIsUnread")).equals("1")) {
                        dao.updateIsUnread(model, con);
                    }
                }

                model.setResult(inquiryInfoList);
                model.setSuccessFlg("1");

            } else {
                model.setSuccessFlg("9");
                model.setMessage("お問い合わせ管理情報がありません");
                return false;
            }

        } catch (Exception e) {
            con.rollback();
            LogUtil.info(e);
            e.printStackTrace();
            model.setSuccessFlg("9");
            model.setMessage("システムエラーが発生しました");
            return false;

        } finally {
            con.setAutoCommit(true);
            con.close();
        }

        return true;
    }

    /**
     * 顧客の場合、契約が1件以上あるか確認
     */
    public boolean checkContract(InquiryMgmtModel model) throws Exception {

        try {

            if (model.getUserId().equals("")) {
                model.setSuccessFlg("9");
                model.setMessage("ユーザ情報がありません");
                return false;
            }

            if (model.getRoleType().equals("3")) {
                conModel.setRoleType("3");
                ArrayList<HashMap<String, Object>> contractInfoList = conDao.getSearchList(conModel);
                if (contractInfoList == null) {
                    model.setResult(null);
                    model.setSuccessFlg("9");
                    model.setMessage("契約情報がありません");
                    return false;
                }

            }

            model.setResult(null);
            model.setSuccessFlg("1");
            return true;

        } catch (Exception e) {
            LogUtil.info(e);
            e.printStackTrace();
            model.setSuccessFlg("9");
            return false;
        }
    }

    /**
     * お問い合わせ管理情報登録 新規問い合わせ
     */
    public boolean inquiry(InquiryMgmtModel model) throws Exception {

        Connection con = dao.getCon();
        con.setAutoCommit(false);

        try {

            // スタッフ
            if (model.getRoleType().equals("1")) {
                // 宛先チェック
                String answererId = model.getAnswererId();
                if (answererId == null || answererId.equals("")) {
                    model.setSuccessFlg("9");
                    model.setMessage("宛先が入力されていません。");
                    return false;
                } else {
                    ArrayList<HashMap<String, Object>> userInfoList = dao.getUserIngoList(model);
                    if (userInfoList == null) {
                        model.setResult(null);
                        model.setSuccessFlg("9");
                        model.setMessage("宛先情報がありません。");
                        return false;
                    }
                }

                // 「同意」ボタン非対象の場合が本文チェック
                if (model.getAgreementBodyKbn().equals("0") || model.getAgreementBodyKbn() == null) {
                    // お問い合わせ内容が1000文字以内かチェック
                    String inquireBody = model.getInquireBody();
                    if (inquireBody == null || inquireBody.equals("")) {
                        model.setSuccessFlg("9");
                        model.setMessage("お問い合わせ内容が入力されていません。");
                        return false;
                    }
                    if (inquireBody.length() > 1000) {
                        model.setSuccessFlg("9");
                        model.setMessage("お問い合わせ内容は1000文字以内で入力してください。");
                        return false;
                    }
                }
            }

            // 加盟店・顧客
            else {
                // お問い合わせ内容が1000文字以内かチェック
                String inquireBody = model.getInquireBody();
                if (inquireBody == null || inquireBody.equals("")) {
                    model.setSuccessFlg("9");
                    model.setMessage("お問い合わせ内容が入力されていません。");
                    return false;
                }
                if (inquireBody.length() > 1000) {
                    model.setSuccessFlg("9");
                    model.setMessage("お問い合わせ内容は1000文字以内で入力してください。");
                    return false;
                }
            }

            // データ登録
            dao.insertInquire(model, con);

            model.setSuccessFlg("1");
            model.setMessage("送信しました。");

        } catch (Exception e) {
            con.rollback();
            LogUtil.info(e);
            e.printStackTrace();
            model.setSuccessFlg("9");
            model.setMessage("送信失敗しました。");
            return false;

        } finally {
            con.setAutoCommit(true);
            con.close();
        }

        return true;
    }

    /**
     * お問い合わせ管理情報登録 返信
     */
    public boolean answer(InquiryMgmtModel model) throws Exception {

        Connection con = dao.getCon();
        con.setAutoCommit(false);

        try {

            // 未返信かチェック
            ArrayList<HashMap<String, Object>> answerInfoList = dao.getSearchList(model);
            if (answerInfoList.get(0).get("AnswerDate") != null) {
                model.setResult(null);
                model.setSuccessFlg("9");
                model.setMessage("既にメッセージを送信しています。");
                return false;
            }

            // 「同意」ボタン非対象の場合が本文チェック
            if (StringUtil.dataToString(answerInfoList.get(0).get("AgreementBodyKbn")).equals("0") || answerInfoList.get(0).get("AgreementBodyKbn") == null) {
                // 1000文字以内かチェック
                String answerBody = model.getAnswerBody();
                if (answerBody == null || answerBody.equals("")) {
                    model.setSuccessFlg("9");
                    model.setMessage("本文が入力されていません。");
                    return false;
                }
                if (answerBody.length() > 1000) {
                    model.setSuccessFlg("9");
                    model.setMessage("本文は1000文字以内で入力してください。");
                    return false;
                }
            }

            // データ登録
            dao.updateInquire(model, con);

            model.setSuccessFlg("1");
            model.setMessage("送信しました。");

        } catch (Exception e) {
            con.rollback();
            LogUtil.info(e);
            e.printStackTrace();
            model.setSuccessFlg("9");
            model.setMessage("送信失敗しました。");
            return false;

        } finally {
            con.setAutoCommit(true);
            con.close();
        }

        return true;

    }

    /**
     * 契約番号から宛先に指定するIDを検索
     */
    public boolean getAnswererInfo(InquiryMgmtModel model) throws Exception {

        try {

            if (model.getUserId().equals("")) {
                model.setSuccessFlg("9");
                model.setMessage("ユーザ情報がありません");
                return false;
            }
            if (model.getWebAppId().equals("")) {
                model.setSuccessFlg("9");
                model.setMessage("契約IDを指定してください");
                return false;
            }

            conModel.setConditionWebAppId(model.getWebAppId());
            ArrayList<HashMap<String, Object>> contractInfoList = conDao.getSearchList(conModel);
            if (contractInfoList == null) {
                model.setResult(null);
                model.setSuccessFlg("9");
                model.setMessage("契約情報がありません");
                return false;
            }

            model.setResult(contractInfoList);
            model.setSuccessFlg("1");
            return true;

        } catch (Exception e) {
            LogUtil.info(e);
            e.printStackTrace();
            model.setSuccessFlg("9");
            return false;
        }
    }

}