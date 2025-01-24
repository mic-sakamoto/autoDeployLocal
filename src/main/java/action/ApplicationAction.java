package action;

import java.util.Objects;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import model.ApplicationModel;
import service.ApplicationService;
import utils.log4j.LogUtil;

@Namespace("/")
@ParentPackage("action-base")
@Results({ @Result(name = "init", location = "/core/application/application.jsp"), @Result(name = "json", type = "json"), @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class ApplicationAction extends BaseAction implements ModelDriven<ApplicationModel> {

    private String className = this.getClass().getName();

    private ApplicationModel model = new ApplicationModel();

    ApplicationService svc = new ApplicationService();

    @Override
    public ApplicationModel getModel() {
        return model;
    }

    @Override
    public String execute() throws Exception {

        ApplicationService.as = this;

        String action = model.getAction();

        try {
            // 申込入力者ロール
            String role_type = Objects.toString(ServletActionContext.getRequest().getSession().getAttribute("role_type"));
            model.setInputRoleKbn(role_type);
            // 代行ログイン
            String staff_id = Objects.toString(ServletActionContext.getRequest().getSession().getAttribute("staff_id"));
            model.setDaikoStaffId(staff_id);

            LogUtil.info(className, "ApplicationAction " + action);

            if (action == null) {
                return "init";
            }
            // 申込入力画面表示
            else if (action.equals("application")) {
                LogUtil.info(className, "WEB受付ID：" + model.getWebAppId());
                svc.getAppInfo(model);
                return "init";
            }
            // 申込データの一時保存
            else if (action.equals("saveAppInput")) {
                svc.saveAppInput(model);
                return "json";
            }
            // 申込処理
            else if (action.equals("confirmApp")) {
                svc.confirmApp(model);
                return "json";
            }
            // 顧客入力切替orログイン手順再送信
            else if (action.equals("resendLoginGuide")) {
                svc.resendLoginMethod(model);
                return "json";
            }
            // プルダウン用にMCCSメーカーリストを取得
            else if (action.equals("getMccsMaker")) {
                svc.getMccsMaker(model);
                return "json";
            }
            // プルダウン用にメーカーにひもづくMCCS車名リストを取得
            else if (action.equals("getMccsShamei")) {
                svc.getMccsShamei(model);
                return "json";
            }
            // プルダウン用に車名にひもづくMCCS型式リストを取得
            else if (action.equals("getMccsKatashiki")) {
                svc.getMccsKatashiki(model);
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