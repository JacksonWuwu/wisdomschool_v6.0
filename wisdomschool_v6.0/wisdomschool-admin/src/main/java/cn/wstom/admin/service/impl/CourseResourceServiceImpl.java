package cn.wstom.admin.service.impl;


import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程章节资源 服务层实现
 *
 * @author dws
 * @date 20190223
 */
@Service
public class CourseResourceServiceImpl extends BaseServiceImpl<CourseResourceMapper, CourseResource>
        implements CourseResourceService {

    @Resource
    private CourseResourceMapper courseResourceMapper;

    @Override
    public List<CourseResource> selectByCidAndSid(String userId, String courseId) {
        return courseResourceMapper.selectByCidAndSid(userId, courseId);
    }

    @Override
    public int saveBackId(CourseResource courseResource) {
        return courseResourceMapper.insertBackId(courseResource);
    }
}
