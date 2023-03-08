package cn.wstom.admin.utils;
import java.util.List;

/**
 * @Author OZY
 * @Date 2019-08-23 16:25
 * @Description json返回结果集(单数据返回)
 * @Version V1.0
 **/
public class JsonResult<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 请求提示信息
     */
    private String msg;

    /**
     * 返回单个数据
     */
    private List<T> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
