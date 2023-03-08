package cn.wstom.admin.service;

import java.io.Serializable;
import java.util.Map;

/**
 * @author dws
 * @date 2019/01/30
 */
public interface Cache<T> extends Serializable {

    /**
     * 获取缓存名称
     *
     * @return
     */
    String getCacheName();

    /**
     * 刷新所有缓存
     *
     * @throws Exception
     */
    void refreshAll() throws Exception;

    /**
     * 获取缓存内容
     *
     * @param key
     * @return
     * @throws Exception
     */
    T getItem(String key) throws Exception;

    /**
     * 获取所有缓存内容
     *
     * @return
     * @throws Exception
     */
    Map getAllItem() throws Exception;

    /**
     * 设置缓存内容
     *
     * @param key
     * @param value
     * @throws Exception
     */
    void setItem(String key, T value) throws Exception;

    /**
     * 刷新指定缓存
     *
     * @param key
     * @return
     * @throws Exception
     */
    T refreshByKey(String key) throws Exception;
}
