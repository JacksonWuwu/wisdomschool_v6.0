package cn.wstom.exam.mapper;



import cn.wstom.exam.entity.MyQuestions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @date: 2019-01-18 下午 8:56
 */

public interface MyQuestionsMapper extends BaseMapper<MyQuestions> {
    /**
     * 按条件查询问题
     */
    @Override
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
    List<MyQuestions> selectQuestionByTid(@Param("titleTypeId") String titleTypeId, @Param("xzsubjectsId") String xzsubjectsId);
    List<MyQuestions> selectExamQuestion1(@Param("createId") String tid,@Param("xzsubjectsId")String cid,@Param("titleTypeNum") String Num);
    List<MyQuestions> selectExamQuestion2(@Param("createId") String tid,@Param("xzsubjectsId")String cid,@Param("titleTypeNum") String Num);
    List<MyQuestions> selectExamQuestion3(@Param("createId") String tid,@Param("xzsubjectsId")String cid,@Param("titleTypeNum") String Num);
    List<MyQuestions> selectExamQuestion4(@Param("createId") String tid,@Param("xzsubjectsId")String cid,@Param("titleTypeNum") String Num);
    List<MyQuestions> selectExamQuestion5(@Param("createId") String tid,@Param("xzsubjectsId")String cid,@Param("titleTypeNum") String Num);
    List<MyQuestions> selectExamQuestion6(@Param("createId") String tid,@Param("xzsubjectsId")String cid,@Param("titleTypeNum") String Num);

}
