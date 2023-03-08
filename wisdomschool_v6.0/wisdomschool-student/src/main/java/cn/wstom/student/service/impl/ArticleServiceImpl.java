package cn.wstom.student.service.impl;



import cn.wstom.student.entity.*;
import cn.wstom.student.mapper.ArticleMapper;
import cn.wstom.student.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public int addArticle(Article article) throws Exception {
        return articleMapper.addArticle(article);
    }

    @Override
    public Article addArticle(Article article, String[] tags) throws Exception {
        if (articleMapper.addArticle(article) == 1) {
            return article;
        } else {
            return null;
        }
    }

    @Override
    public int addArticleComment(Comment articleComment) throws Exception {
        return 0;
    }

    @Override
    public int deleteArticleById(String id) throws Exception {
        return articleMapper.deleteArticleById(id);
    }

    @Override
    public int editArticleById(Article article) throws Exception {
        return articleMapper.update(article);
    }

    @Override
    public int updateArticleStatusById(String id, Integer status, Integer recommend) throws Exception {
        return articleMapper.updateArticleStatusById(id, status, recommend);
    }

    @Override
    public int updateArticleWeight(Double weight, String articleId) {
        return articleMapper.updateArticleWeight(weight, articleId);
    }

    @Override
    public int addArticleVotes(ArticleVotes articleVotes) {
        return articleMapper.addArticleVotes(articleVotes);
    }

    @Override
    public int updateArticleViewCount(String articleId) {
        return articleMapper.updateArticleViewCount(articleId);
    }

    @Override
    public int updateArticleVotesById(ArticleVotes articleVotes) {
        return articleMapper.updateArticleVotesById(articleVotes);
    }

    @Override
    public Article findArticleById(String id) {
        return articleMapper.selectById(id);
    }

    @Override
    public ArticleCount findArticleCountById(String articleId) {
       return articleMapper.findArticleCountById(articleId);
    }

    @Override
    public boolean checkArticleByTitle(String title, String userId, String id) {
        return false;
    }

    @Override
    public boolean checkArticleComment(String articleId, String userId, String content) {
        return false;
    }

    @Override
    public boolean checkArticleVotes(Integer infoType, String infoId, String userId) {
        return articleMapper.checkArticleVotes(infoType, infoId, userId) >= 1;
    }

    @Override
    public List<ArticleVotes> findArticleVotes(ArticleVotes articleVotes) {
        return articleMapper.findArticleVotes(articleVotes.getInfoId(), articleVotes.getUserId());
    }

    @Override
    public PageVo<Article> getArticleListPage(String title, String userId, String createTime, Integer status, String orderby, String order, int pageNum, int rows) {
        return null;
    }

    @Override
    public int getArticleIndexCount() {
        return 0;
    }

    @Override
    public List<Article> getArticleIndexList(int pageNum, int rows) {
        return null;
    }

    @Override
    public PageVo<Comment> getArticleCommentListPage(String articleId, String userId, String createTime, String parentId, String orderby, String order, int pageNum, int rows) {
        return null;
    }

    @Override
    public Comment findNewestArticleById(String articleId) {
        return null;
    }

    @Override
    public List<Comment> getArticleCommentByArticleId(String articleId) {
        return null;
    }

    @Override
    public Comment findArticleCommentByPid(String pid) {
        return null;
    }

    @Override
    public int selectDigCount(Integer infoType, String infoId) {
        return articleMapper.selectDigCount(infoType, infoId);
    }
}
