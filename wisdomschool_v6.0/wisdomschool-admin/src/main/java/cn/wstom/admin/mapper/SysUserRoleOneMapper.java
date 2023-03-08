package cn.wstom.admin.mapper;




import cn.wstom.admin.entity.SysUserRoleOne;

import java.util.List;

/**
 * 用户表 数据层
 */
public interface SysUserRoleOneMapper extends BaseMapper<SysUserRoleOne> {

    /**
     * 通过用户Id删除用户喝角色级联
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserRoleOneByUserId(String userId);

    /**
     * 通过用户iD批量删除用户和角色级联
     * @param ids
     * @return 结果
     */
    int deleteUserRoleOne(String[] ids);
    /**
     * 批量新增用户角色信息
     *
     * @param userRoleOneList 用户角色列表
     * @return 结果
     */
    int batchUserRole(List<SysUserRoleOne> userRoleOneList);
}
