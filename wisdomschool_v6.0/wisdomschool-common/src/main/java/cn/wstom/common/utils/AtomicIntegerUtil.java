package cn.wstom.common.utils;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerUtil {

    //  - key值存储以filed_ + id 形式
    @Getter
    private static Map<String, AtomicIntegerUtil> integerMap = new HashMap<>();
    private AtomicInteger atomicInteger;

    private AtomicIntegerUtil(Long value) {
        atomicInteger = new AtomicInteger(value.intValue());
    }

    /**
     * 查询是否在map中
     * @param clazz
     * @param id
     * @return
     */
    public static boolean isEmplyInMap(Class clazz, String id) {
        String key = createClassKey(clazz) + "_" + id;
        return integerMap.get(key) != null;
    }

    /**
     *  获取实例对象
     * @param clazz
     * @param id  key值
     * @return
     */
    public static AtomicIntegerUtil getInstance(Class clazz, String id) {
        String key = createClassKey(clazz) + "_" + id;
        AtomicIntegerUtil atomicIntegerUtil = integerMap.get(key);
        if (atomicIntegerUtil == null) {
            atomicIntegerUtil = new AtomicIntegerUtil(0L);
            integerMap.put(key, atomicIntegerUtil);
        }
        return atomicIntegerUtil;
    }

    /**
     *  获取实例对象
     * @param clazz
     * @param id  key值
     * @param defaultValue 默认值
     * @return
     */
    public static AtomicIntegerUtil getInstance(Class clazz, String id, Long defaultValue) {
        String key = createClassKey(clazz) + "_" + id;
        AtomicIntegerUtil atomicIntegerUtil = integerMap.get(key);
        if (atomicIntegerUtil == null) {
            atomicIntegerUtil = new AtomicIntegerUtil(defaultValue);
            integerMap.put(key, atomicIntegerUtil);
        }
        return atomicIntegerUtil;
    }

    /**
     * 获取值
     * @return
     */
    public int get() {
        return atomicInteger.get();
    }

    /**
     * /获取当前的值，并设置新的值
     * @param newValue
     * @return
     */
    public int getAndSet(int newValue) {
        return atomicInteger.getAndSet(newValue);
    }

    /**
     * 获取当前的值，并自增
     * @return
     */
    public int getAndIncrement() {
        return atomicInteger.getAndIncrement();
    }

    /**
     * /获取当前的值，并自减
     * @return
     */
    public int getAndDecrement() {
        return atomicInteger.getAndDecrement();
    }

    /**
     * 解析key字段
     *  - 类路径与id使用 "_" 连接符
     * @param key
     * @return
     */
    public static String[] splitKey (String key) {
        return key.split("_");
    }

    private static String createClassKey(Class tClass) {
        return tClass.getName();
    }

    public static Object transformToEntity (String clazzName) {
        Object entity = null;
        try {
            Class<?> clazz = Class.forName(clazzName);
            entity = clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return entity;
    }

}
