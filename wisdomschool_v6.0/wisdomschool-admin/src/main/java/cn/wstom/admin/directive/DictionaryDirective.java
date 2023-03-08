package cn.wstom.admin.directive;


import cn.wstom.admin.cache.CacheCell;
import cn.wstom.admin.cache.CacheItem;
import cn.wstom.admin.cache.CacheManager;
import cn.wstom.admin.cache.Environment;
import cn.wstom.admin.entity.SysDictData;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据字典指令
 *
 * @author dws
 * @date 2018/12/22
 */
@Component
public class DictionaryDirective extends BaseTemplateDirective {



	@Override
	public String getName() {
		return "dictionary";
	}

	@Override
    public void execute(DirectiveHandler handler) throws Exception {
		String dictType = handler.getString("dictType");
        CacheManager cacheManager = (CacheManager) Environment.getCacheManager();
        Map<String, ?> item = cacheManager.getAllItem(CacheCell.CACHE_DICT);
        CacheItem<LinkedHashMap<String, SysDictData>> dictData =
                (CacheItem<LinkedHashMap<String, SysDictData>>) item.get(dictType);
        handler.put("dictData", dictData.getItem().values()).render();
	}
}
