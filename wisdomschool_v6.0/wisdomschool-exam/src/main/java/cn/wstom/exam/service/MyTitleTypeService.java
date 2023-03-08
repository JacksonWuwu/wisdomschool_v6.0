package cn.wstom.exam.service;





import cn.wstom.exam.entity.MyTitleType;

import java.util.List;

/**
* 我的题型 服务层
*
* @author dws
* @date 20190307
*/
public interface MyTitleTypeService extends BaseService<MyTitleType> {
    public List<MyTitleType> findByCid(String id);
    public List<MyTitleType> findByCidAndTid(String cid,String tid);
    /**
     * 查询客观题的题型
     * @return
     */
    List<MyTitleType> findTitleTypeLimit(MyTitleType myTitleType);

}
