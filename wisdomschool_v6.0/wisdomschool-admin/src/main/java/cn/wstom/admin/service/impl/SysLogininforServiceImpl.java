package cn.wstom.admin.service.impl;


import cn.wstom.admin.entity.SysLogininfor;
import cn.wstom.admin.mapper.SysLogininforMapper;
import cn.wstom.admin.service.SysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author dws
 */
@Service
public class SysLogininforServiceImpl extends BaseServiceImpl<SysLogininforMapper, SysLogininfor> implements SysLogininforService {

    @Autowired
    private SysLogininforMapper logininforMapper;

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor() {
        logininforMapper.cleanLogininfor();
    }
}
