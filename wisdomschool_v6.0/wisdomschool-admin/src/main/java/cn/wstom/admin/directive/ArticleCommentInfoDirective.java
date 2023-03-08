package cn.wstom.admin.directive;


import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/12
 */
@Component
public class ArticleCommentInfoDirective extends BaseTemplateDirective {
    /*
        private final ArticleService articleService;

        @Autowired
        public ArticleCommentInfoDirective(ArticleService articleService) {
            this.articleService = articleService;
        }
    */
    @Override
    public String getName() {
        return "articleCommentInfo";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String pid = handler.getString("id");
//        TopicComment comment = articleService.findArticleCommentByPid(pid);
        handler.put("articlecommentinfo", /*comment*/"").render();
    }
}
