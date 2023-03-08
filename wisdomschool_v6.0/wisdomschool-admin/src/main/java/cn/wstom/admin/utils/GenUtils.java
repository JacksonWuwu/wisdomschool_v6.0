package cn.wstom.admin.utils;


import cn.wstom.admin.entity.BaseEntity;
import cn.wstom.admin.entity.SysColumnInfo;
import cn.wstom.admin.entity.SysTableInfo;
import cn.wstom.common.config.Global;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.utils.DateUtils;
import cn.wstom.common.utils.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器 工具类
 *
 * @author dws
 */
public class GenUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenUtils.class);

    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = "main/java/com/wstom";

    /**
     * mybatis空间路径
     */
    private static final String MYBATIS_PATH = "main/resources/mybatis";

    /**
     * html空间路径
     */
    private static final String TEMPLATES_PATH = "main/resources/templates";

    /**
     * 模板文件名
     */
    private static final String TEMPLATES_ENTITY_JAVA = "Entity.java.ftl";
    private static final String TEMPLATES_MAPPER_JAVA = "Mapper.java.ftl";
    private static final String TEMPLATES_SERVICE_JAVA = "Service.java.ftl";
    private static final String TEMPLATES_SERVICE_IMPL_JAVA = "ServiceImpl.java.ftl";
    private static final String TEMPLATES_CONTROLLER_JAVA = "Controller.java.ftl";
    private static final String TEMPLATES_MAPPER_XML = "Mapper.xml.ftl";
    private static final String TEMPLATES_LIST_HTML = "list.html.ftl";
    private static final String TEMPLATES_ADD_HTML = "add.html.ftl";
    private static final String TEMPLATES_EDIT_HTML = "edit.html.ftl";
    private static final String TEMPLATES_SQL = "sql.ftl";

    private static final String PREFIX_TEMPLATE_JAVA_PATH = "template/java/";
    private static final String PREFIX_TEMPLATE_XML_PATH = "template/xml/";
    private static final String PREFIX_TEMPLATE_HTML_PATH = "template/html/";
    private static final String PREFIX_TEMPLATE_SQL_PATH = "template/sql/";

    /**
     * 类型转换
     */
    public static Map<String, String> javaTypeMap = new HashMap<>();

    private static final String BASE_ENTITY = "BaseEntity";

    static {
        javaTypeMap.put("tinyint", "Integer");
        javaTypeMap.put("smallint", "Integer");
        javaTypeMap.put("mediumint", "Integer");
        javaTypeMap.put("int", "Integer");
        javaTypeMap.put("integer", "integer");
        javaTypeMap.put("bigint", "Long");
        javaTypeMap.put("float", "Float");
        javaTypeMap.put("double", "Double");
        javaTypeMap.put("decimal", "BigDecimal");
        javaTypeMap.put("bit", "Boolean");
        javaTypeMap.put("char", "String");
        javaTypeMap.put("varchar", "String");
        javaTypeMap.put("tinytext", "String");
        javaTypeMap.put("text", "String");
        javaTypeMap.put("mediumtext", "String");
        javaTypeMap.put("longtext", "String");
        javaTypeMap.put("time", "Date");
        javaTypeMap.put("date", "Date");
        javaTypeMap.put("datetime", "Date");
        javaTypeMap.put("timestamp", "Date");
    }

    /**
     * 设置列信息
     */
    public static List<SysColumnInfo> transColums(List<SysColumnInfo> columns) {
        // 列信息
        List<SysColumnInfo> columsList = new ArrayList<>();
        for (SysColumnInfo column : columns) {
            // 列名转换成Java属性名
            String attrName = StringUtil.convertToCamelCase(column.getColumnName());
            column.setAttrName(attrName);
            column.setAttrname(StringUtil.uncapitalize(attrName));

            // 列的数据类型，转换成Java类型
            String attrType = javaTypeMap.get(column.getDataType());
            column.setAttrType(attrType);

            columsList.add(column);
        }
        return columsList;
    }

    private static Map<String, Object> getClassAttr(Class<?> c) {
        Map<String, Object> field = null;
        try {
            String name = c.getSimpleName();
            if (c.getSuperclass().getName().contains(BASE_ENTITY)) {
                return null;
            }
            Field[] fields = c.getDeclaredFields();
            field = new HashMap<>(16);
            for (Field f : fields) {
                field.put(f.getName(), f.getType());
            }
        } catch (Exception e) {
            LOGGER.error("获取实体字段异常", e);
        }

        return field;
    }

    private static List filterAttrName(List<SysColumnInfo> source, Map<String, Object> filter) {
        List<SysColumnInfo> target = new ArrayList<>(source);
        if (filter == null) {
            return null;
        }
        List<SysColumnInfo> index = new ArrayList<>(filter.size());
        target.forEach(s -> {
            if (filter.containsKey(s.getAttrname())) {
                index.add(s);
            }
        });
        index.forEach(target::remove);
        return target;
    }

    /**
     * 封装模板数据
     *
     * @return 模板数据
     */
    public static Map<String, Object> wrapTableInfo(SysTableInfo table) {
        Map<String, Object> dataMap = new HashMap<>(9);
        // 封装模板数据
        String packageName = Global.getPackageName();
        dataMap.put("tableName", table.getTableName());
        dataMap.put("tableComment", replaceKeyword(table.getTableComment()));
        dataMap.put("primaryKey", table.getPrimaryKey());
        dataMap.put("className", table.getClassName());
        dataMap.put("classname", table.getClassname());
        dataMap.put("moduleName", GenUtils.getModuleName(packageName));
        dataMap.put("fullColumns", table.getColumns());
        dataMap.put("columns", filterAttrName(table.getColumns(), getClassAttr(BaseEntity.class)));
        dataMap.put("package", packageName);
        dataMap.put("author", Global.getAuthor());
        dataMap.put("datetime", DateUtils.FormatDate.YYYYMMDD.getCurrentDate());

        return dataMap;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplateNames() {
        List<String> templates = new ArrayList<>();
        templates.add(PREFIX_TEMPLATE_JAVA_PATH + TEMPLATES_ENTITY_JAVA);
        templates.add(PREFIX_TEMPLATE_JAVA_PATH + TEMPLATES_MAPPER_JAVA);
        templates.add(PREFIX_TEMPLATE_JAVA_PATH + TEMPLATES_SERVICE_JAVA);
        templates.add(PREFIX_TEMPLATE_JAVA_PATH + TEMPLATES_SERVICE_IMPL_JAVA);
        templates.add(PREFIX_TEMPLATE_JAVA_PATH + TEMPLATES_CONTROLLER_JAVA);
        templates.add(PREFIX_TEMPLATE_XML_PATH + TEMPLATES_MAPPER_XML);
        templates.add(PREFIX_TEMPLATE_HTML_PATH + TEMPLATES_LIST_HTML);
        templates.add(PREFIX_TEMPLATE_HTML_PATH + TEMPLATES_ADD_HTML);
        templates.add(PREFIX_TEMPLATE_HTML_PATH + TEMPLATES_EDIT_HTML);
        templates.add(PREFIX_TEMPLATE_SQL_PATH + TEMPLATES_SQL);
        return templates;
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName) {
        if (Constants.AUTO_REOMVE_PRE.equals(Global.getAutoRemovePre())) {
            tableName = tableName.substring(tableName.indexOf("_") + 1);
        }
        if (StringUtil.isNotEmpty(Global.getTablePrefix())) {
            tableName = tableName.replace(Global.getTablePrefix(), StringUtils.EMPTY);
        }
        return StringUtil.convertToCamelCase(tableName);
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, SysTableInfo table, String moduleName) {
        if (template == null) {
            return null;
        }
        // 小写类名
        String classname = table.getClassname();
        // 大写类名
        String className = table.getClassName();
        String javaPath = PROJECT_PATH + "/" + moduleName + "/";
        String mybatisPath = MYBATIS_PATH + "/" + moduleName + "/" + className;
        String htmlPath = TEMPLATES_PATH + "/" + moduleName + "/" + classname;

        if (template.contains(TEMPLATES_ENTITY_JAVA)) {
            return javaPath + "entity" + "/" + className + ".java";
        }

        if (template.contains(TEMPLATES_MAPPER_JAVA)) {
            return javaPath + "mapper" + "/" + className + "Mapper.java";
        }

        if (template.contains(TEMPLATES_SERVICE_JAVA)) {
            return javaPath + "service" + "/" + className + "Service.java";
        }

        if (template.contains(TEMPLATES_SERVICE_IMPL_JAVA)) {
            return javaPath + "service" + "/" + className + "ServiceImpl.java";
        }

        if (template.contains(TEMPLATES_CONTROLLER_JAVA)) {
            return javaPath + "TestController" + "/" + className + "Controller.java";
        }

        if (template.contains(TEMPLATES_MAPPER_XML)) {
            return mybatisPath + "Mapper.xml";
        }

        if (template.contains(TEMPLATES_LIST_HTML)) {
            return htmlPath + "/" + "list.ftl";
        }
        if (template.contains(TEMPLATES_ADD_HTML)) {
            return htmlPath + "/" + "add.ftl";
        }
        if (template.contains(TEMPLATES_EDIT_HTML)) {
            return htmlPath + "/" + "edit.ftl";
        }
        if (template.contains(TEMPLATES_SQL)) {
            return classname + "Menu.sql";
        }
        return null;
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(Constants.SEPARATOR_DOT);
        int nameLength = packageName.length();
        return StringUtil.substring(packageName, lastIndex + 1, nameLength);
    }

    public static String replaceKeyword(String keyword) {
        String keyName = keyword.replaceAll("(?:表|信息)", StringUtils.EMPTY);
        return keyName;
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.convertToCamelCase("user_name"));
        System.out.println(replaceKeyword("岗位信息表"));
        System.out.println(getModuleName("cn.wstom.system"));
        System.out.println(getClassAttr(BaseEntity.class));
    }
}
