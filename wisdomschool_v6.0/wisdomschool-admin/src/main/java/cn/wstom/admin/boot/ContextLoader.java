package cn.wstom.admin.boot;

import cn.wstom.admin.cache.CacheManager;
import cn.wstom.admin.cache.Environment;
import cn.wstom.admin.service.SysConfigService;
import cn.wstom.admin.service.SysMenuService;
import cn.wstom.common.utils.SpringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ContextLoader
 * @Description 加载系统数据
 * @Author dws
 * @Date 2018/11/22
 */
@Component
public class ContextLoader implements ApplicationRunner, Ordered, ServletContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextLoader.class);


    @Autowired
    private ServletContext servletContext;


    @Value("${wstom.contextPath}")
    private String contextPath;
    @Value("${wstom.storageContextPath}")
    private String storageContextPath;

    @Override
    public void run(ApplicationArguments args) {
        int initialDelay = 1000;
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5,
                new BasicThreadFactory.Builder().namingPattern("loader-schedule-pool-%d").daemon(true).build());
        executorService.schedule(() -> {

            loadContextPath();
            //加载缓存
            loadCache();
            //加载站点配置
            resetSiteConfig(true);
        }, initialDelay, TimeUnit.MILLISECONDS);
    }

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * 重置站点配置
     * @param exit 当配置数据为空时系统是否停止运行
     */
    private void resetSiteConfig(boolean exit) {
        LOGGER.info("初始化配置...");
        System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
        LOGGER.info("初始化配置结束");
    }

    /**
     * 加载缓存
     */
    private void loadCache() {
        LOGGER.info("缓存数据...");
        try {
            CacheManager cacheManager = SpringUtils.getBean("osCacheManager");
            Environment.setCacheManager(cacheManager);
            cacheManager.refreshAll();
        } catch (Exception e) {
            LOGGER.error("缓存数据异常!", e);
            System.exit(1);
        }
        LOGGER.info("缓存结束...");
    }


    public void loadContextPath() {
        LOGGER.info(contextPath);
        servletContext.setAttribute("ctx", contextPath);
        servletContext.setAttribute("storage", storageContextPath);
    }
}
