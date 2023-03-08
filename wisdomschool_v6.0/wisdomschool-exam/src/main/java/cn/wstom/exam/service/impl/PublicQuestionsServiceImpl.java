package cn.wstom.exam.service.impl;


import cn.wstom.exam.entity.PublicQuestions;
import cn.wstom.exam.mapper.PublicQuestionsMapper;
import cn.wstom.exam.service.PublicQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicQuestionsServiceImpl extends BaseServiceImpl<PublicQuestionsMapper, PublicQuestions> implements PublicQuestionsService {
   @Autowired
   PublicQuestionsMapper publicQuestionsMapper;
    /**
     * 查询未整理的代码
     * @param publicQuestions
     * @return
     */
    @Override
    public List<PublicQuestions> unList(PublicQuestions publicQuestions) {
        return publicQuestionsMapper.unList(publicQuestions);
    }
    /**
     * 由Id查询未整理的代码
     * @param id
     * @return
     */
    @Override
    public PublicQuestions getUnListById(String id) {
        return publicQuestionsMapper.getUnListById(id);
    }

    @Override
    public String getTypeIdById(String id) {
        return publicQuestionsMapper.getTypeIdById(id);
    }
}
