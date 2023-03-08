package cn.wstom.admin.service.impl;


import cn.wstom.admin.entity.SysUserOnline;
import cn.wstom.admin.mapper.SysUserOnlineMapper;
import cn.wstom.admin.service.SysUserOnlineService;
import cn.wstom.common.utils.DateUtils;
import cn.wstom.common.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 在线用户 服务层处理
 *
 * @author dws
 */
@Service
public class SysUserOnlineServiceImpl extends BaseServiceImpl<SysUserOnlineMapper, SysUserOnline> implements SysUserOnlineService {
	@Autowired
	private SysUserOnlineMapper userOnlineMapper;

	/**
	 * 通过会话序号删除信息
	 *
	 * @param sessionId 会话ID
	 * @return 在线用户信息
	 */
	@Override
	public boolean removeById(Serializable sessionId) {
		SysUserOnline sysUserOnline = getById(sessionId);
		if (StringUtil.isNotNull(sysUserOnline)) {
			userOnlineMapper.deleteById(sessionId);
		}
		return true;
	}

	/**
	 * 查询会话集合
	 *
	 * @param sysUserOnline 分页参数
	 */
	@Override
	public List<SysUserOnline> selectUserOnlineList(SysUserOnline sysUserOnline) {
		return userOnlineMapper.selectList(sysUserOnline);
	}

	/**
	 * 强退用户
	 *
	 * @param sessionId 会话ID
	 */
	@Override
	public void forceLogout(String sessionId) {
		userOnlineMapper.deleteById(sessionId);
	}

	/**
	 * 查询会话集合
	 *
	 * @param expiredDate 会话信息
	 */
	@Override
	public List<SysUserOnline> selectOnlineByExpired(Date expiredDate) {
        String lastAccessTime = DateUtils.FormatDate.YYYYMMDDHHmmss_HOR_LINE.getDate(expiredDate);
		return userOnlineMapper.selectOnlineByExpired(lastAccessTime);
	}
}
