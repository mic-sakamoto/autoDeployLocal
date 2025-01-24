package action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import model.StartUsingModel;
import service.StartUsingService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/start-using/start-using.jsp"), @Result(name = "complete", location = "/core/start-using/start-using-submit.jsp"), @Result(name = "json", type = "json"),
                @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class StartUsingAction extends ActionSupport implements ModelDriven<StartUsingModel> {

    private String className = this.getClass().getName();

    private StartUsingModel model = new StartUsingModel();

    StartUsingService svc = new StartUsingService();

    @Override
    public StartUsingModel getModel() {
        return model;
    }

    @Override
    public String execute() throws Exception {

        StartUsingService.as = this;

        String action = model.getAction();

        try {

            LogUtil.info(className, "StartUsingAction " + action);

            if (action == null) {
                return "init";
                
            } else if ("sendSMS".equals(action)) {
                svc.sendSMS(model);
                model.setSuccessFlg("1");
                return "json";
                
            } else if ("sendComplete".equals(action)) {
                return "complete";
                
            }

            return "init";

        } catch (Exception e) {
            LogUtil.error(className, e);
            LogUtil.fatal(className, "error");
            return "error";
        }
    }
}
