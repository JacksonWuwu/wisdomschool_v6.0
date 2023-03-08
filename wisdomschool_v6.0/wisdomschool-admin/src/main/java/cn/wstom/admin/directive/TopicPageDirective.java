package cn.wstom.admin.directive;


import cn.wstom.admin.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/08
 */
@Component
public class TopicPageDirective extends BaseTemplateDirective {
    private final TopicService topicService;

    private static final String RESULT_KEY = "topicpage";

    @Autowired
    public TopicPageDirective(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public String getName() {
        return "topicPage";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        // 获取页面的参数
        //所属主信息类型，0是所有，1是文章，2是问题
        String topic = handler.getString("topic");

        String type = handler.getString("type");

        Integer isgood = handler.getInteger("isgood");

        Integer status = handler.getInteger("status", 2);
        /**
         *
         * § orderby='hot' 或 orderby='click' 表示按点击数排列
         * § orderby='sortrank' 或 orderby='pubdate' 按添加时间排列
         * § orderby=='scores' 按得分排序 (未实现)
         * § orderby='id' 按文章ID排序
         * § orderby='rand' 随机获得指定条件的列表
         */
        String orderBy = handler.getString("orderBy");

        String order = handler.getString("order");

        //翻页页数
        Integer p = handler.getInteger("p", 1);
        //每页记录条数
        Integer rows = handler.getInteger("rows", 10);


        // 获取文件的分页
        try {
            //PageVo<Topic> pageVo = topicService.getTopicListPage(topic, type, isgood, status, orderBy, order, p, rows);
            handler.put(RESULT_KEY, /*pageVo*/"").render();
        } catch (Exception e) {
            handler.put(MSG, e.getMessage()).render();
        }
    }
}
