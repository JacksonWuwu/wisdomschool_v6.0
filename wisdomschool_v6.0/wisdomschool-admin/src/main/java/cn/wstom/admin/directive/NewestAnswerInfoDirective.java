package cn.wstom.admin.directive;


import cn.wstom.admin.entity.Answer;
import cn.wstom.admin.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/13
 */
@Component
public class NewestAnswerInfoDirective extends BaseTemplateDirective {

    @Autowired
    private AnswerService answerService;

    @Override
    public String getName() {
        return "newestAnswerInfo";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        // 获取页面的参数
        String questionId = handler.getString("questionId");

        // 获取文件的分页
        Answer answer = answerService.findNewestAnswerById(questionId);
        handler.put("newestanswerinfo", answer).render();
    }
}
