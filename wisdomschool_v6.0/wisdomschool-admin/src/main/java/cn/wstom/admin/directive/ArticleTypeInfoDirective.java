package cn.wstom.admin.directive;


import cn.wstom.admin.entity.ArticleCategory;
import cn.wstom.admin.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/12
 */
@Component
public class ArticleTypeInfoDirective extends BaseTemplateDirective {
    private final ArticleCategoryService articleCategoryService;

    @Autowired
    public ArticleTypeInfoDirective(ArticleCategoryService articleCategoryService) {
        this.articleCategoryService = articleCategoryService;
    }

    @Override
    public String getName() {
        return "articleTypeInfo";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        // 获取页面的参数
        String id = handler.getString("id");
        Integer status = handler.getInteger("status", 0);


        // 获取文件的分页
        ArticleCategory type = articleCategoryService.findCategoryById(id, status);
        handler.put("articletypeinfo", type).render();
    }
}
