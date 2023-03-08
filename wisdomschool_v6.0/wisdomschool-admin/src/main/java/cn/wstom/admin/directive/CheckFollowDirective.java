package cn.wstom.admin.directive;


import cn.wstom.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/13
 */
@Component
public class CheckFollowDirective extends BaseTemplateDirective {

    private final SysUserService userService;

    @Autowired
    public CheckFollowDirective(SysUserService userService) {
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "checkFollow";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        String userFollow = handler.getString("userFollow");
        String userFans = handler.getString("userFans");

        //关注状态，0未关注，1已关注
        int status;
        //处理标签变量
        if (userFollow == null && userFans == null) {
            status = 0;
        } else if (userService.checkUserFans(userFollow, userFans)) {
            status = 1;
        } else {
            status = 0;
        }
        handler.put("checkfollow", status).render();
    }
}
