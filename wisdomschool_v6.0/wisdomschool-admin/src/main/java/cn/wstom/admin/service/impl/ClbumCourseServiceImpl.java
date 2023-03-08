package cn.wstom.admin.service.impl;

import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 班级课程 服务层实现
 *
 * @author hyb
 * @date 20190218
 */
@Service
public class ClbumCourseServiceImpl extends BaseServiceImpl
        <ClbumCourseMapper, ClbumCourse>
        implements ClbumCourseService {

    @Autowired
    private ClbumCourseMapper clbumCourseMapper;


    @Override
    public int ClbumCourseSelectCount(ClbumCourse clbumCourse) {
        return clbumCourseMapper.ClbumCourseSelectCount(clbumCourse);
    }
}
