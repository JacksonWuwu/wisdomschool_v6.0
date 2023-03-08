package cn.wstom.exam.utils;

import java.lang.reflect.Type;

/**
 * @author dws
 * @date 2019/02/08
 */
public class TypeUtils {

    public static boolean isBaseType(String type) {
        type = type.toLowerCase();
        return type.contains(Integer.TYPE.getName()) ||
                type.contains(Long.TYPE.getName()) ||
                type.contains(Double.TYPE.getName()) ||
                type.contains(Boolean.TYPE.getName()) ||
                type.contains("string");
    }

    public static boolean isBaseType(Type type) {
        return TypeUtils.isBaseType(type.getTypeName());
    }

}
