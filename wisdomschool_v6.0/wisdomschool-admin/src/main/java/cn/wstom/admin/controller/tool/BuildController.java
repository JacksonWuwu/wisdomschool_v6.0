package cn.wstom.admin.controller.tool;

import cn.wstom.admin.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * build 表单构建
 *
 * @author dws
 */
@Controller
@RequestMapping("/tool/build")
public class BuildController extends BaseController {
    private String prefix = "tool/build";

    @RequiresPermissions("tool:build:view")
    @GetMapping()
    public String build() {
        return prefix + "/build";
    }
}
