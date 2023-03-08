package cn.wstom.face.controller;

import cn.wstom.face.constants.JwtConstant;
import cn.wstom.face.entity.SysUser;
import cn.wstom.face.feign.AdminService;
import cn.wstom.face.utils.JwtUtils;
import cn.wstom.face.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yaosh
 * @version 1.0
 * @commpany 星瑞国际
 * @date 2020/8/20 14:30
 * @return
 */
@Controller
@RequestMapping("/face")
public class IndexController {
    @Autowired
    AdminService adminService;
    @Autowired
    JwtUtils jwtUtils;

    @RequestMapping(value = "/compare/{paperId}/{tcid}/{cid}")
    public String compare(
            @PathVariable("paperId")String paperId,
            @PathVariable("tcid")String tcid,
            @PathVariable("cid")String cid,
                          ModelMap modelMap) {
        String userId = jwtUtils.getUserIdFromToken(getToken());
        SysUser user = adminService.getUserById(userId);
        modelMap.put("userName",user.getUserName());
        modelMap.put("loginName",user.getLoginName());
        modelMap.put("paperId",paperId);
        modelMap.put("tcid",tcid);
        modelMap.put("cid",cid);
        return "compare";
    }

    @RequestMapping(value = "/registerIndex")
    public String registerIndex() {
        return "faceRegisterLogin";
    }

    @RequestMapping(value = "/toRegister")
    public String toRegister(ModelMap modelMap) {

        return "register";
    }

    public String getToken(){
        String token = ServletUtils.getRequest().getParameter("token");
        if (!"".equals(token)&&token!=null){
            return ServletUtils.getRequest().getParameter("token");
        };

        return ServletUtils.getRequest().getHeader(JwtConstant.tokenHeader);
    }

}
