package action;

import java.util.Objects;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import model.ContractMgmtModel;
import service.ContractMgmtService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/contract-mgmt/contract-mgmt.jsp"), 
    @Result(name = "json", type = "json"), 
    @Result(name = "printJasper", type = "stream", location = "/report/settle.jasper", params = { "inputName", "inputStream", "contentType", "application/pdf;", "contentLength", "${ contentLength }", "contentDisposition", "inline; filename = ${fileName}", "root", "model","ignoreHierarchy", "false" }),
    @Result(name = "downloadPdf", type = "stream", location = "/report/settle.jasper", params = { "inputName", "inputStream", "contentType", "application/octet-stream; charset=UTF-8", "contentLength", "${ contentLength }", "contentDisposition", "attachment; filename = ${fileName}", "root", "model","ignoreHierarchy", "false" }),
    @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class ContractMgmtAction extends BaseAction implements ModelDriven<ContractMgmtModel> {

    private String className = this.getClass().getName();

    ContractMgmtModel model = new ContractMgmtModel();
    ContractMgmtService svc = new ContractMgmtService();

    @Override
    public ContractMgmtModel getModel() {
        return model;
    }

    public String execute() throws Exception {

        ContractMgmtService.as = this;

        String action = model.getAction();

        try {

            LogUtil.info(className, "ContractMgmtAction " + action);
            
            String role_type = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "role_type" ));
            model.setRoleType(role_type);

            if (action == null) {
                
                svc.init(model);
                
                return "init";

            }
            // 一覧取得
            else if ("searchList".equals(action)) {
                
                svc.getSearchList(model);
                
                return "json";
            }
            // ステータス取得
            else if ("getNowStatus".equals(action)) {
                
                svc.getNowStatus(model);
                
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
