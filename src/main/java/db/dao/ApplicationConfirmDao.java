package db.dao;

import java.sql.Connection;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;


public class ApplicationConfirmDao extends AccessEndPoint {

    private String className = this.getClass().getSimpleName();
    HttpSession session = ServletActionContext.getRequest().getSession();

    // トランザクションが必要な場合に使う
    public Connection getCon() throws Exception {
        return writer.getCon();
    }


}