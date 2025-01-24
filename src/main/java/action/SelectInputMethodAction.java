package action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import model.SelectInputMethodModel;
import service.SelectInputMethodService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/select-input-method/select-input-method.jsp"), @Result(name = "json", type = "json"), @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class SelectInputMethodAction extends ActionSupport implements ModelDriven<SelectInputMethodModel> {

    private String className = this.getClass().getName();
    private SelectInputMethodModel model = new SelectInputMethodModel();
    SelectInputMethodService svc = new SelectInputMethodService();

    @Override
    public SelectInputMethodModel getModel() {
        return model;
    }

    @Override
    public String execute() throws Exception {

        SelectInputMethodService.as = this;
        String action = model.getAction();

        try {
            LogUtil.info(className, "SelectInputMethodAction " + action);
            svc.init(model);

            if (action == null) {
                return "init";

            } else if ("checkDigit".equals(action)) {
                svc.checkDigit(model);
                return "json";

            } else if ("checkCustomer".equals(action)) {
                svc.checkCustomer(model);
                return "json";

            } else if ("fromLoanSimulation".equals(action)) {
                return "init";

            } else if ("sendLoginMethod".equals(action)) {
                svc.sendLoginMethod(model);
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
