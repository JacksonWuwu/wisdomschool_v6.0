package cn.wstom.admin.service;

import cn.wstom.admin.entity.Answer;
import cn.wstom.admin.entity.PageVo;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
public interface AnswerService extends BaseService<Answer> {
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "answer", allEntries = true)
    int addAnswer(String questionId, String userId, String content) throws Exception;

    /**
     * 按答案id删除答案信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @CacheEvict(value = "answer", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    int deleteAnswerById(String id) throws Exception;

    /**
     * 执行删除问题时同时删除关联的答案内容
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"answer", "question"}, allEntries = true)
    boolean deleteQuestionAndAnswerById(String id) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    int updateAnswerStatusById(String id, Integer status) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"answer"}, allEntries = true)
    int updateAnswerById(String id, String userId, String content) throws Exception;

    /**
     * 按id查询答案信息
     *
     * @param id
     * @param status
     * @return
     */
    @Cacheable(value = "answer", key = "#id")
    Answer findAnswerById(String id, Integer status);

    @Cacheable(value = "answer")
    Answer findAnswerByIdAndUserId(String id, String userId);

    boolean checkAnswerByContent(String userId, String content);

    @Cacheable(value = "answer")
    PageVo<Answer> getAnswerListPage(String questionId, String userId, String createTime, Integer status, String orderBy, String order, int pageNum, int rows);

    /**
     * 按问题id或者用户id查询答案列表
     *
     * @param questionId
     * @param userId
     * @return
     */
    @Cacheable(value = "answer")
    List<Answer> gettAnswerByQuestionIdList(String questionId, String userId);

    /**
     * 按问题id查询最新的第一条评论内容
     *
     * @param questionId
     * @return
     */
    Answer findNewestAnswerById(String questionId);
}
