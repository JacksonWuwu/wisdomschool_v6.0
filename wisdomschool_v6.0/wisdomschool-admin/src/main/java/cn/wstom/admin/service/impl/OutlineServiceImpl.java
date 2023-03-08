package cn.wstom.admin.service.impl;

import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dws
 * @date 2019/03/28
 */
@Service
public class OutlineServiceImpl extends BaseServiceImpl<OutlineMapper, Outline> implements OutlineService {

    @Autowired
    private OutlineMapper outlineMapper;
}
