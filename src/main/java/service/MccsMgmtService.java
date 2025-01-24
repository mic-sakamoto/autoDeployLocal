package service;

import java.util.ArrayList;
import java.util.HashMap;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.MccsMgmtDao;
import model.MccsMgmtModel;
import utils.CommonUtil;
import utils.ObicApiUtill;
import utils.log4j.LogUtil;

public class MccsMgmtService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    MccsMgmtDao dao = new MccsMgmtDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(MccsMgmtModel model) throws Exception {
        return true;
    }
    
    /**
     * MCCS車両管理情報取得
     */
    public boolean getSearchList(MccsMgmtModel model) throws Exception {
        
        try {
        
            if(model.getUserId().equals("")){
                model.setSuccessFlg("9");
                model.setMessage("ユーザ情報がありません");
                return false;
            }
    
            // データ取得
            ArrayList<HashMap<String, Object>> mccsList = new ArrayList<HashMap<String, Object>>();
            ArrayList<HashMap<String, Object>> mccsInfoList = dao.getSearchList(model);
            if (mccsInfoList != null) {
                for(HashMap<String, Object> row : mccsInfoList) {
                    
                    // OBIC審査契約状況API 車種・グレードは都度OBIC審査契約状況APIから取得
                    ObicApiUtill.ShinsaKeiyakuJokyoReqData shinsaKeiyakuJokyoReqData = new ObicApiUtill.ShinsaKeiyakuJokyoReqData();
//                    shinsaKeiyakuJokyoReqData.setWebmousikomiid(StringUtil.dataToString(row.get("WebAppId")));
                    //TODO デモサイト用にサンプルWEB受付IDで固定
                    shinsaKeiyakuJokyoReqData.setWebmousikomiid("2020011024001");
                    
                    // API実行
                    HashMap<String, String> jsonMap = ObicApiUtill.ShinsaKeiyakuJokyoApi(as, shinsaKeiyakuJokyoReqData);

                    // APIから取得
                    if (jsonMap != null) {
                        String syameiGrade = jsonMap.get("wun_syamei") + " " + jsonMap.get("wun_grade");                        
                        if (CommonUtil.chkNotNullEmpty(model.getConditionModel())) {
                            // 部分一致チェック
                            if (syameiGrade != null && model.getConditionModel() != null) {
                                // 全角半角を統一して比較
                                String normalizedSyameiGrade = CommonUtil.toHankaku(syameiGrade);
                                String normalizedConditionModel = CommonUtil.toHankaku(model.getConditionModel());
                                if (!normalizedSyameiGrade.contains(normalizedConditionModel)) {
                                    // 一致しない場合、フィルターで除外
                                    continue;
                                }
                            }
                        }
                        
                        row.put("Model", syameiGrade);
                        mccsList.add(row);
   
                    } else {
                        throw new Exception("APIの結果取得に失敗しました。");
                    }

                }
                
                if(mccsList.isEmpty()) {
                    model.setSuccessFlg("9");
                    model.setMessage("MCCS車両管理情報がありません");
                    return false;
                }
                
                model.setResult(mccsList);
                model.setSuccessFlg("1");
                
            } else {
                model.setSuccessFlg("9");
                model.setMessage("MCCS車両管理情報がありません");
                return false;
            }
    
            return true;

        } catch(Exception e) {
            LogUtil.info(e);
            e.printStackTrace();
            model.setSuccessFlg("9");
            return false;
        }
    }
    
    
}