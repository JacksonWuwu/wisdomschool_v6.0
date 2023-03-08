package cn.wstom.exam.service;


import cn.wstom.exam.entity.CoursetestStuoptionanswer;

/**
 * 学生答案（课程测试） 服务层
 *
 * @author dws
 * @date 20190315
 */
public interface CoursetestStuoptionanswerService extends BaseService<CoursetestStuoptionanswer> {
    int updateByIdAns(CoursetestStuoptionanswer coursetestStuoptionanswer);

}
