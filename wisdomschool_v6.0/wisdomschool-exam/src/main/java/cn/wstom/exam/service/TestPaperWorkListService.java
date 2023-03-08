package cn.wstom.exam.service;





import cn.wstom.exam.entity.TestPaperWorkList;

import java.util.List;

public interface TestPaperWorkListService extends BaseService<TestPaperWorkList> {
    List<TestPaperWorkList> selectList(TestPaperWorkList testPaperOneList) ;
}
