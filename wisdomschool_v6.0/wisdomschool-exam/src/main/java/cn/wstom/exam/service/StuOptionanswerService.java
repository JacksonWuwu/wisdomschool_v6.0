package cn.wstom.exam.service;




import cn.wstom.exam.entity.OptionSubmitVo;
import cn.wstom.exam.entity.StuOptionanswer;

import java.util.List;

/**
 * 学生考试答案 服务层
 *
 * @author hzh
 * @date 20190304
 */
public interface StuOptionanswerService extends BaseService<StuOptionanswer> {
    int updateByIdAns(StuOptionanswer stuOptionanswer);

    /**
     * 保存学生做题记录
     * @param options
     * @param studentId
     * @return
     */
    int saveOptionAnswers(List<OptionSubmitVo> options, String studentId);

    /**
     * 更新做题的分数以及总分
     * @param optionanswers
     * @param testPaperId
     * @return
     */
    int updateListAndTotalScore(List<StuOptionanswer> optionanswers, String testPaperId, String studentId);

    int saveOptionAnswersByUserId(List<OptionSubmitVo> options, String userId);
}
