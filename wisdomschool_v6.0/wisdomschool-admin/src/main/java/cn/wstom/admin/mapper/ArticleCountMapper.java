package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.ArticleCount;
import org.apache.ibatis.annotations.Param;

/**
 * @author dws
 * @date 2019/03/07
 */
public interface ArticleCountMapper extends BaseMapper<ArticleCount> {

    int updateArticleViewCount(@Param("articleId") String articleId);

    int updateArticleCommentCount(@Param("articleId") String articleId);
}
