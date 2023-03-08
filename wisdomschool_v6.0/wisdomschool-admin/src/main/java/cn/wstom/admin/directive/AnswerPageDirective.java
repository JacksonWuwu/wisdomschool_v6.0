package cn.wstom.admin.directive;


import cn.wstom.admin.entity.Answer;
import cn.wstom.admin.entity.PageVo;
import cn.wstom.admin.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/10
 */
@Component
public class AnswerPageDirective extends BaseTemplateDirective {
    private static final String RESULT_KEY = "answerpage";

    private final AnswerService answerService;

    @Autowired
    public AnswerPageDirective(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Override
    public String getName() {
        return "answerPage";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        // 获取页面的参数
        //问题id
        String questionId = handler.getString("questionId");
        //用户id
        String userId = handler.getString("userId");
        //添加时间
        String addTime = handler.getString("createTime");

        String orderBy = handler.getString("orderBy");

        Integer status = handler.getInteger("status", 0);

        String order = handler.getString("order");
        //当前页数
        int p = handler.getInteger("p", 1);
        //每页记录数
        int rows = handler.getInteger("rows", 10);

        // 获取文件的分页
        try {
            PageVo<Answer> pageVo = answerService.getAnswerListPage(questionId, userId, addTime, status, orderBy, order, p, rows);
            handler.put(RESULT_KEY, pageVo).render();
        } catch (Exception e) {
            handler.put(MSG, e.getMessage()).render();
        }

    }
}
