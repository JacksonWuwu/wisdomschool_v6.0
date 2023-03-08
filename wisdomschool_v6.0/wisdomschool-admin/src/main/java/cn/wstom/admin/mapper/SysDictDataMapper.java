package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.SysDictData;


/**
 * 字典表 数据层
 *
 * @author dws
 */

public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * 查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据
     */
    int countDictDataByType(String dictType);
}
