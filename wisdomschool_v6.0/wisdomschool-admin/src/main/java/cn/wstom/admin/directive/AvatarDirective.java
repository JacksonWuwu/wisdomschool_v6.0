package cn.wstom.admin.directive;

import cn.wstom.common.utils.StringHelperUtils;

import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/09
 */
@Component
public class AvatarDirective extends BaseTemplateDirective {
    @Override
    public String getName() {
        return "avatar";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        String avatarUrl = handler.getString("avatarUrl");

        String avatar = handler.getString("avatar");

        // 获取页面的参数
        avatar = StringHelperUtils.TextReplace(avatarUrl, avatar);
        handler.renderString(avatar);
    }
}
