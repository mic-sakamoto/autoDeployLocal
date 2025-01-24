package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

public class ObicApiUtill {

    @SuppressWarnings("unused")
    public static class HensaiYoteiReqData {

        private String kameitencd;
        private String ryorituPattern;
        private int genkinkakakugoukei;
        private int atamakin;
        private String siharaikbn;
        private int bonuszougaku;
        private int siharaiyoteikaisuu;

        public void setKameitencd(String kameitencd) {
            this.kameitencd = kameitencd;
        }

        public void setRyorituPattern(String ryorituPattern) {
            this.ryorituPattern = ryorituPattern;
        }

        public void setGenkinkakakugoukei(int genkinkakakugoukei) {
            this.genkinkakakugoukei = genkinkakakugoukei;
        }

        public void setAtamakin(int atamakin) {
            this.atamakin = atamakin;
        }

        public void setSiharaikbn(String siharaikbn) {
            this.siharaikbn = siharaikbn;
        }

        public void setBonuszougaku(int bonuszougaku) {
            this.bonuszougaku = bonuszougaku;
        }

        public void setSiharaiyoteikaisuu(int siharaiyoteikaisuu) {
            this.siharaiyoteikaisuu = siharaiyoteikaisuu;
        }

    }
    
    @SuppressWarnings("unused")
    public static class ShinsaKeiyakuJokyoReqData {
        
        private String webmousikomiid;

        public void setWebmousikomiid(String webmousikomiid) {
            this.webmousikomiid = webmousikomiid;
        }
        
    }
    
    @SuppressWarnings("unused")
    public static class KeiyakuShokaiReqData {
        
        private String keiyakuno;

        public void setKeiyakuno(String keiyakuno) {
            this.keiyakuno = keiyakuno;
        }
        
    }
    @SuppressWarnings("unused")
    public static class SeisanShokaiReqData {
        
        private String seisandate;
        private String keiyakukameitencd;
        private String seisankameitencd;
        
        public void setSeisandate(String seisandate) {
            this.seisandate = seisandate;
        }
        public void setKeiyakukameitencd(String keiyakukameitencd) {
            this.keiyakukameitencd = keiyakukameitencd;
        }
        public void setSeisankameitencd(String seisankameitencd) {
            this.seisankameitencd = seisankameitencd;
        }
    }


    public static HashMap<String, String> HensaiYoteiApi(ActionSupport as, HensaiYoteiReqData model) throws Exception {

        Gson _gson = new Gson();
        String json = _gson.toJson(model);

        String resJson =  apiSend(as, json, as.getText("OBIC-API-PATH-HENSAI-YOTEI"));
        HashMap<String, String> jsonMap = _gson.fromJson(resJson, new TypeToken<HashMap<String, String>>() {}.getType());

        return jsonMap;
    }
    public static HashMap<String, String> ShinsaKeiyakuJokyoApi(ActionSupport as, ShinsaKeiyakuJokyoReqData model) throws Exception {

        Gson _gson = new Gson();
        String json = _gson.toJson(model);

        String resJson =  apiSend(as, json, as.getText("OBIC-API-PATH-SHINSA-KEIYAKU-JOKYO"));
        HashMap<String, String> jsonMap = _gson.fromJson(resJson, new TypeToken<HashMap<String, String>>() {}.getType());

        return jsonMap;
    }
    public static HashMap<String, Object> KeiyakuShokaiApi(ActionSupport as, KeiyakuShokaiReqData model) throws Exception {

        Gson _gson = new Gson();
        String json = _gson.toJson(model);

        String resJson =  apiSend(as, json, as.getText("OBIC-API-PATH-KEIYAKU-SHOKAI"));
        HashMap<String, Object> jsonMap = _gson.fromJson(resJson, new TypeToken<HashMap<String, Object>>() {}.getType());

        return jsonMap;
    }
    public static HashMap<String, String> SeisanShokaiApi(ActionSupport as, SeisanShokaiReqData model) throws Exception {

        Gson _gson = new Gson();
        String json = _gson.toJson(model);
        
        String resJson = apiSend(as, json, as.getText("OBIC-API-PATH-SEISAN-SHOKAI"));
        HashMap<String, String> jsonMap = _gson.fromJson(resJson, new TypeToken<HashMap<String, String>>() {}.getType());

        return jsonMap;
    }

    public static String apiSend(ActionSupport as, String json, String action) throws Exception {

        URL url = new URL(as.getText("OBIC-API-URI") + action);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "APIKey " + as.getText("OBIC-API-AUTHORIZATION"));
        conn.setRequestProperty("Content-Type", as.getText("OBIC-API-CONTENT-TYPE"));
        conn.setRequestProperty("Obic-FBB-CompanyNO", as.getText("OBIC-API-FBB-COMPANY-NO"));
        conn.setRequestProperty("Host", as.getText("OBIC-API-HOST"));

        conn.connect();

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
//        PrintStream ps = new PrintStream(conn.getOutputStream());
//        ps.print(json);
//        ps.close();

        int responseCode = conn.getResponseCode();
        // レスポンスを読み取る
        StringBuffer response = new StringBuffer();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;

            while ((output = in.readLine()) != null) {
                output = output.replace("\"null\"", "null");
                response.append(output);
            }
            in.close();
        } else {
        }

        conn.disconnect();

        return response.toString();
    }

}
