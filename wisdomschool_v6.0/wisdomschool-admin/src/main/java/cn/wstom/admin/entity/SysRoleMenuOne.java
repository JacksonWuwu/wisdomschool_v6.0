package cn.wstom.admin.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 角色菜单关联表sys_role_menu
 */
@Data
public class SysRoleMenuOne {

    /**
     * 角色Id
     */
    private String roleId;
    /**
     * 菜单Id
     */
    private String menuId;

    private String schoolId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("roleId", roleId)
                .append("menuId", menuId)
                .toString();
    }
}
