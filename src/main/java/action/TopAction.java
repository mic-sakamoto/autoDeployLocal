package action;

import java.util.Objects;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import model.TopModel;
import service.TopService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/top/top.jsp"), @Result(name = "login", location = "/core/top/top.jsp"), @Result(name = "logout", location = "/core/password-set/password-set.jsp"), @Result(name = "json", type = "json"), @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class TopAction extends BaseAction implements ModelDriven<TopModel> {

    private String className = this.getClass().getName();
    TopModel model = new TopModel();
    TopService svc = new TopService();

    @Override
    public TopModel getModel() {
        return model;
    }

    public String execute() throws Exception {

        TopService.as = this;
        String action = model.getAction();

        try {
            String user_id = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "user_id" ));
            model.setUserId(user_id);
            String role_type = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "role_type" ));
            model.setRoleType(role_type);
            
            LogUtil.info(className, "TopAction " + action);
            svc.init(model);

            if (action == null) {
                return "init";
            }
            else if ("getDisplayAuthority".equals(action)) {
                svc.getDisplayAuthority(model);
                return "json";
            }
            else if ("getAnnouncementInfo".equals(action)) {
                svc.getAnnouncementInfo(model);
                return "json";
            }
            else if ("getUserInfo".equals(action)) {
                svc.getUserInfo(model);
                return "json";
            }
            else if ("updateSetting".equals(action)) {
                svc.updateSetting(model);
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
