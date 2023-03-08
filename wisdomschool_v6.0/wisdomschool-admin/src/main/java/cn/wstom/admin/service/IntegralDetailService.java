package cn.wstom.admin.service;

import cn.wstom.admin.entity.IntegralDetail;
import cn.wstom.admin.entity.SysUser;


import java.util.List;

/**
 * @author dws
 * @date 2019/03/25
 */
public interface IntegralDetailService extends BaseService<IntegralDetail> {
    int handleCedit(SysUser user, String ruleCode, String content) throws Exception;

    List<IntegralDetail> selectBatchIntegral(List<String> ids);

    List<IntegralDetail> selectBatchIntegral();
}
