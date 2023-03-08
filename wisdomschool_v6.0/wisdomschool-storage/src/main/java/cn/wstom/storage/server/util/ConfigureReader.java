package cn.wstom.storage.server.util;


import cn.wstom.storage.server.model.Folder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

/**
 * <h2>配置解析器</h2>
 * <p>
 * 该工具负责读取并解析配置文件。
 * </p>
 *
 * @author dws
 * @version 1.0
 */
public class ConfigureReader {
    private final Logger LOGGER = LoggerFactory.getLogger(ConfigureReader.class);
    private final String configPath = "application.yaml";

    /**
     * 数据库方言
     */
    private String dbDialect = "storage.dialect";
    private static final String DIALECT_MYSQL = "mysql";
    private static final String DIALECT_H2 = "h2";

    private static Map<?, ?> configCache;

    private static ConfigureReader cr;
    private int propertiesStatus;
    private String path;
    private String fileSystemPath;
    private String confdir;
    private String mustLogin;
    private int port;
    private String log;
    private String FSPath;
    private int bufferSize;
    private String fileBlockPath;
    private String fileNodePath;
    private String TFPath;
    private String dbURL;
    private String dbDriver;
    private String dbUser;
    private String dbPwd;
    private final String ACCOUNT_PROPERTIES_FILE = "account.properties";
    private final String SERVER_PROPERTIES_FILE = "server.properties";
    private final int DEFAULT_BUFFER_SIZE = 1048576;
    private final int DEFAULT_PORT = 8080;
    private final String DEFAULT_MUST_LOGIN = "O";
    private final String DEFAULT_FILE_SYSTEM_PATH = "D:/wstom/file";
    private final String DEFAULT_ACCOUNT_ID = "admin";
    private final String DEFAULT_ACCOUNT_PWD = "000000";
    private final String DEFAULT_ACCOUNT_AUTH = "cudrm";
    private final String DEFAULT_AUTH_OVERALL = "l";

    public static final int INVALID_PORT = 1;
    public static final int INVALID_LOG = 2;
    public static final int INVALID_FILE_SYSTEM_PATH = 3;
    public static final int INVALID_BUFFER_SIZE = 4;
    public static final int CANT_CREATE_FILE_BLOCK_PATH = 5;
    public static final int CANT_CREATE_FILE_NODE_PATH = 6;
    public static final int CANT_CREATE_TF_PATH = 7;
    public static final int CANT_CONNECT_DB = 8;
    public static final int LEGAL_PROPERTIES = 0;

    private ConfigureReader() {
        LOGGER.info("正在载入配置文件...");
        // 开发环境下
        this.path = System.getProperty("user.dir");
        try {
            if (configCache == null) {
                configCache = YamlUtil.loadYaml(configPath);
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("错误：配置文件【" + configPath + "】读取失败...", e);
        }
        LOGGER.info("配置文件载入完毕。");
        this.propertiesStatus = this.testServerPropertiesAndEffect();
    }

    public static ConfigureReader instance() {
        if (ConfigureReader.cr == null) {
            ConfigureReader.cr = new ConfigureReader();
        }
        return ConfigureReader.cr;
    }

    public int getBuffSize() {
        return this.bufferSize;
    }

    public String getFileSystemPath() {
        return this.fileSystemPath;
    }

    public String getFileBlockPath() {
        return this.fileBlockPath;
    }

    public String getFileNodePath() {
        return this.fileNodePath;
    }

    public String getTemporaryfilePath() {
        return this.TFPath;
    }

    public String getPath() {
        return this.path;
    }

    public int getPort() {
        return this.port;
    }

    public int getPropertiesStatus() {
        return this.propertiesStatus;
    }

    /**
     * <h2>验证配置并完成赋值</h2>
     * <p>
     * 该方法用于对配置文件进行验证并将正确的值赋予相应的属性，必须在构造器中执行本方法。
     * </p>
     *
     * @return int 验证结果代码
     */
    private int testServerPropertiesAndEffect() {
        LOGGER.info("正在检查配置...");
        String ports = getConfig("server.port");
        if (ports == null) {
            LOGGER.warn("警告：未找到端口配置，将采用默认值（8080）。");
            this.port = 8080;
        } else {
            try {
                this.port = Integer.parseInt(ports);
                if (this.port <= 0 || this.port > 65535) {
                    return 1;
                }
            } catch (Exception e) {
                return 1;
            }
        }
        String bufferSizes = getConfig("storage.buff-size");
        if (bufferSizes == null) {
            LOGGER.warn("警告：未找到缓冲大小配置，将采用默认值（1048576）。");
            this.bufferSize = 1048576;
        } else {
            try {
                this.bufferSize = Integer.parseInt(bufferSizes);
                if (this.bufferSize <= 0) {
                    LOGGER.error("错误：缓冲区大小设置无效。");
                    return 4;
                }
            } catch (Exception e2) {
                LOGGER.error("错误：缓冲区大小设置无效。");
                return 4;
            }
        }
        this.FSPath = getConfig("storage.path");
        if (this.FSPath == null) {
            LOGGER.warn("警告：未找到文件系统配置，将采用默认值。");
            this.fileSystemPath = this.DEFAULT_FILE_SYSTEM_PATH;
        } else {
            this.fileSystemPath = this.FSPath;
        }
        if (!fileSystemPath.endsWith(File.separator)) {
            fileSystemPath = fileSystemPath + File.separator;
        }
        File fsFile = new File(this.fileSystemPath);
        if (!fsFile.isDirectory() || !fsFile.canRead() || !fsFile.canWrite()) {
            LOGGER.error("错误：文件系统路径[" + this.fileSystemPath + "]无效，该路径必须指向一个具备读写权限的文件夹。");
            return 3;
        }
        this.fileBlockPath = this.fileSystemPath + "fileblocks" + File.separator;
        File fbFile = new File(this.fileBlockPath);
        if (!fbFile.isDirectory() && !fbFile.mkdirs()) {
            LOGGER.error("错误：无法创建文件块存放区[" + this.fileBlockPath + "]。");
            return 5;
        }
        this.fileNodePath = this.fileSystemPath + "filenodes" + File.separator;
        File fnFile = new File(this.fileNodePath);
        if (!fnFile.isDirectory() && !fnFile.mkdirs()) {
            LOGGER.error("错误：无法创建文件节点存放区[" + this.fileNodePath + "]。");
            return 6;
        }
        this.TFPath = this.fileSystemPath + "temporaryfiles" + File.separator;
        File tfFile = new File(this.TFPath);
        if (!tfFile.isDirectory() && !tfFile.mkdirs()) {
            LOGGER.error("错误：无法创建临时文件存放区[" + this.TFPath + "]。");
            return 7;
        }

        if (this.useMySQL()) {
            dbDriver = getConfig("storage.db.driver");
            dbURL = getConfig("storage.db.url");
            dbUser = getConfig("storage.db.username");
            dbPwd = getConfig("storage.db.password");
            try {
                Class.forName(dbDriver).newInstance();
                Connection testConn = DriverManager.getConnection(dbURL, dbUser, dbPwd);
                testConn.close();
            } catch (Exception e) {
                LOGGER.error("错误：无法连接至自定义数据库：" + dbURL + "（user=" + dbUser + ",password=" + dbPwd + "），请确重新配置MySQL数据库相关项。");
                return 8;
            }
        } else {
            dbDriver = "org.h2.Driver";
            dbURL = "jdbc:h2:file:" + fileNodePath + File.separator + "wstom";
            dbUser = "root";
            dbPwd = "301537gY";
        }
        LOGGER.info("检查完毕。");
        return 0;
    }

    /**
     * <h2>检查是否开启了mysql数据库</h2>
     * <p>
     * 用于检查是否使用自定义的外部MySQL数据库。
     * </p>
     *
     * @return boolean 是否使用了外部MySQL数据库
     */
    public boolean useMySQL() {
        return DIALECT_MYSQL.equals(getConfig(dbDialect));
    }

    /**
     * <h2>获取文件节点数据库链接URL</h2>
     * <p>
     * 该位置为存储文件系统的数据库的链接URL，如果使用内嵌数据库则表示为一个文件路径；
     * 如果定义了MySQL数据库位置，则使用用户自定义URL，该数据库必须使用UTF-8编码集。
     * </p>
     *
     * @return String 用于数据源或jdbc进行连接的文件节点数据库URL地址
     */
    public String getFileNodePathURL() {
        return dbURL;
    }

    /**
     * <h2>获取文件节点数据库链接驱动类型</h2>
     * <p>
     * 如设定使用MySQL，则使用外置MySQL-connector 8.0，否则使用默认内置数据库驱动。
     * </p>
     *
     * @return java.lang, String 数据库驱动类型
     */
    public String getFileNodePathDriver() {
        return dbDriver;
    }

    /**
     * <h2>获取文件节点数据库链接用户名</h2>
     * <p>
     * 如设定使用MySQL，则使用用户自定义用户名，否则使用默认内置数据库用户名。
     * </p>
     *
     * @return java.lang, String 数据库用户名
     */
    public String getFileNodePathUserName() {
        return dbUser;
    }

    /**
     * <h2>获取文件节点数据库链接密码</h2>
     * <p>
     * 如设定使用MySQL，则使用用户自定义密码，否则使用默认内置数据库密码。
     * </p>
     *
     * @return java.lang, String 数据库密码
     */
    public String getFileNodePathPassWord() {
        return dbPwd;
    }

    private String getConfig(String key) {
        return String.valueOf(YamlUtil.getProperty(configCache, key));
    }

    /**
     * <h2>检查某一用户是否有权限访问某一文件夹</h2>
     * <p>
     * 当访问文件夹的约束等级为“公开的”（0）时，永远返回true； 当为“仅小组”（1）时， 如果文件夹创建者不为“匿名用户”且当前有登录账户，
     * 则比对当前登录账户与创建者的小组是否相同或登录账户是否与创建者相同，相同返回true。其余情况均返回false；
     * 当为“仅创建者”（2）时，如果文件夹创建者不为“匿名用户”且与当前登录账户相同，返回true，其余情况均返回false。
     * </p>
     *
     * @param f       Folder 要访问的文件夹对象
     * @param account String 要访问的账户
     * @return boolean true允许访问，false不允许访问
     */
    public boolean accessFolder(Folder f, String account) {
        if (f == null) {
            // 访问不存在的文件夹肯定是没权限
            return false;
        }
        int cl = f.getFolderConstraint();
        if (cl == 0) {
            return true;
        } else {
            if (account != null) {
                if (cl == 1) {
                    if (f.getFolderCreator().equals(account)) {
                        return true;
                    }
                    String vGroup = getConfig(account + ".group");
                    String cGroup = getConfig(f.getFolderCreator() + ".group");
                    if (vGroup != null && cGroup != null) {
                        if ("*".equals(vGroup) || "*".equals(cGroup)) {
                            return true;
                        }
                        String[] vgs = vGroup.split(";");
                        String[] cgs = cGroup.split(";");
                        for (String vs : vgs) {
                            for (String cs : cgs) {
                                if (vs.equals(cs)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                if (cl == 2) {
                    return f.getFolderCreator().equals(account);
                }
            }
            return false;
        }
    }

}
