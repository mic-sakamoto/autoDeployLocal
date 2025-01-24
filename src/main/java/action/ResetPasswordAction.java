package action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import model.ResetPasswordModel;
import service.ResetPasswordService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/reset-password/reset-password.jsp"), @Result(name = "complete", location = "/core/reset-password/reset-password-send.jsp"), @Result(name = "json", type = "json"),
                @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class ResetPasswordAction extends ActionSupport implements ModelDriven<ResetPasswordModel> {

    private String className = this.getClass().getName();

    private ResetPasswordModel model = new ResetPasswordModel();

    ResetPasswordService svc = new ResetPasswordService();

    @Override
    public ResetPasswordModel getModel() {
        return model;
    }

    @Override
    public String execute() throws Exception {

        ResetPasswordService.as = this;

        String action = model.getAction();

        try {

            LogUtil.info(className, "ResetPasswordAction " + action);

            if (action == null) {
                return "init";

            }
            // 案内送信完了
            else if ("sendComplete".equals(action)) {
                if ("email".equals(model.getSendMethod())) {
                    model.setEmail(svc.maskEmail(model.getEmail()));
                }
                return "complete";

            }
            // ログインIDからパスワードリセット案内送信
            else if ("resetPasswordWithId".equals(action)) {
                svc.resetPasswordWithId(model);
                return "json";

            }
            // ご契約者様情報からパスワードリセット案内送信
            else if ("resetPasswordWithCustomerInfo".equals(action)) {
                svc.resetPasswordWithCustomerInfo(model);
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
