package cn.wstom.admin.directive;


import cn.wstom.admin.entity.SysUser;
import cn.wstom.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dws
 * @date 2019/03/08
 */
@Component
public class HotPageDirective extends BaseTemplateDirective {
    private final SysUserService userService;

    @Autowired
    public HotPageDirective(SysUserService userService) {
        this.userService = userService;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        //用户名
        String userName = handler.getString("userName");
        //昵称
        String nickName = handler.getString("nickName");
        //手机号码
        String mobile = handler.getString("mobile");
        //邮箱
        String email = handler.getString("email");
        //省份id
        Integer province = handler.getInteger("province");
        //地区id
        Integer city = handler.getInteger("city");
        //县市id
        Integer area = handler.getInteger("area");
        //审核状态
        Integer status = handler.getInteger("status");

        String orderBy = handler.getString("orderBy");

        //当前页数
        int p = handler.getInteger("p", 1);
        //每页记录数
        int rows = handler.getInteger("rows", 10);
        SysUser user = new SysUser();
        user.setLoginName(userName);
        user.setUserName(nickName);
        user.setMobilePhone(mobile);
        user.setEmail(email);
        startPage();
        List<SysUser> list = userService.list(user);
        handler.put(DATA, list).render();
    }
}
