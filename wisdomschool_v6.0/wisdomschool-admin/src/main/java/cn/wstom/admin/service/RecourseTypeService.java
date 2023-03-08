package cn.wstom.admin.service;




import cn.wstom.admin.entity.RecourseType;

import java.util.List;

/**
 * 资源 服务层
 *
 * @author dws
 * @date 20190222
 */
public interface RecourseTypeService extends BaseService<RecourseType> {
    public List<RecourseType> findByTCid(String tcid);
}
