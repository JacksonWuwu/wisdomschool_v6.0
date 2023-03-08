package cn.wstom.admin.directive;

import cn.wstom.admin.entity.Answer;

import cn.wstom.admin.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/10
 */
@Component
public class AnswerInfoDirective extends BaseTemplateDirective {

    private final AnswerService answerService;

    private static final String RESULT_KEY = "answerinfo";

    @Autowired
    public AnswerInfoDirective(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Override
    public String getName() {
        return "answerInfo";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        // 获取页面的参数
        String answerId = handler.getString("answerId");
        Integer status = handler.getInteger("status", 0);


        // 获取文件的分页
        Answer answer = answerService.findAnswerById(answerId, status);
        handler.put(RESULT_KEY, answer).render();
    }
}
