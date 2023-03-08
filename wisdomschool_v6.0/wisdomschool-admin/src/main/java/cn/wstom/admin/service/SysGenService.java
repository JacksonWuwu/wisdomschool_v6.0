package cn.wstom.admin.service;




import cn.wstom.admin.entity.SysTableInfo;

import java.util.List;

/**
 * 代码生成 服务层
 *
 * @author dws
 */
public interface SysGenService {
    /**
     * 查询数据库表信息
     *
     * @param tableInfo 表信息
     * @return 数据库表列表
     */
    List<SysTableInfo> selectTableList(SysTableInfo tableInfo);

    /**
     * 生成代码
     *
     * @param tableName 表名称
     * @return 数据
     */
    byte[] generatorCode(String tableName);

    /**
     * 批量生成代码
     *
     * @param tableNames 表数组
     * @return 数据
     */
    byte[] generatorCode(String[] tableNames);
}
