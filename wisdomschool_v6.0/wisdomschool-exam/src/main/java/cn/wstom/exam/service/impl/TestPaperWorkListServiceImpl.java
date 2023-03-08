package cn.wstom.exam.service.impl;

import cn.wstom.exam.entity.TestPaperWorkList;
import cn.wstom.exam.mapper.TestPaperWorkListMapper;
import cn.wstom.exam.service.TestPaperWorkListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestPaperWorkListServiceImpl extends BaseServiceImpl<TestPaperWorkListMapper, TestPaperWorkList> implements TestPaperWorkListService {

    @Resource
    private TestPaperWorkListMapper testPaperWorkListMapper;
    @Override
    public List<TestPaperWorkList> selectList(TestPaperWorkList testPaperOneList) {
        return testPaperWorkListMapper.selectList(testPaperOneList);
    }
}
