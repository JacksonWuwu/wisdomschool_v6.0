package cn.wstom.student.mapper;


import cn.wstom.student.entity.Article;
import cn.wstom.student.entity.ArticleCount;
import cn.wstom.student.entity.ArticleVotes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章Mapper
 *
 * @author dws
 */
public interface ArticleMapper extends BaseMapper<Article> {

    // ///////////////////////////////
    // /////       增加       ////////
    // ///////////////////////////////
    //添加文章
    int addArticle(Article article);

    //添加文章统计关联数据
    int addArticleCount(@Param("articleId") String articleId);

    //按id查询文章信息
    int addArticleAndCategory(@Param("articleId") String articleId,
                              @Param("categoryId") String categoryId,
                              @Param("typeId") String typeId);

    //添加文章顶与踩记录
    int addArticleVotes(ArticleVotes articleVotes);

    //  Lin__
    int updateArticleVotesById(ArticleVotes articleVotes);

    // ///////////////////////////////
    // /////        刪除      ////////
    // ///////////////////////////////
    //按id删除文章信息
    int deleteArticleById(@Param("id") String id);

    //按id删除文章统计关联
    int deleteArticleCountById(@Param("articleId") String articleId);

    //按id删除文章和分类关联
    int deleteArticleAndCcategoryById(@Param("articleId") String articleId);

    //按文章id删除用户顶或者踩记录
    int deleteAllArticleVotesById(@Param("articleId") String articleId);

    /**
     * 按信息类型id、文章id、用户id删除用户顶或者踩记录
     * 只要执行此操作就会删除本条信息的踩或者顶
     *
     * @param infoType
     * @param infoId
     * @param userId
     * @return
     */
    int deleteArticleVotesById(@Param("infoType") Integer infoType,
                               @Param("infoId") String infoId,
                               @Param("userId") String userId);

    //修改文章
    int editArticleById(Article article);

    /**
     * 按id更新文章审核状态
     *
     * @param id        问题id
     * @param status    0未审核 1正常状态 2审核未通过 3删除
     * @param recommend 0不推荐,1内容页推荐,2栏目页推荐,3专题页推荐,4首页推荐,5全站推荐
     * @return
     */
    int updateArticleStatusById(@Param("id") String id, @Param("status") Integer status, @Param("recommend") Integer recommend);

    //按id更新文章分类
    int editArticleAndCcategoryById(@Param("categoryId") String categoryId, @Param("typeId") Integer typeId, @Param("articleId") String articleId);

    /**
     * 更新文章被评论的数量
     *
     * @param articleId 文章id
     * @return
     */
    int updateArticleCommentCount(@Param("articleId") String articleId);

    /**
     * 更新文章被踩的数量
     *
     * @param articleId 文章id
     * @return
     */
    int updateArticleDiggCount(@Param("articleId") String articleId);

    /**
     * 更新文章被踩的数量
     *
     * @param articleId 文章id
     * @return
     */
    int updateArticleBurysCount(@Param("articleId") String articleId);

    /**
     * 更新文章评论被踩的数量
     *
     * @param id 评论id
     * @return
     */
    int updateArticleCommentDiggCount(@Param("id") String id);

    /**
     * 更新文章评论被踩的数量
     *
     * @param id 评论id
     * @return
     */
    int updateArticleCommentBurysCount(@Param("id") String id);

    /**
     * 更新文章被评论的权重分值
     *
     * @param weight    权重分值
     * @param articleId 文章id
     * @return
     */
    int updateArticleWeight(@Param("weight") Double weight, @Param("articleId") String articleId);

    //按id更新文章浏览数量统计
    int updateArticleViewCount(@Param("articleId") String articleId);

    //按id查询文章统计信息
    ArticleCount findArticleCountById(@Param("articleId") String articleId);


    /**
     * 查询文章标题是否存在
     *
     * @param title  发布文章标题
     * @param userId 用户id，可设置为null
     * @param id     当修改内容检查重复标题时，排除当前文章id，不排除可设置为null
     * @return
     */
    int checkArticleByTitle(@Param("title") String title, @Param("userId") String userId, @Param("id") String id);


    /**
     * 按信息类型id、文章id、用户id查询用户顶或者踩记录是否发布过同样内容
     * 注：一个类型的信息一个用户只能有一条记录，或顶或者踩
     *
     * @param infoType 信息类型，0文章，1评论
     * @param infoId   信息id
     * @param userId   用户id
     * @return
     */
    int checkArticleVotes(@Param("infoType") Integer infoType, @Param("infoId") String infoId, @Param("userId") String userId);

    //查询所有文章数量
    int getArticleCount(@Param("title") String title,
                        @Param("userId") String userId,
                        @Param("createTime") String createTime,
                        @Param("status") Integer status);

    //文章列表
    List<Article> getArticleList(@Param("title") String title,
                                 @Param("userId") String userId,
                                 @Param("createTime") String createTime,
                                 @Param("status") Integer status,
                                 @Param("orderby") String orderby,
                                 @Param("order") String order,
                                 @Param("offset") Integer offset,
                                 @Param("rows") Integer rows);

    //文章索引总数
    int getArticleIndexCount();

    //文章索引列表
    List<Article> getArticleIndexList(@Param("offset") Integer offset, @Param("rows") Integer rows);

    /**
     * 按id更新文章审核状态
     *
     * @param infoId 文章id
     * @param userId    用户id
     * @return
     */
    List<ArticleVotes> findArticleVotes(@Param("infoId") String infoId, @Param("userId") String userId);

    /**
     *  Lin__
     *  条件查询点赞数
     * @param infoType
     * @param infoId
     * @return
     */
    int selectDigCount(@Param("infoType") Integer infoType, @Param("infoId") String infoId);
}
