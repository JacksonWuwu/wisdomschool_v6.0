package cn.wstom.admin.cache;

import cn.wstom.admin.service.CacheProvider;
import cn.wstom.common.utils.YamlUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 应用资源环境
 *
 * @author dws
 * @date 2019/01/30
 */
public class Environment implements Serializable {
    private static final long serialVersionUID = 4974048303048202523L;
    /**
     * 当前用户
     */
    public static final String LOGIN_USER_ID = "USER";

    private static String serverOS = "";

    private static final Logger LOGGER = LoggerFactory.getLogger(Environment.class);

    /**
     * sql批量操作数
     */
    public static final int BATCHSQL = 100;

    /**
     * 安全级别
     */
    public static final String SECURITY_LEVEL = "SECURITY_LEVEL";

    private static ServletContext servletContext;

    private static CacheProvider<CacheItem<?>> cacheManager;

    /**
     * 缓存开关
     */
    private static Map<String, Boolean> cacheSwitch = new HashMap<>();

    public static final String REGEX_TRUE_OR_FALSE = "^true|false$";

    public static CacheProvider<CacheItem<?>> getCacheManager() {
        return cacheManager;
    }

    public static Map<String, Boolean> getCacheSwitch() {
        return Collections.unmodifiableMap(cacheSwitch);
    }

    public static void setCacheManager(CacheProvider<CacheItem<?>> cacheManager) {
        Environment.cacheManager = cacheManager;
    }

    public static String getServerOS() {
        return serverOS;
    }

    static {
        try {
            Map<?, ?> configMap = YamlUtil.loadYaml("sysconfig.yaml");
            initCacheSwitch(configMap);
            serverOS = System.getProperty("os.name");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化缓存开关
     *
     * @param confMap
     */
    private static void initCacheSwitch(Map confMap) {
        Object property = YamlUtil.getProperty(confMap, "cache.switch");
        if (property instanceof Map) {
            ((Map) property).forEach((k, v) -> {
                String val = v.toString().trim();
                if (!val.matches(REGEX_TRUE_OR_FALSE)) {
                    LOGGER.warn("缓存【" + k + "】开关值必须为true 或 false");
                }
                cacheSwitch.put(k.toString(), Boolean.valueOf(val));
                LOGGER.info("缓存【" + k.toString() + "】开关为" + Boolean.valueOf(val));
            });
        }
    }

}
