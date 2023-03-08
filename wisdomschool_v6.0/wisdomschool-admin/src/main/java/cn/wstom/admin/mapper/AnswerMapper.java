package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.Answer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface AnswerMapper extends BaseMapper<Answer> {
    // ///////////////////////////////
    // /////       增加       ////////
    // ///////////////////////////////
    //增加答案信息
    int addAnswer(Answer answer);

    //增加答案相关统计信息
    int addAnswerCount(@Param("answerId") String answerId);

    // ///////////////////////////////
    // /////        刪除      ////////
    // ///////////////////////////////

    //答案id删除该id信息
    int deleteAnswerById(@Param("id") String id);

    //按答案id删除答案相关统计信息
    int deleteAnswerCountById(@Param("answerId") String answerId);
    // ///////////////////////////////
    // /////        修改      ////////
    // ///////////////////////////////

    //修改答案审核状态
    int updateAnswerStatusById(@Param("id") String id, @Param("status") Integer status);

    //按id更新答案内容
    int updateAnswerById(Answer answer);

    // ///////////////////////////////
    // ///// 查詢 ////////
    // ///////////////////////////////
    Answer findAnswerById(@Param("id") String id, @Param("status") Integer status);

    /**
     * 按id和用户id查询评论内容
     *
     * @param id     答案id
     * @param userId 用户id
     * @return
     */
    Answer findAnswerByIdAndUserId(@Param("id") String id, @Param("userId") String userId);

    /**
     * 查询该用户同样答案内容是否已存在！
     *
     * @param userId  用户id
     * @param content 答案内容
     * @return
     */
    int checkAnswerByContent(@Param("userId") String userId, @Param("content") String content);

    /**
     * 按参数查询记录数
     *
     * @param questionId 问题id
     * @param userId     用户id
     * @param createTime 添加时间
     * @param status     审核状态
     * @return
     */
    int getAnswerCount(@Param("questionId") String questionId,
                       @Param("userId") String userId,
                       @Param("createTime") String createTime,
                       @Param("status") Integer status);

    /**
     * 按参数查询答案列表
     *
     * @param questionId 问题id
     * @param userId     用户id
     * @param createTime 添加时间
     * @param status     审核状态
     * @param orderby    需要排序的字段
     * @param order      排序：desc asc
     * @param offset     当前页数
     * @param rows       每页条数
     * @return
     */
    List<Answer> getAnswerList(@Param("questionId") String questionId,
                               @Param("userId") String userId,
                               @Param("createTime") String createTime,
                               @Param("status") Integer status,
                               @Param("orderby") String orderby,
                               @Param("order") String order,
                               @Param("offset") Integer offset,
                               @Param("rows") Integer rows);

    //按问题id或者用户id查询答案列表
    List<Answer> gettAnswerByQuestionIdList(@Param("questionId") String questionId, @Param("userId") String userId);

    //按问题id查询最新的第一条评论内容
    Answer findNewestAnswerById(@Param("questionId") String questionId);
}
