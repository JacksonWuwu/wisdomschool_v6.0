package cn.wstom.common.base;

import cn.wstom.common.constant.WxResultContracts;

public class WxResult {
    /**
     * 状态码：1成功，其他为失败
     */
    public int code;

    /**
     * 成功为success，其他为失败原因
     */
    public String message;

    /**
     * 数据结果集
     */
    public Object data;

    public WxResult(WxResultContracts wxResultContracts, Object data) {
        this.code = wxResultContracts.getCode();
        this.message = wxResultContracts.getMessage();
        this.data = data;
    }

    public WxResult(WxResultContracts wxResultContracts) {
        this.code = wxResultContracts.getCode();
        this.message = wxResultContracts.getMessage();
        this.data = null;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
