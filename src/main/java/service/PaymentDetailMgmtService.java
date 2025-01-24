package service;

import java.util.ArrayList;
import java.util.HashMap;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.PaymentDetailMgmtDao;
import model.CommonModel;
import model.PaymentDetailMgmtModel;
import utils.log4j.LogUtil;

public class PaymentDetailMgmtService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    PaymentDetailMgmtDao dao = new PaymentDetailMgmtDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();
    CommonModel comModel = new CommonModel();

    public boolean init(PaymentDetailMgmtModel model) throws Exception {
        return true;
    }

    /**
     * 精算明細票情報取得
     */
    public boolean getSearchList(PaymentDetailMgmtModel model) throws Exception {

        try {
            if (model.getUserId().equals("")) {
                model.setSuccessFlg("9");
                model.setMessage("ユーザ情報がありません");
                return false;
            }

            // データ取得
            ArrayList<HashMap<String, Object>> paymentDetailInfoList = dao.getSearchList(model);

            if (paymentDetailInfoList != null) {
                model.setResult(paymentDetailInfoList);
                model.setSuccessFlg("1");

            } else {
                model.setSuccessFlg("9");
                model.setMessage("精算明細票情報がありません");
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

}