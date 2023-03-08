package cn.wstom.admin.service.impl;

import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SdsadminVoServiceImpl extends BaseServiceImpl<SdsadminVoMapper, SdsadminVo> implements SdsadminVoService {

    @Autowired
    private SdsadminVoMapper sdsadminVoMapper;
    @Override
    public List<SdsadminVo> selectBySdsadminVos(SdsadminVo sdsadminVo) {
        return sdsadminVoMapper.selectBySdsadminVo(sdsadminVo);
    }

    @Override
    public List<SdsadminVo> selectBySdsadminVo2(SdsadminVo sdsadminVo) {
        return sdsadminVoMapper.selectBySdsadminVo2(sdsadminVo);
    }


}
