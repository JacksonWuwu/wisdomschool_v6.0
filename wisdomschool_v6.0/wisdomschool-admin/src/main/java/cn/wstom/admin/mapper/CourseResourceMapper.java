package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.CourseResource;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程章节 数据层
 *
 * @author dws
 * @date 20190223
 */
public interface CourseResourceMapper extends BaseMapper<CourseResource> {

    List<CourseResource> selectByCidAndSid(@Param("userId") String userId,
                                            @Param("courseId") String courseId
                                           );

    int insertBackId(CourseResource courseResource);

}
