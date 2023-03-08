package cn.wstom.admin.service.impl;


import cn.wstom.admin.entity.StatisSysUser;
import cn.wstom.admin.entity.SysRole;
import cn.wstom.admin.entity.SysUser;
import cn.wstom.admin.entity.SysUserRole;
import cn.wstom.admin.mapper.SysRoleMapper;
import cn.wstom.admin.mapper.SysUserMapper;
import cn.wstom.admin.mapper.SysUserRoleMapper;
import cn.wstom.admin.service.SysUserService;

import cn.wstom.common.constant.Constants;
import cn.wstom.common.support.Convert;
import cn.wstom.common.utils.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 业务层处理
 *
 * @author dws
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	@Autowired
	private SysUserMapper userMapper;

	@Autowired
	private SysRoleMapper roleMapper;

	@Autowired
	private SysUserRoleMapper userRoleMapper;


	/**
	 * 根据条件分页查询用户对象
	 *
	 * @param sysUser 用户信息
	 * @return 用户信息集合信息
	 */
	/*@DataScope(tableAlias = "u")*/ //数据域过滤
	@Override
	public List<SysUser> list(SysUser sysUser) {
		return userMapper.selectList(sysUser);
	}

	/**
	 * 通过用户名查询用户
	 *
	 * @param userName 用户名
	 * @return 用户对象信息
	 */
	@Override
	public SysUser selectUserByLoginName(String userName) {
		return userMapper.selectUserByLoginName(userName);
	}

	/**
	 * 通过手机号码查询用户
	 *
	 * @param phoneNumber 用户名
	 * @return 用户对象信息
	 */
	@Override
	public SysUser selectUserByPhoneNumber(String phoneNumber) {
		return userMapper.selectUserByPhoneNumber(phoneNumber);
	}


	@Override
	public SysUser updateUserEmail(String email) {
		return userMapper.updateUserEmail(email);
	}

	/**
	 * 通过属性ID,attid查询用户
	 *
	 * @param userAttrId 用户属性ID
	 * @return 用户对象信息
	 */
	@Override
	public SysUser selectUserByUserAttrId(String userAttrId) {
		return userMapper.selectUserByUserAttrId(userAttrId);
	}


	/**
	 * 通过邮箱查询用户
	 *
	 * @param email 邮箱
	 * @return 用户对象信息
	 */
	@Override
	public SysUser selectUserByEmail(String email) {
		return userMapper.selectUserByEmail(email);
	}


	/**
	 * 新增保存用户信息
	 *
	 * @param sysUser 用户信息
	 * @return 结果
	 */
	@Override
	public boolean save(SysUser sysUser) {
		// 新增用户信息
		int rows = userMapper.insert(sysUser);
		// 更新用户与角色管理
		updateUserRole(sysUser);
		return retBool(rows);
	}

	@Override
	public boolean update(SysUser sysUser, boolean withRole) throws Exception {
		// 更新用户与角色管理
		if (withRole) {
			updateUserRole(sysUser);
		}
		return super.update(sysUser);
	}

	/**
	 * 更新用户角色信息
	 *
	 * @param sysUser 用户对象
	 */
	@Override
	public boolean updateUserRole(SysUser sysUser) {
		if (sysUser.getRoleIds() == null) {
			return false;
		}
		String userId = sysUser.getId();
		// 删除用户与角色关联
		userRoleMapper.deleteUserRoleByUserId(userId);
		// 新增用户与角色关联
		List<SysUserRole> list = new ArrayList<>();
		for (String roleId : sysUser.getRoleIds()) {
			SysUserRole ur = new SysUserRole();
			ur.setUserId(sysUser.getId());
			ur.setRoleId(roleId);
			list.add(ur);
		}
		if (!list.isEmpty()) {
			return retBool(userRoleMapper.batchUserRole(list));
		}
		return false;
	}

	/**
	 * 校验用户名称是否唯一
	 *
	 * @param loginName 用户名
	 * @return
	 */
	@Override
	public String checkLoginNameUnique(String loginName) {
		Map<String, Object> parms = new HashMap<>(1);
		parms.put("loginName", loginName);
		int count = userMapper.selectCount(parms);
		if (count > 0) {
			return Constants.CHECK_NOT_UNIQUE;
		}
		return Constants.CHECK_UNIQUE;
	}

	/**
	 * 校验用户名称是否唯一
	 *
	 * @param sysUser 用户名
	 * @return
	 */
	@Override
	public String checkPhoneUnique(SysUser sysUser) {
		String userId = StringUtil.isNull(sysUser.getId()) ? StringUtils.EMPTY : sysUser.getId();
		SysUser info = userMapper.checkPhoneUnique(sysUser.getPhoneNumber());
		if (StringUtil.isNotNull(info) && info.getId().equals(userId)) {
			return Constants.CHECK_NOT_UNIQUE;
		}
		return Constants.CHECK_UNIQUE;
	}

	/**
	 * 校验email是否唯一
	 *
	 * @param sysUser 用户名
	 * @return
	 */
	@Override
	public String checkEmailUnique(SysUser sysUser) {
		String userId = StringUtil.isNull(sysUser.getId()) ? StringUtils.EMPTY : sysUser.getId();
		SysUser info = userMapper.checkEmailUnique(sysUser.getEmail());
		if (StringUtil.isNotNull(info) && info.getId().equals(userId)) {
			return Constants.CHECK_NOT_UNIQUE;
		}
		return Constants.CHECK_UNIQUE;
	}

	/**
	 * 查询用户所属角色组
	 *
	 * @param userId 用户ID
	 * @return 结果
	 */
	@Override
	public String selectUserRoleGroup(String userId) {
		List<SysRole> list = roleMapper.selectRolesByUserId(userId);
		StringBuffer idsStr = new StringBuffer();
		for (SysRole role : list) {
			idsStr.append(role.getRoleName()).append(",");
		}
		if (StringUtil.isNotEmpty(idsStr.toString())) {
			return idsStr.substring(0, idsStr.length() - 1);
		}
		return idsStr.toString();
	}

	/**
	 * 根据用户属性id和用户属性类型查询用户名
	 *
	 *
	 * @param sysUser 实体
	 * @return 用户名称
	 */
	@Override
	public SysUser selectOneUserName(SysUser sysUser){
		return userMapper.selectOneUserName(sysUser);
	}

	/**
	 * 根据用户属性id和用户属性类型查询用户名
	 *
	 *
	 * @param sysUserList 实体集合
	 * @return 用户名称列表
	 */
	@Override
	public List<SysUser> selectUserName(List<SysUser> sysUserList){
		return userMapper.selectUserName(sysUserList);
	}


	@Override
	public boolean updateArticle(String userId, int step) throws Exception {
		// TODO: 2019/3/7 此处可优化为，先存储在缓存中，当达到一定量时写入数据库
		SysUser user = this.getById(userId);
		user.setPosts(Convert.toInt(user.getPosts(), 0) + step);
		return this.update(user);
	}

	@Override
	public boolean updateAnswer(String userId, int step) throws Exception {
		// TODO: 2019/3/7 此处可优化为，先存储在缓存中，当达到一定量时写入数据库
		SysUser user = this.getById(userId);
		user.setAnswer(Convert.toInt(user.getAnswer(), 0) + step);
		return this.update(user);
	}

	@Override
	public boolean updateTopic(String userId, int step) throws Exception {
		// TODO: 2019/3/7 此处可优化为，先存储在缓存中，当达到一定量时写入数据库
		SysUser user = this.getById(userId);
		user.setTopic(Convert.toInt(user.getTopic(), 0) + step);
		return this.update(user);
	}

	@Override
	public boolean updateAvatar(String userId, String path) throws Exception {
		SysUser user = this.getById(userId);
		user.setAvatar(path);
		return this.update(user);
	}

	@Override
	public boolean updateQuestion(String userId, int step) throws Exception {
		// TODO: 2019/3/7 此处可优化为，先存储在缓存中，当达到一定量时写入数据库
		SysUser user = this.getById(userId);
		user.setQuestion(Convert.toInt(user.getQuestion(), 0) + step);
		return this.update(user);
	}

	@Override
	public boolean updateQuestionFollow(String userId, int step) throws Exception {
		// TODO: 2019/3/7 此处可优化为，先存储在缓存中，当达到一定量时写入数据库
		SysUser user = this.getById(userId);
		user.setQuestionFollow(Convert.toInt(user.getQuestionFollow(), 0) + step);
		return this.update(user);
	}

	@Override
	public boolean updateShare(String userId, int step) throws Exception {
		// TODO: 2019/3/7 此处可优化为，先存储在缓存中，当达到一定量时写入数据库
		SysUser user = this.getById(userId);
		user.setShare(Convert.toInt(user.getShare(), 0) + step);
		return this.update(user);
	}

	@Override
	public boolean updateComment(String userId, int step) throws Exception {
		// TODO: 2019/3/7 此处可优化为，先存储在缓存中，当达到一定量时写入数据库
		SysUser user = this.getById(userId);
		user.setComment(Convert.toInt(user.getComment(), 0) + step);
		return this.update(user);
	}

	@Override
	public List<SysUser> selectStudentByCourseIdAndTeacherId(String courseId, String teacherId) {
		return userMapper.selectStudentByCourseIdAndTeacherId(courseId, teacherId);
	}

	@Override
	public List<SysUser> selectStudentByCourseIdAndTeacherId(String courseId, String teacherId, String loginName, String clbumId) {
		return userMapper.selectStudentByCourseIdAndTeacherId(courseId, teacherId, loginName, clbumId);
	}

	@Override
	public List<SysUser> selectStudentByclbumId(String loginName, String clbumId) {
		return userMapper.selectStudentByclbumId(loginName,clbumId);
	}

	@Override
	public int updateUserPassword(SysUser sysUser) {

		return userMapper.updateUserPassword(sysUser);
	}

	@Override
	public boolean checkUserFans(String userFollow, String userFans) {
		return userMapper.checkUserFans(userFollow, userFans) > 0;
	}

	@Override
	public Map<String, SysUser> listByTids(List<String> tcId) {
		List<SysUser> users = userMapper.selectBatchTids(tcId);
		Map<String, SysUser> result = new LinkedHashMap<>();
		users.forEach(u -> result.put(u.getUserAttrId(), u));
		return result;
	}
	@Override
	public List<SysUser> selectByAttrids(List<String> userAttrIds) {
		return userMapper.selectByAttrids(userAttrIds);
	}

	@Override
	public List<SysUser> selectByrid() {
		return userMapper.selectByrid();
	}

	@Override
	public List<SysUser> selectByridTwo() {
		return userMapper.selectByridTwo();
	}

	@Override
	public List<StatisSysUser> selectStudentByCourseIdsAndTeacherIds(StatisSysUser statisSysUser) {
		return userMapper.selectStudentByCourseIdsAndTeacherIds(statisSysUser);
	}

	@Override
	public List<SysUser> selectUserSchoolId(int id) {
		return userMapper.selectUserSchoolId(id);
	}

	@Override
	public List<SysUser> selectUserByRoleKey(String roleKey) {
		return userMapper.selectUserByRoleKey(roleKey);
	}

	@Override
	public SysUser selectOne(SysUser sysUser){
		return userMapper.selectOne(sysUser);
	}
}
