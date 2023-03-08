package cn.wstom.admin.service.impl;



import cn.wstom.admin.entity.SysOperLog;
import cn.wstom.admin.mapper.SysOperLogMapper;
import cn.wstom.admin.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作日志 服务层处理
 *
 * @author dws
 */
@Service
public class SysOperLogServiceImpl extends BaseServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {
    @Autowired
    private SysOperLogMapper operLogMapper;

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        operLogMapper.cleanOperLog();
    }
}
