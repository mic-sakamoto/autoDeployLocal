package action;

import java.util.Objects;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import model.VehicleMgmtModel;
import service.VehicleMgmtService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/vehicle-mgmt/vehicle-mgmt.jsp"), 
    @Result(name = "json", type = "json"), 
    @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class VehicleMgmtAction extends BaseAction implements ModelDriven<VehicleMgmtModel> {

    private String className = this.getClass().getName();
    VehicleMgmtModel model = new VehicleMgmtModel();
    VehicleMgmtService svc = new VehicleMgmtService();

    @Override
    public VehicleMgmtModel getModel() {
        return model;
    }

    public String execute() throws Exception {

        VehicleMgmtService.as = this;
        String action = model.getAction();

        try {
            String user_id = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "user_id" ));
            model.setUserId(user_id);
            String role_type = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "role_type" ));
            model.setRoleType(role_type);
            
            LogUtil.info(className, "VehicleMgmtAction " + action);
            svc.init(model);

            if (action == null) {
                return "init";
            }
            
            // 一覧取得
            else if ("searchList".equals(action)) {
                
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
