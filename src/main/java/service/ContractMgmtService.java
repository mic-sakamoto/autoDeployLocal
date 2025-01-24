package service;

import java.util.ArrayList;
import java.util.HashMap;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.ContractMgmtDao;
import model.ContractMgmtModel;
import utils.CommonUtil;
import utils.StringUtil;

public class ContractMgmtService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    ContractMgmtDao dao = new ContractMgmtDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(ContractMgmtModel model) throws Exception {

        return true;

    }

    /**
     * リスト生成
     */
    public boolean getSearchList(ContractMgmtModel model) throws Exception {

        // 契約データ取得
        ArrayList<HashMap<String, Object>> searchList = dao.getSearchList(model);
        if (searchList != null) {

            for (HashMap<String, Object> map : searchList) {
                String statusKbn = StringUtil.dataToString(map.get("StatusKbn"));

                HashMap<String, Object> statusMap = comSvc.getStatusText(statusKbn, model.getRoleType());

                map.put("StatusText", StringUtil.dataToString(statusMap.get("StatusText")));
                map.put("StatusColor", StringUtil.dataToString(statusMap.get("StatusColor")));

            }

        } else {
            model.setSuccessFlg("9");
            model.setMessage("契約情報がありません");
            return false;
        }

        model.setSearchList(searchList);

        return true;
    }

    /**
     * ステータスチェック
     */
    public boolean getNowStatus(ContractMgmtModel model) throws Exception {

        ArrayList<HashMap<String, Object>> contractList = dao.getContract(model);

        String status = "0";
        if (contractList != null) {
            String statusKbn = StringUtil.dataToString(contractList.get(0).get("StatusKbn"));
            String verifiedDate = StringUtil.dataToString(contractList.get(0).get("VerifiedDate"));
            String screeningAppDate = StringUtil.dataToString(contractList.get(0).get("ScreeningAppDate"));
            String screeningAppResDate = StringUtil.dataToString(contractList.get(0).get("ScreeningAppResDate"));

            // ベリファイ完了
            if (CommonUtil.chkNotNullEmpty(verifiedDate)) {
                status = "2";
            } else {
                // 審査申込済みなら
                if (CommonUtil.chkNotNullEmpty(screeningAppDate)) {
                    status = "1";
                }
                // 審査結果が入っていれば
                if (CommonUtil.chkNotNullEmpty(screeningAppResDate)) {
                    status = "1";
                }
            }
        }

        model.setStatus(status);

        return true;
    }

}
