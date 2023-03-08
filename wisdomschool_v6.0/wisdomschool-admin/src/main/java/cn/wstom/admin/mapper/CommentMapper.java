package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dws
 * @date 2019/03/07
 */
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> listByPids(List<String> childrenIds);

    /**
     * 查询文章相同的评论内容是否已添加
     *
     * @param typeId  文章id
     * @param userId  用户id
     * @param content 评论内容
     * @return
     */
    int checkArticleComment(@Param("type") Integer type, @Param("typeId") String typeId, @Param("userId") String userId, @Param("content") String content);

    /**
     * 根据类型id查找评论
     *
     * @param type
     * @param typeId
     * @return
     */
    List<Comment> selectByTypeId(@Param("type") Integer type, @Param("typeId") String typeId);

    /**
     * 按问题id查询最新的第一条评论内容
     *
     * @param type   类型
     * @param typeId 类型id
     * @return
     */
    Comment selectNewestCommentById(@Param("type") Integer type, @Param("typeId") String typeId);

    Comment selectByPid(@Param("pid") String pid, @Param("type") Integer type);

    int deleteByTypeId(@Param("type") Integer type, @Param("typeId") String typeId);

    List<Comment> selectCommentList(@Param("type") Integer type,
                                    @Param("typeId") String typeId,
                                    @Param("userId") String userId,
                                    @Param("createTime") String createTime,
                                    @Param("parentId") String parentId,
                                    @Param("orderby") String orderby,
                                    @Param("order") String order,
                                    @Param("offset") Integer offset,
                                    @Param("rows") Integer rows);

    int selectCommentCount(@Param("type") Integer type,
                           @Param("typeId") String typeId,
                           @Param("userId") String userId,
                           @Param("createTime") String createTime,
                           @Param("parentId") String parentId);
}
