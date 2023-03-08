package cn.wstom.admin.service;

import cn.wstom.admin.entity.*;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dws
 * @date 2019/03/07
 */
public interface ArticleService extends BaseService<Article> {

    @CacheEvict(value = "article", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    int addArticle(Article article) throws Exception;

    @CacheEvict(value = "article", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    Article addArticle(Article article, String[] tags) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "articleComment", allEntries = true)
    int addArticleComment(Comment articleComment) throws Exception;

    @CacheEvict(value = "article", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    int deleteArticleById(String id) throws Exception;

    @CacheEvict(value = "article", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    int editArticleById(Article article) throws Exception;

    @CacheEvict(value = "article", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    int updateArticleStatusById(String id, Integer status, Integer recommend) throws Exception;

    int updateArticleWeight(Double weight, String articleId);

    int addArticleVotes(ArticleVotes articleVotes);

    /**
     * 更新文章阅读量
     *
     * @param articleId
     * @return 影响数量
     */
    int updateArticleViewCount(String articleId);

    int updateArticleVotesById(ArticleVotes articleVotes);

    @Cacheable(value = "article")
    Article findArticleById(String id);

    ArticleCount findArticleCountById(String articleId);

    boolean checkArticleByTitle(String title, String userId, String id);

    boolean checkArticleComment(String articleId, String userId, String content);

    boolean checkArticleVotes(Integer infoType, String infoId, String userId);

    List<ArticleVotes> findArticleVotes(ArticleVotes articleVotes);

    PageVo<Article> getArticleListPage(String title, String userId, String createTime, Integer status, String orderby, String order, int pageNum, int rows);

    /**
     * 查询文章总数
     *
     * @return
     */
    int getArticleIndexCount();

    /**
     * 文章索引列表
     *
     * @param pageNum
     * @param rows
     * @return
     */
    List<Article> getArticleIndexList(int pageNum, int rows);

    @Cacheable(value = "articleComment")
    PageVo<Comment> getArticleCommentListPage(String articleId,
                                              String userId,
                                              String createTime,
                                              String parentId,
                                              String orderby,
                                              String order,
                                              int pageNum,
                                              int rows);

    Comment findNewestArticleById(String articleId);

    /**
     * 文章索引列表
     *
     * @param articleId
     * @return
     */
    List<Comment> getArticleCommentByArticleId(String articleId);

    Comment findArticleCommentByPid(String pid);

    int selectDigCount(Integer infoType, String infoId);
}
