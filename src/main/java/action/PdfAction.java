package action;

import java.util.Objects;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import model.PdfModel;
import service.PdfService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({
    @Result(name = "error", type = "httpheader", params = {
        "status", "500", 
        "errorMessage", "Internal Error"
    }),
    @Result(name = "print", type = "stream", location = "/report/settle.jasper", params = {
        "inputName", "inputStream", 
        "contentType", "application/pdf",  
        "contentLength", "${contentLength}", 
        "contentDisposition", "inline; filename=${fileName}",  
        "root", "model", 
        "ignoreHierarchy", "false"
    }),
    @Result(name = "download", type = "stream", location = "/report/settle.jasper", params = {
        "inputName", "inputStream", 
        "contentType", "application/octet-stream; charset=UTF-8", 
        "contentLength", "${contentLength}", 
        "contentDisposition", "attachment; filename=${fileName}", 
        "root", "model", 
        "ignoreHierarchy", "false"
    })
})

public class PdfAction extends BaseAction implements ModelDriven<PdfModel> {

    private String className = this.getClass().getName();

    PdfModel model = new PdfModel();
    PdfService svc = new PdfService();

    @Override
    public PdfModel getModel() {
        return model;
    }

    public String execute() throws Exception {

        PdfService.as = this;
        String action = model.getAction();

        try {

            LogUtil.info(className, "PdfAction " + action);

            String role_type = Objects.toString(ServletActionContext.getRequest().getSession().getAttribute("role_type"));
            model.setRoleType(role_type);

            if (action == null) {

                svc.init(model);

                return "init";

            } else if ("printJasper".equals(action)) {
                String result = null;
                if (svc.printJasper(model)) {
                    result = "print";
                }
                return result;
            }else if ("downloadJasper".equals(action)) {
                String result = null;
                if (svc.printJasper(model)) {
                    result = "download";
                }
                return result;
            }else if ("downloadPdf".equals(action)) {
                String result = null;
                if (svc.downloadPdf(model)) {
                    result = "download";
                }
                return result;
            }

            return "init";

        } catch (Exception e) {
            LogUtil.error(className, e);
            LogUtil.fatal(className, "error");
            return "error";
        }
    }
}
