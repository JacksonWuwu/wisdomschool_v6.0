package cn.wstom.admin.service.impl;


import cn.wstom.admin.entity.SysDictData;
import cn.wstom.admin.mapper.SysDictDataMapper;
import cn.wstom.admin.service.SysDictDataService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 字典 业务层处理
 *
 * @author dws
 */
@Service
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {
    @Autowired
    private SysDictDataMapper dictDataMapper;
}
