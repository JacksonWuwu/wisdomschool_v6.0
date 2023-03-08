package cn.wstom.admin.controller.system;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.SysNotice;
import cn.wstom.admin.service.SysNoticeService;
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

import java.util.List;

/**
 * 公告 信息操作处理
 *
 * @author dws
 */
@Controller
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
    private String prefix = "system/notice";

    @Autowired
    private SysNoticeService noticeService;

    @RequiresPermissions("system:notice:view")
    @GetMapping()
    public String notice() {
        return prefix + "/list";
    }

    /**
     * 查询公告列表
     */
    @RequiresPermissions("system:notice:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysNotice notice) {
        startPage();
        List<SysNotice> list = noticeService.list(notice);
        return wrapTable(list);
    }

    /**
     * 新增公告
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存公告
     */
    @RequiresPermissions("system:notice:add")
    @Log(title = "通知公告", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data addSave(SysNotice notice) throws Exception {
        notice.setCreateBy(getUser().getLoginName());
        return toAjax(noticeService.save(notice));
    }

    /**
     * 修改公告
     */
    @GetMapping("/edit/{noticeId}")
    public String edit(@PathVariable("noticeId") String noticeId, ModelMap mmap) {
        mmap.put("notice", noticeService.getById(noticeId));
        return prefix + "/edit";
    }

    /**
     * 修改保存公告
     */
    @RequiresPermissions("system:notice:edit")
    @Log(title = "通知公告", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data editSave(SysNotice notice) throws Exception {
        notice.setUpdateBy(getUser().getLoginName());
        return toAjax(noticeService.update(notice));
    }

    /**
     * 删除公告
     */
    @RequiresPermissions("system:notice:remove")
    @Log(title = "通知公告", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        try {
            return toAjax(noticeService.removeByIds(Convert.toStrList(ids)));
        } catch (Exception e) {
            throw new Exception("删除公告异常");
        }
    }
}
