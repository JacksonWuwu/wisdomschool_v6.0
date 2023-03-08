package cn.wstom.student.constants;


import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author dws
 */
public class Data extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public static final String RESULT_KEY_CODE = "code";
    public static final String RESULT_KEY_MSG = "msg";
    public static final String RESULT_KEY_DATA = "data";

    /**
     * 初始化一个新创建的 Message 对象
     */
    public Data() {
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static Data error() {
        return error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 内容
     * @return 错误消息
     */
    public static Data error(String msg) {
        return error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 内容
     * @return 错误消息
     */
    public static Data error(int code, String msg) {
        return error(code, msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 内容
     * @return 错误消息
     */
    public static <T> Data error(String msg, Map<String,Object> data) {
        return error(Constants.FAILURE, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 错误码
     * @param msg  内容
     * @return 错误消息
     */
    public static <T> Data error(int code, String msg, Map<String,Object> data) {
        Data result = new Data();
        result.put(RESULT_KEY_CODE, code);
        result.put(RESULT_KEY_MSG, msg);
        result.put(RESULT_KEY_DATA, data);
        return result;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static Data success() {
        return Data.success("操作成功");
    }

    /**
     * 返回成功消息
     *
     * @param msg 内容
     * @return 成功消息
     */
    public static Data success(String msg) {
        return Data.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 内容
     * @return 成功消息
     */
    public static <T> Data success(String msg, Map<String,Object> data) {
        Data result = new Data();
        result.put(RESULT_KEY_CODE, Constants.SUCCESS);
        result.put(RESULT_KEY_MSG, msg);
        result.put(RESULT_KEY_DATA, data);
        return result;
    }

    /**
     * 返回成功消息
     *
     * @param msg 内容
     * @return 成功消息
     */
    public static <T> Data success(int code, String msg, T data) {
        Data result = new Data();
        result.put(RESULT_KEY_CODE, code);
        result.put(RESULT_KEY_MSG, msg);
        result.put(RESULT_KEY_DATA, data);
        return result;
    }

    /**
     * 返回成功消息
     *
     * @param data 数据
     * @return 成功消息
     */
    public static <T> Data success(T data) {
        Data result = new Data();
        result.put(RESULT_KEY_CODE, Constants.SUCCESS);
        result.put(RESULT_KEY_DATA, data);
        return result;
    }


    /**
     * 返回消息
     *
     * @param key   键值
     * @param value 内容
     * @return 消息
     */
    @Override
    public Data put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
