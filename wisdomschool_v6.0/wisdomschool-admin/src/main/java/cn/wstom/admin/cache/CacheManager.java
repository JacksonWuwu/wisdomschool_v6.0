package cn.wstom.admin.cache;

import cn.wstom.admin.service.Cache;
import cn.wstom.admin.service.CacheProvider;
import cn.wstom.common.utils.StringUtil;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

/**
 * 缓存管理
 *
 * @author dws
 * @date 2019/01/30
 */
public class CacheManager extends GeneralCacheAdministrator implements CacheProvider<CacheItem<?>> {
    private static final long serialVersionUID = -5671166406914653070L;

    private static final ThreadLocal<CacheCell> CACHE_TYPE = new ThreadLocal<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheManager.class);

    /**
     * 获取缓存类型
     *
     * @return
     */
    public static CacheCell getCacheType() {
        return CACHE_TYPE.get();
    }

    /**
     * 存储缓存类型
     *
     * @param cacheType
     * @return
     */
    public static CacheManager setCacheType(CacheCell cacheType) {
        CACHE_TYPE.set(cacheType);
        return (CacheManager) Environment.getCacheManager();
    }

    public CacheManager() {
    }

    public CacheManager(Properties properties) {
        super(properties);
    }

    /**
     * 缓存数据
     *
     * @param cache
     */
    private void putCache(Cache<CacheItem<?>> cache) {
        this.putInCache(cache.getCacheName(), cache);
    }

    /**
     * 以key为主键，缓存数据value
     *
     * @param key   主键
     * @param value 数据
     * @param <T>
     */
    public static <T> void put(String key, T value) {
        CacheManager cacheManager = (CacheManager) Environment.getCacheManager();
        cacheManager.putInCache(key, value);
    }

    /**
     * 从缓存中获取数据
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        CacheManager cacheManager = (CacheManager) Environment.getCacheManager();
        try {
            return cacheManager.getFromCache(key);
        } catch (NeedsRefreshException e) {
            e.printStackTrace();
            cacheManager.cancelUpdate(key);
        }
        return null;
    }

    @Override
    public void refreshAll(CacheCell cacheType) throws Exception {
        if (ConfigUtils.isCacheSwitch(cacheType.name)) {
            LOGGER.info("正在初始化缓存【" + cacheType.desc + "】，请稍候...");
            Cache cache;

            try {
                this.getFromCache(cacheType.name);
                this.getCache(cacheType).refreshAll();
            } catch (NeedsRefreshException e) {
                try {
                    Method method = cacheType.clzz.getMethod("getInstance");
                    cache = (Cache) method.invoke(null, null);
                    cache.refreshAll();
                    this.putCache(cache);
                } catch (Exception e1) {
                    LOGGER.error("初始化缓存出错", e1);
                    throw new Exception("初始化缓存出错", e1);
                }
            }
        }
    }

    @Override
    public Cache<CacheItem<?>> getCache(CacheCell cacheType) throws Exception {
        Cache cache = null;

        try {
            cache = (Cache) this.getFromCache(cacheType.name);
            if (cache == null) {
                LOGGER.warn("获取【" + cacheType + "】缓存失败");
                throw new Exception("获取【" + cacheType + "】缓存失败");
            }
        } catch (NeedsRefreshException e) {

            //不存在则创建缓存
            try {
                cache = this.createCacheByType(cacheType);
            } catch (Exception e1) {
                LOGGER.error("更新缓存【" + cacheType + "】异常", e);
            } finally {
                this.cancelUpdate(cacheType.name);
            }
        }
        return cache;
    }

    public Cache createCacheByType(CacheCell cacheType) throws Exception {
        LOGGER.info("初始化缓存【" + cacheType.desc + "】");
        Method method = cacheType.clzz.getMethod("getInstance");
        Cache cache = (Cache) method.invoke(null, null);
        this.putCache(cache);
        return cache;
    }

    public static Object getItem(String key) throws Exception {
        CacheProvider cacheManager = Environment.getCacheManager();
        CacheItem cacheItem = (CacheItem) cacheManager.getItem(key, getCacheType());
        return cacheItem == null ? null : cacheItem.getItem();
    }

    @Override
    public CacheItem<?> getItem(String key, CacheCell cacheType) throws Exception {
        if (StringUtils.EMPTY.equals(StringUtil.trimToEmpty(key))) {
            throw new Exception("key must not null!");
        }
        return this.getCache(cacheType).getItem(key);
    }

    @Override
    public void setItem(CacheCell cacheType, String key, CacheItem<?> value) throws Exception {
        this.getCache(cacheType).setItem(key, value);
    }

    public static void setItem(String key, CacheItem<?> value) throws Exception {
        CacheProvider cacheManager = Environment.getCacheManager();
        cacheManager.setItem(getCacheType(), key, value);
    }

    @Override
    public void refreshByKey(CacheCell cacheType, String key, CacheItem value) throws Exception {
        this.getCache(cacheType).refreshByKey(key);
    }

    @Override
    public Map<String, ?> getAllItem(CacheCell cacheType) throws Exception {
        return this.getCache(cacheType).getAllItem();
    }

    public static Object getAllItem() throws Exception {
        CacheProvider cacheManager = Environment.getCacheManager();
        return cacheManager.getAllItem(getCacheType());
    }

    @Override
    public void refreshAll() throws Exception {
        CacheCell[] caches = CacheCell.values();
        for (CacheCell cache : caches) {
            this.refreshAll(cache);
        }
    }
}
