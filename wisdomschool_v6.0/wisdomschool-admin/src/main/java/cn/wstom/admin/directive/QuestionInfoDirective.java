package cn.wstom.admin.directive;


import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/09
 */
@Component
public class QuestionInfoDirective extends BaseTemplateDirective {
    //private final QuestionService questionService;

    private static final String RESULT_KEY = "questioninfo";

    /*
        @Autowired
        public QuestionInfoDirective(QuestionService questionService) {
            this.questionService = questionService;
        }
    */
    @Override
    public String getName() {
        return "questionInfo";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        // 获取页面的参数
        String id = handler.getString("id");

        /*Question question = questionService.getById(id);
        if (question != null) {
            //查询统计信息
            QuestionCount count = questionService.findQuestionCountById(question.getId());
            question.setCountAnswer(count.getCountAnswer());
            question.setCountView(count.getCountView());
            question.setCountDig(count.getCountDig());
            question.setCountBury(count.getCountBury());
            question.setCountFollow(count.getCountFollow());
        }*/
        handler.put(RESULT_KEY, /*question*/"").render();
    }
}
