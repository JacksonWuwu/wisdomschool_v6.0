package cn.wstom.admin.service;

import cn.wstom.admin.entity.CourseResource;


import java.util.List;

/**
 * 课程章节 服务层
 *
 * @author dws
 * @date 20190223
 */
public interface CourseResourceService extends BaseService<CourseResource> {

    /**
     * 根据学生id和课程id查找章节资源
     *
     * @param userId
     * @param courseId
     * @return
     */
    List<CourseResource> selectByCidAndSid(String userId, String courseId);

    int saveBackId(CourseResource courseResource);
}
