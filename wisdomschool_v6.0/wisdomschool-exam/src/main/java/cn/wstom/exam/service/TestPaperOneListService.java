package cn.wstom.exam.service;





import cn.wstom.exam.entity.MyQuestions;
import cn.wstom.exam.entity.TestPaperOneList;

import java.util.List;

public interface TestPaperOneListService extends BaseService<TestPaperOneList> {
    List<TestPaperOneList> selectList(TestPaperOneList testPaperOneList) ;

    boolean UpdateTestPaperOneTestQuestion(MyQuestions myQuestionsList, int score, String difficult, String paperId, String attrId) throws Exception;
}
