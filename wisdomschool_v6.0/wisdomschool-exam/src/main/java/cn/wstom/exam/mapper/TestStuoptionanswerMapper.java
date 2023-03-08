package cn.wstom.exam.mapper;


import cn.wstom.exam.entity.TestStuoptionanswer;

/**
* 学生答案（节测试） 数据层
*
* @author dws
* @date 20190308
*/
public interface TestStuoptionanswerMapper extends BaseMapper<TestStuoptionanswer> {

    int updateByIdAns(TestStuoptionanswer testStuoptionanswer);

}
