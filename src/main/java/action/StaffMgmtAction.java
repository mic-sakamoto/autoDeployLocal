package action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import model.StaffMgmtModel;
import service.StaffMgmtService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/staff-mgmt/staff-mgmt.jsp"), @Result(name = "json", type = "json"), @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class StaffMgmtAction extends BaseAction implements ModelDriven<StaffMgmtModel> {

    private String className = this.getClass().getName();

    StaffMgmtModel model = new StaffMgmtModel();
    StaffMgmtService svc = new StaffMgmtService();

    @Override
    public StaffMgmtModel getModel() {
        return model;
    }

    public String execute() throws Exception {

        StaffMgmtService.as = this;

        String action = model.getAction();

        try {

            LogUtil.info(className, "StaffMgmtAction " + action);

            svc.init(model);

            if (action == null) {
                return "init";

            } else if ("newRegistration".equals(action)) {
                svc.newRegistration(model);
                return "json";
            } else if ("searchList".equals(action)) {
                svc.getSearchList(model);
                return "json";
            } else if ("changePassword".equals(action)) {
                svc.changePassword(model);
                return "json";
            } else if ("deleteAccount".equals(action)) {
                svc.deleteAccount(model);
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
