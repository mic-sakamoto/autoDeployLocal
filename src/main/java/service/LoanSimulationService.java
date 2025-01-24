package service;

import java.util.HashMap;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.CommonDao;
import db.dao.LoanSimulationDao;
import model.LoanSimulationModel;
import utils.CommonUtil;
import utils.ObicApiUtill;
import utils.StringUtil;
import utils.log4j.LogUtil;

public class LoanSimulationService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    LoanSimulationDao dao = new LoanSimulationDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(LoanSimulationModel model) throws Exception {

        return true;
    }

    public boolean hensaiYotei(LoanSimulationModel model) throws Exception {

        boolean result = true;
        try {

            // 現金合計額入力内容不備
            if (!CommonUtil.chkNotNullEmpty(model.getGenkinkakakugoukei())) {
                model.setSuccessFlg("10");
                throw new Exception("①現金合計額が設定されていません。");
            }
            // 頭金入力内容不備→0円ならOKなので廃止
            // if (!CommonUtil.chkNotNullEmpty(model.getAtamakin())) {
            // model.setSuccessFlg("11");
            // throw new Exception("②頭金が設定されていません。");
            // }
            // ボーナス加算月入力不備
            if (!CommonUtil.chkNotNullEmpty(model.getSiharaikbn())) {
                model.setSuccessFlg("12");
                throw new Exception("ボーナス加算月が設定されていません。");
            } else if (model.getSiharaikbn().equals("91") && !CommonUtil.chkNotNullEmpty(model.getBonuszougaku())) {
                model.setSuccessFlg("13");
                throw new Exception("加算支払金が設定されていません。");
            }
            // 支払回数入力不備
            if (!CommonUtil.chkNotNullEmpty(model.getSiharaiyoteikaisuu())) {
                model.setSuccessFlg("14");
                throw new Exception("お支払い回数が設定されていません。");
            }

            // 料率パターン判定
            String ryoritsu = "";
            boolean isBonus = model.getSiharaikbn().equals("91");
            boolean isMccs = model.getMccsKbn() == 1;
            boolean isNewCar = model.getUsedKbn() == 1;
            if (isBonus) { // ボーナスなし
                if (isMccs) { // MCCSあり
                    ryoritsu = isNewCar ? "0002" : "0001";
                } else { // MCCSなし
                    ryoritsu = isNewCar ? "0004" : "0003";
                }
            } else { // ボーナスあり
                if (isMccs) { // MCCSあり
                    ryoritsu = isNewCar ? "1002" : "1001";
                } else { // MCCSなし
                    ryoritsu = isNewCar ? "1004" : "1003";
                }
            }

            // OBIC審査状況API
            ObicApiUtill.HensaiYoteiReqData hensaiYoteiReqData = new ObicApiUtill.HensaiYoteiReqData();
            // hensaiYoteiReqData.setKameitencd(model.getUserId());
            // TODO デモサイト用
            hensaiYoteiReqData.setKameitencd("109280001");

            hensaiYoteiReqData.setRyorituPattern(ryoritsu);
            hensaiYoteiReqData.setGenkinkakakugoukei(model.getGenkinkakakugoukei());
            hensaiYoteiReqData.setAtamakin(model.getAtamakin());
            hensaiYoteiReqData.setSiharaikbn(model.getSiharaikbn());
            hensaiYoteiReqData.setBonuszougaku(model.getBonuszougaku());
            hensaiYoteiReqData.setSiharaiyoteikaisuu(model.getSiharaiyoteikaisuu());

            // API実行
            HashMap<String, String> jsonMap = ObicApiUtill.HensaiYoteiApi(as, hensaiYoteiReqData);

            // APIから取得
            if (jsonMap != null) {
                // 分割支払金1
                model.setBunkatusiharaikinn1(StringUtil.dataToString(jsonMap.get("bunkatusiharaikinn1")));
                // 分割支払金2
                model.setBunkatusiharaikinn2(StringUtil.dataToString(jsonMap.get("bunkatusiharaikinn2")));
                // 分割支払手数料
                model.setBunkatubaraitesuuryou(StringUtil.dataToString(jsonMap.get("bunkatubaraitesuuryou")));
                // 分割支払金合計
                model.setBunkatusiharaigokei(StringUtil.dataToString(jsonMap.get("bunnkatusiharaikingoukei")));
                // お支払総額
                model.setOsiharaisogaku(StringUtil.dataToString(jsonMap.get("siharaisougaku")));

                LogUtil.info(className, "返済予定APIレスポンス");
                for (String key : jsonMap.keySet()) {
                    LogUtil.info(className, key + " : " + jsonMap.get(key));
                }
            } else {
                throw new Exception("APIの結果取得に失敗しました。");
            }

            // 支払い回数
            model.setKaisu(StringUtil.IntegerToString(model.getSiharaiyoteikaisuu() - 1));
            
            //残金(①-②)
            int zankin = model.getGenkinkakakugoukei() - model.getAtamakin();
            // 申込不可パターンの確認
            // 「ボーナス加算支払金×ボーナス回数」の合計値が「③残金(円)①－②」の50%を超える場合
            if (model.getSiharaikbn().equals("91")) {
                int bonusZogakuGokei = model.getBonuszougaku() * model.getBonuskasankaisu();
                if (zankin * 0.5 < bonusZogakuGokei) {
                    model.setSuccessFlg("15");
                    throw new Exception("申込不可：ボーナス加算支払金×ボーナス回数の合計値が残金の50%以上");
                }
            }
            // 「月々の支払（APIリクエスト結果の第２回分割支払金）」が5000円以下の場合
            if (StringUtil.stringToInt(model.getBunkatusiharaikinn2()) < 5000) {
                model.setSuccessFlg("16");
                throw new Exception("申込不可：月々の支払いが5000円以下");
            }
            // 残金が1000万円以上の場合
            if (zankin > 10000000) {
                model.setSuccessFlg("17");
                throw new Exception("申込不可：残金が1000万以上");
            }

            model.setSuccessFlg("1");
        } catch (Exception e) {
            if (!CommonUtil.chkNotNullEmpty(model.getSuccessFlg())) {
                model.setSuccessFlg("9");
                model.setErrormessage("ローンシミュレーション実行中にエラーが発生しました。");
            } else {
                model.setErrormessage(e.getMessage());
            }
            LogUtil.error(e);
            result = false;
        }

        return result;
    }

}