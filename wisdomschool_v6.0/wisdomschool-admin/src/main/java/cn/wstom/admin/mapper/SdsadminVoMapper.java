package cn.wstom.admin.mapper;
import cn.wstom.admin.entity.SdsadminVo;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SdsadminVoMapper extends BaseMapper<SdsadminVo> {
    List<SdsadminVo> selectBySdsadminVo(SdsadminVo sdsadminVo);
    List<SdsadminVo> selectBySdsadminVo2(SdsadminVo sdsadminVo);
}
