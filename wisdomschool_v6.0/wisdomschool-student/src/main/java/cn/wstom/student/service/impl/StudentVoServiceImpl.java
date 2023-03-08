package cn.wstom.student.service.impl;


import cn.wstom.student.entity.StudentVo;
import cn.wstom.student.mapper.StudentVoMapper;
import cn.wstom.student.service.StudentVoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentVoServiceImpl extends BaseServiceImpl<StudentVoMapper, StudentVo> implements StudentVoService {

    @Resource
    private StudentVoMapper studentVoMapper;

    @Override
    public List<StudentVo> selectByStudentVos(StudentVo studentVo) {
        return studentVoMapper.selectByStudentVos(studentVo);
    }

    @Override
    public StudentVo selectById(String id) {
        return studentVoMapper.selectById(id);
    }
}
