package action;

import java.util.Objects;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import model.StatusModel;
import service.StatusService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/status/status.jsp"), 
    @Result(name = "json", type = "json"), 
    @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class StatusAction extends BaseAction implements ModelDriven<StatusModel> {

    private String className = this.getClass().getName();
    StatusModel model = new StatusModel();
    StatusService svc = new StatusService();

    @Override
    public StatusModel getModel() {
        return model;
    }

    public String execute() throws Exception {

        StatusService.as = this;
        String action = model.getAction();

        try {
            String user_id = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "user_id" ));
            model.setUserId(user_id);
            String role_type = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "role_type" ));
            model.setRoleType(role_type);
            
            LogUtil.info(className, "StatusAction " + action);
            svc.init(model);

            if (action == null) {
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
