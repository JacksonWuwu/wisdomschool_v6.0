package cn.wstom.exam.mapper;



import cn.wstom.exam.entity.UserPaperWork;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 测试关联 数据层
 *
 * @author lzj
 * @date 20190223
 */
public interface UserPaperWorkMapper extends BaseMapper<UserPaperWork> {
    UserPaperWork selectByExamId(String id);
    /**
     * 查找年级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserPaperWork> getStudentInfo(@Param("createId") String createId, @Param("tcoId") String tcoId);

    /**
     * 获取班级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserPaperWork> getTcoName(@Param("createId") String createId, @Param("tcoId") String tcoId);


    /**
     * 获取班级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserPaperWork> getTcoName2(@Param("createId") String createId, @Param("tcoId") String tcoId, @Param("tgId") String tgId);

    /**
     * 获取学生
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserPaperWork> getTcoStu(@Param("createId") String createId, @Param("tcoId") String tcoId);



    /**
     * 查找年级 期末
     *
     * @param tcoId
     * @return
     */
    List<UserPaperWork> getQStudentInfo(@Param("tcoId") String tcoId);

    /**
     * 获取科目 期末
     *
     * @param tcoId
     * @return
     */
    List<UserPaperWork> getQTcoName(@Param("tcoId") String tcoId);

    /**
     * 获取学生 期末
     *
     * @param tcoId
     * @return
     */
    List<UserPaperWork> getQTcoStu(@Param("tcoId") String tcoId);

    /**
     * 试卷 期末
     *
     * @param userTest
     * @return
     */
    public List<UserPaperWork> findStuExamPaper(UserPaperWork userTest);
    public List<UserPaperWork> findStuExamPaperOne(UserPaperWork userTest);
    public List<UserPaperWork> findStuExamPaperTwo(UserPaperWork userTest);

    /**
     * 获取期末试卷
     *
     * @return
     */
    List<UserPaperWork>  getTcoExamPaper(UserPaperWork userTest);
    UserPaperWork selectUserExam(@Param("testPaperId") String testPaperId, @Param("userId") String userId);
    List<UserPaperWork> selectListBase(UserPaperWork userTest);


    public void deleteByPaperWorkId(String id);

    /**
     * <p>
     * 更新记录
     * </p>
     *
     * @param entity 实体对象
     * @return 影响行数
     */
    @Override
    int update(UserPaperWork entity);

    /**
     * 更新学生作业分数
     * @return
     */

    int updatePaperWorkScore(UserPaperWork userPaperWork);
}
