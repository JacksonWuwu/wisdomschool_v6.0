package cn.wstom.exam.service;


import cn.wstom.exam.entity.TestStuoptionanswer;

/**
* 学生答案（节测试） 服务层
*
* @author dws
* @date 20190308
*/
public interface TestStuoptionanswerService extends BaseService<TestStuoptionanswer> {

    int updateByIdAns(TestStuoptionanswer testStuoptionanswer);

}
