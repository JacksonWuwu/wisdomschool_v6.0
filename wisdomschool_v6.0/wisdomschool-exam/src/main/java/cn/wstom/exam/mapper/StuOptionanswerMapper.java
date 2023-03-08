package cn.wstom.exam.mapper;


import cn.wstom.exam.entity.StuOptionanswer;

/**
 * 学生考试答案 数据层
 *
 * @author hzh
 * @date 20190304
 */
public interface StuOptionanswerMapper extends BaseMapper<StuOptionanswer> {

    int updateByIdAns(StuOptionanswer stuOptionanswer);

}
