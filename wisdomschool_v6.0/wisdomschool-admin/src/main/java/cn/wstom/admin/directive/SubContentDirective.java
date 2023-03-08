package cn.wstom.admin.directive;


import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/13
 */
@Component
public class SubContentDirective extends BaseTemplateDirective {
    @Override
    public String getName() {
        return "subContent";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        // 获取页面的参数
        String content = handler.getString("content");
        Integer num = handler.getInteger("num");
        content = Jsoup.clean(content, Whitelist.none());
        content = StringUtils.abbreviate(content, num);
        handler.put("subcontent", content).render();
    }
}
