package model;

public class StartUsingModel extends CommonModel {

    private String successFlg;
    private String message;
    private String errormessage;
    private String contract_number;
    private String last_name_kana;
    private String first_name_kana;
    private String birth_year;
    private String birth_month;
    private String birth_date;
    private String tel_number1;
    private String tel_number2;
    private String tel_number3;

    public String getSuccessFlg() {
        return successFlg;
    }

    public String getContract_number() {
        return contract_number;
    }

    public void setContract_number(String contract_number) {
        this.contract_number = contract_number;
    }

    public String getLast_name_kana() {
        return last_name_kana;
    }

    public void setLast_name_kana(String last_name_kana) {
        this.last_name_kana = last_name_kana;
    }

    public String getFirst_name_kana() {
        return first_name_kana;
    }

    public void setFirst_name_kana(String first_name_kana) {
        this.first_name_kana = first_name_kana;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public String getBirth_month() {
        return birth_month;
    }

    public void setBirth_month(String birth_month) {
        this.birth_month = birth_month;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getTel_number1() {
        return tel_number1;
    }

    public void setTel_number1(String tel_number1) {
        this.tel_number1 = tel_number1;
    }

    public String getTel_number2() {
        return tel_number2;
    }

    public void setTel_number2(String tel_number2) {
        this.tel_number2 = tel_number2;
    }

    public String getTel_number3() {
        return tel_number3;
    }

    public void setTel_number3(String tel_number3) {
        this.tel_number3 = tel_number3;
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