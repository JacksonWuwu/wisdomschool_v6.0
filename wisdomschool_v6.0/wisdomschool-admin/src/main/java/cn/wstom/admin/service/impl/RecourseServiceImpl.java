package cn.wstom.admin.service.impl;


import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资源 服务层实现
 *
 * @author dws
 * @date 2019/02/22
 */
@Service
public class RecourseServiceImpl extends BaseServiceImpl<RecourseMapper, Recourse>
        implements RecourseService {

    @Resource
    private RecourseMapper recourseMapper;

    @Transactional
    @Override
    public Recourse selectByAttrId(String id) {
        Recourse recourse = new Recourse();
        recourse.setAttrId(id);
        return recourseMapper.selectList(recourse).get(0);
    }

    @Transactional
    @Override
    public List<Recourse> getResourcesByType(String tcid, String type) {

        if (tcid == null && type == null) {
            throw new RuntimeException();
        }
        Recourse recourse = new Recourse();
        RecourseType recourseType = new RecourseType();
        recourseType.setId(type);
        recourse.setCategory(recourseType);
        recourse.setTcId(tcid);
        List<Recourse> list = recourseMapper.selectList(recourse);
        return list;
    }
    @Transactional
    @Override
    public List<Recourse> getResources(String studentId) {
        List<Recourse> list = recourseMapper.getResources(studentId);
        return list;
    }
    @Transactional
    @Override
    public List<Recourse> getResourcePpt(String studentId) {
        List<Recourse> list = recourseMapper.getResourcePpt(studentId);
        return list;
    }

    @Override
    public List<Recourse> getResourceVideo(String studentId) {
        List<Recourse> list = recourseMapper.getResourceVideo(studentId);
        return list;
    }

    @Override
    public String selectByRecourse(Recourse recourse) {
        return recourseMapper.selectByRecourse(recourse);
    }

    @Override
    public int addreturn(Recourse recourse) {
        recourseMapper.addreturn(recourse);
        int i = Integer.valueOf(recourse.getId());
        return i;
    }
}
