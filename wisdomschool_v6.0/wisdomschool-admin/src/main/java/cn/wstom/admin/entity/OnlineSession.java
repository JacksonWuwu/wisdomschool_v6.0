package cn.wstom.admin.entity;

import cn.wstom.common.enums.OnlineStatus;
import org.apache.shiro.session.mgt.SimpleSession;

/**
 * 在线用户会话属性
 *
 * @author dws
 */
public class OnlineSession extends SimpleSession {

    private static final long serialVersionUID = -2882961702681630071L;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String loginName;

    /**
     * 登录IP地址
     */
    private String host;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 在线状态
     */
    private OnlineStatus status = OnlineStatus.ON_LINE;

    /**
     * 属性是否改变 优化session数据同步
     */
    private transient boolean attributeChanged = false;

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public OnlineStatus getStatus() {
        return status;
    }

    public void setStatus(OnlineStatus status) {
        this.status = status;
    }

    public void markAttributeChanged() {
        this.attributeChanged = true;
    }

    public void resetAttributeChanged() {
        this.attributeChanged = false;
    }

    public boolean isAttributeChanged() {
        return attributeChanged;
    }

    @Override
    public void setAttribute(Object key, Object value) {
        super.setAttribute(key, value);
    }

    @Override
    public Object removeAttribute(Object key) {
        return super.removeAttribute(key);
    }
}
