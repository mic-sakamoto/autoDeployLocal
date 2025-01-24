package service;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.ApplicationConfirmDao;
import db.dao.CommonDao;
import model.ApplicationConfirmModel;

public class ApplicationConfirmService extends ActionSupport {

    @SuppressWarnings("unused")
    private String className = this.getClass().getName();
    public static ActionSupport as;

    ApplicationConfirmDao dao = new ApplicationConfirmDao();
    CommonDao comdao = new CommonDao();
    CommonService comSvc = new CommonService();

    public boolean init(ApplicationConfirmModel model) throws Exception {
        return true;
    }
    
    
}