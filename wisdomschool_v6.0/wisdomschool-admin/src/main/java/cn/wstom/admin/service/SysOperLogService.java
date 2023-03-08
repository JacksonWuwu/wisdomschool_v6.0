package cn.wstom.admin.service;


import cn.wstom.admin.entity.SysOperLog;


/**
 * 操作日志 服务层
 *
 * @author dws
 */
public interface SysOperLogService extends BaseService<SysOperLog> {

    /**
     * 清空操作日志
     */
    void cleanOperLog();
}
