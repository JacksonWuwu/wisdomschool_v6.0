package ${package}.web.controller;

import java.util.List;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ${package}.entity.${className};
import ${package}.service.${className}Service;

/**
* ${tableComment} 信息操作处理
*
* @author ${author}
* @date ${datetime}
*/
@Controller
@RequestMapping("/${moduleName}/${classname}")
public class ${className}Controller extends BaseController {
private String prefix = "${moduleName}/${classname}";

@Autowired
private ${className}Service ${classname}Service;

@RequiresPermissions("${moduleName}:${classname}:view")
@GetMapping()
public String toList() {
return prefix + "list";
}

/**
* 查询${tableComment}列表
*/
@RequiresPermissions("${moduleName}:${classname}:list")
@PostMapping("/list")
@ResponseBody
public TableDataInfo list(${className} ${classname}) {
startPage();
List<${className}> list = ${classname}Service.list(${classname});
return wrapTable(list);
}

/**
* 新增${tableComment}
*/
@GetMapping("/add")
public String toAdd() {
return prefix + "/add";
}

/**
* 新增保存${tableComment}
*/
@RequiresPermissions("${moduleName}:${classname}:add")
@Log(title = "${tableComment}", actionType = BusinessType.INSERT)
@PostMapping("/add")
@ResponseBody
public Data add(${className} ${classname}) {
return toAjax(${classname}Service.save(${classname}));
}

/**
* 修改${tableComment}
*/
@GetMapping("/edit/{${primaryKey.attrname}}")
public String toEdit(@PathVariable("${primaryKey.attrname}") ${primaryKey.attrType} ${primaryKey.attrname}, ModelMap mmap) {
${className} ${classname} = ${classname}Service.getById(${primaryKey.attrname});
mmap.put("${classname}", ${classname});
return prefix + "/edit";
}

/**
* 修改保存${tableComment}
*/
@RequiresPermissions("${moduleName}:${classname}:edit")
@Log(title = "${tableComment}", actionType = BusinessType.UPDATE)
@PostMapping("/edit")
@ResponseBody
public Data edit(${className} ${classname}) {
return toAjax(${classname}Service.update(${classname}));
}

/**
* 删除${tableComment}
*/
@RequiresPermissions("${moduleName}:${classname}:remove")
@Log(title = "${tableComment}", actionType = BusinessType.DELETE)
@PostMapping( "/remove")
@ResponseBody
public Data remove(String ids) {
return toAjax(${classname}Service.removeById(ids));
}
}
