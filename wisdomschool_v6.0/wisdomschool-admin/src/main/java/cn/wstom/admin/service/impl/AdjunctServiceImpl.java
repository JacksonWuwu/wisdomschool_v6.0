package cn.wstom.admin.service.impl;

import cn.wstom.admin.entity.Adjunct;

import cn.wstom.admin.entity.AdjunctVo;
import cn.wstom.admin.mapper.AdjunctMapper;
import cn.wstom.admin.service.AdjunctService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdjunctServiceImpl extends BaseServiceImpl<AdjunctMapper, Adjunct>
        implements AdjunctService {

    @Autowired
    private AdjunctMapper adjunctMapper;
    @Override
    public int addreturn(Adjunct adjunct) {
        adjunctMapper.addreturn(adjunct);
        int i = Integer.valueOf(adjunct.getId());
        return i;
    }

    @Override
    public List<AdjunctVo> listAll(Adjunct adjunct) {
        return adjunctMapper.listAll(adjunct);
    }
}
