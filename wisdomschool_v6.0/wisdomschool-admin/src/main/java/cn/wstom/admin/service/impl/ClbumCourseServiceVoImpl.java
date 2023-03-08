package cn.wstom.admin.service.impl;

import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 班级课程 服务层实现
 *
 * @author hyb
 * @date 20190218
 */
@Service
public class ClbumCourseServiceVoImpl extends BaseServiceImpl
        <ClbumCourseVoMapper<ClbumCourse>, ClbumCourse>
        implements ClbumCourseVoService<ClbumCourse> {
    /*  Lin_   ClbumCourseServiceVoImpl */
    @Autowired
    private ClbumCourseVoMapper clbumCourseVoMapper;

    @Override
    public List<ClbumCourse> selectByClbumCourseVos(ClbumCourse clbumCourse) {
        return clbumCourseVoMapper.selectByClbumCourseVos(clbumCourse);
    }
}
