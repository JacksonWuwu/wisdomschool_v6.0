package cn.wstom.admin.controller.monitor;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;

import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.support.Convert;
import cn.wstom.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统访问记录
 *
 * @author dws
 */
@Controller
@RequestMapping("/monitor/logininfor")
public class SysLogininforController extends BaseController {
    private String prefix = "/monitor/logininfor";

    @Autowired
    private SysLogininforService logininforService;


    @GetMapping()
    public String logininfor() {
        return prefix + "/logininfor";
    }


    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysLogininfor logininfor) {
        startPage();
        List<SysLogininfor> list = logininforService.list(logininfor);
        return wrapTable(list);
    }

    @Log(title = "登陆日志", actionType = ActionType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public Data export(SysLogininfor logininfor) {
        try {
            List<SysLogininfor> list = logininforService.list(logininfor);
            ExcelUtil<SysLogininfor> util = new ExcelUtil<>(SysLogininfor.class);
            return util.exportExcel(list, "logininfor");
        } catch (Exception e) {
            e.printStackTrace();
            return Data.error(e.getMessage());
        }
    }


    @Log(title = "登陆日志", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        try {
            return toAjax(logininforService.removeByIds(Convert.toStrList(ids)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("删除日志异常");
        }
    }

    @Log(title = "登陆日志", actionType = ActionType.CLEAN)
    @PostMapping("/clean")
    @ResponseBody
    public Data clean() {
        logininforService.cleanLogininfor();
        return success();
    }
}
