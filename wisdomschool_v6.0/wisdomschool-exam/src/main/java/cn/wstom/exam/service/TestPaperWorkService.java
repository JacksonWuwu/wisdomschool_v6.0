package cn.wstom.exam.service;



import cn.wstom.exam.entity.SysUser;
import cn.wstom.exam.entity.TestPaperWork;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 试卷 服务层
 *
 * @author hzh
 * @date 20190223
 */
public interface TestPaperWorkService extends BaseService<TestPaperWork> {

    List<TestPaperWork> selectPaper(TestPaperWork testPaperWork);

    boolean updateWithoutCheckType(TestPaperWork entity) throws Exception;
    /**
     * 校验标题名唯一
     *
     * @param testPaperWork
     * @return
     */
    String checkTestNameUnique(TestPaperWork testPaperWork);
    boolean updateSetExam(TestPaperWork testPaperWork);

    /**
     * 查找年级
     *
     * @param teacherId
     * @return
     */
    List<TestPaperWork> getStudentInfo(@Param("teacherId") String teacherId);

    /**
     * 获取科目
     *
     * @param teacherId
     * @return
     */
    List<TestPaperWork> getTcoName(@Param("courseId") String teacherId);

    /**
     * 获取学生
     *
     * @param teacherId
     * @return
     */
    List<TestPaperWork> getTcoStu(@Param("teacherId") String teacherId);


    /**
     * 查找学生试卷
     * @param testPaperWork
     * @return
     */
    List<TestPaperWork> findStuPaper(TestPaperWork testPaperWork);


    /**
     * 获取作业资源和相关班级的目录
     * @return
     */
    List<Map<String, Object>> getTestPagerAndClbumTree(SysUser user, String cId, String pageType);


    public TestPaperWork selectOne(TestPaperWork testPaperWork);

}
