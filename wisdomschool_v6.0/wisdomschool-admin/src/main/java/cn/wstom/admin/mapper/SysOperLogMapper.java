package cn.wstom.admin.mapper;



import cn.wstom.admin.entity.SysOperLog;

/**
 * 操作日志 数据层
 *
 * @author dws
 */

public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

    /**
     * 清空操作日志
     */
    void cleanOperLog();
}
