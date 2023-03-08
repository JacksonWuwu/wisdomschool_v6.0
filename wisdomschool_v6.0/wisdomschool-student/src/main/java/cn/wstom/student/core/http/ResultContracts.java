package cn.wstom.student.core.http;

public enum ResultContracts {
    /**
     * 失败
     */
    FAILED(0, "failed"),

    /**
     * 成功
     */
    SUCCESS(1, "success"),

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

    private int code;
    private String msg;

    ResultContracts(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
