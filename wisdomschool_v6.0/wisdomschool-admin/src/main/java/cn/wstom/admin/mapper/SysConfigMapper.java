package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.SysConfig;

import java.util.List;


/**
 * 参数配置 数据层
 *
 * @author dws
 */

public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    List<SysConfig> selectConfigList(SysConfig config);

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数配置信息
     */
    SysConfig checkConfigKeyUnique(String configKey);
}
