package cn.wstom.exam.mapper;


import cn.wstom.exam.entity.CoursetestStuoptionanswer;

/**
 * 学生答案（课程测试） 数据层
 *
 * @author dws
 * @date 20190315
 */
public interface CoursetestStuoptionanswerMapper extends BaseMapper<CoursetestStuoptionanswer> {
    int updateByIdAns(CoursetestStuoptionanswer coursetestStuoptionanswer);

}
