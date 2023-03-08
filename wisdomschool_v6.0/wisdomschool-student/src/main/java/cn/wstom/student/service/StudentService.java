package cn.wstom.student.service;





import cn.wstom.student.entity.Statistics;
import cn.wstom.student.entity.Student;

import java.util.List;

/**
 * 学生service
 *
 * @author dws
 * @date 2019/01/05
 */
public interface StudentService extends BaseService<Student> {

    /**
     * 统计指定的学生数据
     *
     * @param userId
     * @return
     */
    Statistics statisticsById(String userId);


    List<String> selectBycid(String cid);
}
