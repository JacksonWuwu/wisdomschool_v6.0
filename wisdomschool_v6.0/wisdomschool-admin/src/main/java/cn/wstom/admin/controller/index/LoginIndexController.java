package cn.wstom.admin.controller.index;

import cn.wstom.admin.utils.ServletUtils;
import cn.wstom.common.constant.HttpConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginIndexController {
    //普通登录页面
    @RequestMapping("/loginIndex")
    public String login(){
        return "login";
    }

    //学生考试登录页面
    @RequestMapping("/examLogin")
    public String examLogin(String tcid, String cid, String testPaperOneId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request)) {
            return ServletUtils.renderString(response, "{\"code\":\"" + HttpConstants.CODE_NOT_LOGIN + "\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }
        modelMap.put("tcid", tcid);
        modelMap.put("cid", cid);
        modelMap.put("testPaperOneId", testPaperOneId);
        return "examLogin";
    }
}
