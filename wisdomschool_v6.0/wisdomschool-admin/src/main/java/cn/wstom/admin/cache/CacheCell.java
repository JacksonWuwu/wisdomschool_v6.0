package cn.wstom.admin.cache;

/**
 * 缓存枚举
 *
 * @author dws
 * @date 2019/01/30
 */
public enum CacheCell {
    /**
     * 系统配置参数缓存
     */
    /*CACHE_SYS("SysConfCache", "系统参数", null),*/
    /**
     * 数据字典缓存
     */
    CACHE_DICT("DictCache", "数据字典", DictCache.class);

    /**
     * 缓存名称
     */
    public final String name;
    /**
     * 缓存描述
     */
    public final String desc;
    /**
     * 缓存实现类
     */
    public final Class clzz;

    CacheCell(String name, String desc, Class clazz) {
        this.name = name;
        this.desc = desc;
        this.clzz = clazz;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
