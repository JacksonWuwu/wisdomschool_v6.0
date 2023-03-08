package cn.wstom.admin.service;

import cn.wstom.admin.entity.Adjunct;
import cn.wstom.admin.entity.AdjunctVo;


import java.util.List;


public interface AdjunctService extends BaseService<Adjunct> {

    int addreturn(Adjunct adjunct);
    List<AdjunctVo> listAll(Adjunct adjunct);
}
