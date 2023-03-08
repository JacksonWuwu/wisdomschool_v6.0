package cn.wstom.admin.controller.system;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.SysDictType;
import cn.wstom.admin.service.SysDictTypeService;
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
@RequestMapping("/system/dict")
public class SysDictTypeController extends BaseController {
    private String prefix = "system/dict/type";

    @Autowired
    private SysDictTypeService dictTypeService;

    @RequiresPermissions("system:dict:view")
    @GetMapping()
    public String dictType() {
        return prefix + "/list";
    }

    @PostMapping("/list")
    @RequiresPermissions("system:dict:list")
    @ResponseBody
    public TableDataInfo list(SysDictType dictType) {
        startPage();
        List<SysDictType> list = dictTypeService.list(dictType);
        return wrapTable(list);
    }

    @Log(title = "字典类型", actionType = ActionType.EXPORT)
    @RequiresPermissions("system:dict:export")
    @PostMapping("/export")
    @ResponseBody
    public Data export(SysDictType dictType) {

        try {
            List<SysDictType> list = dictTypeService.list(dictType);
            ExcelUtil<SysDictType> util = new ExcelUtil<>(SysDictType.class);
            return util.exportExcel(list, "dictType");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }

    /**
     * 新增字典类型
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存字典类型
     */
    @Log(title = "字典类型", actionType = ActionType.INSERT)
    @RequiresPermissions("system:dict:add")
    @PostMapping("/add")
    @ResponseBody
    public Data addSave(SysDictType dict) throws Exception {
        dict.setCreateBy(getUser().getLoginName());
        return toAjax(dictTypeService.save(dict));
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictId}")
    public String edit(@PathVariable("dictId") String dictId, ModelMap mmap) {
        mmap.put("dict", dictTypeService.getById(dictId));
        return prefix + "/edit";
    }

    /**
     * 修改保存字典类型
     */
    @Log(title = "字典类型", actionType = ActionType.UPDATE)
    @RequiresPermissions("system:dict:edit")
    @PostMapping("/edit")
    @ResponseBody
    public Data editSave(SysDictType dict) throws Exception {
        dict.setUpdateBy(getUser().getLoginName());
        return toAjax(dictTypeService.update(dict));
    }

    @Log(title = "字典类型", actionType = ActionType.DELETE)
    @RequiresPermissions("system:dict:remove")
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        try {
            return toAjax(dictTypeService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 查询字典详细
     */
    @RequiresPermissions("system:dict:list")
    @GetMapping("/detail/{dictId}")
    public String detail(@PathVariable("dictId") String dictId, ModelMap mmap) {
        mmap.put("dict", dictTypeService.getById(dictId));
        mmap.put("dictList", dictTypeService.list(null));
        return "system/dict/data/list";
    }

    /**
     * 校验字典类型
     */
    @PostMapping("/checkDictTypeUnique")
    @ResponseBody
    public String checkDictTypeUnique(SysDictType dictType) {
        return dictTypeService.checkDictTypeUnique(dictType);
    }
}
