package cn.wstom.exam.service;


import cn.wstom.exam.entity.SysUser;
import cn.wstom.exam.entity.TestPaper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 试卷 服务层
 *
 * @author hzh
 * @date 20190223
 */
public interface TestPaperService extends BaseService<TestPaper> {

    boolean updateWithoutCheckType(TestPaper entity) throws Exception;
    /**
     * 校验标题名唯一
     *
     * @param testPaper
     * @return
     */
    String checkTestNameUnique(TestPaper testPaper);

    /**
     * 查找年级
     *
     * @param teacherId
     * @return
     */
    List<TestPaper> getStudentInfo(@Param("teacherId") String teacherId);

    /**
     * 获取科目
     *
     * @param teacherId
     * @return
     */
    List<TestPaper> getTcoName(@Param("teacherId") String teacherId);

    /**
     * 获取学生
     *
     * @param teacherId
     * @return
     */
    List<TestPaper> getTcoStu(@Param("teacherId") String teacherId);


    /**
     * 查找学生试卷
     * @param testPaper
     * @return
     */
    List<TestPaper> findStuPaper(TestPaper testPaper);


    /**
     * 获取作业资源和相关班级的目录
     * @return
     */
    List<Map<String, Object>> getTestPagerAndClbumTree(SysUser user, String cId, String pageType);
}
