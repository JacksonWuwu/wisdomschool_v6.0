package cn.wstom.student.service;


import cn.wstom.student.entity.StudentVo;

import java.util.List;

public interface StudentVoService extends BaseService<StudentVo> {
    List<StudentVo> selectByStudentVos(StudentVo studentVo);

    StudentVo selectById(String id);
}
