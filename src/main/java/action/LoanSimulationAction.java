package action;

import java.util.Objects;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import model.LoanSimulationModel;
import service.LoanSimulationService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/loan-simulation/loan-simulation.jsp"), @Result(name = "json", type = "json"), @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class LoanSimulationAction extends ActionSupport implements ModelDriven<LoanSimulationModel> {

    private String className = this.getClass().getName();

    private LoanSimulationModel model = new LoanSimulationModel();

    LoanSimulationService svc = new LoanSimulationService();

    @Override
    public LoanSimulationModel getModel() {
        return model;
    }

    @Override
    public String execute() throws Exception {

        LoanSimulationService.as = this;

        String action = model.getAction();

        try {
            String user_id = Objects.toString(ServletActionContext.getRequest().getSession().getAttribute("user_id"));
            model.setUserId(user_id);

            LogUtil.info(className, "LoanSimulationAction " + action);

            svc.init(model);
            if (action == null) {
                return "init";

            } else if (action.equals("hensaiYotei")) {
                svc.hensaiYotei(model);
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