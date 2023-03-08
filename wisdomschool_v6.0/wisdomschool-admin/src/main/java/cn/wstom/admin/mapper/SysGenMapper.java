package cn.wstom.admin.mapper;




import cn.wstom.admin.entity.SysColumnInfo;
import cn.wstom.admin.entity.SysTableInfo;

import java.util.List;

/**
 * 代码生成 数据层
 *
 * @author dws
 */

public interface SysGenMapper {
    /**
     * 查询数据库表信息
     *
     * @param tableInfo 表信息
     * @return 数据库表列表
     */
    List<SysTableInfo> selectTableList(SysTableInfo tableInfo);

    /**
     * 根据表名称查询信息
     *
     * @param tableName 表名称
     * @return 表信息
     */
    SysTableInfo selectTableByName(String tableName);

    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    List<SysColumnInfo> selectTableColumnsByName(String tableName);
}
