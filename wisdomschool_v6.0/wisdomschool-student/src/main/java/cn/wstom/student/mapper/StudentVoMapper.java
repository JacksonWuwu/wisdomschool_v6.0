package cn.wstom.student.mapper;



import cn.wstom.student.entity.StudentVo;

import java.util.List;

public interface StudentVoMapper extends BaseMapper<StudentVo> {
    List<StudentVo> selectByStudentVos(StudentVo studentVo);
    StudentVo selectById(String id);
}
