package cn.wstom.admin.service.impl;


import cn.wstom.admin.entity.SysConfig;

import cn.wstom.admin.mapper.SysConfigMapper;
import cn.wstom.admin.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数配置 服务层实现
 *
 * @author dws
 */
@Service
public class SysConfigServiceImpl
        extends BaseServiceImpl<SysConfigMapper, SysConfig>
        implements SysConfigService {

    @Autowired
    private SysConfigMapper configMapper;

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数名称
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("configKey", configKey);
        SysConfig config = configMapper.selectOne(params);
        return config == null ? null : config.getConfigKey();
    }

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public boolean checkConfigKeyUnique(SysConfig config) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("configKey", config.getConfigKey());
        return configMapper.checkConfigKeyUnique(config.getConfigKey()) != null;
    }
}
