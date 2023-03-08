package cn.wstom.admin.controller.system;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.SysDictData;
import cn.wstom.admin.service.SysDictDataService;
import cn.wstom.admin.utils.ExcelUtil;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.support.Convert;

import cn.wstom.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author dws
 */
@Controller
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {
    private String prefix = "system/dict/data";

    @Autowired
    private SysDictDataService dictDataService;

    @RequiresPermissions("system:dict:view")
    @GetMapping()
    public String dictData() {
        return prefix + "/list";
    }

    @PostMapping("/list")
    @RequiresPermissions("system:dict:list")
    @ResponseBody
    public TableDataInfo list(SysDictData dictData) {
        startPage();
        List<SysDictData> list = dictDataService.list(dictData);
        return wrapTable(list);
    }

    @Log(title = "字典数据", actionType = ActionType.EXPORT)
    @RequiresPermissions("system:dict:export")
    @PostMapping("/export")
    @ResponseBody
    public Data export(SysDictData dictData) {
        try {
            List<SysDictData> list = dictDataService.list(dictData);
            ExcelUtil<SysDictData> util = new ExcelUtil<>(SysDictData.class);
            return util.exportExcel(list, "dictData");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }

    /**
     * 新增字典类型
     */
    @GetMapping("/add/{dictType}")
    public String add(@PathVariable("dictType") String dictType, ModelMap mmap) {
        mmap.put("dictType", dictType);
        return prefix + "/add";
    }

    /**
     * 新增保存字典类型
     */
    @Log(title = "字典数据", actionType = ActionType.INSERT)
    @RequiresPermissions("system:dict:add")
    @PostMapping("/add")
    @ResponseBody
    public Data addSave(SysDictData dict) throws Exception {
        dict.setCreateBy(getUser().getLoginName());
        return toAjax(dictDataService.save(dict));
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictCode}")
    public String edit(@PathVariable("dictCode") Long dictCode, ModelMap mmap) {
        mmap.put("dict", dictDataService.getById(dictCode));
        return prefix + "/edit";
    }

    /**
     * 修改保存字典类型
     */
    @Log(title = "字典数据", actionType = ActionType.UPDATE)
    @RequiresPermissions("system:dict:edit")
    @PostMapping("/edit")
    @ResponseBody
    public Data editSave(SysDictData dict) throws Exception {
        dict.setUpdateBy(getUser().getLoginName());
        return toAjax(dictDataService.update(dict));
    }

    @Log(title = "字典数据", actionType = ActionType.DELETE)
    @RequiresPermissions("system:dict:remove")
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        try {
            return toAjax(dictDataService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
        } catch (Exception e) {
            throw new Exception("字典数据删除异常");
        }
    }
}
