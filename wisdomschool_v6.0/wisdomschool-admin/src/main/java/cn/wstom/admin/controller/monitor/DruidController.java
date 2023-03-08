package cn.wstom.admin.controller.monitor;


import cn.wstom.admin.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * druid 监控
 *
 * @author dws
 */
@Controller
@RequestMapping("/monitor/data")
public class DruidController extends BaseController {
    private String prefix = "/monitor/druid";


    @GetMapping()
    public String index() {
        return redirect(prefix + "/index");
    }
}
