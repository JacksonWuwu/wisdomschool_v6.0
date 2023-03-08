package cn.wstom.admin.service;


import cn.wstom.admin.entity.StatisSysUser;
import cn.wstom.admin.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * 用户 业务层
 *
 * @author dws
 */
public interface SysUserService extends BaseService<SysUser> {

	/**
	 * 通过用户名查询用户
	 *
	 * @param userName 用户名
	 * @return 用户对象信息
	 */
	SysUser selectUserByLoginName(String userName);

	/**
	 * 通过手机号码查询用户
	 *
	 * @param phoneNumber 手机号码
	 * @return 用户对象信息
	 */
	SysUser selectUserByPhoneNumber(String phoneNumber);

	/**
	 * 通过邮箱查询用户
	 *
	 * @param email 邮箱
	 * @return 用户对象信息
	 */

	SysUser updateUserEmail(String email);

	SysUser selectUserByEmail(String email);

	boolean update(SysUser sysUser, boolean withRole) throws Exception;
	/**
	 * 检验邮箱是否有效
	 * @param email 邮箱
	 * @return 用户对象信息
	 */

	/**
	 * 保存用户信息
	 *
	 * @param sysUser 用户信息
	 * @return 结果
	 */
	boolean updateUserRole(SysUser sysUser);

	/**
	 * 校验用户名称是否唯一
	 *
	 * @param loginName 登录名称
	 * @return 结果
	 */
	String checkLoginNameUnique(String loginName);

	/**
	 * 校验手机号码是否唯一
	 *
	 * @param sysUser 用户信息
	 * @return 结果
	 */
	String checkPhoneUnique(SysUser sysUser);

	/**
	 * 校验email是否唯一
	 *
	 * @param sysUser 用户信息
	 * @return 结果
	 */
	String checkEmailUnique(SysUser sysUser);

	/**
	 * 根据用户ID查询用户所属角色组
	 *
	 * @param userId 用户ID
	 * @return 结果
	 */
	String selectUserRoleGroup(String userId);

	/**
	 * 更新用户文章数
	 *
	 * @param userId
	 * @param step
	 * @return
	 */

	/**
	 * 根据用户属性id和用户属性类型查询用户名
	 *
	 *
	 * @param sysUser 实体
	 * @return 用户名称
	 */
	SysUser selectOneUserName(SysUser sysUser);

	/**
	 * 根据用户属性id和用户属性类型查询用户名
	 *
	 *
	 * @param sysUserList 实体集合
	 * @return 用户名称列表
	 */
	List<SysUser> selectUserName(List<SysUser> sysUserList);


	/**
	 * 更新文章数
	 *
	 * @param userId
	 * @param step
	 * @return
	 * @throws Exception
	 */
	boolean updateArticle(String userId, int step) throws Exception;

	/**
	 * 更新回复数
	 *
	 * @param userId
	 * @param step
	 * @return
	 * @throws Exception
	 */
	boolean updateAnswer(String userId, int step) throws Exception;

	/**
	 * 更新话题数
	 * 更新问题数
	 * @param userId
	 * @param step
	 * @return
	 * @throws Exception
	 */
	boolean updateTopic(String userId, int step) throws Exception;

	/**
	 * 更新头像
	 * @param id
	 * @param path
	 * @return
	 * @throws Exception
	 */
	boolean updateAvatar(String id, String path) throws Exception;

	/**
	 * 更新问题数
	 * @param userId
	 * @param step
	 * @return
	 * @throws Exception
	 */
	boolean updateQuestion(String userId, int step) throws Exception;

	/**
	 * 更新问题关注数
	 * @param userId
	 * @param step
	 * @return
	 * @throws Exception
	 */
	boolean updateQuestionFollow(String userId, int step) throws Exception;

	/**
	 * 更新分享数
	 * @param userId
	 * @param step
	 * @return
	 * @throws Exception
	 */
	boolean updateShare(String userId, int step) throws Exception;

	/**
	 * 检测用户粉丝
	 * @param userFollow
	 * @param userFans
	 * @return
	 */
	boolean checkUserFans(String userFollow, String userFans);


	Map<String, SysUser> listByTids(List<String> tcId);

	boolean updateComment(String userId, int stepIncrease) throws Exception;

	List<SysUser> selectStudentByCourseIdAndTeacherId(String courseId,
													  String teacherId);

	List<SysUser> selectStudentByCourseIdAndTeacherId(String courseId, String teacherId, String loginName, String clbumId);

	List<SysUser> selectStudentByclbumId(String loginName, String clbumId);

	/**
	 * 通过属性ID,attid查询用户
	 *
	 * @param userAttrId 用户属性ID
	 * @return 用户对象信息
	 */
	SysUser selectUserByUserAttrId(String userAttrId);


	int updateUserPassword(SysUser sysUser);


	List<SysUser> selectByAttrids(List<String> userAttrIds);
	/*
	 * 查询拥有角色sdsadmin的老师
	 * */
	List<SysUser> selectByrid();

	/*
	 * 查询拥有班主任角色的老师
	 * */
	List<SysUser> selectByridTwo();


	List<StatisSysUser> selectStudentByCourseIdsAndTeacherIds(StatisSysUser statisSysUser);

	List<SysUser> selectUserSchoolId(int id);

	List<SysUser> selectUserByRoleKey(String roleKey);

	SysUser selectOne(SysUser sysUser);
}
