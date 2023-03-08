package cn.wstom.admin.service.impl;

import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 专业 服务层实现
 *
 * @author xyl
 * @date 2019-01-25
 */
@Service
public class MajorServiceImpl extends BaseServiceImpl
        <MajorMapper, Major>
        implements MajorService {

    @Autowired
    private MajorMapper majorMapper;
}
