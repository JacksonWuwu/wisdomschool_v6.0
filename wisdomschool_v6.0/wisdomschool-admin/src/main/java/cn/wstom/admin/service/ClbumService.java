package cn.wstom.admin.service;

import cn.wstom.admin.entity.Clbum;


import java.util.List;

/**
 * 班级 服务层
 *
 * @author dws
 * @date 2019-01-25
 */
public interface ClbumService extends BaseService<Clbum> {
    /*
     * 根据tcid查询班级
     * */
    List<Clbum> selectBytcid(String tcid);
    List<Clbum> selectBytid(String tid);
}
