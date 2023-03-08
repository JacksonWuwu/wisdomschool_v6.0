package cn.wstom.admin.service;



import cn.wstom.admin.entity.SysDictType;


/**
 * 字典 业务层
 *
 * @author dws
 */
public interface SysDictTypeService extends BaseService<SysDictType> {

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    String checkDictTypeUnique(SysDictType dictType);
}
