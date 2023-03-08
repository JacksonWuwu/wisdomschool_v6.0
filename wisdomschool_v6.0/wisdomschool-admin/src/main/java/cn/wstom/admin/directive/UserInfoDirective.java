package cn.wstom.admin.directive;


import cn.wstom.admin.entity.Statistics;
import cn.wstom.admin.entity.SysUser;
import cn.wstom.admin.feign.StudentService;
import cn.wstom.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dws
 * @date 2019/03/09
 */
@Component
public class UserInfoDirective extends BaseTemplateDirective {


    private static final String RESULT_KEY = "userinfo";

    private final SysUserService userService;
    private final StudentService studentService;

    @Autowired
    public UserInfoDirective(SysUserService userService, StudentService studentService) {
        this.userService = userService;
        this.studentService = studentService;
    }

    @Override
    public String getName() {
        return "userInfo";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        // 获取页面的参数
        String userId = handler.getString("userId");
        // 获取文件的分页
        SysUser user = userService.getById(userId);

        Statistics statistics = studentService.getStatisticsById(userId);

        Map<String, Object> result = new HashMap<>(2);
        result.put("user", user);
        result.put("statistics", statistics);
        handler.put(RESULT_KEY, result).render();
    }
}
