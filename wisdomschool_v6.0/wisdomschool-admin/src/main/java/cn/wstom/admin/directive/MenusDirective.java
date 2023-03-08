package cn.wstom.admin.directive;

import cn.wstom.admin.entity.SysMenu;
import cn.wstom.common.constant.UserConstants;
import cn.wstom.common.utils.StringUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.LinkedList;
import java.util.List;

/**
 * 后台菜单指令
 *
 * @author dws
 * @date 2018/11/26
 */
@Component
public class MenusDirective extends BaseTemplateDirective {

	@Autowired
	private ServletContext servletContext;

	@Override
	public String getName() {
		return "menus";
	}

	@Override
    public void execute(DirectiveHandler handler) throws Exception {
		List<SysMenu> menus = filterMenu(SecurityUtils.getSubject());
		handler.put("menus", menus).render();
	}

	private List<SysMenu> filterMenu(Subject subject) {
		List<SysMenu> menus = (List<SysMenu>) servletContext.getAttribute("sysMenus");
        if (!subject.hasRole(UserConstants.ROLE_ADMIN)) {
			menus = check(subject, menus);
		}
		return menus;
	}

	private List<SysMenu> check(Subject subject, List<SysMenu> menus) {
		List<SysMenu> results = new LinkedList<>();
		menus.forEach(m -> {
			if (check(subject, m)) {
				results.add(m);
			}
		});
		return results;
	}

	private boolean check(Subject subject, SysMenu menu) {
		boolean authorized = false;
		String perms = menu.getPerms();
		if (StringUtil.isBlank(perms)) {
			authorized = true;
		} else {
			for (String perm : perms.split(",")) {
				if (subject.isPermitted(perm)) {
					authorized = true;
					break;
				}
			}
		}
		return authorized;
	}
}
