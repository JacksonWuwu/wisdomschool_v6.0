package cn.wstom.admin.service;

import cn.wstom.admin.entity.SysUserOnline;

import java.util.Date;
import java.util.List;

/**
 * 在线用户 服务层
 *
 * @author dws
 */
public interface SysUserOnlineService extends BaseService<SysUserOnline> {

	/**
	 * 查询会话集合
	 *
	 * @param sysUserOnline 分页参数
	 * @return 会话集合
	 */
	List<SysUserOnline> selectUserOnlineList(SysUserOnline sysUserOnline);

	/**
	 * 强退用户
	 *
	 * @param sessionId 会话ID
	 */
	void forceLogout(String sessionId);

	/**
	 * 查询会话集合
	 *
	 * @param expiredDate 有效期
	 * @return 会话集合
	 */
	List<SysUserOnline> selectOnlineByExpired(Date expiredDate);
}
