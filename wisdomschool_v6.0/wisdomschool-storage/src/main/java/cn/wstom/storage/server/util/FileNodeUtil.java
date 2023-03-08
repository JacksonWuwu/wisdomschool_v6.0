package cn.wstom.storage.server.util;

import cn.wstom.storage.server.enumeration.Constants;
import cn.wstom.storage.server.model.Folder;
import cn.wstom.storage.server.model.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 * <h2>文件节点初始化工具</h2>
 * <p>
 * 该工具负责初始化文件系统的文件节点，其会在数据库内建立文件节点相关的表（如果不存在）并提供该数据库的链接对象。
 * 该类无需生成实例，全部方法均为静态的。
 * </p>
 *
 * @author dws
 * @version 1.0
 */
public class FileNodeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileNodeUtil.class);

    private FileNodeUtil() {
    }

    private static Connection conn;

    /**
     * 当前链接的节点数据库位置
     */
    private static String url;

    /**
     * <h2>为数据库建立初始化节点表</h2>
     * <p>
     * 该方法将检查数据库并建立初始的文件节点表及相关索引，应在使用文件系统前先执行该操作。
     * 仅在该操作执行后，本类提供的链接对象才会创建并可以使用。
     * </p>
     */
    public static void initNodeTableToDataBase() {
        LOGGER.info("初始化文件节点...");
        try {
            if (conn == null) {
                Class.forName(ConfigureReader.instance().getFileNodePathDriver()).newInstance();
            }
            String newUrl = ConfigureReader.instance().getFileNodePathURL();
            // 判断当前位置是否初始化文件节点
            if (url == null || !url.equals(newUrl)) {
                conn = DriverManager.getConnection(newUrl, ConfigureReader.instance().getFileNodePathUserName(), ConfigureReader.instance().getFileNodePathPassWord());
                url = newUrl;
                //初始化文件夹表
                Statement initFolderStatement = conn.createStatement();
                initFolderStatement.execute(
                        "CREATE TABLE IF NOT EXISTS FOLDER(folder_id VARCHAR(128) PRIMARY KEY,  folder_name VARCHAR(128) NOT NULL,folder_creation_date VARCHAR(128) NOT NULL,  folder_creator VARCHAR(128) NOT NULL,folder_parent VARCHAR(128) NOT NULL,folder_constraint INT NOT NULL)");
                initFolderStatement.executeQuery("SELECT count(*) FROM FOLDER WHERE folder_id = 'root'");
                // TODO: 2019/2/16 资源欠优化
                ResultSet rs = initFolderStatement.getResultSet();
                if (rs.next()) {
                    if (rs.getInt(1) == 0) {
                        Statement state = conn.createStatement();
                        state.execute("INSERT INTO FOLDER(folder_id, folder_name, folder_creation_date, folder_creator, folder_constraint) VALUES('root', 'ROOT', '--', '--', 0)");
                        state.close();
                    }
                }

                initFolderStatement.executeQuery("SELECT count(*) FROM FOLDER WHERE folder_id = 'wstom'");
                rs = initFolderStatement.getResultSet();
                if (rs.next()) {
                    if (rs.getInt(1) == 0) {
                        Statement state = conn.createStatement();
                        state.execute("INSERT INTO FOLDER(folder_id, folder_name, folder_creation_date, folder_creator, folder_constraint) VALUES('wstom', 'WSTOM', '--', '--', 0)");
                        state.close();
                    }
                }

                initFolderStatement.executeQuery("SELECT count(*) FROM FOLDER WHERE folder_id = 'thumbnail'");
                rs = initFolderStatement.getResultSet();
                if (rs.next()) {
                    if (rs.getInt(1) == 0) {
                        Statement state = conn.createStatement();
                        state.execute("INSERT INTO FOLDER VALUES('thumbnail', 'THUMBNAIL', '--', '--', 'wstom', 0)");
                        state.close();
                    }
                }
                initFolderStatement.close();

                //初始化文件表
                final Statement initFileStatement = conn.createStatement();
                initFileStatement.execute(
                        "CREATE TABLE IF NOT EXISTS FILE(file_id VARCHAR(128) PRIMARY KEY,file_name VARCHAR(128) NOT NULL,file_size VARCHAR(128) NOT NULL,file_parent_folder varchar(128) NOT NULL,file_creation_date varchar(128) NOT NULL,file_creator varchar(128) NOT NULL,file_path varchar(128) NOT NULL)");
                initFileStatement.close();

                //为了匹配之前的版本而设计的兼容性字段设置，后续可能会删除
                if (!ConfigureReader.instance().useMySQL()) {
                    final Statement state3 = conn.createStatement();
                    state3.execute("ALTER TABLE FOLDER ADD COLUMN IF NOT EXISTS folder_constraint INT NOT NULL DEFAULT 0");
                    state3.close();
                }
                //为数据库生成索引，此处分为MySQL和H2两种操作
                if (ConfigureReader.instance().useMySQL()) {
                    final Statement initIndexStatement = conn.createStatement();
                    ResultSet indexCount = initIndexStatement.executeQuery("SHOW INDEX FROM FILE WHERE Key_name = 'file_index'");
                    if (!indexCount.next()) {
                        final Statement state41 = conn.createStatement();
                        state41.execute("CREATE INDEX file_index ON FILE (file_id,file_name)");
                        state41.close();
                    }
                    initIndexStatement.close();
                } else {
                    final Statement initIndexStatement = conn.createStatement();
                    initIndexStatement.execute("CREATE INDEX IF NOT EXISTS file_index ON FILE (file_id,file_name)");
                    initIndexStatement.close();
                }
            }
            LOGGER.info("文件节点初始化完毕。");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            LOGGER.error("错误：文件节点初始化失败。");
        }
    }

    /**
     * <h2>获取文件节点的数据库链接</h2>
     * <p>
     * 在执行initNodeTableToDataBase方法后，可通过本方法获取文件节点的数据库链接以便继续操作，否则返回null。
     * </p>
     *
     * @return java.sql.Connection 文件节点数据库的链接，除非程序关闭，否则该链接不应关闭。
     */
    public static Connection getNodeDBConnection() {
        return conn;
    }

    /**
     * <h2>生成不与已存在文件同名的、带计数的新文件名</h2>
     * <p>
     * 针对需要保留两个文件至一个路径下的行为，可使用该方法生成新文件名，其格式为“{原文件名} (计数).{后缀}”的格式。
     * 例如，某路径下已存在“test1.txt”，再传入一个“test1.txt”时，会返回“test1 (1).txt”，继续传入“test1 (1).txt”
     * 则返回“test1 (2).txt”，以此类推。当文件列表中不含同名文件时，返回原始文件名。
     * </p>
     *
     * @param originalName java.lang.String 原始文件名
     * @param nodes        java.util.List Node 要检查的文件节点列表
     * @return java.lang.String 新文件名
     */
    public static String getNewNodeName(String originalName, List<Node> nodes) {
        int i = 0;
        List<String> fileNames = Arrays
                .asList(nodes.stream().parallel().map(Node::getFileName).toArray(String[]::new));
        String newName = originalName;
        while (fileNames.contains(newName)) {
            i++;
            newName = originalName.substring(0, originalName.lastIndexOf(Constants.SEPARATOR_DOT)) + " (" + i + ")"
                    + originalName.substring(originalName.lastIndexOf(Constants.SEPARATOR_DOT));
        }
        return newName;
    }

    /**
     * <h2>生成不与已存在文件夹同名的、带计数的新文件夹名</h2>
     * <p>功能与得到新文件名类似，当文件夹列表中存在“doc”文件夹时，传入“doc”则返回“doc 2”，以此类推。</p>
     *
     * @param originalName java.lang.String 原始文件夹名
     * @param folders      java.util.List Folder 要检查的文件夹列表
     * @return java.lang.String 新文件夹名
     */
    public static String getNewFolderName(String originalName, List<? extends Folder> folders) {
        int i = 0;
        List<String> fileNames = Arrays
                .asList(folders.stream().parallel().map(Folder::getFolderName).toArray(String[]::new));
        String newName = originalName;
        while (fileNames.contains(newName)) {
            i++;
            newName = originalName + " " + i;
        }
        return newName;
    }

    /**
     * <h2>生成不与已存在文件夹同名的、带计数的新文件夹名</h2>
     * <p>功能与得到新文件名类似，当文件夹列表中存在“doc”文件夹时，传入“doc”则返回“doc 2”，以此类推。</p>
     *
     * @param folder       kohgylw..server.model.Folder 原始文件夹
     * @param parentfolder java.io.File 要检查的文件夹
     * @return java.lang.String 新文件夹名
     */
    public static String getNewFolderName(Folder folder, File parentfolder) {
        int i = 0;
        List<String> fileNames = Arrays
                .asList(Arrays.stream(parentfolder.listFiles()).parallel().filter(File::isDirectory).map(File::getName).toArray(String[]::new));
        String newName = folder.getFolderName();
        while (fileNames.contains(newName)) {
            i++;
            newName = folder.getFolderName() + " " + i;
        }
        return newName;
    }

    /**
     * <h2>生成不与已存在文件同名的、带计数的新文件名</h2>
     * <p>
     * 针对需要保留两个文件至一个路径下的行为，可使用该方法生成新文件名，其格式为“{原文件名} (计数).{后缀}”的格式。
     * 例如，某路径下已存在“test1.txt”，再传入一个“test1.txt”时，会返回“test1 (1).txt”，继续传入“test1 (1).txt”
     * 则返回“test1 (2).txt”，以此类推。当文件列表中不含同名文件时，返回原始文件名。
     * </p>
     *
     * @param n      kohgylw..server.model.Node 要重命名的文件节点
     * @param folder java.io.File 要检查的本地文件夹
     * @return java.lang.String 新文件名
     */
    public static String getNewNodeName(Node n, File folder) {
        int i = 0;
        List<String> fileNames = Arrays
                .asList(Arrays.stream(folder.listFiles()).parallel().filter(File::isFile).map(File::getName).toArray(String[]::new));
        String newName = n.getFileName();
        while (fileNames.contains(newName)) {
            i++;
            newName = n.getFileName().substring(0, n.getFileName().lastIndexOf(Constants.SEPARATOR_DOT)) + " (" + i + ")"
                    + n.getFileName().substring(n.getFileName().lastIndexOf(Constants.SEPARATOR_DOT));
        }
        return newName;
    }

}
