package cn.wstom.exam.service;






import cn.wstom.exam.entity.MyQuestions;

import java.util.List;

public interface MyQuestionsService extends BaseService<MyQuestions> {
    /**
     * 按条件查询问题
     */
    List<MyQuestions> selectList(MyQuestions myQuestions);

    /**
     * 查询未整理的代码
     * @param myQuestions
     * @return
     */
    List<MyQuestions> unList(MyQuestions myQuestions);
    /**
     * 由Id查询未整理的代码
     * @param id
     * @return
     */
    MyQuestions getUnListById(String id);

    /**
     * 通过ids查询试题
     * @param ids
     * @return
     */
    List<MyQuestions> selectBatchByChapterIds(List<String> ids);

    /**
     * 查询一门科目中的一种题型的全部试题
     *
     * @param titleTypeId
     * @param xzsubjectsId
     * @return
     */
    List<MyQuestions> selectQuestionByTid(String titleTypeId, String xzsubjectsId);

    List<MyQuestions> selectExamQuestion1(String tid,String cid,String Num);
    List<MyQuestions> selectExamQuestion2(String tid,String cid,String Num);
    List<MyQuestions> selectExamQuestion3(String tid,String cid,String Num);
    List<MyQuestions> selectExamQuestion4(String tid,String cid,String Num);
    List<MyQuestions> selectExamQuestion5(String tid,String cid,String Num);
    List<MyQuestions> selectExamQuestion6(String tid,String cid,String Num);
}

