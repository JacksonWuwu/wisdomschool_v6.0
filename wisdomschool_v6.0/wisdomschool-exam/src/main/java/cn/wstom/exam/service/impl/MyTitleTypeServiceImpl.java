package cn.wstom.exam.service.impl;

import cn.wstom.exam.entity.MyTitleType;
import cn.wstom.exam.mapper.MyTitleTypeMapper;
import cn.wstom.exam.service.MyTitleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 我的题型 服务层实现
*
* @author dws
* @date 20190307
*/
@Service
public class MyTitleTypeServiceImpl extends BaseServiceImpl
        <MyTitleTypeMapper, MyTitleType>
implements MyTitleTypeService {

@Autowired
private MyTitleTypeMapper myTitleTypeMapper;

@Override
public List<MyTitleType> findByCid(String id){
    return myTitleTypeMapper.selectByCId(id);

}
    @Override
    public List<MyTitleType> findByCidAndTid(String cid, String tid){
    return myTitleTypeMapper.selectByCIdAndTId(cid ,tid);
    }
    @Override
    public List<MyTitleType> findTitleTypeLimit(MyTitleType myTitleType) {
        return myTitleTypeMapper.findTitleTypeLimit(myTitleType);
    }


}
