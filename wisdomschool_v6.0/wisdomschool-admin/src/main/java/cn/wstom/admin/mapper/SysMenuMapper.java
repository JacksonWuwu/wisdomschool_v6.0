package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.SysMenu;
import cn.wstom.admin.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单表 数据层
 *
 * @author dws
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 查询系统所有菜单（含按钮）
     *
     * @return 菜单列表
     */
    List<SysMenu> selectMenuAll();

    /**
     * 查询系统正常显示菜单（不含按钮）
     *
     * @return 菜单列表
     */
    List<SysMenu> selectMenuNormalAll();

    /**
     * 根据用户ID查询菜单
     *
     * @param sysUser 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenusByUser(SysUser sysUser);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectPermsByUserId(String userId);

    /**
     * 根据角色ID查询菜单
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<String> selectMenuTree(String roleId);

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    List<SysMenu> selectMenuList(SysMenu menu);

    /**
     * 查询菜单数量
     *
     * @param parentId 菜单父ID
     * @return 结果
     */
    int selectCountMenuByParentId(String parentId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menuName 菜单名称
     * @param parentId 父菜单ID
     * @return 结果
     */
    SysMenu checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") String parentId);

    /**
     * 查询子菜单
     * @param menu
     * @return
     */
    List<SysMenu> selectMenuByParentId(SysMenu menu);

    /*
    * 下级管理员的菜单
    * */

    List<SysMenu> selectMenusByUserId_sdsadmin(String userId);
    /*
     * 班主任的菜单
     * */

    List<SysMenu> selectMenusByUserId_classadmin(String userId);
    /*
    * 教师的菜单
    * */
    List<SysMenu> selectMenusByUserId_teacher(String userId);

    List<SysMenu> selectMenuBySchoolId(String schoolId);

}
