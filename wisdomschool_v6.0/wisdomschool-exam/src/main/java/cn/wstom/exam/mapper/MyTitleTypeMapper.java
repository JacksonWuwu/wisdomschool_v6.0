package cn.wstom.exam.mapper;




import cn.wstom.exam.entity.MyTitleType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 我的题型 数据层
*
* @author dws
* @date 20190307
*/
public interface MyTitleTypeMapper extends BaseMapper<MyTitleType> {
    public List<MyTitleType> selectByCId(String id);
public List<MyTitleType>selectByCIdAndTId(@Param("cid") String cid,@Param("tid") String tid);
     /**
     * 查询客观题的题型
     * @return
     */
    List<MyTitleType> findTitleTypeLimit(MyTitleType myTitleType);
}
