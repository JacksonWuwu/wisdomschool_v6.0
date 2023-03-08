package cn.wstom.admin.service.impl;


import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资源 服务层实现
 *
 * @author dws
 * @date 2019/02/22
 */
@Service
public class RecourseTypeServiceImpl extends BaseServiceImpl<RecourseTypeMapper, RecourseType>
        implements RecourseTypeService {

    @Resource
    private RecourseTypeMapper recourseTypeMapper;
    public List<RecourseType> findByTCid(String tcid){
        return recourseTypeMapper.selectByTCid(tcid);
    }
}
