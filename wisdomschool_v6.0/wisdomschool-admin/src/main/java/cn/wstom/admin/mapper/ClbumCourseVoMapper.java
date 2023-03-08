package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.ClbumCourse;

import java.util.List;

/**
 * 班级课程 数据层
 *
 * @author hyb
 * @date 20190218
 */
public interface ClbumCourseVoMapper<T extends ClbumCourse> extends BaseMapper<T> {

    /*  Lin_ ClbumCourseMapper */
    List<T> selectByClbumCourseVos(T clbumCourse);

    List<T> selectClbumCourseVosByClbumId(String clbumId);

    List<T> selectClbumCourseVosByTeacherId(String tId);
}
