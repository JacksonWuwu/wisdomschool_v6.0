package cn.wstom.admin.service.impl;

import cn.wstom.admin.service.TagService;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 标签 服务层实现
 *
 * @author dws
 * @date 20190227
 */
@Service
public class TagServiceImpl extends BaseServiceImpl
        <TagMapper, Tag>
        implements TagService {

    @Resource
    private TagMapper tagMapper;
}
