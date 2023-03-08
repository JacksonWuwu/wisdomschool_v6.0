package cn.wstom.admin.controller.monitor;

import cn.wstom.admin.service.impl.SysUserOnlineServiceImpl;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.enums.OnlineStatus;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 在线用户监控
 *
 * @author dws
 */
@Controller
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {
    private String prefix = "monitor/online";

    @Autowired
    private SysUserOnlineService userOnlineService;

    //@Autowired
    //private OnlineSessionDAO onlineSessionDAO;


    @RequestMapping(value = "/sysUserOnlineList")
    @ResponseBody
    List<SysUserOnline> sysUserOnlineList(@RequestBody SysUserOnline sysUserOnline){
        return userOnlineService.selectUserOnlineList(sysUserOnline);
    }

    @RequestMapping(value = "/{sysUserOnlineId}")
    @ResponseBody
    Boolean removeSysUserOnlineById(@PathVariable(value = "sysUserOnlineId")String sysUserOnlineId) throws Exception {
        return userOnlineService.removeById(sysUserOnlineId);
    }


    @GetMapping()
    public String online() {
        return prefix + "/online";
    }


    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysUserOnline sysUserOnline) {
        startPage();
        List<SysUserOnline> list = userOnlineService.selectUserOnlineList(sysUserOnline);
        return wrapTable(list);
    }


    @Log(title = "在线用户", actionType = ActionType.FORCE)
    @PostMapping("/batchForceLogout")
    @ResponseBody
    public Data batchForceLogout(@RequestParam("ids[]") String[] ids) throws Exception {
        for (String sessionId : ids) {
            SysUserOnline online = userOnlineService.getById(sessionId);
            if (online == null) {
                return error("用户已下线");
            }
            //OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getId());
            //if (onlineSession == null) {
            //    return error("用户已下线");
            //}
            //if (sessionId.equals(ShiroUtils.getSessionId())) {
            //    return error("当前登陆用户无法强退");
            //}
            //onlineSession.setStatus(OnlineStatus.OFF_LINE);
            online.setStatus(OnlineStatus.OFF_LINE);
            userOnlineService.save(online);
        }
        return success();
    }


    @Log(title = "在线用户", actionType = ActionType.FORCE)
    @PostMapping("/forceLogout")
    @ResponseBody
    public Data forceLogout(String sessionId) throws Exception {
        SysUserOnline online = userOnlineService.getById(sessionId);
        //if (sessionId.equals(ShiroUtils.getSessionId())) {
        //    return error("当前登陆用户无法强退");
        //}
        if (online == null) {
            return error("用户已下线");
        }
        //OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getId());
        //if (onlineSession == null) {
        //    return error("用户已下线");
        //}
        //onlineSession.setStatus(OnlineStatus.OFF_LINE);
        online.setStatus(OnlineStatus.OFF_LINE);
        userOnlineService.save(online);
        return success();
    }
}
