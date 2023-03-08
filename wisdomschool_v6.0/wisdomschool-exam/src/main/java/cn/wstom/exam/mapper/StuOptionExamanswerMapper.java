package cn.wstom.exam.mapper;


import cn.wstom.exam.entity.StuOptionExamanswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生考试答案 数据层
 *
 * @author hzh
 * @date 20190304
 */
@Mapper
public interface StuOptionExamanswerMapper extends BaseMapper<StuOptionExamanswer> {

    int updateByIdAns(StuOptionExamanswer stuOptionanswer);

    int updateStuAnswer(StuOptionExamanswer stuOptionanswer);
    int updateAnswerType(StuOptionExamanswer stuOptionanswer);
    StuOptionExamanswer selectByCreatId(@Param("paperOneId")String paperOneId,@Param("createId") String createId, @Param("stoption")String stoption);
    List<StuOptionExamanswer> selectByStudentAnswer(@Param("paperOneId")String paperOneId, @Param("createId") String createId);
}
