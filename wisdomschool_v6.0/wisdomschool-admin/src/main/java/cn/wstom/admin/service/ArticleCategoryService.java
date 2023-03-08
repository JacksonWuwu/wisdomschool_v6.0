package cn.wstom.admin.service;


import cn.wstom.admin.entity.ArticleCategory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dws
 * @date 2019/03/07
 */
public interface ArticleCategoryService extends BaseService<ArticleCategory> {

    @Transactional(rollbackFor = Exception.class)
    int addArticleCategory(String pid, String name) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    int editArticleCategoryById(String id, String name) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    int editCategoryDragsById(String id, String pId) throws Exception;

    ArticleCategory findCategoryById(String id, Integer status);

    boolean checkCategoryById(String id, Integer status);

    boolean checkArticleCategoryByName(String name, String id);

    List<ArticleCategory> getCategoryListByParentId(String parentId);

    List<ArticleCategory> getCategoryAllList();
}
