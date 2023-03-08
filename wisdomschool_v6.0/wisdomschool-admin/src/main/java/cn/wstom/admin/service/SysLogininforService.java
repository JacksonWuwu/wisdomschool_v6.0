package cn.wstom.admin.service;



import cn.wstom.admin.entity.SysLogininfor;


/**
 * 系统访问日志情况信息 服务层
 *
 * @author dws
 */
public interface SysLogininforService extends BaseService<SysLogininfor> {

    /**
     * 清空系统登录日志
     */
    void cleanLogininfor();
}
