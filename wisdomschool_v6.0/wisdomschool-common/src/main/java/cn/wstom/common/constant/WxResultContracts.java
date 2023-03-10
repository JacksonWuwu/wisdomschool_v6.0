package cn.wstom.common.constant;

/**
 * 结果常量枚举类
 * @author liniec
 * @since 2019年9月27日 16点55分
 */
public enum WxResultContracts {
    /**
     * 失败
     */
    FAILED(0, "failed"),

    /**
     * 成功
     */
    SUCCESS(1, "success"),

    /**
     * 无效长度
     */
    INVALID_LENGTH(10001, "Invalid length"),

    /**
     * 用户名不能为空
     */
    EMPTY_USERNAME(10101, "Username cannot be empty"),

    /**
     * 密码不能为空
     */
    EMPTY_PASSWORD(10102, "Password cannot be empty"),

    /**
     * 帐号不存在
     */
    INVALID_USERNAME(10103, "Account does not exist"),

    /**
     * 密码错误
     */
    INVALID_PASSWORD(10104, "Password error"),

    /**
     * 无效帐号
     */
    INVALID_ACCOUNT(10105, "Invalid dao");

    public int code;

    public String message;

    WxResultContracts(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
