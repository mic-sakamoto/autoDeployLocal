package interceptor;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import utils.log4j.LogUtil;

@Namespace(value = "/")
@Results({ @Result(name = "error", type = "httpheader", params = { "status", "500", "errorMessage", "Internal Error" }) })

public class LoginInterceptor extends AbstractInterceptor {

    private String className = this.getClass().getName();

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        LogUtil.info("ログインインターセプター開始");
        HttpSession session = ServletActionContext.getRequest().getSession();

        Object login_id = session.getAttribute("user_id");

        if (login_id == null && invocation.getAction().toString().indexOf("Login") == -1) {

            LogUtil.info(className, "不許可,セッション破棄");
            // セッション破棄
            session.invalidate();
            //ログインページに遷移させる
            return "userlogin";

        } else if (login_id != null) {

            LogUtil.info(className, "許可");
            return invocation.invoke();

        }

        return invocation.invoke();
    }

}