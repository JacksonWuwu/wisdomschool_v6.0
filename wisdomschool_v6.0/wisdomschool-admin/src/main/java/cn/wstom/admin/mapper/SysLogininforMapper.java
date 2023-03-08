package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.SysLogininfor;

/**
 * 系统访问日志情况信息 数据层
 *
 * @author dws
 */
public interface SysLogininforMapper extends BaseMapper<SysLogininfor> {

    /**
     * 清空系统登录日志
     */
    int cleanLogininfor();
}
