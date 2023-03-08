package cn.wstom.exam.service;





import cn.wstom.exam.entity.PublicQuestions;

import java.util.List;

public interface PublicQuestionsService extends BaseService<PublicQuestions> {

    /**
     * 查询未整理的代码
     * @param publicQuestions
     * @return
     */
    List<PublicQuestions> unList(PublicQuestions publicQuestions);
    /**
     * 由Id查询未整理的代码
     * @param id
     * @return
     */
    PublicQuestions getUnListById(String id);

    public String getTypeIdById(String id);
}


