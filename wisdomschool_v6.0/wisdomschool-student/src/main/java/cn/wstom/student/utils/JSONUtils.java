package cn.wstom.student.utils;

import com.alibaba.fastjson.JSON;

import java.io.*;

/**
 * json工具
 *
 * @author dws
 * @date 2019/02/04
 */
public class JSONUtils {
    public static final String DEFAULT_FAIL = "\"Parse failed\"";

    public static void writeValue(File file, Object value) throws Exception {
        OutputStream out = new FileOutputStream(file);
        JSON.writeJSONString(out, value);
    }

    public static void writeValue(OutputStream os, Object value) throws Exception {
        JSON.writeJSONString(os, value);
    }

    public static String writeValue(Object value) {
        return JSON.toJSONString(value);
    }

    public static byte[] writeValueAsBytes(Object value) {
        return JSON.toJSONBytes(value);
    }

    public static <T> T readValue(File file, Class<T> valueType) throws Exception {
        InputStream in = new FileInputStream(file);
        return JSON.parseObject(in, valueType);
    }

    public static <T> T readValue(InputStream is, Class<T> valueType) throws Exception {
        return JSON.parseObject(is, valueType);
    }

    public static <T> T readValue(String str, Class<T> valueType) {
        return JSON.parseObject(str, valueType);
    }

    public static <T> T readValue(byte[] bytes, Class<T> valueType) {
        if (bytes == null) {
            bytes = new byte[0];
        }
        return JSON.parseObject(bytes, valueType);
    }
}
