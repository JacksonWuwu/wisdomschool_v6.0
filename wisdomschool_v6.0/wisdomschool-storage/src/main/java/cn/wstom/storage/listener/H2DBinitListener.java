package cn.wstom.storage.listener;

import cn.wstom.storage.server.util.FileNodeUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class H2DBinitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        FileNodeUtil.initNodeTableToDataBase();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
