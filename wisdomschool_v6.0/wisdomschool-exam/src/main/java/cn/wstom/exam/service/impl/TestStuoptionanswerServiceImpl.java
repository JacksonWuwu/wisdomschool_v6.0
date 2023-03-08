package cn.wstom.exam.service.impl;



import cn.wstom.exam.entity.TestStuoptionanswer;
import cn.wstom.exam.mapper.TestStuoptionanswerMapper;
import cn.wstom.exam.service.TestStuoptionanswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
* 学生答案（节测试） 服务层实现
*
* @author dws
* @date 20190308
*/
@Service
public class TestStuoptionanswerServiceImpl extends BaseServiceImpl
        <TestStuoptionanswerMapper, TestStuoptionanswer>
implements TestStuoptionanswerService {

@Autowired
private TestStuoptionanswerMapper testStuoptionanswerMapper;

    @Override
    public int updateByIdAns(TestStuoptionanswer testStuoptionanswer) {
        return testStuoptionanswerMapper.updateByIdAns(testStuoptionanswer);
    }
}
