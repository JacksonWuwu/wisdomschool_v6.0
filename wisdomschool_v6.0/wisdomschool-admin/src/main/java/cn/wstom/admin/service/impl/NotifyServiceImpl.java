package cn.wstom.admin.service.impl;

import cn.wstom.admin.service.NotifyService;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 消息 服务层实现
 *
 * @author dws
 * @date 20190227
 */
@Service
public class NotifyServiceImpl extends BaseServiceImpl
        <NotifyMapper, Notify>
        implements NotifyService {

    @Resource
    private NotifyMapper notifyMapper;

    @Autowired
    protected SysUserService userService;
}
