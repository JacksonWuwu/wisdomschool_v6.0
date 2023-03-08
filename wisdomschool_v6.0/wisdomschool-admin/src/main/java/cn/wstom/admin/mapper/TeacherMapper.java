package cn.wstom.admin.mapper;

import cn.wstom.admin.entity.Teacher;


import java.util.List;

/**
 * 教师mapper
 *
 * @author xyl
 * @date 2019/01/05
 */
public interface TeacherMapper extends BaseMapper<Teacher> {
    /**
     * 根据课程id查找教师信息
     *
     * @param tcId
     * @return
     */
    List<Teacher> mapByCids(List<String> tcId);

    /*
     * 根据系部找信息
     * */
    List<Teacher> mapByDeptids(int depe_id);

}
