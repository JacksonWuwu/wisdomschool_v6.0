package cn.wstom.admin.mapper;

import cn.wstom.admin.entity.IntegralDetail;


import java.util.List;

/**
 * @author dws
 * @date 2019/03/25
 */
public interface IntegralDetailMapper extends BaseMapper<IntegralDetail> {

    /**
     * 根据指定的id获取积分排行
     *
     * @param ids
     * @return
     */
    List<IntegralDetail> selectBatchIntegral(List<String> ids);

    List<IntegralDetail> selectWeekIntegral();
}
