package cn.wstom.admin.service;

import cn.wstom.admin.entity.ClbumCourse;


import java.util.List;

/**
 * 班级课程 服务层
 *
 * @author hyb
 * @date 20190218
 */
public interface ClbumCourseVoService<T extends ClbumCourse> extends BaseService<T> {
    /*  Lin_ ClbumCourseMapper */
    List<T> selectByClbumCourseVos(T clbumCourse);
}
