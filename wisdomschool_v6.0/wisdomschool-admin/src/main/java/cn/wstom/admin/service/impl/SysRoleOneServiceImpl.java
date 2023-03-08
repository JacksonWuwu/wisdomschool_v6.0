package cn.wstom.admin.service.impl;


import cn.wstom.admin.entity.SysRoleMenuOne;
import cn.wstom.admin.entity.SysRoleOne;
import cn.wstom.admin.mapper.SysRoleMenuOneMapper;
import cn.wstom.admin.mapper.SysRoleOneMapper;
import cn.wstom.admin.mapper.SysUserRoleOneMapper;
import cn.wstom.admin.service.SysRoleOneService;
import cn.wstom.common.annotation.DataScope;

import cn.wstom.common.constant.Constants;

import cn.wstom.common.utils.StringUtil;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * 角色 业务层处理
 */
@Service
public class SysRoleOneServiceImpl extends BaseServiceImpl<SysRoleOneMapper, SysRoleOne> implements SysRoleOneService {
    @Resource
    private SysRoleOneMapper roleOneMapper;
    @Resource
    private SysRoleMenuOneMapper roleMenuOneMapper;

    @Resource
    private SysUserRoleOneMapper sysUserRoleOneMapper;

    /**
     * 根据用户Id查询权限
     * @param userId
     * @return 权限列表
     */
    @Override
    public Set<String> selectRoleOneKeys(String userId) {
       List<SysRoleOne> ones = roleOneMapper.selectRolesOneByUserId(userId);
       Set<String> onesSet = new HashSet<>();
        for (SysRoleOne one:ones
             ) {
            onesSet.addAll(Arrays.asList(one.getRoleKey().trim()));
        }
        return onesSet;
    }

    /**
     * 根据用户ID查询角色
     * @param userId
     * @return 角色列表
     */
    @Override
    public List<SysRoleOne> selectRolesOneByUserId(String userId) {
       List<SysRoleOne> userRoles = roleOneMapper.selectRolesOneByUserId(userId);
       List<SysRoleOne> roleOnes = list(null);
        for (SysRoleOne roleOne:roleOnes) {
            for(SysRoleOne userRole : userRoles){
                roleOne.setFlag(true);
                break;
            }
        }
        return roleOnes;
    }

    /**
     * 更新角色菜单信息
     * @param roleOne
     * @return
     */
    @Override
    public int updateRoleMenu(SysRoleOne roleOne) {
       int rows = 1;
       //删除角色与菜单关联
        roleMenuOneMapper.deleteRoleMenuByRoleOneId(roleOne.getId());
        //新增用户与角色管理
        List<SysRoleMenuOne> list = new ArrayList<>();
        for (String menuId:roleOne.getMenuIds()) {
            SysRoleMenuOne roleMenuOne = new SysRoleMenuOne();
            roleMenuOne.setRoleId(roleOne.getId());
            roleMenuOne.setMenuId(menuId);
            list.add(roleMenuOne);
        }
        if(!list.isEmpty()){
            rows = roleMenuOneMapper.batchRoleMenuOne(list);
        }
        return rows;
    }
    /**
     * 校验角色名称是否唯一
     *
     * @param roleOne 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(SysRoleOne roleOne) {
        String roleId = StringUtil.isNull(roleOne.getId())? StringUtil.EMPTY : roleOne.getId();
        SysRoleOne info = roleOneMapper.checkRoleNameUnique(roleOne.getRoleName());
        if(StringUtil.isNotNull(info)&&info.getId().equals(roleId)){
            return Constants.CHECK_NOT_UNIQUE;
        }
        return Constants.CHECK_UNIQUE;
    }
    /**
     * 校验角色权限是否唯一
     *
     * @param roleOne 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleKeyUnique(SysRoleOne roleOne) {
       String roleId = StringUtil.isNull(roleOne.getId())?StringUtil.EMPTY:roleOne.getId();
       SysRoleOne info = roleOneMapper.checkRoleKeyUnique(roleOne.getRoleKey());
       if(StringUtil.isNotNull(info)&&info.getId().equals(roleId)){
           return Constants.CHECK_NOT_UNIQUE;
       }
       return Constants.CHECK_UNIQUE;
    }

    /**
     * 根据条件分页查询角色数据
     *
     * @param roleOne 角色信息
     * @return 角色数据集合信息
     */
    @Override
    @DataScope(tableAlias = "u")
    public List<SysRoleOne> list(SysRoleOne roleOne) {
        return roleOneMapper.selectList(roleOne);
    }
    /**
     * 批量删除角色信息
     *
     * @param idList 需要删除的数据ID
     * @throws Exception
     */
    @Override
    public boolean removeByIds(List<? extends Serializable> idList) throws Exception {
        Map<String, Object> params = new HashMap<>(1);
        for (Serializable id : idList) {
			/*
			判断该角色是否已被使用
			 */
            params.clear();
            params.put("roleId", id);
            if (sysUserRoleOneMapper.selectCount(params) > 0) {
                SysRoleOne role = getById(id);
                throw new Exception(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        return retBool(roleOneMapper.deleteBatchIds(idList));
    }
}
