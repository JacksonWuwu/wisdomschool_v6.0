package cn.wstom.exam.service.impl;


import cn.wstom.exam.entity.MyQuestions;
import cn.wstom.exam.mapper.MyQuestionsMapper;
import cn.wstom.exam.service.MyQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyQuestionsServiceImpl extends BaseServiceImpl<MyQuestionsMapper, MyQuestions> implements MyQuestionsService {

    @Autowired
    MyQuestionsMapper myQuestionsMapper;

    /**
     * 按条件查询问题
     */
    @Override
    public List<MyQuestions> selectList(MyQuestions myQuestions){
        return  myQuestionsMapper.selectList(myQuestions);
    }
    /**
     * 查询未整理的代码
     * @param myQuestions
     * @return
     */
    @Override
    public List<MyQuestions> unList(MyQuestions myQuestions) {
        return myQuestionsMapper.unList(myQuestions);
    }
    /**
     * 由Id查询未整理的代码
     * @param id
     * @return
     */
    @Override
    public MyQuestions getUnListById(String id) {
        return myQuestionsMapper.getUnListById(id);
    }

    /**
     * 通过ids查询试题
     *
     * @param ids
     * @return
     */
    @Override
    public List<MyQuestions> selectBatchByChapterIds(List<String> ids) {
        return myQuestionsMapper.selectBatchByChapterIds(ids);
    }

    /**
     * 查询一门科目中的一种题型的全部试题
     *
     * @param titleTypeId
     * @param xzsubjectsId
     * @return
     */
    @Override
    public List<MyQuestions> selectQuestionByTid(String titleTypeId, String xzsubjectsId) {
        return myQuestionsMapper.selectQuestionByTid(titleTypeId, xzsubjectsId);
    }

    @Override
    public List<MyQuestions> selectExamQuestion1(String tid, String cid, String Num) {
        return myQuestionsMapper.selectExamQuestion1(tid,cid,Num);
    }

    @Override
    public List<MyQuestions> selectExamQuestion2(String tid, String cid, String Num) {
        return myQuestionsMapper.selectExamQuestion2(tid,cid,Num);
    }

    @Override
    public List<MyQuestions> selectExamQuestion3(String tid, String cid, String Num) {
        return myQuestionsMapper.selectExamQuestion3(tid,cid,Num);
    }

    @Override
    public List<MyQuestions> selectExamQuestion4(String tid, String cid, String Num) {
        return myQuestionsMapper.selectExamQuestion4(tid,cid,Num);
    }

    @Override
    public List<MyQuestions> selectExamQuestion5(String tid, String cid, String Num) {
        return myQuestionsMapper.selectExamQuestion5(tid,cid,Num);
    }

    @Override
    public List<MyQuestions> selectExamQuestion6(String tid, String cid, String Num) {
        return myQuestionsMapper.selectExamQuestion6(tid,cid,Num);
    }


}

