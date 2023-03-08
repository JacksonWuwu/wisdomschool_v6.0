package cn.wstom.admin.directive;


import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/09
 */
@Component
public class ArticleInfoDirective extends BaseTemplateDirective {
    //private final ArticleService articleService;

    private static final String RESULT_KEY = "articleinfo";

    /*@Autowired
    public ArticleInfoDirective(ArticleService articleService) {
        this.articleService = articleService;
    }
*/
    @Override
    public String getName() {
        return "articleInfo";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        // 获取页面的参数
        String id = handler.getString("id");

        /*Article article = articleService.findArticleById(id);
        if (article != null) {
            //查询统计信息
            ArticleCount count = articleService.findArticleCountById(article.getId());
            article.setCountDig(count.getCountDig());
            article.setCountBury(count.getCountBury());
            article.setCountView(count.getCountView());
            article.setCountComment(count.getCountComment());
        }*/
        handler.put(RESULT_KEY, /*article*/"").render();
    }
}
