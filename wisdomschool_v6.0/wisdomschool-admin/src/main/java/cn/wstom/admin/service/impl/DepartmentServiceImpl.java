package cn.wstom.admin.service.impl;

import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dws
 * @date 2019/01/14
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentMapper, Department>
        implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;
}
