package cn.wstom.storage;

import cn.wstom.storage.config.MvcConfig;
import cn.wstom.storage.server.util.ConfigureReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;


/**
 * <h2>存储应用控制器</h2>
 * <p>该类为存储应用的控制层，用于控制服务器行为。包括启动、关闭、重启等。同时，该类也为
 * 应用入口，负责初始化SpringBoot容器。详见内置公有方法。
 * </p>
 *
 * @author dws
 * @version 1.0
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@Import({MvcConfig.class})
public class WstomStorageApp implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WstomStorageApp.class);

    private static ApplicationContext context;
    private static boolean run;

    /**
     * <h2>启动存储应用</h2>
     * <p>该方法将启动SpringBoot存储应用并返回启动结果。该过程较为耗时，为了不阻塞主线程，请在额外线程中执行该方法。</p>
     *
     * @return boolean 启动结果
     */
    public boolean start() {
        LOGGER.info("启动存储应用...");
        if (!WstomStorageApp.run) {
            if (ConfigureReader.instance().getPropertiesStatus() == 0) {
                try {
                    LOGGER.info("正在开启存储应用...");
                    WstomStorageApp.context = SpringApplication.run(WstomStorageApp.class, new String[0]);
                    WstomStorageApp.run = (WstomStorageApp.context != null);
                    LOGGER.info("存储应用已启动。");
                    return WstomStorageApp.run;
                } catch (Exception e) {
                    LOGGER.error("存储应用设置检查失败，无法启动存储应用。", e);
                }
            }
            LOGGER.error("存储应用设置检查失败，无法开启存储应用。");
            return false;
        }
        LOGGER.info("存储应用正在运行中。");
        return true;
    }

    /**
     * <h2>停止存储应用</h2>
     * <p>该方法将关闭存储应用并清理缓存文件。该方法较为耗时。</p>
     *
     * @return boolean 关闭结果
     */
    public boolean stop() {
        LOGGER.info("关闭存储应用...");
        if (WstomStorageApp.context != null) {
            LOGGER.info("正在终止存储应用...");
            try {
                WstomStorageApp.run = (SpringApplication.exit(WstomStorageApp.context) != 0);
                LOGGER.info("存储应用已终止。");
                return !WstomStorageApp.run;
            } catch (Exception e) {
                LOGGER.error("存储应用关闭异常!", e);
            }
        }
        LOGGER.error("存储应用未启动。");
        return true;
    }

    /**
     * SpringBoot框架设置全局错误页面，无需调用
     */
    @Override
    public void customize(final ConfigurableServletWebServerFactory factory) {
        factory.setPort(ConfigureReader.instance().getPort());
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/errorController/pageNotFound.do"));
        factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/errorController/pageNotFound.do"));
    }

    /**
     * <h2>获取服务器运行状态</h2>
     * <p>该方法返回服务器引擎的运行状态，该状态由内置属性记录，且唯一。</p>
     *
     * @return boolean 服务器是否启动
     */
    public boolean started() {
        return WstomStorageApp.run;
    }

    static {
        WstomStorageApp.run = false;
    }
}
