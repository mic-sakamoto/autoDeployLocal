package action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.ActionSupport;

@Namespace("/")
@ParentPackage("action-base")
@InterceptorRefs({
    @InterceptorRef("defaultStack"),
    @InterceptorRef("loginIntercepterStack")
})
public class BaseAction extends ActionSupport {

    public BaseAction() {
        HttpServletResponse responce = ServletActionContext.getResponse();
        responce.setHeader("X-Content-Type-Options", "nosniff");
    }

}
