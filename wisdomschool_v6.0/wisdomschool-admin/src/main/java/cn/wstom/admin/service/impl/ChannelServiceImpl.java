package cn.wstom.admin.service.impl;

import cn.wstom.admin.service.ChannelService;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 栏目 服务层实现
 *
 * @author dws
 * @date 20190227
 */
@Service
public class ChannelServiceImpl extends BaseServiceImpl
        <ChannelMapper, Channel>
        implements ChannelService {

    @Resource
    private ChannelMapper channelMapper;
}
