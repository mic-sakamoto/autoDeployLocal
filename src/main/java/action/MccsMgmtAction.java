package action;

import java.util.Objects;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import model.MccsMgmtModel;
import service.MccsMgmtService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/mccs-mgmt/mccs-mgmt.jsp"), 
    @Result(name = "json", type = "json"), 
    @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class MccsMgmtAction extends BaseAction implements ModelDriven<MccsMgmtModel> {

    private String className = this.getClass().getName();
    MccsMgmtModel model = new MccsMgmtModel();
    MccsMgmtService svc = new MccsMgmtService();

    @Override
    public MccsMgmtModel getModel() {
        return model;
    }

    public String execute() throws Exception {

        MccsMgmtService.as = this;
        String action = model.getAction();

        try {
            String user_id = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "user_id" ));
            model.setUserId(user_id);
            String role_type = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "role_type" ));
            model.setRoleType(role_type);
            
            LogUtil.info(className, "MccsMgmtAction " + action);
            svc.init(model);

            if (action == null) {
                return "init";
            } else if ("searchList".equals(action)) {
                svc.getSearchList(model);
                return "json";
            }
            return "init";

        } catch (Exception e) {
            LogUtil.error(className, e);
            LogUtil.fatal(className, "error");
            return "error";
        }
    }
}