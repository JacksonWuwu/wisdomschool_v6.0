package cn.wstom.admin.mapper;




import cn.wstom.admin.entity.SysUserOnline;

import java.util.List;

/**
 * 在线用户 数据层
 *
 * @author dws
 */
public interface SysUserOnlineMapper extends BaseMapper<SysUserOnline> {

	/**
	 * 查询过期会话集合
	 *
	 * @param lastAccessTime 过期时间
	 * @return 会话集合
	 */
	List<SysUserOnline> selectOnlineByExpired(String lastAccessTime);
}
