package cn.wstom.admin.service;




import cn.wstom.admin.entity.Recourse;

import java.util.List;

/**
 * 资源 服务层
 *
 * @author dws
 * @date 20190222
 */
public interface RecourseService extends BaseService<Recourse> {

    Recourse selectByAttrId(String id);

    List<Recourse> getResourcesByType(String tcid, String type);

    List<Recourse> getResources(String studentId);
    List<Recourse> getResourcePpt(String studentId);
    List<Recourse> getResourceVideo(String studentId);
    String selectByRecourse(Recourse recourse);
    int addreturn(Recourse recourse);
}
