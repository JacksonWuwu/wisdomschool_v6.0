package cn.wstom.admin.service;



import cn.wstom.admin.entity.Teacher;

import java.util.List;
import java.util.Map;

/**
 * 学生service
 *
 * @author dws
 * @date 2019/01/05
 */
public interface TeacherService extends BaseService<Teacher> {
    /**
     * 根据课程id查找教师信息
     *
     * @param tcId
     * @return
     */
    Map<String, Teacher> mapByCids(List<String> tcId);
    /*
     * 根据系部找信息
     * */
    List<Teacher> mapByDeptids(int depe_id);
}
