package cn.wstom.admin.service.impl;


import cn.wstom.admin.entity.Answer;
import cn.wstom.admin.entity.PageVo;
import cn.wstom.admin.entity.Question;
import cn.wstom.admin.mapper.AnswerMapper;
import cn.wstom.admin.mapper.QuestionMapper;
import cn.wstom.admin.service.AnswerService;
import cn.wstom.admin.service.FeedService;
import cn.wstom.admin.service.ImagesService;
import cn.wstom.admin.service.SysUserService;
import cn.wstom.common.constant.UserConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 答案处理服务
 */
@Service
public class AnswerServiceImpl extends BaseServiceImpl
        <AnswerMapper, Answer>
        implements AnswerService {



    //private final QuestionService questionService;

    @Resource
    private AnswerMapper answerMapper;

    @Resource
    private QuestionMapper questionMapper;

    private final FeedService feedService;
    private final ImagesService imagesService;

    private final SysUserService userService;

    @Autowired
    public AnswerServiceImpl(/*QuestionService questionService, */FeedService feedService, ImagesService imagesService, SysUserService userService) {
        /*this.questionService = questionService;*/
        this.feedService = feedService;
        this.imagesService = imagesService;
        this.userService = userService;
    }


    /**
     * 添加用户答案
     *
     * @param questionId
     * @param userId
     * @param content
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addAnswer(String questionId, String userId, String content) throws Exception {
        if (checkAnswerByContent(userId, content)) {
            throw new Exception("请勿重复发表相同内容");
        }
        Answer answer = new Answer();
        answer.setQuestionId(questionId);
        answer.setUserId(userId);
        answer.setContent(imagesService.replaceContent(1, answer.getId(), answer.getUserId(), content));
        answer.setCreateTime(new Date());
        answer.setStatus(0);
        int totalCount = answerMapper.addAnswer(answer);
        if (totalCount > 0) {
            if (answer.getStatus() == 0) {
                //添加用户feed信息
                feedService.addUserFeed(userId, 3, answer.getId());
                //更新用户发布答案数量
                userService.updateAnswer(userId, UserConstants.STEP_INCREASE);
                //更新回答数量统计
                questionMapper.updateQuestionByAnswerCount(questionId);

            }
            //添加答案统计信息
            return answerMapper.addAnswerCount(answer.getId());
        }
        return 0;
    }

    // ///////////////////////////////
    // /////        刪除      ////////
    // ///////////////////////////////
    //按答案id删除答案信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAnswerById(String id) throws Exception {
        Answer answer = answerMapper.findAnswerById(id, 0);
        if (answer != null) {
            //更新回答数量统计
            questionMapper.updateQuestionByAnswerCount(answer.getQuestionId());
            //查询该Feed答案修改审核状态，设置为影藏
            feedService.updateuUserFeedById(3, answer.getId(), 0);
            //更新用户发布答案数量
            userService.updateAnswer(answer.getUserId(), UserConstants.STEP_INCREASE);
            //答案id删除该id信息
            answerMapper.deleteAnswerById(id);
            //按答案id删除答案相关统计信息
            return answerMapper.deleteAnswerCountById(answer.getId());
        }
        return 0;
    }

    //执行删除问题时同时删除关联的答案内容
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteQuestionAndAnswerById(String id) throws Exception {
        Answer answer = answerMapper.findAnswerById(id, 0);
        if (answer != null) {
            //答案内内容里的图片未做删除处理，这里备忘！

            //更新统计用户所有发布的答案数量
            userService.updateAnswer(answer.getUserId(), UserConstants.STEP_INCREASE);
            answerMapper.deleteAnswerById(id);
            return true;
        }
        return false;
    }

    // ///////////////////////////////
    // /////        修改      ////////
    // ///////////////////////////////

    /**
     * 按id更新文章审核状态
     *
     * @param id     问题id
     * @param status 0未审核 1正常状态 2审核未通过 3删除
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAnswerStatusById(String id, Integer status) throws Exception {
        Answer answer = answerMapper.findAnswerById(id, 0);
        if (answer == null) {
            throw new Exception("该信息不存在或已删除");
        }
        answerMapper.updateAnswerStatusById(id, status);
        if (status == 1) {
            //添加用户feed信息,如果存在在修改状态为1
            if (feedService.checkUserFeed(answer.getUserId(), 3, answer.getId())) {
                feedService.addUserFeed(answer.getUserId(), 3, answer.getId());
            } else {
                feedService.updateuUserFeedById(3, answer.getId(), 1);
            }
            userService.updateAnswer(answer.getUserId(), UserConstants.STEP_INCREASE);
            //更新回答数量统计
            questionMapper.updateQuestionByAnswerCount(answer.getQuestionId());
        } else {
            //删除用户feed信息
            feedService.updateuUserFeedById(3, answer.getId(), 0);
            //更新用户问答数量
            userService.updateAnswer(answer.getUserId(), UserConstants.STEP_INCREASE);
            //更新回答数量统计
            return questionMapper.updateQuestionByAnswerCount(answer.getQuestionId());
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAnswerById(String id, String userId, String content) throws Exception {
        Answer answer = this.findAnswerByIdAndUserId(id, userId);
        if (answer == null) {
            throw new Exception("该答案不存在或已删除");
        }
        answer.setContent(imagesService.replaceContent(1, answer.getId(), answer.getUserId(), answer.getContent()));
        // TODO: 2019/3/11 移至控制层
        answer.setCreateBy("");
        answer.setCreateTime(new Date());
        Question question = new Question();
        //Question question = questionService.getById(answer.getQuestionId());
        if (question.getStatus() != 1) {
            answer.setStatus(0);
        } else {
            answer.setStatus(2);
        }
        answerMapper.updateAnswerById(answer);
        userService.updateAnswer(answer.getUserId(), UserConstants.STEP_INCREASE);
        //更新回答数量统计
        return questionMapper.updateQuestionByAnswerCount(answer.getQuestionId());
    }


    /**
     * 按id查询答案信息
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public Answer findAnswerById(String id, Integer status) {
        return answerMapper.findAnswerById(id, status);
    }

    /**
     * 按id和用户id查询评论内容
     *
     * @param id     答案id
     * @param userId 用户id
     * @return
     */
    @Override
    public Answer findAnswerByIdAndUserId(String id, String userId) {
        return answerMapper.findAnswerByIdAndUserId(id, userId);
    }

    /**
     * 查询该用户同样答案内容是否已存在！
     *
     * @param userId  用户id
     * @param content 答案内容
     * @return
     */
    @Override
    public boolean checkAnswerByContent(String userId, String content) {
        int totalCount = answerMapper.checkAnswerByContent(userId, content);
        return totalCount > 0;
    }

    /**
     * 答案列表翻页查询
     *
     * @param pageNum
     * @param rows
     * @return
     * @throws Exception
     */
    @Override
    public PageVo<Answer> getAnswerListPage(String questionId, String userId, String createTime, Integer status, String orderBy, String order, int pageNum, int rows) {
        PageVo<Answer> pageVo = new PageVo<>(pageNum);
        pageVo.setRows(rows);
        List<Answer> list = new ArrayList<>();
        if (orderBy == null) {
            orderBy = "id";
        }
        if (order == null) {
            order = "desc";
        }
        pageVo.setList(answerMapper.getAnswerList(questionId, userId, createTime, status, orderBy, order, pageVo.getOffset(), pageVo.getRows()));
        pageVo.setCount(answerMapper.getAnswerCount(questionId, userId, createTime, status));
        return pageVo;
    }

    //按问题id或者用户id查询答案列表
    @Override
    public List<Answer> gettAnswerByQuestionIdList(String questionId, String userId) {
        return answerMapper.gettAnswerByQuestionIdList(questionId, userId);
    }

    //按问题id查询最新的第一条评论内容
    @Override
    public Answer findNewestAnswerById(String questionId) {
        return answerMapper.findNewestAnswerById(questionId);
    }
}
