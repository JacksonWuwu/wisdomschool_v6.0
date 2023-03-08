package cn.wstom.admin.directive;


import cn.wstom.admin.entity.ArticleCategory;
import cn.wstom.admin.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dws
 * @date 2019/03/09
 */
@Component
public class ArticleTypeDirective extends BaseTemplateDirective {

    private final ArticleCategoryService articleCategoryService;

    @Autowired
    public ArticleTypeDirective(ArticleCategoryService articleCategoryService) {
        this.articleCategoryService = articleCategoryService;
    }

    @Override
    public String getName() {
        return "articleType";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        // 获取页面的参数
        //指定父级id
        String parentId = handler.getString("parentId", "0");

        // 获取文件的分页
        try {
            List<ArticleCategory> pageVo = articleCategoryService.getCategoryListByParentId(parentId);
            handler.put("articletype", pageVo).render();
        } catch (Exception e) {
            e.printStackTrace();
            handler.put(MSG, e.getMessage()).render();
        }
    }
}
