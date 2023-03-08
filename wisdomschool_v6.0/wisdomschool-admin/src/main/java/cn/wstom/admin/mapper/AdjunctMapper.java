package cn.wstom.admin.mapper;



import cn.wstom.admin.entity.Adjunct;
import cn.wstom.admin.entity.AdjunctVo;

import java.util.List;

public interface AdjunctMapper extends BaseMapper<Adjunct> {
    int addreturn(Adjunct adjunct);
    List<AdjunctVo> listAll(Adjunct adjunct);
}
