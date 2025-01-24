package action;

import java.util.Objects;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import model.InquiryMgmtModel;
import service.InquiryMgmtService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/inquiry-mgmt/inquiry-mgmt.jsp"), 
    @Result(name = "json", type = "json"), 
    @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class InquiryMgmtAction extends BaseAction implements ModelDriven<InquiryMgmtModel> {

    private String className = this.getClass().getName();
    InquiryMgmtModel model = new InquiryMgmtModel();
    InquiryMgmtService svc = new InquiryMgmtService();

    @Override
    public InquiryMgmtModel getModel() {
        return model;
    }

    public String execute() throws Exception {

        InquiryMgmtService.as = this;
        String action = model.getAction();

        try {
            String user_id = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "user_id" ));
            model.setUserId(user_id);
            String role_type = Objects.toString( ServletActionContext.getRequest().getSession().getAttribute( "role_type" ));
            model.setRoleType(role_type);
            
            String seq = ServletActionContext.getRequest().getParameter("seq");
            
            LogUtil.info(className, "InquiryMgmtAction " + action);
            svc.init(model);

            if (action == null) {
                
                if(seq != null) {
                    return "regist";
                }
                
                return "init";
            } 
            else if ("searchList".equals(action)) {
                svc.getSearchList(model);
                return "json";
            }
            else if ("searchDetail".equals(action)) {
                svc.getSearchDetail(model);
                return "json";
            }
            else if ("checkContract".equals(action)) {
                svc.checkContract(model);
                return "json";
            }
            else if ("inquiry".equals(action)) {
                svc.inquiry(model);
                return "json";
            }
            else if ("answer".equals(action)) {
                svc.answer(model);
                return "json";
            }
            else if ("getAnswererInfo".equals(action)) {
                svc.getAnswererInfo(model);
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
