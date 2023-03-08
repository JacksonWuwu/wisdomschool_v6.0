package cn.wstom.student.mapper;





import cn.wstom.student.entity.Statistics;
import cn.wstom.student.entity.Student;

import java.util.List;

/**
 * 学生mapper
 * @author dws
 * @date 2019/01/05
 */
public interface StudentMapper extends BaseMapper<Student> {
    /**
     * 查找指定学生的统计信息
     *
     * @param userId
     * @return
     */
    Statistics statisticsById(String userId);


    List<String> selectBycid(String cid);
}
