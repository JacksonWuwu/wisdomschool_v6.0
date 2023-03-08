package cn.wstom.admin.directive;


import cn.wstom.admin.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/10
 */
@Component
public class TopicInfoDirective extends BaseTemplateDirective {

    private final TopicService topicService;
    private static final String RESULT_KEY = "topicinfo";

    @Autowired
    public TopicInfoDirective(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        // 获取页面的参数
        //
        String infoId = handler.getString("infoId");
        Integer type = handler.getInteger("type");
        //处理标签变量

        /*List<Topic> topicList = topicService.getInfoByTopicList(type, infoId);*/

        handler.put(RESULT_KEY, /*topicList*/"").render();
    }
}
