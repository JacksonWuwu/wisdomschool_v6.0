package cn.wstom.admin.service;



import cn.wstom.admin.entity.SysConfig;


/**
 * 参数配置 服务层
 *
 * @author dws
 */
public interface SysConfigService extends BaseService<SysConfig> {

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    String selectConfigByKey(String configKey);

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数信息
     * @return 结果
     */
    boolean checkConfigKeyUnique(SysConfig config);
}
