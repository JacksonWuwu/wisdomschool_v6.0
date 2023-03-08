package cn.wstom.admin.mapper;

import cn.wstom.admin.entity.SysDictType;

import java.util.List;


/**
 * 字典表 数据层
 *
 * @author dws
 */

public interface SysDictTypeMapper extends BaseMapper<SysDictType> {

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    List<SysDictType> selectDictTypeAll();

    /**
     * 修改字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    int updateDictType(SysDictType dictType);

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    SysDictType checkDictTypeUnique(String dictType);
}
