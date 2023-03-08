package cn.wstom.admin.service.impl;

import cn.wstom.admin.entity.ArticleCount;
import cn.wstom.admin.mapper.ArticleCountMapper;
import cn.wstom.admin.service.ArticleCountService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dws
 * @date 2019/03/07
 */
@Service
public class ArticleCountServiceImpl extends BaseServiceImpl
        <ArticleCountMapper, ArticleCount>
        implements ArticleCountService {

    @Resource
    private ArticleCountMapper articleCountMapper;

    @Override
    public int updateComment(String targetId, int step) throws Exception {
        // TODO: 2019/3/7 此处可优化为，先存储在缓存中，当达到一定量时写入数据库
        ArticleCount articleCount = articleCountMapper.selectById(targetId);
        articleCountMapper.updateArticleCommentCount(articleCount.getArticleId());
        int count = articleCount.getCountComment() + step;
        return count > 0 ? count : 0;
    }

    @Override
    public int updateView(String targetId, int step) throws Exception {
        // TODO: 2019/3/7 此处可优化为，先存储在缓存中，当达到一定量时写入数据库
        ArticleCount articleCount = articleCountMapper.selectById(targetId);
        articleCountMapper.updateArticleViewCount(articleCount.getArticleId());
        int count = articleCount.getCountView() + step;
        return count > 0 ? count : 0;
    }
}
