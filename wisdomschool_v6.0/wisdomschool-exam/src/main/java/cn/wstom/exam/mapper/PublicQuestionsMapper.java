package cn.wstom.exam.mapper;



import cn.wstom.exam.entity.PublicQuestions;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @date: 2019-01-21 下午 2:02
 */
public interface PublicQuestionsMapper extends BaseMapper<PublicQuestions> {

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

    String getTypeIdById(String id);
}
