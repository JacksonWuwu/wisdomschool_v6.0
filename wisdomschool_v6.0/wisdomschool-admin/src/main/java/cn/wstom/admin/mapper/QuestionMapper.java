package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.Question;
import cn.wstom.admin.entity.QuestionCount;
import cn.wstom.admin.entity.QuestionFollow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface QuestionMapper extends BaseMapper<Question> {


    //增加问题信息
    int addQuestion(Question question);

    //增加问题相关统计信息
    int addQuestionCount(@Param("questionId") String questionId);

    //添加关注问题关联
    int addQuestionFollow(@Param("questionId") String questionId, @Param("userId") String userId);

    //按问题id删除主表信息
    int deleteQuestionById(@Param("questionId") String questionId);

    //按问题id删除问题相关统计信息
    int deleteQuestionCountById(@Param("questionId") String questionId);

    /**
     * 按问题id和用户id删除关注关联信息，可以单独删除单个信息id关联的关注信息
     * <p>
     * 如：deleteQuestionFollow(questionId,null);
     */
    int deleteQuestionFollow(@Param("questionId") String questionId, @Param("userId") String userId);

    /**
     * 按id更新问题审核状态
     *
     * @param id        问题id
     * @param status    0所有，1未审核 2正常状态 3审核未通过 4删除
     * @param recommend 0不推荐,1内容页推荐,2栏目页推荐,3专题页推荐,4首页推荐,5全站推荐
     * @return
     */
    int updateQuestionById(@Param("id") String id, @Param("status") Integer status, @Param("recommend") Integer recommend);

    /**
     * 按id更新问题回答数量统计
     *
     * @param questionId 问题id
     * @return
     */
    int updateQuestionByAnswerCount(@Param("questionId") String questionId);

    /**
     * 按id更新问题关注数量统计
     *
     * @param questionId 问题id
     * @return
     */
    int updateQuestionByFollowCount(@Param("questionId") String questionId);

    /**
     * 按id更新问题浏览数量统计
     *
     * @param questionId 问题id
     * @return
     */
    int updateQuestionByViewCount(@Param("questionId") String questionId);

    //按shortUrl查询文章信息
    Question findQuestionByShorturl(@Param("shortUrl") String shortUrl);

    /**
     * 按id查询问题信息
     *
     * @param id     问题id
     * @param status 0所有，1未审核 2正常状态 3审核未通过 4删除
     * @return
     */
    Question findQuestionById(@Param("id") String id, @Param("status") int status);

    /**
     * 按id查询问题统计信息
     *
     * @param questionId 问题id
     * @return
     */
    QuestionCount findQuestionCountById(@Param("questionId") String questionId);

    /**
     * 查询问答短域名是否被占用
     *
     * @param id
     * @return
     */
    int checkQuestionById(@Param("id") String id);

    /**
     * 查询用户组名是否存在,如果id、userId不为空或者null，排除当前id意外检查是否已存在！
     *
     * @param title  标题
     * @param userId 用户id
     * @return
     */
    int checkQuestionByTitle(@Param("title") String title, @Param("userId") String userId);

    /**
     * 查询是否已关注该问题
     *
     * @param questionId 问题id
     * @param userId     用户id
     * @return
     */
    int checkQuestionFollow(@Param("questionId") String questionId, @Param("userId") String userId);

    //查询所有问题数量
    int getQuestionCount(@Param("title") String title,
                         @Param("userId") String userId,
                         @Param("createTime") String createTime,
                         @Param("status") Integer status);

    //问题列表
    List<Question> getQuestionList(@Param("title") String title,
                                   @Param("userId") String userId,
                                   @Param("createTime") String createTime,
                                   @Param("status") Integer status,
                                   @Param("orderby") String orderby,
                                   @Param("order") String order,
                                   @Param("offset") Integer offset,
                                   @Param("rows") Integer rows);

    //问题与用户关联列表
    List<QuestionFollow> getQuestionFollowList(@Param("questionId") String questionId, @Param("userId") String userId);

    //问题索引总数
    int getQuestionIndexCount();

    //问题索引列表
    List<Question> getQuestionIndexList(@Param("offset") Integer offset, @Param("rows") Integer rows);
}
