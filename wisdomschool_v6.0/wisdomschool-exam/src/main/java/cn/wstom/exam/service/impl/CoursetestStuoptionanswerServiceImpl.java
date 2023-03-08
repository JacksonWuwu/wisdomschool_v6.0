package cn.wstom.exam.service.impl;


import cn.wstom.exam.entity.CoursetestStuoptionanswer;
import cn.wstom.exam.mapper.CoursetestStuoptionanswerMapper;
import cn.wstom.exam.service.CoursetestStuoptionanswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 学生答案（课程测试） 服务层实现
 *
 * @author dws
 * @date 20190315
 */
@Service
public class CoursetestStuoptionanswerServiceImpl extends BaseServiceImpl
        <CoursetestStuoptionanswerMapper, CoursetestStuoptionanswer>
        implements CoursetestStuoptionanswerService {

    @Autowired
    private CoursetestStuoptionanswerMapper coursetestStuoptionanswerMapper;

    @Override
    public int updateByIdAns(CoursetestStuoptionanswer coursetestStuoptionanswer) {
        return coursetestStuoptionanswerMapper.updateByIdAns(coursetestStuoptionanswer);
    }
}
