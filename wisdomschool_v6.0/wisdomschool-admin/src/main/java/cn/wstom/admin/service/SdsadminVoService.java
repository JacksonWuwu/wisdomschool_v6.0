package cn.wstom.admin.service;


import cn.wstom.admin.entity.SdsadminVo;

import java.util.List;

public interface SdsadminVoService extends BaseService<SdsadminVo> {
    List<SdsadminVo> selectBySdsadminVos(SdsadminVo sdsadminVo);
    List<SdsadminVo> selectBySdsadminVo2(SdsadminVo sdsadminVo);
}
