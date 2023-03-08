package cn.wstom.admin.service;

import cn.wstom.admin.cache.CacheCell;
import cn.wstom.admin.cache.CacheItem;

import java.util.Map;

/**
 * 缓存操作统一接口
 *
 * @author dws
 * @date 2019/01/30
 */
public interface CacheProvider<T> {
    /**
     * 刷新所有缓存
     *
     * @param cache
     * @throws Exception
     */
    void refreshAll(CacheCell cache) throws Exception;

    /**
     * 获取指定的缓存
     *
     * @param cacheType
     * @return
     * @throws Exception
     */
    Cache<CacheItem<?>> getCache(CacheCell cacheType) throws Exception;

    /**
     * 获取指定的缓存内容
     *
     * @param key
     * @param cacheType
     * @return
     * @throws Exception
     */
    T getItem(String key, CacheCell cacheType) throws Exception;

    /**
     * 缓存数据内容
     *
     * @param cacheType
     * @param key
     * @param value
     * @throws Exception
     */
    void setItem(CacheCell cacheType, String key, T value) throws Exception;

    /**
     * 刷新指定的缓存
     *
     * @param cacheType
     * @param key
     * @param value
     * @throws Exception
     */
    void refreshByKey(CacheCell cacheType, String key, T value) throws Exception;

    /**
     * 刷新指定的缓存
     *
     * @param cache
     * @return
     * @throws Exception
     */
    Map<String, ?> getAllItem(CacheCell cache) throws Exception;

    /**
     * 刷新所有缓存
     *
     * @throws Exception
     */
    void refreshAll() throws Exception;
}
