package action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import model.LoginModel;
import service.LoginService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/login/login.jsp"), 
    @Result(name = "logout", location = "/core/login/login.jsp"), 
    @Result(name = "top", location = "/core/top/top.jsp"),
    @Result(name = "json", type = "json"), 
    @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class LoginAction extends ActionSupport implements ModelDriven<LoginModel> {

    private String className = this.getClass().getName();

    private LoginModel model = new LoginModel();
    LoginService svc = new LoginService();

    @Override
    public LoginModel getModel() {
        return model;
    }

    @Override
    public String execute() throws Exception {

        LoginService.as = this;

        String action = model.getAction();

        try {

            LogUtil.info(className, "LoginAction " + action);
            
            svc.init(model);

            if (action == null) {
                return "init";

            } else if ("login".equals(action)) {
                svc.login(model);
                return "json";

            } else if ("logout".equals(action)) {
                // ログアウト処理
                svc.logout(model);
                return "logout";

            }
            else if ("proxyLogout".equals(action)) {
                svc.proxyLogout(model);
                return "top";
            }

            return "init";

        } catch (Exception e) {
            LogUtil.error(className, e);
            LogUtil.fatal(className, "error");
            return "error";
        }
    }
}
