package cn.wstom.admin.cache;



/**
 * 应用配置工具类
 *
 * @author dws
 * @date 2019/01/30
 */
public class ConfigUtils {

    public static boolean isCacheSwitch(String cacheName) {
        return Environment.getCacheSwitch().get(cacheName);
    }
}
