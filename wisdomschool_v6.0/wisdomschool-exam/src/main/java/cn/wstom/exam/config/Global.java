package cn.wstom.exam.config;


import cn.wstom.exam.utils.StringUtil;
import cn.wstom.exam.utils.YamlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类
 *
 * @author dws
 */
public class Global {
    private static final Logger log = LoggerFactory.getLogger(Global.class);

    private static String NAME = "application.yml";

    /**
     * 当前对象实例
     */
    private static Global global = null;

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = new HashMap<>();

    private Global() {
    }

    /**
     * 静态工厂方法 获取当前对象实例 多线程安全单例模式(使用双重同步锁)
     */

    public static synchronized Global getInstance() {
        if (global == null) {
            synchronized (Global.class) {
                if (global == null){
                    global = new Global();
                }
            }
        }
        return global;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            try {
                value = String.valueOf(YamlUtil.getProperty(NAME, key));
                map.put(key, value != null ? value : StringUtil.EMPTY);
            } catch (FileNotFoundException e) {
                log.error("获取全局配置异常 {}", key);
            }
        }
        return value;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String key, String defaultValue) {
        String config = getConfig(key);
        return StringUtil.nvl(config, defaultValue);
    }

    /**
     * 获取项目名称
     */
    public static String getName() {
        return StringUtil.nvl(getConfig("wstom.name"), "wstom");
    }

    /**
     * 获取项目版本
     */
    public static String getVersion() {
        return StringUtil.nvl(getConfig("wstom.version"), "3.0.0");
    }

    /**
     * 获取版权年份
     */
    public static String getCopyrightYear() {
        return StringUtil.nvl(getConfig("wstom.copyrightYear"), "2018");
    }

    /**
     * 获取ip地址开关
     */
    public static Boolean isAddressEnabled() {
        return Boolean.valueOf(getConfig("wstom.addressEnabled"));
    }

    /**
     * 获取文件上传路径
     */
    /*public static String getProfile() {
        return getConfig("wstom.profile");
    }*/

    /**
     * 获取头像上传路径
     */
    /*public static String getAvatarPath() {
        return getProfile() + "/avatar/";
    }*/

    /**
     * 获取下载上传路径
     */
    /*public static String getDownloadPath() {
        return getProfile() + "/download/";
    }*/

    /**
     * 获取作者
     */
    public static String getAuthor() {
        return StringUtil.nvl(getConfig("gen.author"), "wstom");
    }

    /**
     * 生成包路径
     */
    public static String getPackageName() {
        return StringUtil.nvl(getConfig("gen.packageName"), "cn.wstom.project.module");
    }

    /**
     * 是否自动去除表前缀
     */
    public static String getAutoRemovePre() {
        return StringUtil.nvl(getConfig("gen.autoRemovePre"), "true");
    }

    /**
     * 表前缀(类名不会包含表前缀)
     */
    public static String getTablePrefix() {
        return StringUtil.nvl(getConfig("gen.tablePrefix"), "sys_");
    }
}
