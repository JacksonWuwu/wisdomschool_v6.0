package cn.wstom.common.constant;

/**
 * 网络请求常量
 *
 * @author dws
 * @date 2018/12/23
 */
public interface HttpConstants {

	/**
	 * 状态码--操作失败
	 */
	int CODE_FAILURE = -1;

    /**
     * 状态码--未登录
     */
    int CODE_NOT_LOGIN = 401;

	/**
	 * 状态码--操作成功
	 */
	int CODE_SUCCESS = 0;

	/**
	 * 状态码--权限不足
	 */
	int CODE_FORBID = 403;

	/**
	 * 状态码--系统异常
	 */
	int CODE_ERROR = 500;

}
