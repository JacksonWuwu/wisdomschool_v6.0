package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.Recourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源 数据层
 *
 * @author dws
 * @date 20190222
 */
public interface RecourseMapper extends BaseMapper<Recourse> {
    List<Recourse> getResources(@Param("studentId") String studentId);
    List<Recourse> getResourcePpt(@Param("studentId") String studentId);
    List<Recourse> getResourceVideo(@Param("studentId") String studentId);
    String selectByRecourse(Recourse recourse);
    int addreturn(Recourse recourse);
}
