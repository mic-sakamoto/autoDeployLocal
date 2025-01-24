package action;

import java.util.Objects;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import model.StoreMgmtModel;
import service.StoreMgmtService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/store-mgmt/store-mgmt.jsp"), 
    @Result(name = "json", type = "json"), 
    @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class StoreMgmtAction extends BaseAction implements ModelDriven<StoreMgmtModel> {

    private String className = this.getClass().getName();
    StoreMgmtModel model = new StoreMgmtModel();
    StoreMgmtService svc = new StoreMgmtService();

    @Override
    public StoreMgmtModel getModel() {
        return model;
    }

    public String execute() throws Exception {

        StoreMgmtService.as = this;
        String action = model.getAction();

        try {
            String user_id = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "user_id" ));
            model.setUserId(user_id);
            String role_type = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "role_type" ));
            model.setRoleType(role_type);
            
            LogUtil.info(className, "StoreMgmtAction " + action);
            svc.init(model);

            if (action == null) {
                return "init";
            }
            // 一覧取得
            else if ("searchList".equals(action)) {
                
                svc.getSearchList(model);
                
                return "json";
            }
            // 代行ログイン
            else if ("proxyLogin".equals(action)) {
                
                svc.proxyLogin(model);
                
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
