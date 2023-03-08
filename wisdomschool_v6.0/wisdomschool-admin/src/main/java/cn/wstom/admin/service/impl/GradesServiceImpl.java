package cn.wstom.admin.service.impl;


import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 年级 服务层实现
 *
 * @author xyl
 * @date 2019-01-22
 */
@Service
public class GradesServiceImpl extends BaseServiceImpl<GradesMapper, Grades>
        implements GradesService {

    @Autowired
    private GradesMapper gradesMapper;
}
