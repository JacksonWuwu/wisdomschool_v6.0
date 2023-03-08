package cn.wstom.admin.service.impl;


import cn.wstom.admin.entity.SysDictType;
import cn.wstom.admin.mapper.SysDictDataMapper;
import cn.wstom.admin.mapper.SysDictTypeMapper;

import cn.wstom.admin.service.SysDictTypeService;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author dws
 */
@Service
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {
    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 批量删除字典类型
     *
     * @param idList 需要删除的数据
     * @return 结果
     */
    @Override
    public boolean removeByIds(List<? extends Serializable> idList) throws Exception {

        for (Serializable dictId : idList) {
            SysDictType dictType = getById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
                throw new Exception(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
        }

        return retBool(dictTypeMapper.deleteBatchIds(idList));
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public String checkDictTypeUnique(SysDictType dict) {
        if (StringUtil.isNotNull(dict)) {
            SysDictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
            if (StringUtil.isNotNull(dictType) && StringUtil.isNotNull(dictType.getId())
                    && dictType.getId().equals(dict.getId())) {
                return Constants.CHECK_NOT_UNIQUE;
            }
        }
        return Constants.CHECK_UNIQUE;
    }
}
