package cn.wstom.exam.service;



import cn.wstom.exam.entity.OptionExamSubmitVo;
import cn.wstom.exam.entity.StuOptionExamanswer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生考试答案 服务层
 *
 * @author hzh
 * @date 20190304
 */
public interface StuOptionExamanswerService extends BaseService<StuOptionExamanswer> {
    int updateByIdAns(StuOptionExamanswer stuOptionanswer);

    /**
     * 保存学生做题记录
     * @param options
     * @param studentId
     * @return
     */
    int saveOptionAnswers(List<OptionExamSubmitVo> options, String studentId);

    /**
     * 更新做题的分数以及总分
     * @param optionanswers
     * @param testPaperId
     * @return
     */
    int updateListAndTotalScore(List<StuOptionExamanswer> optionanswers, String testPaperId, String studentId);

    int saveOptionAnswersByUserId(List<OptionExamSubmitVo> options, String userId);
    int submitOptionAnswersByUserId(List<OptionExamSubmitVo> options, String userId);
    int autoSubmitOptionAnswersByUserId(List<OptionExamSubmitVo> options, String userId);

    int submitPaperWorkOptionAnswersByUserId(List<OptionExamSubmitVo> options, String userId);
    int updateStuAnswer(StuOptionExamanswer stuOptionanswer);
    int updateAnswerType(StuOptionExamanswer stuOptionanswer);
    StuOptionExamanswer selectByCreatId(@Param("paperOneId")String paperOneId, @Param("createId") String createId, @Param("stoption")String stoption);
    List<StuOptionExamanswer> selectByStudentAnswer(@Param("paperOneId")String paperOneId, @Param("createId") String createId);



}
