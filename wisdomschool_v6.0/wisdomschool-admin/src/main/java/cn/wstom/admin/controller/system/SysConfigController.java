package cn.wstom.admin.controller.system;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.SysConfig;
import cn.wstom.admin.service.SysConfigService;
import cn.wstom.admin.utils.ExcelUtil;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.web.page.TableDataInfo;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数配置 信息操作处理
 *
 * @author dws
 */
@Controller
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {
    private String prefix = "system/config";

    @Autowired
    private SysConfigService configService;

    @RequiresPermissions("system:config:view")
    @GetMapping()
    public String config() {
        return prefix + "/list";
    }

    /**
     * 查询参数配置列表
     */
    @RequiresPermissions("system:config:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysConfig config) {
        startPage();
        List<SysConfig> list = configService.list(config);
        return wrapTable(list);
    }

    @Log(title = "参数管理", actionType = ActionType.EXPORT)
    @RequiresPermissions("system:config:export")
    @PostMapping("/export")
    @ResponseBody
    public Data export(SysConfig config) {
        try {
            List<SysConfig> list = configService.list(config);
            ExcelUtil<SysConfig> util = new ExcelUtil<>(SysConfig.class);
            return util.exportExcel(list, "config");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }

    /**
     * 新增参数配置
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存参数配置
     */
    @RequiresPermissions("system:config:add")
    @Log(title = "参数管理", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data addSave(SysConfig config) throws Exception {
        config.setCreateBy(getUser().getLoginName());
        return toAjax(configService.save(config));
    }

    /**
     * 修改参数配置
     */
    @GetMapping("/edit/{configId}")
    public String edit(@PathVariable("configId") String configId, ModelMap mmap) {
        mmap.put("config", configService.getById(configId));
        return prefix + "/edit";
    }

    /**
     * 修改保存参数配置
     */
    @RequiresPermissions("system:config:edit")
    @Log(title = "参数管理", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data editSave(SysConfig config) throws Exception {
        config.setUpdateBy(getUser().getLoginName());
        return toAjax(configService.saveOrUpdate(config));
    }

    /**
     * 删除参数配置
     */
    @RequiresPermissions("system:config:remove")
    @Log(title = "参数管理", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(configService.removeById(ids));
    }

    /**
     * 校验参数键名
     */
    @PostMapping("/checkConfigKeyUnique")
    @ResponseBody
    public String checkConfigKeyUnique(SysConfig config) {

        Map<String, Object> params = new HashMap<>(1);
        params.put("configKey", config.getConfigKey());
        return configService.getOne(params) == null ? Constants.CHECK_UNIQUE : Constants.CHECK_NOT_UNIQUE;
    }
}
