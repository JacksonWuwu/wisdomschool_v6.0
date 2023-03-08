package cn.wstom.student.core.http;

public class ResultInfo {
    /**
     * 状态码：1成功，其他为失败
     */
    public int code;

    /**
     * 成功为success，其他为失败原因
     */
    public String msg;

    /**
     * 数据结果集
     */
    public Object data;

    public ResultInfo(ResultContracts resultContracts, Object data) {
        this.code = resultContracts.getCode();
        this.msg = resultContracts.getMsg();
        this.data = data;
    }

    public ResultInfo(ResultContracts resultContracts) {
        this.code = resultContracts.getCode();
        this.msg = resultContracts.getMsg();
        this.data = resultContracts;
    }

    public ResultInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
