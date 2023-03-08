package cn.wstom.admin.service.impl;

import cn.wstom.admin.async.AsyncFactory;
import cn.wstom.admin.async.AsyncManager;
import cn.wstom.admin.entity.OnlineSession;
import cn.wstom.admin.entity.SysUserOnline;
import cn.wstom.common.enums.OnlineStatus;
import cn.wstom.common.utils.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * 针对自定义的ShiroSession的db操作
 *
 * @author dws
 */
public class OnlineSessionDAO extends EnterpriseCacheSessionDAO {
	/**
	 * 上次同步数据库的时间戳
	 */
	private static final String LAST_SYNC_DB_TIMESTAMP = OnlineSessionDAO.class.getName() + "LAST_SYNC_DB_TIMESTAMP";
	/**
	 * 同步session到数据库的周期 单位为毫秒（默认1分钟）
	 */
	@Value("${shiro.session.dbSyncPeriod}")
	private int dbSyncPeriod;

	/**
	 * 单位：分钟
	 */
	private int minute = 60 * 1000;

	@Autowired
	private SysUserOnlineServiceImpl onlineService;

	public OnlineSessionDAO() {
		super();
	}

	public OnlineSessionDAO(long expireTime) {
		super();
	}

	/**
	 * 根据会话ID获取会话
	 *
	 * @param sessionId 会话ID
	 * @return ShiroSession
	 */
	@Override
	protected Session doReadSession(Serializable sessionId) {
		SysUserOnline sysUserOnline = onlineService.getById(sessionId);
		if (StringUtil.isNotNull(sysUserOnline)) {
			return super.doReadSession(sessionId);
		}
		return null;
	}

	/**
	 * 更新会话；如更新会话最后访问时间/停止会话/设置超时时间/设置移除属性等会调用
	 */
	public void syncToDb(OnlineSession onlineSession) {
		Date lastSyncTimestamp = (Date) onlineSession.getAttribute(LAST_SYNC_DB_TIMESTAMP);
		if (lastSyncTimestamp != null) {
			boolean needSync = true;
			long deltaTime = onlineSession.getLastAccessTime().getTime() - lastSyncTimestamp.getTime();
			if (deltaTime < dbSyncPeriod * minute) {
				// 时间差不足 无需同步
				needSync = false;
			}
            boolean isGuest = onlineSession.getUserId() == null || StringUtils.EMPTY.equals(onlineSession.getUserId());

			// session 数据变更了 同步
			if (!isGuest && onlineSession.isAttributeChanged()) {
				needSync = true;
			}

			if (!needSync) {
				return;
			}
		}
		onlineSession.setAttribute(LAST_SYNC_DB_TIMESTAMP, onlineSession.getLastAccessTime());
		// 更新完后 重置标识
		if (onlineSession.isAttributeChanged()) {
			onlineSession.resetAttributeChanged();
		}
		AsyncManager.async().execute(AsyncFactory.syncSessionToDb(onlineSession));
	}

	/**
	 * 当会话过期/停止（如用户退出时）属性等会调用
	 */
	@Override
	protected void doDelete(Session session) {
		OnlineSession onlineSession = (OnlineSession) session;
		if (null == onlineSession) {
			return;
		}
		onlineSession.setStatus(OnlineStatus.OFF_LINE);
		onlineService.removeById(onlineSession.getId());
	}
}
