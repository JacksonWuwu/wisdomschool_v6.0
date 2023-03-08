package cn.wstom.admin.service;

import cn.wstom.admin.entity.ClbumCourse;


/**
 * 班级课程 服务层
 *
 * @author hyb
 * @date 20190218
 */
public interface ClbumCourseService extends BaseService<ClbumCourse> {
    /*
     * 检验是否存在
     * */
    int ClbumCourseSelectCount(ClbumCourse clbumCourse);
}
