package cn.wstom.admin.directive;


import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/09
 */
@Component
public class ArticlePageDirective extends BaseTemplateDirective {

    //private final ArticleService articleService;

    /*@Autowired
    public ArticlePageDirective(ArticleService articleService) {
        this.articleService = articleService;
    }
*/
    @Override
    public String getName() {
        return "article";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        // 获取页面的参数
        //问题标题
        String title = handler.getString("title");

        String orderBy = handler.getString("orderBy");

        String order = handler.getString("order");

        Integer status = handler.getInteger("status");

        //String userId = handler.getBoolean("personal", false) ? ShiroUtils.getUserId() : null;

        String createTime = handler.getString("createTime");
        //当前页数
        int p = handler.getInteger("p", 1);
        //每页记录数
        int rows = handler.getInteger("rows", 10);
        // 获取文件的分页
        try {
            //PageVo<Article> pageVo = articleService.getArticleListPage(title, userId, createTime, status, orderBy, order, p, rows);
            handler.put(DATA, /*pageVo*/"").render();
        } catch (Exception e) {
            handler.put(MSG, e.getMessage()).render();
        }
    }
}
