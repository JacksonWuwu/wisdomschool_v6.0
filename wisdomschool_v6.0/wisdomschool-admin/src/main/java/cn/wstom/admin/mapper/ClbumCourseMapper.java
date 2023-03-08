package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.ClbumCourse;

/**
 * 班级课程 数据层
 *
 * @author hyb
 * @date 20190218
 */
public interface ClbumCourseMapper extends BaseMapper<ClbumCourse> {
    /*
    * 检验是否存在
    * */
      int ClbumCourseSelectCount(ClbumCourse clbumCourse);
}
