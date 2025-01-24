package action;

import java.util.Objects;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import model.PaymentDetailMgmtModel;
import service.PaymentDetailMgmtService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/payment-detail-mgmt/payment-detail-mgmt.jsp"), 
    @Result(name = "json", type = "json"), 
    @Result(name = "pdf", type = "stream", location = "/report/settle.jasper", params = { "inputName", "inputStream", "contentType",
                    "application/pdf;", "contentLength", "${ contentLength }", "contentDisposition", "inline; filename = ${fileName}", "root", "model",
                    "ignoreHierarchy", "false" }),
    @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class PaymentDetailMgmtAction extends BaseAction implements ModelDriven<PaymentDetailMgmtModel> {

    private String className = this.getClass().getName();
    PaymentDetailMgmtModel model = new PaymentDetailMgmtModel();
    PaymentDetailMgmtService svc = new PaymentDetailMgmtService();

    @Override
    public PaymentDetailMgmtModel getModel() {
        return model;
    }

    public String execute() throws Exception {

        PaymentDetailMgmtService.as = this;
        String action = model.getAction();

        try {
            String user_id = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "user_id" ));
            model.setUserId(user_id);
            String role_type = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "role_type" ));
            model.setRoleType(role_type);
            
            LogUtil.info(className, "PaymentDetailMgmtAction " + action);        

            if (action == null) {
                svc.init(model);
                return "init";
                
            } else if ("searchList".equals(action)) {
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
