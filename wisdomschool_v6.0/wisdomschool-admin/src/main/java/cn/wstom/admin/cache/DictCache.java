package cn.wstom.admin.cache;

import cn.wstom.admin.entity.SysDictData;
import cn.wstom.admin.mapper.SysDictDataMapper;
import cn.wstom.admin.service.Cache;
import cn.wstom.common.utils.SpringUtils;
import cn.wstom.common.utils.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dws
 * @date 2019/01/30
 */
public class DictCache implements Cache<CacheItem<LinkedHashMap<String, SysDictData>>> {
    private static final long serialVersionUID = 5699557380998936242L;

    private static final Logger LOGGER = LoggerFactory.getLogger(DictCache.class);

    private static Cache<CacheItem<LinkedHashMap<String, SysDictData>>> dictCache;

    private static HashMap<String, CacheItem<LinkedHashMap<String, SysDictData>>> cache;

    private SysDictDataMapper dictDataMapper;

    private DictCache() {
        cache = new HashMap<>();
        dictDataMapper = SpringUtils.getBean(SysDictDataMapper.class);
    }

    public static synchronized Cache<CacheItem<LinkedHashMap<String, SysDictData>>> getInstance() {
        if (dictCache == null) {
            synchronized (DictCache.class) {
                if (dictCache == null) {
                    dictCache = new DictCache();
                }
            }
        }
        return dictCache;
    }

    @Override
    public String getCacheName() {
        return CacheCell.CACHE_DICT.name;
    }

    @Override
    public void refreshAll() throws Exception {
        this.getAllDict();
    }

    @Override
    public CacheItem<LinkedHashMap<String, SysDictData>> getItem(String key) throws Exception {
        CacheItem<LinkedHashMap<String, SysDictData>> item = cache.get(key);
        return item == null ? this.refreshByKey(key) : item;
    }


    @Override
    public void setItem(String key, CacheItem<LinkedHashMap<String, SysDictData>> value) throws Exception {
        if (StringUtils.EMPTY.equals(StringUtil.trimToEmpty(key))) {
            LOGGER.error("key must not null");
        } else {
            if (ConfigUtils.isCacheSwitch(this.getCacheName())) {
                cache.put(key, value);
            }
        }
    }

    @Override
    public CacheItem<LinkedHashMap<String, SysDictData>> refreshByKey(String key) throws Exception {

        try {
            //获取指定的字典值
            SysDictData dictData = new SysDictData();
            dictData.setDictType(key);
            List<SysDictData> dictDataList = dictDataMapper.selectList(dictData);

            LinkedHashMap<String, SysDictData> item = new LinkedHashMap<>();
            dictDataList.forEach(d -> item.put(d.getDictValue(), d));

            CacheItem<LinkedHashMap<String, SysDictData>> tmp = null;
            if (!item.isEmpty()) {
                tmp = new CacheItem<>();
                tmp.setItem(item);
                tmp.setCode(key);
                if (ConfigUtils.isCacheSwitch(this.getCacheName())) {
                    cache.put(key, tmp);
                }
            }

            return tmp;
        } catch (Exception e) {
            StringBuilder msg = new StringBuilder("获取【").append(this.getCacheName()).append("】异常，").append(e.getMessage());
            LOGGER.error(msg.toString(), e);
            throw new Exception(msg.toString(), e);
        }
    }

    /**
     * 获取所有的数据字典并缓存
     *
     * @return 获取的数据字典
     * @throws Exception
     */
    private Map<String, CacheItem<LinkedHashMap<String, SysDictData>>> getAllDict() throws Exception {
        try {
            List<SysDictData> dictDatas = dictDataMapper.selectList(null);
            //存放临时字典值
            HashMap<String, CacheItem<LinkedHashMap<String, SysDictData>>> tmp = new HashMap<>(dictDatas.size());

            boolean isCache = ConfigUtils.isCacheSwitch(this.getCacheName());

            //获取所有字典
            dictDatas.forEach(d -> {
                String key = d.getDictType();
                String value = d.getDictValue();

                CacheItem<LinkedHashMap<String, SysDictData>> cacheItem = tmp.get(key);
                //若字典值不存在临时变量中，则添加数据并缓存
                if (cacheItem == null) {
                    cacheItem = new CacheItem<>();
                    LinkedHashMap<String, SysDictData> item = new LinkedHashMap<>();

                    item.put(value, d);

                    cacheItem.setItem(item);
                    cacheItem.setCode(key);

                    tmp.put(key, cacheItem);
                    if (isCache) {
                        cache.put(key, cacheItem);
                    }
                } else {
                    LinkedHashMap<String, SysDictData> item = cacheItem.getItem();
                    item.putIfAbsent(value, d);
                }
            });
            return tmp;
        } catch (Exception e) {
            StringBuilder msg = new StringBuilder("获取所有字典异常：").append(this.getCacheName()).append(e.getMessage());
            LOGGER.error(msg.toString(), e);
            throw new Exception(msg.toString(), e);
        }
    }

    @Override
    public Map<?, ?> getAllItem() throws Exception {
        return ConfigUtils.isCacheSwitch(this.getCacheName()) ? cache : this.getAllDict();
    }
}
