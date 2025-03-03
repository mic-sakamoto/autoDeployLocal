package action;


import java.util.Objects;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import model.AnnouncementMgmtModel;
import service.AnnouncementMgmtService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/announcement-mgmt/announcement-mgmt.jsp"), 
    @Result(name = "regist", location = "/core/announcement-mgmt/announcement-mgmt-regist.jsp"), 
    @Result(name = "json", type = "json"), 
    @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class AnnouncementMgmtAction extends ActionSupport implements ModelDriven<AnnouncementMgmtModel> {

    private String className = this.getClass().getName();
    private AnnouncementMgmtModel model = new AnnouncementMgmtModel();
    AnnouncementMgmtService svc = new AnnouncementMgmtService();

    @Override
    public AnnouncementMgmtModel getModel() {
        return model;
    }

    @Override
    public String execute() throws Exception {

        AnnouncementMgmtService.as = this;
        String action = model.getAction();

        try {
            String user_id = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "user_id" ));
            model.setUserId(user_id);
            
            String seq = ServletActionContext.getRequest().getParameter("seq");
            
            LogUtil.info(className, "AnnouncementMgmtAction " + action);
            svc.init(model);
            if (action == null) {
                
                if(seq != null) {
                    return "regist";
                }
                
                return "init";
            } 
            else if("newAnnouncementRegist".equals(action)){
                return "regist";
            }
            else if ("searchList".equals(action)) {
                svc.getSearchList(model);
                return "json";
            }
            else if("backPage".equals(action)) {
                svc.getSearchList(model);
                return "init";
            }
            
            return "init";

        } catch (Exception e) {
            LogUtil.error(className, e);
            LogUtil.fatal(className, "error");
            return "error";
        }
    }
}
