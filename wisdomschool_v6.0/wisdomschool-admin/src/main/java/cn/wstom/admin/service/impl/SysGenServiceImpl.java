package cn.wstom.admin.service.impl;

import cn.wstom.admin.entity.SysColumnInfo;
import cn.wstom.admin.entity.SysTableInfo;
import cn.wstom.admin.mapper.SysGenMapper;
import cn.wstom.admin.service.SysGenService;
import cn.wstom.admin.utils.FreemarkerUtils;
import cn.wstom.admin.utils.GenUtils;
import cn.wstom.common.config.Global;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.utils.StringUtil;

import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成 服务层处理
 *
 * @author dws
 */
@Service
public class SysGenServiceImpl implements SysGenService {
    private static final Logger log = LoggerFactory.getLogger(SysGenServiceImpl.class);

    @Resource
    private SysGenMapper genMapper;
    @Autowired
    private FreemarkerUtils freemarkerUtils;

    /**
     * 查询ry数据库表信息
     *
     * @param tableInfo 表信息
     * @return 数据库表列表
     */
    @Override
    public List<SysTableInfo> selectTableList(SysTableInfo tableInfo) {
        return genMapper.selectTableList(tableInfo);
    }

    /**
     * 生成代码
     *
     * @param tableName 表名称
     * @return 数据
     */
    @Override
    public byte[] generatorCode(String tableName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        // 查询表信息
        SysTableInfo table = genMapper.selectTableByName(tableName);
        // 查询列信息
        List<SysColumnInfo> columns = genMapper.selectTableColumnsByName(tableName);
        // 生成代码
        generatorCode(table, columns, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 批量生成代码
     *
     * @param tableNames 表数组
     * @return 数据
     */
    @Override
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            // 查询表信息
            SysTableInfo table = genMapper.selectTableByName(tableName);
            // 查询列信息
            List<SysColumnInfo> columns = genMapper.selectTableColumnsByName(tableName);
            // 生成代码
            generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 生成代码
     */
    public void generatorCode(SysTableInfo table, List<SysColumnInfo> columns, ZipOutputStream zip) {
        // 表名转换成Java属性名
        String className = GenUtils.tableToJava(table.getTableName());
        table.setClassName(className);
        table.setClassname(StringUtil.uncapitalize(className));
        // 列信息
        table.setColumns(GenUtils.transColums(columns));
        // 设置主键
        table.setPrimaryKey(table.getColumnsLast());

        String packageName = Global.getPackageName();
        String moduleName = GenUtils.getModuleName(packageName);

        Map<String, Object> context = GenUtils.wrapTableInfo(table);

        // 获取模板列表
        List<String> templateNames = GenUtils.getTemplateNames();
        for (String templateName : templateNames) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            try {
                Template template = freemarkerUtils.getTemplate(templateName, Constants.UTF8);
                template.process(context, sw);
                // 添加到zip
                zip.putNextEntry(new ZipEntry(GenUtils.getFileName(templateName, table, moduleName)));
                IOUtils.write(sw.toString(), zip, Constants.UTF8);
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (Exception e) {
                log.error("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }

    }
}
