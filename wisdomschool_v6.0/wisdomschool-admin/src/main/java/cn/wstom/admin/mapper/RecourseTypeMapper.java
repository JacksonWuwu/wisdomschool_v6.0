package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.RecourseType;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源 数据层
 *
 * @author dws
 * @date 20190222
 */
public interface RecourseTypeMapper extends BaseMapper<RecourseType> {
    public List<RecourseType> selectByTCid(@Param("tcid") String tcid);
}
