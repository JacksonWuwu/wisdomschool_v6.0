//package cn.wstom.admin.service.impl;
//
//
//import cn.wstom.admin.entity.Statistics;
//import cn.wstom.admin.entity.Student;
//import cn.wstom.admin.mapper.StudentMapper;
//import cn.wstom.admin.service.StudentService;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * @author dws
// * @date 2019/01/05
// */
//@Service
//public class StudentServiceImpl extends BaseServiceImpl<StudentMapper, Student>
//        implements StudentService {
//
//    @Resource
//    private StudentMapper studentMapper;
//
//    @Override
//    public Statistics statisticsById(String userId) {
//        return studentMapper.statisticsById(userId);
//    }
//
//    @Override
//    public List<String> selectBycid(String cid) {
//        return studentMapper.selectBycid(cid);
//    }
//}
