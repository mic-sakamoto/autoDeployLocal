package model;

public class LoanSimulationModel extends CommonModel {

    private String successFlg;
    private String message;
    private String errormessage;

    private String userId;
    private String userName;
    private String roleType;

    // API送信パラメータ
    private int genkinkakakugoukei;
    private int atamakin;
    private String siharaikbn;
    private int bonuszougaku;
    private int siharaiyoteikaisuu;
    
    // 非API送信パラメータ
    private int bonuskasankaisu = 0;
    private int mccsKbn;
    private int usedKbn;

    // API受信パラメータ
    private String bunkatusiharaikinn1;
    private String bunkatusiharaikinn2;
    private String bunkatubaraitesuuryou;

    // 非API受信パラメータの画面表示項目
    private String kaisu;
    private String bunkatusiharaigokei;
    private String osiharaisogaku;

    public int getUsedKbn() {
        return usedKbn;
    }

    public void setUsedKbn(int usedKbn) {
        this.usedKbn = usedKbn;
    }

    public int getMccsKbn() {
        return mccsKbn;
    }

    public void setMccsKbn(int mccsKbn) {
        this.mccsKbn = mccsKbn;
    }

    public int getBonuskasankaisu() {
        return bonuskasankaisu;
    }

    public void setBonuskasankaisu(int bonuskasankaisu) {
        this.bonuskasankaisu = bonuskasankaisu;
    }

    public String getKaisu() {
        return kaisu;
    }

    public void setKaisu(String kaisu) {
        this.kaisu = kaisu;
    }

    public String getBunkatusiharaigokei() {
        return bunkatusiharaigokei;
    }

    public void setBunkatusiharaigokei(String bunkatusiharaigokei) {
        this.bunkatusiharaigokei = bunkatusiharaigokei;
    }

    public String getOsiharaisogaku() {
        return osiharaisogaku;
    }

    public void setOsiharaisogaku(String osiharaisogaku) {
        this.osiharaisogaku = osiharaisogaku;
    }

    public String getBunkatusiharaikinn1() {
        return bunkatusiharaikinn1;
    }

    public void setBunkatusiharaikinn1(String bunkatusiharaikinn1) {
        this.bunkatusiharaikinn1 = bunkatusiharaikinn1;
    }

    public String getBunkatusiharaikinn2() {
        return bunkatusiharaikinn2;
    }

    public void setBunkatusiharaikinn2(String bunkatusiharaikinn2) {
        this.bunkatusiharaikinn2 = bunkatusiharaikinn2;
    }

    public String getBunkatubaraitesuuryou() {
        return bunkatubaraitesuuryou;
    }

    public void setBunkatubaraitesuuryou(String bunkatubaraitesuuryou) {
        this.bunkatubaraitesuuryou = bunkatubaraitesuuryou;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public int getGenkinkakakugoukei() {
        return genkinkakakugoukei;
    }

    public void setGenkinkakakugoukei(int genkinkakakugoukei) {
        this.genkinkakakugoukei = genkinkakakugoukei;
    }

    public int getAtamakin() {
        return atamakin;
    }

    public void setAtamakin(int atamakin) {
        this.atamakin = atamakin;
    }

    public String getSiharaikbn() {
        return siharaikbn;
    }

    public void setSiharaikbn(String siharaikbn) {
        this.siharaikbn = siharaikbn;
    }

    public int getBonuszougaku() {
        return bonuszougaku;
    }

    public void setBonuszougaku(int bonuszougaku) {
        this.bonuszougaku = bonuszougaku;
    }

    public int getSiharaiyoteikaisuu() {
        return siharaiyoteikaisuu;
    }

    public void setSiharaiyoteikaisuu(int siharaiyoteikaisuu) {
        this.siharaiyoteikaisuu = siharaiyoteikaisuu;
    }

    public String getSuccessFlg() {
        return successFlg;
    }

    public void setSuccessFlg(String successFlg) {
        this.successFlg = successFlg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

}