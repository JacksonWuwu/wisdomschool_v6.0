package cn.wstom.storage.listener;

import cn.wstom.storage.server.mapper.NodeMapper;
import cn.wstom.storage.server.util.ConfigureReader;
import cn.wstom.storage.server.util.FileBlockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

/**
 * @author dws
 */
@WebListener
public class InitNodeListener implements ServletContextListener {
    private final Logger LOGGER = LoggerFactory.getLogger(InitNodeListener.class);

    @Autowired
    private NodeMapper nodeMapper;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("文件系统节点信息校对...");
        String fsp = ConfigureReader.instance().getFileSystemPath();
        File file = new File(fsp);
        if (file.isDirectory() && file.canRead() && file.canWrite()) {
            ApplicationContext context = WebApplicationContextUtils
                    .getWebApplicationContext(sce.getServletContext());
            FileBlockUtil.checkFileBlocks(nodeMapper);
            //临时目录
            String tfPath = ConfigureReader.instance().getTemporaryfilePath();
            File f = new File(tfPath);
            if (!f.exists()) {
                f.mkdir();
            } else {
                File[] listFiles = f.listFiles();
                for (File fs : listFiles) {
                    fs.delete();
                }
            }
            LOGGER.info("校对完成。");
        } else {
            LOGGER.info("错误：文件系统节点信息校对失败，存储位置无法读写或不存在。");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("清理临时文件...");
        String tfPath = ConfigureReader.instance().getTemporaryfilePath();
        File f = new File(tfPath);
        File[] listFiles = f.listFiles();
        for (File fs : listFiles) {
            fs.delete();
        }
    }
}
