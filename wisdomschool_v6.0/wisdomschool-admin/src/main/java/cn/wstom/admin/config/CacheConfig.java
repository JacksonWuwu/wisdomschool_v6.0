package cn.wstom.admin.config;


import cn.wstom.admin.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author dws
 * @date 2019/02/01
 */
@Component
public class CacheConfig {

    @Bean(destroyMethod = "destroy")
    public CacheManager osCacheManager() throws Exception {
        Properties properties = new Properties();
        InputStream resource = CacheConfig.class.getClassLoader()
                .getResourceAsStream("oscache/oscache.properties");
        properties.load(resource);
        return new CacheManager(properties);
    }
}
