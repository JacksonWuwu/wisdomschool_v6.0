package cn.wstom.admin.controller.monitor;

import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.support.Convert;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 操作日志记录
 *
 * @author dws
 */
@Controller
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController {
    private String prefix = "monitor/operlog";

    @Autowired
    private SysOperLogService operLogService;


    @GetMapping()
    public String toOperlog() {
        return prefix + "/list";
    }


    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysOperLog operLog) {
        startPage();
        List<SysOperLog> list = operLogService.list(operLog);
        return wrapTable(list);
    }

    @Log(title = "操作日志", actionType = ActionType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public Data export(SysOperLog operLog) {
        try {
            List<SysOperLog> list = operLogService.list(operLog);
            ExcelUtil<SysOperLog> util = new ExcelUtil<>(SysOperLog.class);
            return util.exportExcel(list, "operLog");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }

    /**
     * 批量删除日志
     * @param ids 日志id，多个id以“,”隔开
     * @return
     * @throws Exception
     */
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        try {
            return toAjax(operLogService.removeByIds(Convert.toStrList(ids)));
        } catch (Exception e) {
            throw new Exception("删除日志异常");
        }
    }

    /**
     * 日志详情
     * @param operId
     * @param mmap
     * @return
     */

    @GetMapping("/detail/{operId}")
    public String detail(@PathVariable("operId") String operId, ModelMap mmap) {
        mmap.put("operLog", operLogService.getById(operId));
        return prefix + "/detail";
    }

    /**
     * 清空日志
     * @return
     */
    @Log(title = "操作日志", actionType = ActionType.CLEAN)
    @PostMapping("/clean")
    @ResponseBody
    public Data clean() {
        operLogService.cleanOperLog();
        return success();
    }
}
