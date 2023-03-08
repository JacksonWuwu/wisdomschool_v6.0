package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.ArticleCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章类别
 *
 * @author dws
 */
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {

    /**
     * 添加文章分类
     *
     * @param articleCategory
     * @return
     */
    int addArticleCategory(ArticleCategory articleCategory);

    /**
     * 按分类id删除该分类信息
     *
     * @param id
     * @return
     */
    int deleteArticleCategoryById(String id);

    /**
     * 添加文章分类
     *
     * @param articleCategory
     * @return
     */
    int editArticleCategoryById(ArticleCategory articleCategory);


    /**
     * 查询分类名称是否存在
     *
     * @param name
     * @param id
     * @return
     */
    int checkArticleCategoryByName(@Param("name") String name, @Param("id") String id);

    /**
     * 按id查询分类信息
     *
     * @param id
     * @param status
     * @return
     */
    ArticleCategory findCategoryById(@Param("id") String id, @Param("status") Integer status);

    /**
     * 根据分类id查询所属的所有子类
     *
     * @param parentId
     * @return
     */
    List<ArticleCategory> getCategoryListByParentId(@Param("parentId") String parentId);

    /**
     * 查询所有分类
     *
     * @return
     */
    List<ArticleCategory> getCategoryAllList();
}
