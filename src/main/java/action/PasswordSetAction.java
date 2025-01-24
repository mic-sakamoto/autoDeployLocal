package action;

import java.util.Objects;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import model.PasswordSetModel;
import service.PasswordSetService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/password-set/password-set.jsp"), @Result(name = "login", location = "/core/password-set/password-set.jsp"), @Result(name = "logout", location = "/core/password-set/password-set.jsp"), @Result(name = "json", type = "json"), @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class PasswordSetAction extends ActionSupport implements ModelDriven<PasswordSetModel> {

    private String className = this.getClass().getName();
    private PasswordSetModel model = new PasswordSetModel();
    PasswordSetService svc = new PasswordSetService();

    @Override
    public PasswordSetModel getModel() {
        return model;
    }

    @Override
    public String execute() throws Exception {

        PasswordSetService.as = this;
        String action = model.getAction();

        try {
            String user_id = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "user_id" ));
//            String user_id = "000"; //テストのため固定
            model.setUserId(user_id);
            
            LogUtil.info(className, "PasswordSetAction " + action);
            svc.init(model);
            if (action == null) {
                return "init";
            } 
            else if ("getUserInfo".equals(action)) {
                svc.getUserInfo(model);
                return "json";
            }
            else if ("updatePassword".equals(action)) {
                svc.updatePassword(model);
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
