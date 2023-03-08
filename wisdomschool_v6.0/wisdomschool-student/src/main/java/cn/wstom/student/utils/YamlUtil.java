package cn.wstom.student.utils;



import cn.wstom.student.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 配置处理工具类
 *
 * @author dws
 */
public class YamlUtil {
    /**
     * 加载配置文件
     *
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public static Map<?, ?> loadYaml(String fileName) throws FileNotFoundException {

        if (StringUtil.isEmpty(fileName)) {
            return null;
        }
        InputStream in = YamlUtil.class.getClassLoader().getResourceAsStream(fileName);
        return new Yaml().<LinkedHashMap<?, ?>>load(in);
    }

    /**
     * 写入配置文件
     *
     * @param fileName
     * @param map
     * @throws IOException
     */
    public static void dumpYaml(String fileName, Map<?, ?> map) throws IOException {
        if (StringUtil.isNotEmpty(fileName)) {
            FileWriter fileWriter = new FileWriter(YamlUtil.class.getResource(fileName).getFile());
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            yaml.dump(map, fileWriter);
        }
    }

    /**
     * 获取属性值
     * @param map
     * @param qualifiedKey
     * @return
     */
    public static Object getProperty(Map<?, ?> map, Object qualifiedKey) {
        if (map != null && !map.isEmpty() && qualifiedKey != null) {
            String input = String.valueOf(qualifiedKey);
            if (!StringUtils.EMPTY.equals(input)) {
                if (input.contains(Constants.SEPARATOR_DOT)) {
                    int index = input.indexOf(Constants.SEPARATOR_DOT);
                    String left = input.substring(0, index);
                    String right = input.substring(index + 1);
                    return getProperty((Map<?, ?>) map.get(left), right);
                } else {
                    return map.getOrDefault(input, null);
                }
            }
        }
        return null;
    }


    /**
     * 获取属性值
     *
     * @param name
     * @param qualifiedKey
     * @return
     */
    public static Object getProperty(String name, Object qualifiedKey) throws FileNotFoundException {
        return YamlUtil.getProperty(YamlUtil.loadYaml(name), qualifiedKey);
    }

    @SuppressWarnings("unchecked")
    public static void setProperty(Map<?, ?> map, Object qualifiedKey, Object value) {
        if (map != null && !map.isEmpty() && qualifiedKey != null) {
            String input = String.valueOf(qualifiedKey);
            if (!StringUtils.EMPTY.equals(input)) {
                if (input.contains(Constants.SEPARATOR_DOT)) {
                    int index = input.indexOf(Constants.SEPARATOR_DOT);
                    String left = input.substring(0, index);
                    String right = input.substring(index + 1);
                    setProperty((Map<?, ?>) map.get(left), right, value);
                } else {
                    ((Map<Object, Object>) map).put(qualifiedKey, value);
                }
            }
        }
    }
}
