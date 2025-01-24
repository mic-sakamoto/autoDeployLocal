package service;

import java.util.ArrayList;
import java.util.HashMap;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.VehicleMgmtDao;
import model.VehicleMgmtModel;

public class VehicleMgmtService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    VehicleMgmtDao dao = new VehicleMgmtDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(VehicleMgmtModel model) throws Exception {
        return true;
    }
    
    /**
     * リスト生成
     */
    public boolean getSearchList(VehicleMgmtModel model) throws Exception {

        // 契約データ取得
//        ArrayList<HashMap<String, Object>> searchList = dao.getSearchList(model);
        ArrayList<HashMap<String, Object>> searchList = new ArrayList<HashMap<String, Object>>();
        
        // 開発中
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("seq", "1");
        map.put("id", "630");
        map.put("vehicle_no", "C-03827");
        map.put("maker", "トヨタ");
        map.put("model", "プリウスPHEV");
        map.put("katashiki", "6LA-MXWH61");
        map.put("year", "R05/03");
        map.put("e_g_start_type", "1");
        map.put("e_g_start_typeText", "プッシュ式");
        map.put("verify_code", "1");
        map.put("verify_codeText", "可");
        searchList.add(map);
        searchList.add(map);
        searchList.add(map);
        searchList.add(map);
        searchList.add(map);
        searchList.add(map);
        searchList.add(map);
        searchList.add(map);
        searchList.add(map);
        searchList.add(map);
        
        if (searchList != null) {

//            for (HashMap<String, Object> map : searchList) {
//                
//            }
            
        } else {
            model.setSuccessFlg("9");
            model.setMessage("車両情報がありません");
            return false;
        }
        
        model.setSearchList(searchList);
        
        return true;
    }
    
}